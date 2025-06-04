package com.bcu.assessment.service;

import com.bcu.assessment.bean.Assessment;
import com.bcu.assessment.dao.AssessmentMapper;
import com.bcu.common.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssessmentService {
    private final AssessmentMapper assessmentMapper;

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
    public Result getAssessmentById(Integer sId) {
        Assessment assessment = assessmentMapper.selectByPrimaryKey(sId);
        if (assessment != null) {
            return Result.success(assessment);
        } else {
            return Result.error("评估不存在");
        }
    }

    // 更新评估
    public Result updateAssessment(Assessment assessment) {
        Result result = getAssessmentById(assessment.getS_id());
        Object data = result.getData();
        // 判断是否为空集合
        if (data instanceof List<?> list && list.isEmpty()) {
            return createAssessment(assessment);
        }
        // 否则更新
        int updateResult = assessmentMapper.updateByPrimaryKeySelective(assessment);
        return updateResult > 0 ? Result.success("更新成功") : Result.error("更新失败");

    }

    // 删除评估
    public Result deleteAssessment(Integer sId) {

        int result = assessmentMapper.deleteByPrimaryKey(sId);
        if (result > 0) {
            return Result.success("评估删除成功");
        } else {
            return Result.error("评估删除失败");
        }
    }
}