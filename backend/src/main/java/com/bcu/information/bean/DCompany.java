package com.bcu.information.bean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


/*
* 实训公司实体类
* */
@Data
@Getter
@Setter
public class DCompany {
    private String name;    //公司名称
    private String introduction;    //公司介绍
}