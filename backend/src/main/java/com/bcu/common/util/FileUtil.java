package com.bcu.common.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class FileUtil {
    public static ResponseEntity<byte[]> downloadTemplateFile(Map<String,String> template ,String type) {
        String fileName = template.get(type.toLowerCase());
        if (fileName == null) {
            return ResponseEntity.badRequest()
                    .body("无效的模板类型".getBytes(StandardCharsets.UTF_8));
        }

        try {
            Resource resource = new ClassPathResource(fileName);
            if (!resource.exists()) {
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
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("读取文件出错：" + e.getMessage()).getBytes(StandardCharsets.UTF_8));
        }
    }
}
