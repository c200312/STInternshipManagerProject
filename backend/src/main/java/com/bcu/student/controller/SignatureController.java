package com.bcu.student.controller;

import com.bcu.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/student/signature")
public class SignatureController {

    @Value("${file.upload.signature-dir}")
    private String signatureDir;

    @PostMapping("/{username}")
    public Result uploadSignature(@PathVariable String username, @RequestParam("file") MultipartFile file) {
        try {
            // 创建用户签名目录
            String userDir = signatureDir + File.separator + username;
            File dir = new File(userDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 删除旧签名（如果存在）
            File[] oldFiles = dir.listFiles();
            if (oldFiles != null) {
                for (File oldFile : oldFiles) {
                    oldFile.delete();
                }
            }

            // 保存新签名
            String fileName = "signature.png";
            Path filePath = Paths.get(userDir, fileName);
            Files.write(filePath, file.getBytes());

            // 返回签名URL
            Map<String, String> data = new HashMap<>();
            data.put("signatureUrl", "/api/student/signature/" + username + "/download");
            return Result.success(data);
        } catch (IOException e) {
            return Result.error("上传签名失败：" + e.getMessage());
        }
    }

    @GetMapping("/{username}")
    public Result getSignature(@PathVariable String username) {
        try {
            String userDir = signatureDir + File.separator + username;
            File dir = new File(userDir);
            if (!dir.exists()) {
                return Result.success(null);
            }

            File[] files = dir.listFiles();
            if (files == null || files.length == 0) {
                return Result.success(null);
            }

            Map<String, String> data = new HashMap<>();
            data.put("signatureUrl", "/api/student/signature/" + username + "/download");
            return Result.success(data);
        } catch (Exception e) {
            return Result.error("获取签名失败：" + e.getMessage());
        }
    }

    @GetMapping("/{username}/download")
    public ResponseEntity<byte[]> downloadSignature(@PathVariable String username) {
        try {
            String userDir = signatureDir + File.separator + username;
            File dir = new File(userDir);
            if (!dir.exists()) {
                return ResponseEntity.notFound().build();
            }

            File[] files = dir.listFiles();
            if (files == null || files.length == 0) {
                return ResponseEntity.notFound().build();
            }

            File signatureFile = files[0];
            byte[] data = Files.readAllBytes(signatureFile.toPath());
            return ResponseEntity.ok()
                    .header("Content-Type", "image/png")
                    .header("Content-Disposition", "attachment; filename=\"signature.png\"")
                    .body(data);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{username}")
    public Result<Void> deleteSignature(@PathVariable String username) {
        try {
            String userDir = signatureDir + File.separator + username;
            File dir = new File(userDir);
            if (!dir.exists()) {
                return Result.success(null);
            }

            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
            dir.delete();

            return Result.success(null);
        } catch (Exception e) {
            return Result.error("删除签名失败：" + e.getMessage());
        }
    }
} 