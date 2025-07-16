package com.bcu.information.controller.ai;

import com.bcu.common.result.Result;
import com.bcu.information.service.ai.DTeacherService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/dteacher")
@RequiredArgsConstructor
public class DTeacherController {
    private final DTeacherService service;
    
    /**
     * 生成评语（同步方式）
     */
    @PostMapping("/generate-comment")
    public Result generateComment(@RequestBody Map<String, Object> request) {
        try {
            String studentId = request.get("studentId").toString();
            List<Integer> weeks = (List<Integer>) request.get("weeks");
            String comment = service.generateComment(studentId, weeks);
            return Result.success(comment, "评语生成成功");
        } catch (Exception e) {
            return Result.error("生成评语失败: " + e.getMessage());
        }
    }
    
    /**
     * 生成评语（流式输出）
     * 使用Server-Sent Events (SSE)进行流式传输
     * 使用SseEmitter实现真正的流式传输
     */
    @PostMapping(value = "/generate-comment-stream", 
                 consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter generateCommentStream(@RequestBody Map<String, Object> request, HttpServletResponse response) {
        // 设置响应头，禁用缓冲
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Connection", "keep-alive");
        response.setHeader("X-Accel-Buffering", "no"); // 禁用Nginx缓冲
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");
        
        SseEmitter emitter = new SseEmitter(30000L); // 30s超时
        
        try {
            String studentId = request.get("studentId").toString();
            List<Integer> weeks = (List<Integer>) request.get("weeks");
            
            long startTime = System.currentTimeMillis();
            System.out.println("[" + startTime + "] 开始流式处理，学生ID: " + studentId + ", 周期: " + weeks);
            
            // 直接在当前线程中处理流式数据，避免异步缓冲
            service.generateCommentStream(studentId, weeks)
                    .doOnNext(chunk -> {
                        if (chunk != null && !chunk.trim().isEmpty()) {
                            try {
                                long sendTime = System.currentTimeMillis();
                                System.out.println("[" + sendTime + "] 准备发送数据块: " + chunk.substring(0, Math.min(20, chunk.length())) + "...");
                                
                                // 直接发送原始数据，避免SSE事件包装导致的缓冲
                                emitter.send(chunk);
                                
                                // 立即刷新，确保数据立即发送到客户端
                                try {
                                    response.flushBuffer();
                                } catch (IOException flushEx) {
                                    System.err.println("刷新缓冲区失败: " + flushEx.getMessage());
                                }
                                
                                long sentTime = System.currentTimeMillis();
                                System.out.println("[" + sentTime + "] 数据块发送完成，耗时: " + (sentTime - sendTime) + "ms");
                                
                            } catch (IOException e) {
                                System.err.println("发送SSE数据失败: " + e.getMessage());
                                emitter.completeWithError(e);
                            }
                        }
                    })
                    .doOnError(error -> {
                        long errorTime = System.currentTimeMillis();
                        System.err.println("[" + errorTime + "] 流式处理出错: " + error.getMessage());
                        emitter.completeWithError(error);
                    })
                    .doOnComplete(() -> {
                        long endTime = System.currentTimeMillis();
                        System.out.println("[" + endTime + "] 流式处理完成，总耗时: " + (endTime - startTime) + "ms");
                        emitter.complete();
                    })
                    .subscribe(); // 启动流式处理
            
        } catch (Exception e) {
            System.err.println("流式输出请求参数错误: " + e.getMessage());
            try {
                emitter.send(SseEmitter.event()
                        .data("[ERROR] 请求参数错误: " + e.getMessage())
                        .name("error"));
                emitter.complete();
            } catch (IOException ioException) {
                emitter.completeWithError(ioException);
            }
        }
        
        return emitter;
    }
    
}
