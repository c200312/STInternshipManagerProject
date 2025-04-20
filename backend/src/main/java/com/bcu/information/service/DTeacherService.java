package com.bcu.information.service;

import com.bcu.information.bean.Internship.DUser;
import com.bcu.information.dao.DUserRepository;
import com.bcu.information.service.ai.AiServiceSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DTeacherService {

    @Autowired
    private AiServiceSelector aiSelector;

    @Autowired
    private DUserRepository repository;

    public DUser getById(String id) {
        return repository.findById(id).orElse(null);
    }



    public String generateComment(String studentId, List<Integer> weeks) {
        DUser user = getById(studentId);
        return aiSelector.get().generateComment(user, weeks);
    }

}
