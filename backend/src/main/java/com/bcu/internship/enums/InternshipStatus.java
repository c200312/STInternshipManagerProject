package com.bcu.internship.enums;

/**
 * 实习审核状态枚举
 */
public enum InternshipStatus {
    DRAFT((byte) 0, "草稿"),
    SUBMITTED((byte) 1, "待审核"),
    APPROVED((byte) 2, "审核通过"),
    REJECTED((byte) 3, "审核拒绝"),
    NEED_MODIFY((byte) 4, "需要修改"),
    WITHDRAWN((byte) 5, "已撤回");

    private final Byte code;
    private final String description;

    InternshipStatus(Byte code, String description) {
        this.code = code;
        this.description = description;
    }

    public Byte getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 根据状态码获取枚举
     */
    public static InternshipStatus fromCode(Byte code) {
        if (code == null) {
            return DRAFT;
        }
        for (InternshipStatus status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        return DRAFT;
    }

    /**
     * 检查是否可以提交审核
     */
    public boolean canSubmit() {
        return this == DRAFT || this == REJECTED || this == NEED_MODIFY;
    }

    /**
     * 检查是否可以审核
     */
    public boolean canReview() {
        return this == SUBMITTED;
    }

    /**
     * 检查是否可以修改
     */
    public boolean canModify() {
        return this == DRAFT || this == REJECTED || this == NEED_MODIFY;
    }
}