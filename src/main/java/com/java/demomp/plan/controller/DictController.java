package com.java.demomp.plan.controller;


import com.java.demomp.plan.entity.Dict;
import com.java.demomp.plan.service.DictParentService;
import com.java.demomp.plan.service.DictService;
import com.java.demomp.util.Result;
import com.java.demomp.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
