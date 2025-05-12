package com.bcu.internship.dao;

import com.bcu.internship.bean.Internship;
import com.bcu.internship.bean.InternshipExample;
import com.bcu.internship.bean.InternshipKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InternshipMapper {
    int countByExample(InternshipExample example);

    int deleteByExample(InternshipExample example);

    int deleteByPrimaryKey(InternshipKey key);

    int insert(Internship record);

    int insertSelective(Internship record);

    List<Internship> selectByExample(InternshipExample example);

    Internship selectByPrimaryKey(InternshipKey key);

    int updateByExampleSelective(@Param("record") Internship record, @Param("example") InternshipExample example);

    int updateByExample(@Param("record") Internship record, @Param("example") InternshipExample example);

    int updateByPrimaryKeySelective(Internship record);

    int updateByPrimaryKey(Internship record);
}