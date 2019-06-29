package com.java.demomp.plan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.java.demomp.admin.entity.User;
import com.java.demomp.plan.entity.Dict;
import com.java.demomp.plan.entity.DictParent;
import com.java.demomp.plan.entity.Plan;
import com.java.demomp.plan.mapper.PlanMapper;
import com.java.demomp.plan.service.DictParentService;
import com.java.demomp.plan.service.DictService;
import com.java.demomp.plan.service.PlanService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.demomp.util.PlanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-06-17
 */
@Service
@Transactional
public class PlanServiceImpl extends ServiceImpl<PlanMapper, Plan> implements PlanService {

    @Autowired
    HttpSession session;

    @Autowired
    DictParentService dictParentService;

    @Autowired
    DictService dictService;

    @Autowired
    RedisTemplate redisTemplate;

    // redis存储的最大时间
    static final Integer REDIS_MAX_TIME = 7;


    /**
     * 添加计划
     *
     * @param plan
     * @return
     */
    public Integer addPlan(Plan plan) {
        Integer redisUserId = getRedisUserId();


        // 1.正常的添加
        // 临时添加，查找父类，添加一个和父类一样的颜色
        // 如果是最大的父类，就直接给他添加一个颜色，如果是子类，就用父类的颜色
        if (plan.getParentId() == null) {
            // 表示是最大的父类
            // 获取所有的颜色列表
            DictParent dictParent = dictParentService.getOne(new QueryWrapper<DictParent>().eq("code", "color"));
            List<Dict> dictList = new ArrayList<>();

            if (dictParent != null) {
                // 1 2 3 4 6
                dictList = dictService.getBaseMapper().selectList(new QueryWrapper<Dict>().eq("parent_id", dictParent.getId()));
            }

            // 获取已选颜色列表,父类的列表
            List<Plan> planList = baseMapper.selectList(new QueryWrapper<Plan>().eq("type", 4).eq("user_id",redisUserId));
            // 获取所有有颜色的父类列表
            List<String> colorList = new ArrayList<>();
            for (int i = 0; i < planList.size(); i++) {
                // 1 2 3f
                String color = planList.get(i).getColor();
                if (color != null) {
                    colorList.add(color);
                }
            }
            // 如果还没有填写任何颜色
            //找出一个不同的颜色
            for (int i = 0; i < dictList.size(); i++) {
                String resultColor = dictList.get(i).getName();
                // 如果年计划中没有颜色，就选中了第一个
                if (colorList.size() == 0) {
                    plan.setColor(resultColor);
                    break;
                } else {
                    // 年计划中有了颜色
                    if (!colorList.contains(resultColor)) {
                        plan.setColor(resultColor);
                        break;
                    }
                }

            }

        } else {
            // 如果有父亲，就用父亲的颜色
            Plan parentPlan = baseMapper.selectById(plan.getParentId());
            plan.setColor(parentPlan.getColor());
        }

        plan.setUserId(redisUserId);
        int insert = baseMapper.insert(plan);

        // 删除redis
        handleRedis();

        // 2.改变父类的百分比
        // 1.日  -> 周、月、年

        // 如果添加的是年计划，就不用更新了
        Integer parentId = plan.getParentId();
        while (parentId != null) {
            parentId = updateFatherByParentId(parentId);
        }
        return insert;

    }

    // 暂时没有service调用
    public List<Plan> getPlanByType(Integer type) {
        Integer redisUserId = getRedisUserId();
        return baseMapper.selectList(new QueryWrapper<Plan>().eq("type", type).eq("user_id",redisUserId).orderByDesc("top", "rank"));
    }

    // 获得分组Plan
    public Map<String, List<Plan>> getGroupPlan() {
        Integer redisUserId = getRedisUserId();

        // 处理redis

        if(redisTemplate.opsForValue().get("groupPlanList"+"_"+redisUserId) == null){
            Map<String, List<Plan>> map = new HashMap<>();
            map.put("todayPlan",getPlanByType(1));
            map.put("weekPlan", getPlanByType(2));
            map.put("monthPlan", getPlanByType(3));
            map.put("yearPlan", getPlanByType(4));
            redisTemplate.opsForValue().set("groupPlanList"+"_"+redisUserId,map,REDIS_MAX_TIME,TimeUnit.DAYS);
            return map;
        }else {
            return (Map<String, List<Plan>>) redisTemplate.opsForValue().get("groupPlanList"+"_"+redisUserId);
        }
        // 结束处理redis


    }

