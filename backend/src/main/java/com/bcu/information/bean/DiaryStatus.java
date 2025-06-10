package com.bcu.information.bean;

/**
 * 周记审核状态枚举
 */
public enum DiaryStatus {
    DRAFT("草稿"),
    SUBMITTED("已提交"),
    APPROVED("已通过"),
    REJECTED("已拒绝");

    private final String description;

    DiaryStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return this.name();
    }
}