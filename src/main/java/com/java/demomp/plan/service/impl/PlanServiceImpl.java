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
 *  服务实现类
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
     * @param plan
     * @return
     */
    public Integer addPlan(Plan plan) {
       return baseMapper.insert(plan);

    }

    public List<Plan> getPlanByType(Integer type) {
        return baseMapper.selectList(new QueryWrapper<Plan>().eq("type",type).orderByDesc("top",    "rank"));
    }

    public Integer updatePlanFinishedById(Plan plan) {
        int finished = plan.getFinished() == 1?0:1;

        //一、 更新自己的状态
        int updateNum = baseMapper.update(new Plan().setFinished(finished).setPercent(100.00), new UpdateWrapper<Plan>().eq("id", plan.getId()));


        // 二、更新周计划

           // 1.通过id获取父亲下面的节点的个数
        Integer parentId = plan.getParentId();
            // 2.通过父节点获取完成的数量和总数量
        Integer countTotal = baseMapper.selectCount(new QueryWrapper<Plan>().eq("parent_id", parentId));
        Integer countFinished = baseMapper.selectCount(new QueryWrapper<Plan>().eq("parent_id", parentId).eq("finished", 1));
          // 3.更新父亲百分比
        Double percent = countFinished * 1.00 / countTotal * 100;
        baseMapper.update(new Plan().setPercent(percent), new QueryWrapper<Plan>().eq("id", parentId));

        // 三、更新月计划
        // 1.通过id获取父亲下面的节点的个数
        Plan weekPlan= baseMapper.selectById(parentId);
        Integer weekPlanParentId = weekPlan.getParentId();
        // 2.通过父节点获取完成的数量和总数量
        Integer weekCountTotal = baseMapper.selectCount(new QueryWrapper<Plan>().eq("parent_id", weekPlanParentId));
        Double weekPercentFinished = baseMapper.selectSumPercent(weekPlanParentId); // 选出百分比
        // 3.更新父亲百分比
        Double weekPercent = weekPercentFinished * 1.00 / weekCountTotal;
        baseMapper.update(new Plan().setPercent(weekPercent), new QueryWrapper<Plan>().eq("id", weekPlanParentId));


        // 三、更新年计划
        // 1.通过id获取父亲下面的节点的个数
        Plan yearPlan= baseMapper.selectById(weekPlanParentId);
        Integer yearPlanParentId = yearPlan.getParentId();
        // 2.通过父节点获取完成的数量和总数量
        Integer yearCountTotal = baseMapper.selectCount(new QueryWrapper<Plan>().eq("parent_id", yearPlanParentId));
        Double yearPercentFinished = baseMapper.selectSumPercent(yearPlanParentId); // 选出百分比
        // 3.更新父亲百分比
        Double yearPercent = yearPercentFinished * 1.00 / yearCountTotal;
        baseMapper.update(new Plan().setPercent(yearPercent), new QueryWrapper<Plan>().eq("id", yearPlanParentId));


        return updateNum;
    }
}
