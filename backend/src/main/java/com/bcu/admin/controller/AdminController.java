package com.bcu.admin.controller;

import com.bcu.admin.service.AdminService;
import com.bcu.common.result.Result;
import com.bcu.common.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
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
        Map<String,String> template = new HashMap<>();
        template.put("student", "student.xlsx");
        template.put("teacher", "teacher.xlsx");
        template.put("user", "user.xlsx");
        return FileUtil.downloadTemplateFile(template,type);
    }
    @GetMapping("/stinfo")
    public Result getStInfo() throws IOException {
        return adminService.getStInfo();
    }
}
