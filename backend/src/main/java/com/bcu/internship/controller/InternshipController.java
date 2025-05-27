package com.bcu.internship.controller;

import com.bcu.common.result.Result;
import com.bcu.internship.bean.Internship;
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
}