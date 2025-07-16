package com.bcu.information.service.ai;

import com.bcu.information.bean.DUser;
import com.bcu.common.util.PromptBuilder;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

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

        // 直接使用简单的流式调用，不添加任何格式化参数避免缓冲
        return chatClient.prompt()
                .user(promptText)
                .stream()
                .content()
                .filter(chunk -> chunk != null && !chunk.trim().isEmpty())
                .doOnNext(chunk -> {
                    long timestamp = System.currentTimeMillis();
                    System.out.println("[" + timestamp + "] 服务层输出数据块 (长度:" + chunk.length() + "): '" + 
                        chunk.substring(0, Math.min(50, chunk.length())).replace("\n", "\\n").replace("\r", "\\r") + "'" + 
                        (chunk.length() > 50 ? "..." : ""));
                })
                .doOnSubscribe(subscription -> {
                    System.out.println("[" + System.currentTimeMillis() + "] 开始订阅流式数据");
                })
                .doOnComplete(() -> {
                    System.out.println("[" + System.currentTimeMillis() + "] 流式数据完成");
                })
                .doOnError(error -> {
                    System.err.println("[" + System.currentTimeMillis() + "] 流式数据错误: " + error.getMessage());
                });
    }

}
