package com.bcu.information.util;

import com.bcu.information.bean.Internship.DCompany;
import com.bcu.information.bean.Internship.DDiary;
import com.bcu.information.bean.Internship.DUser;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/*
* 工具类
* 后续如需添加模型时使用这个类可减少代码冗余
* */

public class PromptBuilder {

    public static String buildPrompt(DUser user, List<Integer> weeks) {
        String studentName = user.getName();
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
                "请以教师的身份根据以下内容生成一段300字评语：\n学生姓名：%s\n实习公司：%s\n以下是学生的实习周记：\n%s",
                studentName, companyName, String.join("\n", selectedDiaries)
        );
    }
}
