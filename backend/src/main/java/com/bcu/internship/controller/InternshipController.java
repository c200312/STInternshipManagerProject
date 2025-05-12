package com.bcu.internship.controller;

import com.bcu.common.result.Result;
import com.bcu.internship.bean.Internship;
import com.bcu.internship.bean.InternshipKey;
import com.bcu.internship.service.InternshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internship")
public class InternshipController {
    @Autowired
    private InternshipService internshipService;

    // 添加实习信息
    @PostMapping
    public Result addInternship(@RequestBody Internship internship) {
        return internshipService.addInternship(internship);
    }

    // 删除实习信息
    @DeleteMapping("/{sId}/{cId}")
    public Result deleteInternship(@PathVariable Integer sId, @PathVariable Integer cId) {
        InternshipKey key = new InternshipKey();
        key.setS_id(sId);
        key.setC_id(cId);
        return internshipService.deleteInternship(key);
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

    // 根据主键查询实习信息
    @GetMapping("/{sId}/{cId}")
    public Result getInternshipById(@PathVariable Integer sId, @PathVariable Integer cId) {
        InternshipKey key = new InternshipKey();
        key.setS_id(sId);
        key.setC_id(cId);
        return internshipService.getInternshipById(key);
    }
}