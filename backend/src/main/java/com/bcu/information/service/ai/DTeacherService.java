package com.bcu.information.service.ai;

import com.bcu.information.bean.DUser;
import com.bcu.information.dao.DUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * 教师AI评语生成服务
 * 基于Spring AI ChatClient API实现
 * 提供同步和流式两种评语生成模式
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DTeacherService {
    private final AiServiceSelector aiSelector;
    private final DUserRepository repository;

    /**
     * 根据学生ID获取用户信息
     * 
     * @param id 学生ID
     * @return 学生用户信息，如果不存在则返回null
     */
    private DUser getById(String id) {
        return repository.findById(id).orElse(null);
    }

    /**
     * 生成评语（同步方式）
     * 使用Spring AI ChatClient的call()方法进行同步调用
     * 
     * @param studentId 学生ID
     * @param weeks 需要生成评语的周数列表
     * @return 完整的评语内容
     * @throws RuntimeException 当学生不存在或生成过程中发生错误时
     */
    public String generateComment(String studentId, List<Integer> weeks) {
        log.info("开始生成评语 - 学生ID: {}, 周数: {}", studentId, weeks);
        
        DUser user = getById(studentId);
        if (user == null) {
            log.error("学生不存在: {}", studentId);
            throw new RuntimeException("学生不存在: " + studentId);
        }
        
        try {
            String comment = aiSelector.get().generateComment(user, weeks);
            log.info("评语生成成功 - 学生ID: {}, 评语长度: {}", studentId, comment.length());
            return comment;
        } catch (Exception e) {
            log.error("评语生成失败 - 学生ID: {}, 错误: {}", studentId, e.getMessage(), e);
            throw new RuntimeException("评语生成失败: " + e.getMessage(), e);
        }
    }

    /**
     * 生成评语（流式输出）
     * 使用Spring AI ChatClient的stream()方法进行流式调用
     * 支持实时输出，提升用户体验
     * 
     * @param studentId 学生ID
     * @param weeks 需要生成评语的周数列表
     * @return 流式输出的评语片段，使用Reactor Flux进行响应式处理
     */
    public Flux<String> generateCommentStream(String studentId, List<Integer> weeks) {
        log.info("开始流式生成评语 - 学生ID: {}, 周数: {}", studentId, weeks);
        
        DUser user = getById(studentId);
        if (user == null) {
            log.error("学生不存在: {}", studentId);
            return Flux.error(new RuntimeException("学生不存在: " + studentId));
        }
        
        return aiSelector.get().generateCommentStream(user, weeks)
                .doOnSubscribe(subscription -> log.info("开始流式输出 - 学生ID: {}", studentId))
                .doOnNext(chunk -> log.debug("流式输出片段 - 学生ID: {}, 内容长度: {}", studentId, chunk.length()))
                .doOnComplete(() -> log.info("流式输出完成 - 学生ID: {}", studentId))
                .doOnError(error -> log.error("流式输出错误 - 学生ID: {}, 错误: {}", studentId, error.getMessage(), error));
    }
}