    public Integer updatePlanFinishedById(Plan plan) {
        int finished = plan.getFinished() == 1 ? 0 : 1;
        Double percent = finished == 1 ? 100.0 : 0;
        //1、 更新自己的状态
        int updateNum = baseMapper.update(new Plan().setFinished(finished).setPercent(percent), new UpdateWrapper<Plan>().eq("id", plan.getId()));

        Integer parentId = plan.getParentId();
        // 遍历更新上面的节点
        while (parentId != null) {
            parentId = updateFatherByParentId(parentId);
        }

        // 删除redis
        handleRedis();

        return updateNum;
    }

    // 更新计划
    public Integer updatePlan(Plan plan) {
        // 二话不说，先找出新的父类
        Integer newParentId = plan.getParentId();
        Integer beforeParentId = plan.selectById().getParentId();

        //  通过parentId找到他的父亲   1.如果有父亲，就把颜色设置成父亲的颜色   ，如果自己就是最大的父亲，日常更新就行了
        Plan parentEntity = plan.selectById(newParentId);
        if(parentEntity!=null){
            plan.setColor(plan.selectById(newParentId).getColor());
        }
        int i = baseMapper.updateById(plan);
        // 如果有父类，并且我也更新了父类
        if (newParentId != null && newParentId != beforeParentId) {
            // 如果改变了父类
            // 1.更新原来的父类进度（其实相当于删除了）
            while (beforeParentId != null) {
                beforeParentId = updateFatherByParentId(beforeParentId);
            }
            //  2.更新新的父类的进度
            while (newParentId != null) {
                newParentId = updateFatherByParentId(newParentId);
            }
        }

        // 删除redis
        handleRedis();


        return i;
    }

    // 通过id删除
    public Integer deletePlanById(Integer id) {
        Integer redisUserId = getRedisUserId();
        if(id == 118 || id == 119 || id == 120){
            return 0;
        }

        // 首先获得父id
        Integer parentId = baseMapper.selectById(id).getParentId();

        // 二话不说，先删了再说(刀下留人，如果删了父亲节点，儿子没归宿了)
        // 所以要先获得孩子节点，有孩子，不能删除
        List<Plan> children = baseMapper.selectList(new QueryWrapper<Plan>().eq("parent_id", id).eq("user_id",redisUserId));
        if(children != null  && children.size() > 0){
            return 0;
        }
        // 确保没有孩子才能删除
        int i = baseMapper.deleteById(id);
        while (parentId != null) {
            parentId = updateFatherByParentId(parentId);
        }

        // 删除redis
        handleRedis();

        return i;
    }

    // 获得树列表，所有的
    public List<Object> getTreeList() {
        Integer redisUserId = getRedisUserId();


        String testString = "treeListAll"+"_"+redisUserId;

        if(redisTemplate.opsForValue().get(testString) == null){
            List<Plan> planList = baseMapper.selectList(new QueryWrapper<Plan>());
            List<Object> objectList = new ArrayList<>();
            if(planList != null && planList.size() >0 ){
                for(int i=0;i<planList.size();i++){
                    Plan plan = planList.get(i);
                    Map<String ,Object> map = new HashMap<>();
                    map.put("key",plan.getId()+"");
                    map.put("title",plan.getName());
                    objectList.add(map);
                }
            }
            redisTemplate.opsForValue().set("treeListAll"+"_"+redisUserId,objectList,REDIS_MAX_TIME,TimeUnit.DAYS);
            return objectList;
        }else {
          return (List<Object>) redisTemplate.opsForValue().get("treeListAll"+"_"+redisUserId);
        }
    }

