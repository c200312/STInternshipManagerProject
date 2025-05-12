package com.bcu.assessment.controller;

import com.bcu.assessment.bean.Assessment;
import com.bcu.assessment.service.AssessmentService;
import com.bcu.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assessments")
public class AssessmentController {
    @Autowired
    private AssessmentService assessmentService;

    // 创建评估
    @PostMapping
    public Result createAssessment(@RequestBody Assessment assessment) {
        return assessmentService.createAssessment(assessment);
    }

    // 获取所有评估
    @GetMapping
    public Result getAllAssessments() {
        return assessmentService.getAllAssessments();
    }

    // 根据主键获取评估
    @GetMapping("/{sId}/{cId}/{tId}")
    public Result getAssessmentById(@PathVariable Integer sId, @PathVariable Integer cId, @PathVariable Integer tId) {
        return assessmentService.getAssessmentById(sId, cId, tId);
    }

    // 更新评估
    @PutMapping
    public Result updateAssessment(@RequestBody Assessment assessment) {
        return assessmentService.updateAssessment(assessment);
    }

    // 删除评估
    @DeleteMapping("/{sId}/{cId}/{tId}")
    public Result deleteAssessment(@PathVariable Integer sId, @PathVariable Integer cId, @PathVariable Integer tId) {
        return assessmentService.deleteAssessment(sId, cId, tId);
    }
}