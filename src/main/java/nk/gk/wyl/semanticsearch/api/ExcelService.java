package nk.gk.wyl.semanticsearch.api;

import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;
import java.util.Map;

public interface ExcelService {
    // 写入数据
    boolean writeData(SqlSessionTemplate sqlSessionTemplate,List<Map<String,String>> list) throws Exception;

    /**
     * 获取问题名称
     * @param ques_name
     * @return
     * @throws Exception
     */
    int getId(SqlSessionTemplate sqlSessionTemplate,String ques_name)throws Exception;

    /**
     * 清除表
     * @param table
     * @return
     * @throws Exception
     */
    int deleteFromTable(SqlSessionTemplate sqlSessionTemplate,String table) throws Exception;

}
