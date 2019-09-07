package com.java.demomp.plan.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.demomp.plan.entity.Dict;
import com.java.demomp.plan.entity.DictParent;
import com.java.demomp.plan.service.DictParentService;
import com.java.demomp.plan.service.DictService;
import com.java.demomp.util.Result;
import com.java.demomp.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lost丶wind
 * @since 2019-06-18
 */
@RestController
@RequestMapping("/plan/dictParent")
public class DictParentController {

    @Autowired
    DictParentService dictParentService;

    @Autowired
    DictService dictService;

    @GetMapping
    public Result list(){
        return new Result(true, StatusCode.OK,"查询成功",dictParentService.list(new QueryWrapper<DictParent>().orderByAsc("order_list")));
    }

    @PutMapping
    public Result update(@RequestBody DictParent dictParent){
        return new Result(true,StatusCode.OK,"修改成功",dictParentService.updateById(dictParent));
    }

    @PostMapping
    public Result add(@RequestBody DictParent dictParent){
        // 设置为最大的orderList
        DictParent maxOrderList = dictParentService.getMaxOrderList();
        if(maxOrderList == null){
            dictParent.setOrderList(1);
        }else {
            dictParent.setOrderList(maxOrderList.getOrderList()+1);
        }
        return new Result(true,StatusCode.OK,"添加成功",dictParentService.save(dictParent));
    }

    @DeleteMapping
    public Result delete(@RequestBody DictParent dictParent){
        List<Dict> dictList = dictService.list(new QueryWrapper<Dict>().eq("parent_id", dictParent.getId()));
        if(dictList.size() == 0){
            return new Result(true,StatusCode.OK,"删除成功",dictParentService.removeById(dictParent));
        }else {
            return new Result(false,StatusCode.ERROR,"有子类不能删除");
        }

    }

}
