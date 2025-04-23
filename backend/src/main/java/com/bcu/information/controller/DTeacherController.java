package com.bcu.information.controller;

import com.bcu.common.result.Result;

import com.bcu.information.service.DTeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class DTeacherController {
    private final DTeacherService service;
    @PostMapping("/generate-comment")
    public Result generateComment(@RequestBody Map<String, Object> request) {
        String studentId = (String) request.get("studentId");
        List<Integer> weeks = (List<Integer>) request.get("weeks");
        String comment = service.generateComment(studentId, weeks);
        return Result.success(comment, "评语生成成功");
    }
}
