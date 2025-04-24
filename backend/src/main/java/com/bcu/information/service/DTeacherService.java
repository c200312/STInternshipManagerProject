package com.bcu.information.service;

import com.bcu.information.bean.DUser;
import com.bcu.information.dao.DUserRepository;
import com.bcu.information.service.ai.AiServiceSelector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DTeacherService {
    private final AiServiceSelector aiSelector;
    private final DUserRepository repository;

    public DUser getById(String id) {
        return repository.findById(id).orElse(null);
    }



    public String generateComment(String studentId, List<Integer> weeks) {
        DUser user = getById(studentId);
        return aiSelector.get().generateComment(user, weeks);
    }

}
