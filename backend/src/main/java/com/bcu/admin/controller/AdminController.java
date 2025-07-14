package com.bcu.admin.controller;

import com.bcu.admin.bean.StudentDetailDTO;
import com.bcu.admin.service.AdminService;
import com.bcu.common.result.Result;

import com.bcu.common.util.*;
import com.bcu.common.util.StudentWordDataUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员控制器
 * 提供管理员相关的API接口，包括Excel导入、模板下载、学生信息查询和Word文档生成等功能
 * 
 * @author System
 * @version 1.0
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;


    /**
     * 上传Excel文件并导入数据
     * 支持导入学生、教师、用户等信息的Excel文件
     * 
     * @param file 上传的Excel文件
     * @return Result 导入结果，包含成功或失败信息
     * @throws IOException 文件读取异常
     */
    @PostMapping("/import")
    public Result uploadExcel(@RequestParam("file") MultipartFile file) throws IOException {
        return adminService.importExcel(file);
    }

    /**
     * 下载Excel模板文件
     * 根据指定类型下载对应的Excel模板文件
     * 
     * @param type 模板类型，支持 "student"、"teacher"、"user"
     * @return ResponseEntity<byte[]> 包含模板文件内容的响应实体
     */
    @GetMapping("/download/{type}")
    public ResponseEntity<byte[]> downloadTemplate(@PathVariable String type) {
        Map<String, String> template = new HashMap<>();
        template.put("student", "student.xlsx");
        template.put("teacher", "teacher.xlsx");
        template.put("user", "user.xlsx");
        return FileUtil.downloadTemplateFile(template, type);
    }

    /**
     * 获取所有学生详细信息
     * 返回包含学生、教师、实习和评估信息、周记、教师评语的完整数据列表
     * 
     * @return Result 包含所有学生详细信息的结果对象
     */
    @GetMapping("/stinfo")
    public Result getStInfo(){
        return adminService.getAllStInfo();
    }
    /**
     * 为指定学生生成Word文档
     * 根据学生ID获取学生信息并生成对应的Word文档
     * 
     * @param id 学生ID
     * @return Result 生成结果，成功时包含文件路径，失败时包含错误信息
     */
    @GetMapping("/wordoutput/{id}")
    public Result WordOutput(@PathVariable String id) {
        Result result = adminService.getStInfo(id);
        
        if (result.getData() == null) {
            return Result.error("获取学生信息失败");
        }
        
        StudentDetailDTO data = (StudentDetailDTO) result.getData();
        
        // 检查必要的对象是否为null
        if (data.getStudent() == null) {
            return Result.error("学生信息不存在");
        }
        
        try {
            // 使用工具类生成Word文档
            String outputPath = StudentWordDataUtil.generateStudentWordDocument(data, "D:/templates/模板/模板.docx");
            
            if (outputPath == null) {
                return Result.error("Word文档生成失败");
            }
            
            // 返回成功状态，包含文件路径信息
            return Result.success(outputPath, "Word文档生成成功");
        } catch (Exception e) {
            return Result.error("Word文档生成过程中发生错误: " + e.getMessage());
        }
    }

    /**
     * 批量生成所有学生的Word文档
     * 获取所有学生信息并为每个学生生成对应的Word文档
     * 
     * @return Result 批量生成结果，包含成功和失败的统计信息
     */
    @PostMapping("/allwordoutput")
    public Result allWordOutput() {
        Result result = adminService.getAllStInfo();
        
        if (result.getData() == null) {
            return Result.error("获取学生信息失败");
        }
        
        List<StudentDetailDTO> studentList;
        try {
            Object data = result.getData();
            if (!(data instanceof List<?>)) {
                return Result.error("学生信息数据格式错误");
            }
            @SuppressWarnings("unchecked")
            List<StudentDetailDTO> tempList = (List<StudentDetailDTO>) data;
            studentList = tempList;
        } catch (ClassCastException e) {
            return Result.error("学生信息数据格式错误");
        }
        
        int successCount = 0;
        int failCount = 0;
        
        for (StudentDetailDTO data : studentList) {
            try {
                // 检查必要的对象是否为null
                if (data.getStudent() == null) {
                    failCount++;
                    continue;
                }
                
                // 使用工具类生成Word文档
                String outputPath = StudentWordDataUtil.generateStudentWordDocument(data, "D:/templates/模板/模板.docx");
                
                if (outputPath != null) {
                    successCount++;
                } else {
                    failCount++;
                }
                
            } catch (Exception e) {
                failCount++;
                // 可以记录日志，但继续处理下一个学生
                System.err.println("生成学生 " + (data.getStudent() != null ? data.getStudent().getStudent_name() : "未知") + " 的Word文档时发生错误: " + e.getMessage());
            }
        }
        
        String message = String.format("批量生成完成，成功: %d 个，失败: %d 个", successCount, failCount);
        return Result.success(message);
    }
}
