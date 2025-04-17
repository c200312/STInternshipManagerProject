package com.bcu.information.controller;

import com.bcu.common.result.Result;
import com.bcu.information.bean.*;
import com.bcu.information.service.DUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/duser")
public class DUserController {

    @Autowired
    private DUserService service;

    //  初始化
    @PostMapping("/create")
    public Result create(@RequestBody DUser user) {
        try {
            return Result.success(service.create(user),"初始化成功");
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    //  查询所有
    @GetMapping("/all")
    public Result getAll() {
        return Result.success(service.getAll(),"查询所有成功");
    }

    //  按id查询单个
    @GetMapping("/{id}")
    public Result getById(@PathVariable String id) {
        DUser user = service.getById(id);
        return user != null ? Result.success(user,"按id查询成功") : Result.error("未找到该学生");
    }

    //  删除
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable String id) {
        return service.deleteById(id) ? Result.success(null,"删除成功") : Result.error("学生不存在");
    }

    //  部分更新
    @PatchMapping("/patch/{id}")
    public Result patchUpdate(@PathVariable String id, @RequestBody DUser partialUser) {
        try {
            return Result.success(service.patchUpdate(id, partialUser),"任意部分更新");
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
}
