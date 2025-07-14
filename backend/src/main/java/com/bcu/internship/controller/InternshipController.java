package com.bcu.internship.controller;

import com.bcu.common.result.Result;
import com.bcu.internship.bean.Internship;
import com.bcu.internship.dto.InternshipReviewRequest;
import com.bcu.internship.service.InternshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internship")
@RequiredArgsConstructor
public class InternshipController {
    private final InternshipService internshipService;

    // 添加实习信息
    @PostMapping
    public Result addInternship(@RequestBody Internship internship) {
        return internshipService.addInternship(internship);
    }

    // 删除实习信息
    @DeleteMapping("/{sId}")
    public Result deleteInternship(@PathVariable Integer sId) {
        return internshipService.deleteInternship(sId);
    }

    // 更新实习信息
    @PutMapping
    public Result updateInternship(@RequestBody Internship internship) {
        return internshipService.updateInternship(internship);
    }

    // 查询所有实习信息
    @GetMapping
    public Result getAllInternships() {
        return internshipService.getAllInternships();
    }

    // 根据sid查询实习信息
    @GetMapping("/{sId}")
    public Result getInternshipById(@PathVariable Integer sId) {
        return internshipService.getInternshipBySId(sId);
    }
    
    // 提交实习信息审核
    @PostMapping("/{sId}/submit")
    public Result submitInternshipForReview(@PathVariable Integer sId) {
        return internshipService.submitInternshipForReview(sId);
    }
    
    // 审核实习信息
    @PostMapping("/{sId}/review")
    public Result reviewInternship(@PathVariable Integer sId, @RequestBody InternshipReviewRequest request) {
        return internshipService.reviewInternship(sId, request);
    }
    
    // 获取待审核的实习信息列表
    @GetMapping("/pending")
    public Result getPendingInternships() {
        return internshipService.getPendingInternships();
    }
    
    // 根据审核状态查询实习信息
    @GetMapping("/status/{status}")
    public Result getInternshipsByStatus(@PathVariable Byte status) {
        return internshipService.getInternshipsByStatus(status);
    }
    
    // 撤回实习审核
    @PostMapping("/{sId}/withdraw")
    public Result withdrawInternship(@PathVariable Integer sId) {
        return internshipService.withdrawInternship(sId);
    }
}