package com.bcu.teacher.service;

import com.bcu.teacher.dao.TeacherMapper;
import com.bcu.teacher.bean.Teacher;
import com.bcu.teacher.bean.TeacherExample;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherMapper mapper;

    public List<Teacher> getAllTeachers() {
        return mapper.selectByExample(new TeacherExample());
    }

    public Teacher selectByPrimaryKey(Integer t_id) {
        return mapper.selectByPrimaryKey(t_id);
    }

    public int insert(Teacher record) {
        return mapper.insert(record);
    }

    public int updateByPrimaryKey(Teacher record) {
        return mapper.updateByPrimaryKey(record);
    }

    public int deleteByPrimaryKey(Integer t_id) {
        return mapper.deleteByPrimaryKey(t_id);
    }
}
