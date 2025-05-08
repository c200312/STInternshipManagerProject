package com.bcu.information.bean;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
/*
* mongodb整体结构类
* */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class DUser {
    @Id
    private String id;  //学生学号
    private String name;

    private List<DCompany> company = new ArrayList<>(); //实习公司
    private List<DDiary> diary = new ArrayList<>(); //周记
    private List<DComment> comment = new ArrayList<>(); //教师评语
}
