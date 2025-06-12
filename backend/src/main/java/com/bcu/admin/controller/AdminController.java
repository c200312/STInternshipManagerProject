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


@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;


    @PostMapping("/import")
    public Result uploadExcel(@RequestParam("file") MultipartFile file) throws IOException {
        return adminService.importExcel(file);
    }

    @GetMapping("/download/{type}")
    public ResponseEntity<byte[]> downloadTemplate(@PathVariable String type) {
        Map<String, String> template = new HashMap<>();
        template.put("student", "student.xlsx");
        template.put("teacher", "teacher.xlsx");
        template.put("user", "user.xlsx");
        return FileUtil.downloadTemplateFile(template, type);
    }

    @GetMapping("/stinfo")
    public Result getStInfo(){
        return adminService.getAllStInfo();
    }
    @GetMapping("/wordoutput/{id}")
    public Result WordOutput(@PathVariable String id) throws IOException {
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

    @PostMapping("/allwordoutput")
    public Result allWordOutput() throws IOException {
        Result result = adminService.getAllStInfo();
        
        if (result.getData() == null) {
            return Result.error("获取学生信息失败");
        }
        
        List<StudentDetailDTO> studentList;
        try {
            studentList = (List<StudentDetailDTO>) result.getData();
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
