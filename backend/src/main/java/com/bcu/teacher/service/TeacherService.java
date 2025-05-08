package com.bcu.teacher.service;

import com.bcu.teacher.dao.TeacherMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherMapper mapper;


}
