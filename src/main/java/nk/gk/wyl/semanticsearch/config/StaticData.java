package nk.gk.wyl.semanticsearch.config;

import java.util.HashMap;
import java.util.Map;

public class StaticData {
    // excel中的表头和数据库中的字段对应
    private static Map<String,String> ExcelFields = null;
    private static Map<String,Map<String,String>> tableFileds = null;

    public static Map<String, String> getExcelFields() {
        return ExcelFields;
    }

    public static void setExcelFields(Map<String, String> excelFields) {
        ExcelFields = excelFields;
    }

    public static Map<String, Map<String, String>> getTableFileds() {
        return tableFileds;
    }

    public static void setTableFileds(Map<String, Map<String, String>> tableFileds) {
        StaticData.tableFileds = tableFileds;
    }

    // 构造
    public StaticData(){
        ExcelFields = new HashMap<>();
        addMap(ExcelFields);
        setExcelFields(ExcelFields);
        // 分表操作
        Map<String,String> ques_info = new HashMap<>();
        ques_info.put("name","");
        ques_info.put("key_words","");
        ques_info.put("related_keys","");
        Map<String,String> answer_info = new HashMap<>();
        answer_info.put("content","");
        answer_info.put("pri_img","");
        answer_info.put("file_path","");
        answer_info.put("xsd","");
        answer_info.put("create_time","");
        answer_info.put("click_num","");
        tableFileds = new HashMap<>();
        tableFileds.put("ques_info",ques_info);
        tableFileds.put("answer_info",answer_info);
    }
    // 添加map
    void addMap(Map<String,String> ExcelFields){
        //问题名称	关键词1	关键词2	相关关键词1	相关关键词2	问题答案	相关图片	相关文档	答案相关度	答案创建时间	答案点击率
        ExcelFields.put("问题名称","name");
        ExcelFields.put("关键词1","key_words");
        ExcelFields.put("关键词2","key_words");
        ExcelFields.put("相关关键词1","related_keys");
        ExcelFields.put("相关关键词2","related_keys");
        ExcelFields.put("问题答案","content");
        ExcelFields.put("相关图片","pri_img");
        ExcelFields.put("相关文档","file_path");
        ExcelFields.put("答案相关度","xsd");
        ExcelFields.put("答案创建时间","create_time");
        ExcelFields.put("答案点击率","click_num");
    }
}