    // 用于controller的方法，获得树()
    public List<Object> getTreeList(Integer parentId){
        Integer redisUserId = getRedisUserId();


        QueryWrapper<Plan> query = new QueryWrapper<>();
        if(parentId == null){
           query = new QueryWrapper<Plan>().isNull("parent_id").eq("user_id",redisUserId);
        }else{
            query = new QueryWrapper<Plan>().eq("parent_id",parentId).eq("user_id",redisUserId);
        }

        Object redisTreeList = redisTemplate.opsForValue().get("treeList"+"_"+redisUserId);
        if(redisTreeList == null){
            List<Object> treeList = getTreeListMethod(parentId, baseMapper.selectList(query));
            redisTemplate.opsForValue().set("treeList"+"_"+redisUserId,treeList,REDIS_MAX_TIME,TimeUnit.DAYS);
            return treeList;
        }else {
            List<Object> treeList = (List<Object>) redisTemplate.opsForValue().get("treeList"+"_"+redisUserId);
            return treeList;
        }

    }


    // 获得树    首先传入要找的父节点   和通过这个parentId查找到的list
    public  List<Object> getTreeListMethod(Integer parentId,List<Plan> listPlan) {
        Integer redisUserId = getRedisUserId();

        List<Object > totalList = new ArrayList<>();
        if(listPlan != null && listPlan.size()> 0){
            // 对每一个父亲
            for(int i=0;i<listPlan.size();i++){
                Plan plan = listPlan.get(i);
                // 父亲还是实体
                Map<String,Object> map = new HashMap<>();
                map.put("key",plan.getId()+"");
                map.put("title",plan.getName());
                // 下面2个是非必要属性
                map.put("color",plan.getColor());
                map.put("percent",plan.getPercent());
                map.put("rank", PlanUtil.getRankStrByRank(plan.getRank()));
                // 如果有儿子
                List<Plan> children = baseMapper.selectList(new QueryWrapper<Plan>().eq("parent_id", plan.getId()).eq("user_id",redisUserId));
                if(children!= null && children.size()>0){
                    List<Object> list = getTreeListMethod(plan.getId(),children);
                    map.put("children",list);
                }
                totalList.add(map);
            }
        }

       return totalList;

    }

// 传入要递归的集合，和父节点
//    private List<Map<String, Object>> getLevelData(List<Map<String, Object>> dbList, Integer parentcode) {
//        List<Map<String, Object>> resultList = new ArrayList<>();
//        for (Map<String, Object> data : dbList) {
//            if (data.get("parentcode") == parentcode) {
//                List<Map<String, Object>> childList = getLevelData(dbList, (Integer)data.get("code"));
//                data.put("children", childList);
//                resultList.add(data);
//            }
//        }
//        return resultList;
//    }



    /**
     * 传入父id
     *
     * @param parentId
     * @return
     */
    // 抽取一个公共发放，通过父id更新父亲的百分比状态并 返回那个父亲的父id
    public Integer updateFatherByParentId(Integer parentId) {   // 55

        Integer redisUserId = getRedisUserId();
        // 1.通过父id获取父亲实体
        Plan plan = baseMapper.selectById(parentId);
        // 2.通过父节点获取完成的数量和总数量
        // 如果修改后，那个节点下没有儿子了,就要把那个父节点的百分比设置为0
        Integer countTotal = baseMapper.selectCount(new QueryWrapper<Plan>().eq("parent_id", parentId).eq("user_id",redisUserId));
        Double percentFinished = baseMapper.selectSumPercent(parentId,redisUserId); // 选出百分比
        // 3.更新父亲百分比
        Double percent = 0.00;
        if (countTotal == 0) {
            percent = 0.00;
        } else {
            percent = percentFinished * 1.00 / countTotal;
        }
        baseMapper.update(new Plan().setPercent(percent), new QueryWrapper<Plan>().eq("id", parentId).eq("user_id",redisUserId));
        return plan.getParentId();
    }


    /**
     * 懒处理，当有增删改的时候，直接删除redis
     */
    public  void handleRedis(){
        Integer redisUserId = getRedisUserId();
        // --------处理redis  懒处理,直接删除----------
        redisTemplate.delete("treeListAll"+"_"+redisUserId);
        redisTemplate.delete("treeList"+"_"+redisUserId);
        redisTemplate.delete("groupPlanList"+"_"+redisUserId);
        //--------结束redis处理---------
    }

    public   Integer getRedisUserId(){
        User userSession = (User) session.getAttribute("user");
        if(userSession!=null){
            return userSession.getId();
        }else {
            return 0;
        }
    }

}
