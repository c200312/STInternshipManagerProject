package com.example;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.Pictures;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Word {
    public static void generateByFile() throws IOException {
        // 准备数据
        Map<String, Object> data = new HashMap<>();
        
        // 简单文本替换
        data.put("sname", "张三");
        data.put("sid", "20221204475");
        data.put("cname", "a公司");
        data.put("class", "1");
        data.put("internshiptime", "2024.1.1-2025.1.1");
        data.put("cintroduction","中国银行是中国持续经营时间最久的银行。1912年2月正式成立，先后行使中央银行、国际汇兑银行和国际贸易专业银行职能。1949年以后，长期作为国家外汇外贸专业银行，统一经营管理国家外汇，开展国际贸易结算、侨汇和其他非贸易外汇业务。1994年改组为国有独资商业银行，全面提供各类金融服务，发展成为本外币兼营、业务品种齐全、实力雄厚的大型商业银行。2006年率先成功在香港联交所和上交所挂牌上市，成为国内首家“A+H”上市银行。中国银行是2008年北京夏季奥运会和2022年北京冬季奥运会唯一官方银行合作伙伴，是中国唯一的“双奥银行”。2011年，中国银行成为新兴经济体中首家全球系统重要性银行，目前已连续14年入选，国际地位、竞争能力、综合实力跻身全球大型银行前列。当前，中国银行对标党中央决策部署，以服务实体经济为根本宗旨，以防控风险为永恒主题，以巩固扩大全球化优势、提升全球布局能力为首要任务，着力做好科技金融、绿色金融、普惠金融、养老金融、数字金融五篇大文章，在实干笃行中助力金融强国建设。\n" +
                "\n" +
                "中国银行是中国全球化和综合化程度最高的银行，在中国境内及境外64个国家和地区设有机构，中银香港、澳门分行担任当地的发钞行。中国银行拥有比较完善的全球服务网络，形成了以公司金融、个人金融和金融市场等商业银行业务为主体，涵盖投资银行、直接投资、证券、保险、基金、飞机租赁、资产管理、金融科技、金融租赁等多个领域的综合金融服务体系，为客户提供“一点接入、全球响应、综合服务”的金融解决方案。\n" +
                "\n" +
                "中国银行是拥有崇高使命感和责任感的银行。成立113年来，中国银行始终恪守“为社会谋福利、为国家求富强”的历史使命，形成了宝贵的精神财富，与诚实守信、以义取利、稳健审慎、守正创新、依法合规的中国特色金融文化同向同频、和声共鸣。在全面建设社会主义现代化国家的新征程上，中国银行将坚持以习近平新时代中国特色社会主义思想为指导，完整准确全面贯彻新发展理念，找准落实中央决策部署和实现自身高质量发展的结合点、发力点、支撑点，当好贯彻党中央决策部署的实干家、服务实体经济的主力军、服务双循环新发展格局的排头兵、维护金融稳定的压舱石、做优做强国有大型金融机构的行动派，坚定不移走好中国特色金融发展之路，不断开创中国银行高质量发展新局面，为以中国式现代化全面推进强国建设、民族复兴伟业作出更大贡献。");
        data.put("stnameimg", Pictures.ofUrl("https://pic1.zhimg.com/v2-a8d7decc87c88e686d096b666eeb3f02_r.jpg?source=1940ef5c").size(100, 20).create());

        // 渲染模板
        XWPFTemplate template = XWPFTemplate.compile("src/main/webapp/static/模板.docx").render(data);
        template.writeAndClose(new FileOutputStream("src/main/webapp/static/output.docx"));
    }
    

    
    public static void main(String[] args) throws IOException {
        generateByFile();

    }
}
