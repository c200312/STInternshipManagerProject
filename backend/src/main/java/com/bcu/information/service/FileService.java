package com.bcu.information.service;

import com.bcu.information.bean.FileInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    // 项目根目录（关键修改：获取项目根目录）
    private final String projectBaseDir = new File("").getAbsolutePath();

    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList(
            "jpg", "jpeg", "png", "gif", "pdf", "doc", "docx", "xls", "xlsx"
    );

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB

    @PostConstruct
    public void init() {
        try {
            // 组合项目根目录和相对路径
            String absoluteUploadDir = projectBaseDir + File.separator + uploadDir;
            File directory = new File(absoluteUploadDir);

            if (!directory.exists()) {
                boolean created = directory.mkdirs();
                if (created) {
                    log.info("项目根目录文件上传目录已创建: {}", directory.getAbsolutePath());
                } else {
                    log.error("无法创建项目根目录文件上传目录: {}", directory.getAbsolutePath());
                }
            } else {
                log.info("项目根目录文件上传目录已存在: {}", directory.getAbsolutePath());
            }
        } catch (Exception e) {
            log.error("初始化项目根目录文件上传目录失败: {}", e.getMessage(), e);
        }
    }

    public String getUploadDir() {
        return uploadDir;
    }

    public String saveFile(Integer studentId, MultipartFile file) throws IOException {
        // 验证文件
        validateFile(file);

        // 组合项目根目录、相对路径和学生ID
        String absoluteUploadDir = projectBaseDir + File.separator + uploadDir;
        String studentDir = absoluteUploadDir + File.separator + studentId;
        File dir = new File(studentDir);

        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (!created) {
                log.error("无法创建学生目录: {}", dir.getAbsolutePath());
                throw new IOException("无法创建学生目录: " + dir.getAbsolutePath());
            }
            log.info("创建学生目录成功: {}", dir.getAbsolutePath());
        }

        // 使用原始文件名
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new IllegalArgumentException("文件名不能为空");
        }

        // 检查文件是否已存在，如果存在则添加数字后缀
        String filename = originalFilename;
        int counter = 1;
        while (new File(dir, filename).exists()) {
            String extension = getFileExtension(originalFilename);
            String nameWithoutExtension = originalFilename.substring(0, originalFilename.lastIndexOf("."));
            filename = nameWithoutExtension + "(" + counter + ")." + extension;
            counter++;
        }

        // 保存文件
        File destFile = new File(dir, filename);
        try {
            log.info("文件将保存至: {}", destFile.getAbsolutePath());
            file.transferTo(destFile);
            log.info("文件保存成功: {}", destFile.getAbsolutePath());
            return filename;
        } catch (IOException e) {
            log.error("文件保存失败: {}", e.getMessage(), e);
            throw new IOException("文件保存失败: " + e.getMessage());
        }
    }

    private void validateFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("文件为空");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("文件大小超过限制（最大10MB）");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new IllegalArgumentException("文件名不能为空");
        }

        String extension = getFileExtension(originalFilename);
        if (!ALLOWED_EXTENSIONS.contains(extension.toLowerCase())) {
            throw new IllegalArgumentException("不支持的文件类型，允许的类型: " + String.join(", ", ALLOWED_EXTENSIONS));
        }
    }

    private String getFileExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex == -1) {
            throw new IllegalArgumentException("文件没有扩展名");
        }
        return filename.substring(lastDotIndex + 1);
    }

    public Resource loadFileAsResource(Integer studentId, String fileName) throws IOException {
        try {
            Path filePath = Paths.get(uploadDir, studentId.toString(), fileName);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                log.info("文件存在: {}", filePath);
                return resource;
            } else {
                log.error("文件不存在: {}", filePath);
                throw new FileNotFoundException("文件不存在: " + studentId + "/" + fileName);
            }
        } catch (MalformedURLException ex) {
            log.error("文件URL格式错误: {}", ex.getMessage(), ex);
            throw new FileNotFoundException("文件不存在: " + studentId + "/" + fileName);
        }
    }

    public void deleteFile(Integer studentId, String fileName) throws IOException {
        Path filePath = Paths.get(uploadDir, studentId.toString(), fileName);
        File file = filePath.toFile();

        if (file.exists()) {
            if (!file.delete()) {
                log.error("无法删除文件: {}", filePath);
                throw new IOException("无法删除文件: " + studentId + "/" + fileName);
            }
            log.info("文件删除成功: {}", filePath);
        } else {
            log.error("文件不存在: {}", filePath);
            throw new FileNotFoundException("文件不存在: " + studentId + "/" + fileName);
        }
    }

    public List<FileInfo> listFiles(Integer studentId) throws IOException {
        Path studentDir = Paths.get(uploadDir, studentId.toString());
        if (!Files.exists(studentDir)) {
            log.info("学生目录不存在，返回空列表: {}", studentDir);
            return new ArrayList<>();
        }

        return Files.list(studentDir)
                .map(path -> {
                    String fileName = path.getFileName().toString();
                    FileInfo fileInfo = new FileInfo();
                    fileInfo.setName(fileName);
                    fileInfo.setUrl(studentId + "/" + fileName);
                    try {
                        fileInfo.setSize(Files.size(path));
                        fileInfo.setType(determineContentType(fileName));
                    } catch (IOException e) {
                        log.error("获取文件信息失败: {}", e.getMessage(), e);
                        fileInfo.setSize(0L);
                    }
                    return fileInfo;
                })
                .collect(Collectors.toList());
    }

    private String determineContentType(String filename) {
        String extension = getFileExtension(filename).toLowerCase();
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