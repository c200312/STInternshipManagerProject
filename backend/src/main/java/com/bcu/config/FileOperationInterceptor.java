package com.bcu.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileOperationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 检查是否是文件操作请求
        String path = request.getRequestURI();
        log.info("拦截到请求: {}", path);
        
        if (path.startsWith("/api/files/") || path.startsWith("/files/")) {
            // 设置请求属性，防止被静态资源处理器处理
            request.setAttribute("isFileOperation", true);
            log.info("标记为文件操作请求");
        }
        return true;
    }
} 