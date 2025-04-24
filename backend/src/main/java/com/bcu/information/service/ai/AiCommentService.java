package com.bcu.information.service.ai;

import com.bcu.information.bean.DUser;

import java.util.List;
/*
* 模型统一调用接口
* */
public interface AiCommentService {
    String generateComment(DUser user, List<Integer> weeks);
}
