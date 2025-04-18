package com.bcu.information.bean;

/*
 * 周学生记实体类（结构化）
 * */
public class DDiary {
    private Integer week;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }
}
