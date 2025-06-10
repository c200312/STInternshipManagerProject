package com.bcu.internship.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 实习审核请求DTO
 */
@Data
public class InternshipReviewRequest {
    /**
     * 审核状态：2(通过) 或 3(拒绝) 或 4(需要修改)
     */
    @JsonProperty("approval_status")
    private Byte approvalStatus;
    
    /**
     * 审核意见
     */
    @JsonProperty("review_comments")
    private String reviewComments;
    
    /**
     * 审核人姓名
     */
    @JsonProperty("reviewer_name")
    private String reviewerName;
}