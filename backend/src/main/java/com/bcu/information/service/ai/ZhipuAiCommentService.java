package com.bcu.information.service.ai;

import com.bcu.information.bean.DUser;
import com.bcu.information.util.PromptBuilder;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.stereotype.Service;

import java.util.List;
/*
* 智谱模型调用
*
* */
@Service("zhipuAiCommentService")
public class ZhipuAiCommentService implements AiCommentService {

    private final ZhiPuAiChatModel chatModel;

    public ZhipuAiCommentService(ZhiPuAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Override
    public String generateComment(DUser user, List<Integer> weeks) {
        String prompt = PromptBuilder.buildPrompt(user, weeks);
        System.out.println(prompt);
        return chatModel.call(prompt);
    }
}
