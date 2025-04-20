package com.bcu.information.service.ai;

import org.springframework.stereotype.Component;

/*
* 模型选择器
* 后续如需增加模型，只需添加对应的服务即可
* */

@Component
public class AiServiceSelector {

    private final AiCommentService commentService;

    public AiServiceSelector(ZhipuAiCommentService commentService) {
        this.commentService = commentService;
    }

    public AiCommentService get() {
        return commentService;
    }
}
