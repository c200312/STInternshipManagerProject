package com.bcu.admin.controller;

import com.bcu.admin.service.AdminService;
import com.bcu.common.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController implements ResourceLoaderAware {

    private final AdminService adminService;
    private ResourceLoader resourceLoader;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @PostMapping("/import")
    public Result uploadExcel(@RequestParam("file") MultipartFile file) throws IOException {
        return adminService.importExcel(file);
    }

    @GetMapping("/download/{type}")
    public ResponseEntity<byte[]> downloadTemplate(@PathVariable String type) throws IOException {
        String fileName;
        if ("student".equalsIgnoreCase(type)) {
            fileName = "student.xlsx";
        } else if ("teacher".equalsIgnoreCase(type)) {
            fileName = "teacher.xlsx";
        } else {
            return ResponseEntity.badRequest()
                    .body("无效的模板类型".getBytes(StandardCharsets.UTF_8));
        }

        Resource resource = new ClassPathResource(fileName);
        System.out.println(resource);
        if (!resource.exists()) {
            System.out.println("文件不存在：" + fileName);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(("文件不存在：" + fileName).getBytes(StandardCharsets.UTF_8));
        }

        byte[] bytes = resource.getInputStream().readAllBytes();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename(URLEncoder.encode(fileName, StandardCharsets.UTF_8)).build());

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }
}
