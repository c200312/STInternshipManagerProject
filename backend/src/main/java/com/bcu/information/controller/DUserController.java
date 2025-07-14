package com.bcu.information.controller;

import com.bcu.common.result.Result;
import com.bcu.information.bean.DUser;
import com.bcu.information.bean.DiaryReviewRequest;
import com.bcu.information.service.DUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/duser")
@RequiredArgsConstructor
public class DUserController {
    private final DUserService service;

    @GetMapping("/selectbytnumber/{t_number}")
    public Result selectByTNumber(@PathVariable String t_number) {

        return service.selectByTNumber(t_number);
    }
    // 用户初始化
    @PostMapping
    public Result create(@RequestBody DUser user) {
        return service.create(user);
    }

    // 查询全部id的全部信息
    @GetMapping
    public Result getAll() {
        return service.getAll();
    }

    // 按id查询该id下的全部信息
    @GetMapping("/{id}")
    public Result getById(@PathVariable String id) {
        return service.getById(id);
    }

    // 删除该id下的全部信息
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        return service.deleteById(id);
    }

    // 按id部分更新(按id对信息进行覆盖，仅覆盖输入的部分)
    @PatchMapping("/{id}")
    public Result patchUpdate(@PathVariable String id, @RequestBody DUser partialUser) {
        return service.patchUpdate(id, partialUser);
    }

    // 提交周记审核
    @PostMapping("/{id}/diary/{week}/submit")
    public Result submitDiaryForReview(@PathVariable String id, @PathVariable String week) {
        return service.submitDiaryForReview(id, week);
    }

    // 审核周记
    @PostMapping("/{id}/diary/{week}/review")
    public Result reviewDiary(@PathVariable String id, @PathVariable String week, 
                             @RequestBody DiaryReviewRequest request) {
        return service.reviewDiary(id, week, request.getStatus(), request.getReviewComment(), request.getReviewer());
    }

    // 获取待审核的周记列表
    @GetMapping("/diary/pending")
    public Result getPendingDiaries() {
        return service.getPendingDiaries();
    }
}
