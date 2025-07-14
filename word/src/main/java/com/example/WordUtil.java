package com.example;

import com.deepoove.poi.XWPFTemplate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
public class WordUtil {

    /**
     * 根据模板和数据生成 Word 文件
     *
     * @param data        填充模板的数据，key 为占位符，value 为替换内容
     * @param templatePath 模板文件路径（如：src/main/webapp/static/模板.docx）
     * @param outputPath   输出文件路径（如：src/main/webapp/static/output.docx）
     * @throws IOException 如果文件操作失败
     */
    public static void generateWord(Map<String, Object> data, String templatePath, String outputPath) throws IOException {
        try (XWPFTemplate template = XWPFTemplate.compile(templatePath).render(data);
             FileOutputStream out = new FileOutputStream(outputPath)) {
            template.write(out);
        }
    }
}