package com.bcu.assessment.service;

import com.bcu.assessment.bean.Assessment;
import com.bcu.assessment.bean.AssessmentKey;
import com.bcu.assessment.dao.AssessmentMapper;
import com.bcu.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssessmentService {
    @Autowired
    private AssessmentMapper assessmentMapper;

    // 创建评估
    public Result createAssessment(Assessment assessment) {
        int result = assessmentMapper.insert(assessment);
        if (result > 0) {
            return Result.success(assessment,"评估创建成功");
        } else {
            return Result.error("评估创建失败");
        }
    }

    // 获取所有评估
    public Result getAllAssessments() {
        List<Assessment> assessments = assessmentMapper.selectByExample(null);
        return Result.success(assessments);
    }

    // 根据主键获取评估
    public Result getAssessmentById(Integer sId, Integer cId, Integer tId) {
        AssessmentKey key = new AssessmentKey();
        key.setS_id(sId);
        key.setC_id(cId);
        key.setT_id(tId);
        Assessment assessment = assessmentMapper.selectByPrimaryKey(key);
        if (assessment != null) {
            return Result.success(assessment);
        } else {
            return Result.error("评估不存在");
        }
    }

    // 更新评估
    public Result updateAssessment(Assessment assessment) {
        int result = assessmentMapper.updateByPrimaryKey(assessment);
        if (result > 0) {
            return Result.success(assessment,"评估更新成功");
        } else {
            return Result.error("评估更新失败");
        }
    }

    // 删除评估
    public Result deleteAssessment(Integer sId, Integer cId, Integer tId) {
        AssessmentKey key = new AssessmentKey();
        key.setS_id(sId);
        key.setC_id(cId);
        key.setT_id(tId);
        int result = assessmentMapper.deleteByPrimaryKey(key);
        if (result > 0) {
            return Result.success("评估删除成功");
        } else {
            return Result.error("评估删除失败");
        }
    }
}