package com.bcu.common.util;

import com.bcu.information.bean.DCompany;
import com.bcu.information.bean.DDiary;
import com.bcu.information.bean.DUser;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/*
* 工具类 ai使用
* 后续如需添加模型时使用这个类可减少代码冗余
* */

public class PromptBuilder {

    public static String buildPrompt(DUser user, List<Integer> weeks) {
        String companyName = user.getCompany().stream()
                .findFirst()
                .map(DCompany::getName)
                .orElse("未知公司");

        List<String> selectedDiaries = user.getDiary().stream()
                .filter(d -> weeks.contains(d.getWeek()))
                .sorted(Comparator.comparing(DDiary::getWeek))
                .map(d -> "第" + d.getWeek() + "周周记：" + d.getContent())
                .collect(Collectors.toList());
        return String.format(
                "根据学生实习内容周记，撰写指导教师意见，要指出建议，字数200字，使用第三人称撰写：\n学生实习公司：%s\n以下是学生的实习周记：\n%s",
                companyName, String.join("\n", selectedDiaries)
        );
    }
}
