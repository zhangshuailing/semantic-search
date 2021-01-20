package nk.gk.wyl.semanticsearch.util;

import org.apache.commons.lang3.time.DateFormatUtils;
import java.util.*;

/**
* @Description:    工具类
* @Author:         zhangshuailing
* @CreateDate:     2019/8/9 13:29
* @UpdateUser:     zhangshuailing
* @UpdateDate:     2019/8/9 13:29
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
public class Util {

    /**
     * title 获取唯一流水号
     * @return string
     */
    public static String getResourceId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * title 获取当前时间
     * @return  时间
     */
    public static Date getDate() {
        return new Date();
    }

    /**
     * 获取字符串时间
     * @return 返回字符串时间
     */
    public static String getStrDate(){
        return DateFormatUtils.format(getDate(),"yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 将list按照指定的key和value 转成对应的map集合
     * @param list 集合
     * @param key key值
     * @param value value值
     * @return 返回map对象
     */
    public static Map<String,Object> listToMap(List<Map<String,Object>> list,String key,String value){
        Map<String,Object> map_return = new HashMap<>();
        if(list==null){
            new RuntimeException("list 不能为空");
        }
        for (Map<String,Object> map:list){
            if(map.get(key)!=null && !"".equals(map.get(key).toString())){
                map_return.put(map.get(key).toString(),map.get(value)==null ? "" : map.get(value).toString());
            }
        }
        return map_return;
    }
    /**
     * 将list按照指定的key和value 转成对应的map集合
     * @param list 集合
     * @param key key值
     * @param value value值
     * @return 返回map对象
     */
    public static Map<String,Object> _listToMap(List<Map> list,String key,String value){
        Map<String,Object> map_return = new HashMap<>();
        if(list==null){
            new RuntimeException("list 不能为空");
        }
        for (Map map:list){
            if(map.get(key)!=null && !"".equals(map.get(key).toString())){
                map_return.put(map.get(key).toString(),map.get(value)==null ? "" : map.get(value).toString());
            }
        }
        return map_return;
    }
    /**
     * 将list按照指定的key和value 转成对应的map集合
     * @param list 集合
     * @param key key值
     * @param value value值
     * @return 返回map对象
     */
    public static Map<String,Object> listToMap_(List<Map> list,String key,String value){
        Map<String,Object> map_return = new HashMap<>();
        if(list==null){
            new RuntimeException("list 不能为空");
        }
        for (Map<String,Object> map:list){
            if(map.get(key)!=null && !"".equals(map.get(key).toString())){
                map_return.put(map.get(key).toString(),map.get(value)==null ? "" : map.get(value).toString());
            }
        }
        return map_return;
    }
    /**
     * 新增的添加公共的数据（create_time,create_by）
     * @param map 组合参数
     */
    public static void addMap(Map<String,Object> map){
        map.put("create_time",getStrDate());
        map.put("create_by",map.get("uid") == null ? "" : map.get("uid").toString());
    }

    /**
     * 新增的添加公共的数据（create_time,create_by）
     * @param map 组合参数
     */
    public static void addMap(Map<String,Object> map,String uid){
        if(map.get("create_time")==null ||"".equals(map.get("create_time").toString())){
            map.put("create_time",getStrDate());
        }
        if(map.get("create_by")==null||"".equals(map.get("create_by").toString())){
            map.put("create_by",uid);
        }

    }

    /**
     * 新增的添加公共的数据（create_time,create_by）
     * @param map 组合参数
     */
    public static void editMap(Map<String,Object> map){
        map.put("update_time",getStrDate());
        map.put("update_by",map.get("uid") == null ? "" : map.get("uid").toString());
    }
    /**
     * 新增的添加公共的数据（create_time,create_by）
     * @param map 组合参数
     */
    public static void editMap(Map<String,Object> map,String uid){
        map.put("update_time",getStrDate());
        map.put("update_by",uid);
    }

    /**
     * 添加审核时间和审核人
     * @param map
     * @param uid
     */
    public static void checkMap(Map<String,Object> map,String uid){
        map.put("check_time",getStrDate());
        map.put("check_user",uid);
    }


    /**
     * 添加审核时间和审核人
     * @param map
     */
    public static void checkMap(Map<String,Object> map){
        String check_user = map.get("check_user")==null?"":map.get("check_user").toString();
        map.put("check_time",getStrDate());
    }

    /**
     * 添加到提交人和提交时间
     * @param map
     * @param uid
     */
    public static void submitMap(Map<String,Object> map,String uid){
        map.put("submit_time",getStrDate());
        map.put("submit_by",uid);
    }

    /**
     * 第三方调用接口，保存反馈信息
     * @param map
     * @param uid
     */
    public static void feedbackMap(Map<String,Object> map,String uid){
        map.put("feedback_time",getStrDate());
        map.put("feedback_by",uid);
    }

}
