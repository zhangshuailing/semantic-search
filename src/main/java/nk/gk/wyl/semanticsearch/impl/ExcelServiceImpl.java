package nk.gk.wyl.semanticsearch.impl;

import nk.gk.wyl.semanticsearch.api.ExcelService;
import nk.gk.wyl.semanticsearch.config.StaticData;
import nk.gk.wyl.semanticsearch.controller.SearchController;
import nk.gk.wyl.semanticsearch.util.NumberUtil;
import nk.gk.wyl.sql.api.SqlService;
import nk.gk.wyl.sql.util.QueryUtil;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExcelServiceImpl implements ExcelService {
    @Autowired
    private SqlService sqlService;

    /**
     * 写入数据
     */
    @Override
    @Transactional
    public boolean writeData(SqlSessionTemplate sqlSessionTemplate, List<Map<String,String>> list) throws Exception{
        if(list!=null && list.size()>0){
            List<String> list_ = new ArrayList<>();
            list_ = new ArrayList<>();
            list_.add("ques_info");
            list_.add("answer_info");
            sqlService.deleteFromTable(sqlSessionTemplate,list_);
        }
        // 定义excel和数据库中字段对应
        Map<String,String> map_fields = StaticData.getExcelFields();
        // 定义 对象
        Map<String,Integer> id_map = new HashMap<>();
        // 分表操作
        Map<String,Map<String,String>> tableFileds = StaticData.getTableFileds();

        Map<String,String> ques_info = tableFileds.get("ques_info");
        Map<String,String> answer_info = tableFileds.get("answer_info");
        for (int i = 0; i < list.size(); i++) {
            Map<String,String> map = list.get(i);
            // 生成新的map
            Map<String,Object> obj = new HashMap<>();
            try{
                for (Map.Entry<String,String> key:map.entrySet()){
                    String key_key = key.getKey();
                    String key_value = key.getValue();
                    if(map_fields.containsKey(key_key)){
                        if(obj.containsKey(map_fields.get(key_key))){
                            obj.put(map_fields.get(key_key),obj.get(map_fields.get(key_key))+"，"+key_value);
                        }else{
                            obj.put(map_fields.get(key_key),key_value);
                        }
                    }
                }
                Map<String,Object> ques_info_ = new HashMap<>();
                Map<String,Object> answer_info_ = new HashMap<>();
                for (Map.Entry<String,Object> key:obj.entrySet()){
                    String key_key = key.getKey();
                    Object key_value = key.getValue();
                    if(ques_info.containsKey(key_key)){
                        ques_info_.put(key_key,key_value);
                    }else if(answer_info.containsKey(key_key)){
                        answer_info_.put(key_key,key_value);
                    }
                }
                // 先判断问题名称是否存在
                String name = ques_info_.get("name")==null?"":ques_info_.get("name").toString();
                int id = getId(sqlSessionTemplate,name);
                if(id ==-1){
                    // 获取最大的值
                    //select  max(id)  from  ques_info
                    int maxId = sqlService.getMaxId(sqlSessionTemplate,"ques_info");
                    ques_info_.put("orderby",maxId);
                    // 不存在，插入问题数据
                    id = sqlService.insertKey(sqlSessionTemplate,"ques_info",ques_info_);
                }
                answer_info_.put("ques_id",id);
                // 插入问题
                sqlService.insertKey(sqlSessionTemplate,"answer_info",answer_info_);
            }catch (Exception e){
                continue;
            }

        }
        return true;
    }

    /**
     * 获取问题名称
     *
     * @param ques_name
     * @return
     * @throws Exception
     */
    @Override
    public int getId(SqlSessionTemplate sqlSessionTemplate,String ques_name) throws Exception {
        String sql = "select  *  from ques_info where name ='"+ques_name+"'";
        List<Map<String,Object>> list = sqlService.findList(sqlSessionTemplate,sql);
        if(list.size()==0){
            return -1;
        }
        Map<String,Object> map = list.get(0);
        String id = QueryUtil.getValue(map,"id");
        if(NumberUtil.isNumeric(id)){
            return Integer.parseInt(id);
        }
        return -1;
    }

    /**
     * 清除表
     *
     * @param sqlSessionTemplate
     * @param table
     * @return
     * @throws Exception
     */
    @Override
    public int deleteFromTable(SqlSessionTemplate sqlSessionTemplate, String table) throws Exception {
        return sqlService.deleteFromTable(sqlSessionTemplate,table);
    }

}
