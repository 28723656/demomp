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
@RequestMapping("/plan/dict")
public class DictController {

    @Autowired
    DictService dictService;

    @Autowired
    DictParentService dictParentService;
    /**
     * 通过code获取DictList
     * @param code
     * @return
     */
    @GetMapping("/{code}")
    public Result getDictListByParentCode(@PathVariable String code){
        List<Dict> list =  dictParentService.getDictListByParentCode(code);
        if(list.size() > 0){
            return new Result(true, StatusCode.OK,"查询成功",list);
        }else {
            return new Result(false,StatusCode.NODATA,"没有数据");
        }
    }


    /**
     * 通过父类id获取子类集合
     * @param parentId
     * @return
     */
    @GetMapping("/list")
    public Result getDictListByParentId(@PathVariable String parentId){
        List<Dict> dictList = dictService.list(new QueryWrapper<Dict>().eq("parent_id", parentId));
        if(dictList!=null){
            return new Result(true,StatusCode.OK,"查询成功",dictList);
        }else {
            return new Result(false,StatusCode.NODATA,"没有数据");
        }
    }


    @PutMapping
    public Result update(@RequestBody Dict dict){
        return new Result(true,StatusCode.OK,"修改成功",dictService.updateById(dict));
    }

    @PostMapping
    public Result add(@RequestBody Dict dict){
        // 设置为最大的orderList
        Dict maxOrderList = dictService.getMaxOrderListByParentId(dict.getParentId());
        if(maxOrderList == null){
            dict.setOrderList(1);
        }else {
            dict.setOrderList(maxOrderList.getOrderList()+1);
        }
        return new Result(true,StatusCode.OK,"添加成功",dictService.save(dict));
    }

    @DeleteMapping
    public Result delete(@RequestBody Dict dict){
        return new Result(true,StatusCode.OK,"删除成功",dictService.removeById(dict));
    }

}
