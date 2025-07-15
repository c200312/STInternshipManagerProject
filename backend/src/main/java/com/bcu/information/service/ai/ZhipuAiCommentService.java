package com.bcu.information.service.ai;

import com.bcu.information.bean.DUser;
import com.bcu.common.util.PromptBuilder;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
/*
* 智谱模型调用
* 使用Spring AI ChatClient API进行重构
* */
@Service("zhipuAiCommentService")
public class ZhipuAiCommentService implements AiCommentService {

    private final ChatClient chatClient;

    public ZhipuAiCommentService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public String generateComment(DUser user, List<Integer> weeks) {
        String prompt = PromptBuilder.buildPrompt(user, weeks);
        System.out.println("生成评语提示词: " + prompt);
        
        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }

    @Override
    public Flux<String> generateCommentStream(DUser user, List<Integer> weeks) {
        String promptText = PromptBuilder.buildPrompt(user, weeks);
        System.out.println("流式输出提示词: " + promptText);
        
        // 使用ChatClient的流式接口，返回流式数据
        return chatClient.prompt()
                .user(promptText)
                .stream()
                .content()
                .filter(content -> content != null && !content.trim().isEmpty())
                .doOnNext(content -> System.out.println("流式输出: " + content))
                .doOnError(error -> System.err.println("流式输出错误: " + error.getMessage()));
    }

}
