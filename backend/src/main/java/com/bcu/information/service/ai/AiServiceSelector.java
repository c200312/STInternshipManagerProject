package com.bcu.information.service.ai;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * AI服务选择器
 * 基于Spring AI ChatClient API设计
 * 负责选择和管理AI评语生成服务实例
 * 
 * 设计模式：策略模式
 * 扩展性：后续如需增加新的AI模型服务，只需实现AiCommentService接口并注入即可
 * 
 * @author System
 * @since 1.0
 */
@Slf4j
@Component
public class AiServiceSelector {

    private final AiCommentService commentService;

    /**
     * 构造函数注入AI评语服务
     * 当前默认使用智谱AI服务
     * 
     * @param commentService 智谱AI评语服务实现
     */
    public AiServiceSelector(ZhipuAiCommentService commentService) {
        this.commentService = commentService;
        log.info("AI服务选择器初始化完成，当前使用服务: {}", commentService.getClass().getSimpleName());
    }

    /**
     * 获取当前配置的AI评语服务
     * 
     * @return AI评语服务实例
     */
    public AiCommentService get() {
        log.debug("获取AI服务实例: {}", commentService.getClass().getSimpleName());
        return commentService;
    }

    /**
     * 获取当前服务的类型信息
     * 
     * @return 服务类型名称
     */
    public String getCurrentServiceType() {
        return commentService.getClass().getSimpleName();
    }
}
