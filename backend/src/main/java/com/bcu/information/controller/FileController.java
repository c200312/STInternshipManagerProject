package com.bcu.information.controller;

import com.bcu.information.bean.FileInfo;
import com.bcu.common.result.Result;
import com.bcu.information.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping({"/api/files", "/files"})
@RequiredArgsConstructor
@Slf4j
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload/{studentId}")
    public Result uploadFile(@PathVariable Integer studentId, @RequestParam("file") MultipartFile file) {
        log.info("开始上传文件，学生ID: {}, 文件名: {}, 文件大小: {} bytes", 
            studentId, file.getOriginalFilename(), file.getSize());
        
        try {
            String fileUrl = fileService.saveFile(studentId, file);
            log.info("文件上传成功: {}", fileUrl);
            return Result.success(fileUrl, "文件上传成功");
        } catch (IllegalArgumentException e) {
            log.error("文件上传参数错误: {}", e.getMessage());
            return Result.error(e.getMessage());
        } catch (IOException e) {
            log.error("文件上传失败: {}", e.getMessage(), e);
            return Result.error("文件上传失败: " + e.getMessage());
        }
    }

    @GetMapping("/download/{studentId}/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Integer studentId, @PathVariable String fileName) {
        log.info("开始下载文件: {} 从学生ID: {}", fileName, studentId);
        try {
            Resource resource = fileService.loadFileAsResource(studentId, fileName);
            String contentType = getContentType(fileName);
            
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(resource);
        } catch (FileNotFoundException e) {
            log.error("文件不存在: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("文件下载失败: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/delete/{studentId}/{fileName:.+}")
    public Result deleteFile(@PathVariable Integer studentId, @PathVariable String fileName) {
        log.info("开始删除文件: {} 从学生ID: {}", fileName, studentId);
        try {
            fileService.deleteFile(studentId, fileName);
            log.info("文件删除成功: {}", fileName);
            return Result.success(null, "文件删除成功");
        } catch (FileNotFoundException e) {
            log.error("文件不存在: {}", e.getMessage());
            return Result.error("文件不存在");
        } catch (Exception e) {
            log.error("文件删除失败: {}", e.getMessage(), e);
            return Result.error("文件删除失败: " + e.getMessage());
        }
    }

    @GetMapping("/list/{studentId}")
    public Result listFiles(@PathVariable Integer studentId) {
        log.info("开始获取文件列表，学生ID: {}", studentId);
        try {
            List<FileInfo> files = fileService.listFiles(studentId);
            log.info("获取到文件数量: {}", files.size());
            return Result.success(files, "获取文件列表成功");
        } catch (Exception e) {
            log.error("获取文件列表失败: {}", e.getMessage(), e);
            return Result.error("获取文件列表失败: " + e.getMessage());
        }
    }

    private String getContentType(String filename) {
        String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        return switch (extension) {
            case "pdf" -> "application/pdf";
            case "doc", "docx" -> "application/msword";
            case "xls", "xlsx" -> "application/vnd.ms-excel";
            case "jpg", "jpeg" -> "image/jpeg";
            case "png" -> "image/png";
            case "gif" -> "image/gif";
            default -> "application/octet-stream";
        };
    }
} 