package com.bcu.information.controller;

import com.bcu.common.result.Result;
import com.bcu.information.bean.DUser;
import com.bcu.information.service.DUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/duser")
@RequiredArgsConstructor
public class DUserController {
    private final DUserService service;
    /*
    * 用户初始化
    * @param user
    * @return Result
    * */
    @PostMapping("/create")
    public Result create(@RequestBody DUser user) {
        try {
            return Result.success(service.create(user),"初始化成功");
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
    /*
    * 查询全部id的全部信息
    * @return Result
    * */

    @GetMapping("/all")
    public Result getAll() {
        return Result.success(service.getAll(),"查询所有成功");
    }
    /*
    * 按id查询该id下的全部信息
    * @param id
    * @return Result
    * */
    @GetMapping("/{id}")
    public Result getById(@PathVariable String id) {
        DUser user = service.getById(id);
        return user != null ? Result.success(user,"按id查询成功") : Result.error("未找到该学生");
    }
    /*
    * 删除该id下的全部信息
    * @param id
    * @return Result
    * */
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable String id) {
        return service.deleteById(id) ? Result.success(null,"删除成功") : Result.error("学生不存在");
    }

    /*
    * 按id部分更新(按id对信息进行覆盖，仅覆盖输入的部分)
    * @param id , partialUser
    * @return Result
    * */
    @PatchMapping("/patch/{id}")
    public Result patchUpdate(@PathVariable String id, @RequestBody DUser partialUser) {
        try {
            return Result.success(service.patchUpdate(id, partialUser),"任意部分更新");
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }



}
