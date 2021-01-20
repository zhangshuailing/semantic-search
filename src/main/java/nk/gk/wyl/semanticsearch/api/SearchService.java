package nk.gk.wyl.semanticsearch.api;

import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;
import java.util.Map;

/**
* @Description:    接口
* @Author:         zhangshuailing
* @CreateDate:     2021/1/18 14:09
* @UpdateUser:     zhangshuailing
* @UpdateDate:     2021/1/18 14:09
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
public interface SearchService {
    /**
     * 搜索前n条数据
     * @param sqlSessionTemplate
     * @param size 前n条数据
     * @return
     */
    List<Map<String,Object>> SearchLog(SqlSessionTemplate sqlSessionTemplate,int size) throws Exception;

    /**
     * 复合检索
     * @param sqlSessionTemplate
     * @param map 参数
     * @return 返回结果
     * @throws Exception 异常信息
     */
    Map<String,Object> page(SqlSessionTemplate sqlSessionTemplate,Map<String,Object> map) throws Exception;

    /**
     * 相似推荐
     * @param sqlSessionTemplate
     * @param q
     * @return
     * @throws Exception
     */
    List<String> relatedRec(SqlSessionTemplate sqlSessionTemplate,String q) throws Exception;

}
