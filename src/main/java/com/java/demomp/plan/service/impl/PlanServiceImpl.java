package com.java.demomp.plan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.java.demomp.plan.entity.Plan;
import com.java.demomp.plan.mapper.PlanMapper;
import com.java.demomp.plan.service.PlanService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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


    /**
     * 添加计划
     *
     * @param plan
     * @return
     */
    public Integer addPlan(Plan plan) {
        // 1.正常的添加
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
