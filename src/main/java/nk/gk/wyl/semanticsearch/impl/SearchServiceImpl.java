package nk.gk.wyl.semanticsearch.impl;

import nk.gk.wyl.semanticsearch.api.SearchService;
import nk.gk.wyl.semanticsearch.config.SysConfig;
import nk.gk.wyl.semanticsearch.util.IkUtil;
import nk.gk.wyl.sql.api.SqlService;
import nk.gk.wyl.sql.util.QueryUtil;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
* @Description:    接口
* @Author:         zhangshuailing
* @CreateDate:     2021/1/18 14:49
* @UpdateUser:     zhangshuailing
* @UpdateDate:     2021/1/18 14:49
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
@Service
public class SearchServiceImpl implements SearchService {

    private Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);

    @Autowired
    private SqlService sqlService;

    /**
     * 搜索前n条数据
     *
     * @param sqlSessionTemplate
     * @param size 前n条数据
     * @return
     */
    @Override
    public List<Map<String, Object>> SearchLog(SqlSessionTemplate sqlSessionTemplate,int size)throws Exception {
        if(size <=0)
            size=10;
        String sql = "select *  from  ques_info order by orderby LIMIT "+size;
        return sqlService.findList(sqlSessionTemplate,sql);
    }



    /**
     * 复合检索
     *
     * @param sqlSessionTemplate
     * @param map                参数
     * @return 返回结果
     * @throws Exception 异常信息
     */
    @Override
    public Map<String, Object> page(SqlSessionTemplate sqlSessionTemplate, Map<String, Object> map) throws Exception {
        // 获取搜索信息
        String q = QueryUtil.checkValue(map,"q");
        // 通过q 获取分词结果
        List<String> result = IkUtil.getStringList(q);
        logger.info("分词结果"+result);


        String search_sql = "";
        String sql = "select  ai.id,ai.ques_id,ai.xsd,DATE_FORMAT(ai.create_time,'%Y/%m/%d %H:%i:%s') create_time,ai.click_num,ai.pri_img,ai.file_path,CONVERT (content USING utf8) AS content  " +
                "from ques_info qi,answer_info ai where ai.ques_id=qi.id ";

        String orderby_sql =" order by xsd  desc";
        // 判断前端是否传递排序
        String ob = "";
        try{
            Map<String,Integer> order = (Map<String, Integer>) map.get("order");
            if(order!=null && !order.isEmpty()){
                for (Map.Entry<String,Integer> key:order.entrySet()){
                    // 倒序
                    if(key.getValue()==1){
                        if("".equals(ob)){
                            ob = "order by " + " "+key.getKey()+" desc";
                        }else{
                            ob = ob + ","+ " "+key.getKey()+" desc";
                        }
                    }else{
                        // 正序
                        if("".equals(ob)){
                            ob = "order by " + " "+key.getKey()+" asc";
                        }else{
                            ob = ob + ","+ " "+key.getKey()+" asc";
                        }
                    }
                }
            }else{
                ob = "";
            }
        }catch (Exception e){
            throw new Exception("排序参数错误");
        }

        if(!"".equals(ob)){
            orderby_sql = ob;
        }

        Map<String, Object> data = null;
        int size = 0;
        // 先匹配问题
        {
            search_sql = sql + " and qi.name ='"+q+"' " + orderby_sql;
            data = sqlService.page(sqlSessionTemplate,map,search_sql);
            Map<String, Integer> pager = (Map<String, Integer>) data.get("pager");
            size = pager.get("total");
        }
        if(size ==0){
            // blob 转字符串
            // CONVERT (*** USING utf8) AS userName
            // 若分词没有结果直接匹配问题表中的名称
            if(result==null || result.size() ==0){
                search_sql = sql + " and qi.name ='"+q+"' " + orderby_sql;
                data = sqlService.page(sqlSessionTemplate,map,search_sql);
            }else{
                // 按照关键字全匹配
                String like_sql = "";
                for (int i = 0; i < result.size(); i++) {
                    // key_words like '%姚明%' and key_words like '%身高%'
                    if(i ==0){
                        like_sql =" key_words like '%"+result.get(i)+"%' ";
                    }else{
                        like_sql =like_sql + " and key_words like '%"+result.get(i)+"%' ";
                    }
                }
                search_sql = sql + " and  " +like_sql+ orderby_sql;
                data = sqlService.page(sqlSessionTemplate,map,search_sql);
                Map<String, Integer> pager = (Map<String, Integer>) data.get("pager");
                int total_num = pager.get("total");
                if(total_num==0){
                    search_sql = "";
                    // 若关键字全匹配不到 匹配单个就可以
                    for (int i = 0; i < result.size(); i++) {
                        // key_words like '%姚明%' and key_words like '%身高%'
                        if(i ==0){
                            like_sql =" key_words like '%"+result.get(i)+"%' ";
                        }else{
                            like_sql =like_sql + " or  key_words like '%"+result.get(i)+"%' ";
                        }
                    }
                    search_sql = sql + " and  (" +like_sql+")"+ orderby_sql;
                    data = sqlService.page(sqlSessionTemplate,map,search_sql);
                }
            }
        }


        List<Map<String,Object>> data_= (List<Map<String, Object>>) data.get("data");
        for (int i = 0; i < data_.size(); i++) {
            Map<String,Object> map1 = data_.get(i);
            map1.put("answer","");
            try{
                String content = map1.get("content").toString();
                String[] str = content.split("\n");
                if(str.length>=3){
                    String value = str[0]+"\n"+str[1]+"\n";
                    map1.put("answer",value);
                    map1.put("content",content.replaceFirst(value,""));
                }else if(str.length==2){
                    String value = str[0]+"\n";
                    map1.put("answer",value);
                    map1.put("content",content.replaceFirst(value,""));
                }
            }catch (Exception e){
                continue;
            }


        }
        return data;
    }

    /**
     * 相似推荐
     *
     * @param sqlSessionTemplate
     * @param q
     * @return
     * @throws Exception
     */
    @Override
    public List<String> relatedRec(SqlSessionTemplate sqlSessionTemplate, String q) throws Exception {
        // 先分词
        List<String> result = new ArrayList<>();
        // 通过q 获取分词结果
        List<String> ik_result = IkUtil.getStringList(q);
        logger.info("分词结果"+ik_result);
        // 通过分词反查数据
        String search_sql = "";
        // 查询数据
        List<Map<String,Object>> list ;
        String sql = "select related_keys  from ques_info  where 1=1 ";
                // "key_words like '%姚明%' and key_words like '%身高%' ";

        int size = 0;
        // 先匹配问题
        {
            search_sql = sql + " and name ='"+q+"' ";
            list = sqlService.findList(sqlSessionTemplate,search_sql);
            size = list.size();
        }
        if(size ==0){
            if(ik_result==null || ik_result.size() ==0){
                search_sql = sql + " and name ='"+q+"' ";
                list = sqlService.findList(sqlSessionTemplate,search_sql);
            }else{
                // 按照关键字全匹配
                String like_sql = "";
                for (int i = 0; i < ik_result.size(); i++) {
                    // key_words like '%姚明%' and key_words like '%身高%'
                    if(i ==0){
                        like_sql ="  key_words like '%"+ik_result.get(i)+"%' ";
                    }else{
                        like_sql =like_sql + " and key_words like '%"+ik_result.get(i)+"%' ";
                    }
                }
                search_sql = sql +" and " +like_sql;
                list = sqlService.findList(sqlSessionTemplate,search_sql);
                if(list.size()==0){
                    search_sql = "";
                    // 若关键字全匹配不到 匹配单个就可以
                    for (int i = 0; i < ik_result.size(); i++) {
                        // key_words like '%姚明%' and key_words like '%身高%'
                        if(i ==0){
                            like_sql =" and key_words like '%"+ik_result.get(i)+"%' ";
                        }else{
                            like_sql =like_sql + " or  key_words like '%"+ik_result.get(i)+"%' ";
                        }
                    }
                    search_sql = sql + " and  (" +like_sql+")";
                    list = sqlService.findList(sqlSessionTemplate,search_sql);
                }
            }
        }


        HashSet<String> hSet = new HashSet<>();
        if(list.size() !=0){
            for (int i = 0; i < list.size(); i++) {
                Map<String,Object> map = list.get(i);
                String related_keys = map.get("related_keys")==null?"":map.get("related_keys").toString();
                String[] strs = related_keys.split("，");
                for (int j = 0; j < strs.length; j++) {
                    hSet.add(strs[j]);
                }
            }
        }
        result= new ArrayList<>(hSet);
        return result;
    }

    /**
     * 相似推荐
     *
     * @param sqlSessionTemplate
     * @param q
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> relatedRecObj(SqlSessionTemplate sqlSessionTemplate, String q) throws Exception {
        // 推荐的关键词
        List<String> list = relatedRec(sqlSessionTemplate,q);
        List<Map<String,Object>> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Map<String,Object> map = new HashMap<>();
            String value = list.get(i);
            map.put("name",value);
            map.put("pic",value+ SysConfig.getFileSuffix());
            result.add(map);
        }
        return result;
    }

    /**
     * 相关图片推荐
     *
     * @param sqlSessionTemplate
     * @param q
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> relatedGraph(SqlSessionTemplate sqlSessionTemplate, String q) throws Exception {
        String sql = "select *  from ques_info  where 1=1 ";
        String search_sql = "";
        int size = 0;
        String orderby = " ";
        List<Map<String,Object>> list = new ArrayList<>();
        // 先匹配问题
        {
            search_sql = sql + " and name ='"+q+"' "+orderby;
            list = sqlService.findList(sqlSessionTemplate,search_sql);
            size = list.size();
        }
        if(size ==0){
            // 先分词
            // 通过q 获取分词结果
            List<String> ik_result = IkUtil.getStringList(q);
            logger.info("分词结果"+ik_result);
            if(ik_result==null || ik_result.size() ==0){
                search_sql = sql + " and name ='"+q+"' "+orderby;
                list = sqlService.findList(sqlSessionTemplate,search_sql);
            }else{
                // 按照关键字全匹配
                String like_sql = "";
                for (int i = 0; i < ik_result.size(); i++) {
                    // key_words like '%姚明%' and key_words like '%身高%'
                    if(i ==0){
                        like_sql =" key_words like '%"+ik_result.get(i)+"%' ";
                    }else{
                        like_sql =like_sql + " and key_words like '%"+ik_result.get(i)+"%' ";
                    }
                }
                search_sql = sql +"and" +like_sql +orderby;
                list = sqlService.findList(sqlSessionTemplate,search_sql);
                if(list.size()==0){
                    search_sql = "";
                    // 若关键字全匹配不到 匹配单个就可以
                    for (int i = 0; i < ik_result.size(); i++) {
                        // key_words like '%姚明%' and key_words like '%身高%'
                        if(i ==0){
                            like_sql =" key_words like '%"+ik_result.get(i)+"%' ";
                        }else{
                            like_sql =like_sql + " or  key_words like '%"+ik_result.get(i)+"%' ";
                        }
                    }
                    search_sql = sql + " and (" +like_sql+")"+orderby;
                    list = sqlService.findList(sqlSessionTemplate,search_sql);
                }
            }
        }
        List<Map<String,Object>> result = new ArrayList<>();
        if(list!=null && list.size()>0){
            result.add(list.get(0));
        }
        return result;
    }

    /**
     * 获取goin url
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String,Object>> findList(SqlSessionTemplate sqlSessionTemplate,String id) throws Exception {
        List<Map<String,Object>> result = null;
        String table = "goin_url";
        if(!"".equals(id)){
            result = new ArrayList<>();
            result.add(sqlService.get(sqlSessionTemplate,table,id,null));
        }else{
            result = sqlService.findList(sqlSessionTemplate,table,new HashMap<>());
        }
        return result;
    }


}
