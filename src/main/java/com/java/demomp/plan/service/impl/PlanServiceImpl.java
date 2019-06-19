package com.java.demomp.plan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.java.demomp.plan.entity.Dict;
import com.java.demomp.plan.entity.DictParent;
import com.java.demomp.plan.entity.Plan;
import com.java.demomp.plan.mapper.PlanMapper;
import com.java.demomp.plan.service.DictParentService;
import com.java.demomp.plan.service.DictService;
import com.java.demomp.plan.service.PlanService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
    DictParentService dictParentService;

    @Autowired
    DictService dictService;

    /**
     * 添加计划
     *
     * @param plan
     * @return
     */
    public Integer addPlan(Plan plan) {
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
            List<Plan> planList = baseMapper.selectList(new QueryWrapper<Plan>().eq("type", 4));
            // 获取所有有颜色的父类列表
            List<String> colorList = new ArrayList<>();
            for (int i = 0; i < planList.size(); i++) {
                // 1 2 3
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

        int insert = baseMapper.insert(plan);
        // 2.改变父类的百分比
        // 1.日  -> 周、月、年

        // 如果添加的是年计划，就不用更新了
        Integer parentId = plan.getParentId();
        while (parentId != null) {
            parentId = updateFatherByParentId(parentId);
        }
        return insert;

    }

    public List<Plan> getPlanByType(Integer type) {
        return baseMapper.selectList(new QueryWrapper<Plan>().eq("type", type).orderByDesc("top", "rank"));
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
        /*
        // 2、更新周计划
        Integer weekParentId = updateFatherByParentId(plan.getParentId());
        // 3、更新月计划
        Integer monthParentId = updateFatherByParentId(weekParentId);
        // 4、更新年计划
        updateFatherByParentId(monthParentId);
        */
        return updateNum;
    }

    // 更新计划
    public Integer updatePlan(Plan plan) {
        // 二话不说，先找出新的父类
        Integer newParentId = plan.getParentId();
        Integer beforeParentId = plan.selectById().getParentId();

        // 如果是年计划,直接更新就行了
        if (newParentId == null) {
            baseMapper.updateById(plan);
        } else {
            //  大前提：如果不是年计划
            //  如果没变改变parentId
            if (newParentId == beforeParentId) {
                baseMapper.updateById(plan);
            } else {
                // 如果改变了父类
                baseMapper.updateById(plan);
                // 1.更新之前的进度（就是相当于我删了这个parentId）
                while (beforeParentId != null) {
                    beforeParentId = updateFatherByParentId(beforeParentId);
                }
                //  2.更新新的父类的进度
                while (newParentId != null) {
                    newParentId = updateFatherByParentId(newParentId);
                }

            }
        }

        return 1;
    }


    /**
     * 传入父id
     *
     * @param parentId
     * @return
     */
    // 抽取一个公共发放，通过父id更新父亲的百分比状态并 返回那个父亲的父id
    public Integer updateFatherByParentId(Integer parentId) {   // 55
        // 1.通过父id获取父亲实体
        Plan plan = baseMapper.selectById(parentId);
        // 2.通过父节点获取完成的数量和总数量
        Integer countTotal = baseMapper.selectCount(new QueryWrapper<Plan>().eq("parent_id", parentId));
        Double percentFinished = baseMapper.selectSumPercent(parentId); // 选出百分比
        // 3.更新父亲百分比
        Double percent = percentFinished * 1.00 / countTotal;
        baseMapper.update(new Plan().setPercent(percent), new QueryWrapper<Plan>().eq("id", parentId));
        return plan.getParentId();
    }


}
