package com.bcu.information.service.ai;

import com.bcu.information.bean.DUser;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * AI评语生成服务统一接口
 * 基于Spring AI ChatClient API设计
 * 支持同步和流式两种生成模式
 */
public interface AiCommentService {
    
    /**
     * 同步生成评语
     * 使用Spring AI ChatClient的call()方法
     * 
     * @param user 学生用户信息，包含周记等数据
     * @param weeks 需要生成评语的周数列表
     * @return 完整的评语内容
     * @throws RuntimeException 当生成过程中发生错误时
     */
    String generateComment(DUser user, List<Integer> weeks);
    
    /**
     * 流式生成评语
     * 使用Spring AI ChatClient的stream()方法
     * 支持实时输出，提升用户体验
     * 
     * @param user 学生用户信息，包含周记等数据
     * @param weeks 需要生成评语的周数列表
     * @return 流式输出的评语片段，使用Reactor Flux进行响应式处理
     * @throws RuntimeException 当生成过程中发生错误时
     */
    Flux<String> generateCommentStream(DUser user, List<Integer> weeks);
}
