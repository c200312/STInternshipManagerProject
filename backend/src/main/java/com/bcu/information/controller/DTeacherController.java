package com.bcu.information.controller;

import com.bcu.common.result.Result;

import com.bcu.information.service.DTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teacher")
public class DTeacherController {
    @Autowired
    private DTeacherService service;
    @PostMapping("/generate-comment")
    public Result generateComment(@RequestBody Map<String, Object> request) {
        String studentId = (String) request.get("studentId");
        List<Integer> weeks = (List<Integer>) request.get("weeks");
        String comment = service.generateComment(studentId, weeks);
        return Result.success(comment, "评语生成成功");
    }
}
