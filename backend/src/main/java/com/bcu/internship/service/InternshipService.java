package com.bcu.internship.service;

import com.bcu.common.result.Result;
import com.bcu.internship.bean.Internship;
import com.bcu.internship.bean.InternshipExample;
import com.bcu.internship.dao.InternshipMapper;
import com.bcu.internship.dto.InternshipReviewRequest;
import com.bcu.internship.enums.InternshipStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InternshipService {
    private final InternshipMapper internshipMapper;

    // 添加实习信息
    public Result addInternship(Internship internship) {
        int result = internshipMapper.insertSelective(internship);
        if (result > 0) {
            return Result.success("添加成功");
        } else {
            return Result.error("添加失败");
        }
    }

    // 删除实习信息
    public Result deleteInternship(Integer sid) {
        InternshipExample example = new InternshipExample();
        example.createCriteria().andS_idEqualTo(sid);
        int result = internshipMapper.deleteByExample(example);
        if (result > 0) {
            return Result.success("删除成功");
        } else {
            return Result.error("删除失败");
        }
    }

    // 更新实习信息
    public Result updateInternship(Internship internship) {
        Result result = getInternshipBySId(internship.getS_id());
        Object data = result.getData();

        // 判断是否为空集合
        if (data instanceof List<?> list && list.isEmpty()) {
            return addInternship(internship);
        }

        // 否则更新
        int updateResult = internshipMapper.updateByPrimaryKeySelective(internship);
        return updateResult > 0 ? Result.success("更新成功") : Result.error("更新失败");
    }

    // 查询所有实习信息
    public Result getAllInternships() {
        InternshipExample example = new InternshipExample();
        List<Internship> internships = internshipMapper.selectByExample(example);
        return Result.success(internships, "查询成功");
    }

    // 根据S_id查询实习信息
    public Result getInternshipBySId(Integer id) {
        InternshipExample example = new InternshipExample();
        example.createCriteria().andS_idEqualTo(id);

        List<Internship> internship = internshipMapper.selectByExample(example);
        if (internship != null) {
            return Result.success(internship, "查询成功");
        } else {
            return Result.error("未找到相关记录");
        }
    }

    // 提交实习信息审核
    public Result submitInternshipForReview(Integer sId) {
        Result result = getInternshipBySId(sId);
        Object data = result.getData();
        
        if (data instanceof List<?> list && list.isEmpty()) {
            return Result.error("实习信息不存在");
        }
        
        @SuppressWarnings("unchecked")
        List<Internship> internships = (List<Internship>) data;
        Internship internship = internships.get(0);
        
        InternshipStatus currentStatus = InternshipStatus.fromCode(internship.getApproval_status());
        if (!currentStatus.canSubmit()) {
            return Result.error("当前状态不允许提交审核，状态：" + currentStatus.getDescription());
        }
        
        // 验证必要字段
        if (internship.getCompany_name() == null || internship.getCompany_name().trim().isEmpty()) {
            return Result.error("公司名称不能为空");
        }
        if (internship.getStart_date() == null || internship.getEnd_date() == null) {
            return Result.error("实习开始和结束时间不能为空");
        }
        
        // 更新状态为待审核
        internship.setApproval_status(InternshipStatus.SUBMITTED.getCode());
        internship.setReview_comments(null); // 清空之前的审核意见
        internship.setReviewer_name(null);
        internship.setReview_time(null);
        
        int updateResult = internshipMapper.updateByPrimaryKeySelective(internship);
        return updateResult > 0 ? Result.success("提交审核成功") : Result.error("提交审核失败");
    }
    
    // 审核实习信息
    public Result reviewInternship(Integer sId, InternshipReviewRequest request) {
        if (request.getReviewComments() == null || request.getReviewComments().trim().isEmpty() || 
            request.getReviewerName() == null || request.getReviewerName().trim().isEmpty()) {
            System.out.println(request.getReviewComments());
            System.out.println(request.getReviewerName());
            System.out.println(request.getReviewerName());
            return Result.error("审核意见和审核人不能为空");
        }
        
        // 验证审核状态
        if (request.getApprovalStatus() == null) {
            return Result.error("审核状态不能为空");
        }
        
        InternshipStatus reviewStatus = InternshipStatus.fromCode(request.getApprovalStatus());
        
        if (reviewStatus != InternshipStatus.APPROVED && 
            reviewStatus != InternshipStatus.REJECTED) {
            return Result.error("审核状态只能是通过(2)、拒绝(3)");
        }
        
        Result result = getInternshipBySId(sId);
        Object data = result.getData();
        
        if (data instanceof List<?> list && list.isEmpty()) {
            return Result.error("实习信息不存在");
        }
        
        @SuppressWarnings("unchecked")
        List<Internship> internships = (List<Internship>) data;
        Internship internship = internships.get(0);
        
        InternshipStatus currentStatus = InternshipStatus.fromCode(internship.getApproval_status());
        if (!currentStatus.canReview()) {
            return Result.error("只能审核待审核状态的实习信息，当前状态：" + currentStatus.getDescription());
        }
        
        // 更新审核信息
        internship.setApproval_status(reviewStatus.getCode());
        internship.setReview_comments(request.getReviewComments());
        internship.setReviewer_name(request.getReviewerName());
        internship.setReview_time(new Date());
        
        int updateResult = internshipMapper.updateByPrimaryKeySelective(internship);
        return updateResult > 0 ? Result.success("审核完成") : Result.error("审核失败");
    }
    
    // 获取待审核的实习信息列表
    public Result getPendingInternships() {
        InternshipExample example = new InternshipExample();
        example.createCriteria().andApproval_statusEqualTo(InternshipStatus.SUBMITTED.getCode());
        
        List<Internship> internships = internshipMapper.selectByExample(example);
        return Result.success(internships, "获取待审核实习信息成功");
    }
    
    // 根据审核状态查询实习信息
    public Result getInternshipsByStatus(Byte status) {
        InternshipExample example = new InternshipExample();
        example.createCriteria().andApproval_statusEqualTo(status);
        
        List<Internship> internships = internshipMapper.selectByExample(example);
        InternshipStatus internshipStatus = InternshipStatus.fromCode(status);
        return Result.success(internships, "获取" + internshipStatus.getDescription() + "的实习信息成功");
    }
    
    // 撤回实习审核
    public Result withdrawInternship(Integer sId) {
        Result result = getInternshipBySId(sId);
        Object data = result.getData();
        
        if (data instanceof List<?> list && list.isEmpty()) {
            return Result.error("实习信息不存在");
        }
        
        @SuppressWarnings("unchecked")
        List<Internship> internships = (List<Internship>) data;
        Internship internship = internships.get(0);
        
        InternshipStatus currentStatus = InternshipStatus.fromCode(internship.getApproval_status());
        if (currentStatus != InternshipStatus.SUBMITTED) {
            return Result.error("只能撤回待审核状态的实习信息，当前状态：" + currentStatus.getDescription());
        }
        
        // 更新状态为已撤回
        internship.setApproval_status(InternshipStatus.WITHDRAWN.getCode());
        internship.setReview_comments("用户主动撤回");
        internship.setReview_time(new Date());
        
        int updateResult = internshipMapper.updateByPrimaryKeySelective(internship);
        return updateResult > 0 ? Result.success("撤回成功") : Result.error("撤回失败");
    }

}