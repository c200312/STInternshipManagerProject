package com.bcu.assessment.dao;

import com.bcu.assessment.bean.Assessment;
import com.bcu.assessment.bean.AssessmentExample;
import com.bcu.assessment.bean.AssessmentKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AssessmentMapper {
    int countByExample(AssessmentExample example);

    int deleteByExample(AssessmentExample example);

    int deleteByPrimaryKey(AssessmentKey key);

    int insert(Assessment record);

    int insertSelective(Assessment record);

    List<Assessment> selectByExample(AssessmentExample example);

    Assessment selectByPrimaryKey(AssessmentKey key);

    int updateByExampleSelective(@Param("record") Assessment record, @Param("example") AssessmentExample example);

    int updateByExample(@Param("record") Assessment record, @Param("example") AssessmentExample example);

    int updateByPrimaryKeySelective(Assessment record);

    int updateByPrimaryKey(Assessment record);
}