package com.bcu.teacher.controller;

import com.bcu.teacher.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("teacher")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService service;
}
