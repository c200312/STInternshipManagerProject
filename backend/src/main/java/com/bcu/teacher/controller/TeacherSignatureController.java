package com.bcu.teacher.controller;

import com.bcu.common.result.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@RestController
@RequestMapping("/teacher/signature")
public class TeacherSignatureController {

    @Value("${file.upload.signature-dir}")
    private String signatureDir;

    private File getSignatureDir(String username) {
        return new File(signatureDir, "teacher" + File.separator + username);
    }

    private String getSignatureUrl(String username) {
        return "/api/teacher/signature/" + username + "/download";
    }

    private void clearDirectory(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }

    @PostMapping("/{username}")
    public Result uploadSignature(@PathVariable String username, @RequestParam("file") MultipartFile file) {
        try {
            File dir = getSignatureDir(username);
            dir.mkdirs();
            clearDirectory(dir);
            
            Files.write(Paths.get(dir.getPath(), "signature.png"), file.getBytes());
            return Result.success(Map.of("signatureUrl", getSignatureUrl(username)));
        } catch (IOException e) {
            return Result.error("上传签名失败：" + e.getMessage());
        }
    }

    @GetMapping("/{username}")
    public Result getSignature(@PathVariable String username) {
        try {
            File dir = getSignatureDir(username);
            File[] files = dir.listFiles();
            
            if (files == null || files.length == 0) {
                return Result.success(null);
            }
            
            return Result.success(Map.of("signatureUrl", getSignatureUrl(username)));
        } catch (Exception e) {
            return Result.error("获取签名失败：" + e.getMessage());
        }
    }

    @GetMapping("/{username}/download")
    public ResponseEntity<byte[]> downloadSignature(@PathVariable String username) {
        try {
            File dir = getSignatureDir(username);
            File[] files = dir.listFiles();
            
            if (files == null || files.length == 0) {
                return ResponseEntity.notFound().build();
            }
            
            byte[] data = Files.readAllBytes(files[0].toPath());
            return ResponseEntity.ok()
                    .header("Content-Type", "image/png")
                    .header("Content-Disposition", "attachment; filename=\"signature.png\"")
                    .body(data);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{username}")
    public Result deleteSignature(@PathVariable String username) {
        try {
            File dir = getSignatureDir(username);
            if (dir.exists()) {
                clearDirectory(dir);
                dir.delete();
            }
            return Result.success(null);
        } catch (Exception e) {
            return Result.error("删除签名失败：" + e.getMessage());
        }
    }
}