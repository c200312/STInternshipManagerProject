package com.bcu.information.bean;

/*
 * 周学生记实体类（结构化）
 * */
public class DDiary {
    private Integer week;
    private String work;     // 实习实践工作内容、任务与完成情况
    private String gain;     // 实习实践收获
    private String problem;  // 存在问题与改进措施

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getGain() {
        return gain;
    }

    public void setGain(String gain) {
        this.gain = gain;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }
}
