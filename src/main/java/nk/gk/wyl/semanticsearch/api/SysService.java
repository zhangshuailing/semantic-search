package nk.gk.wyl.semanticsearch.api;

import org.mybatis.spring.SqlSessionTemplate;

public interface SysService {
    /**
     * 初始化参数
     * @param sqlSessionTemplate
     */
    void init(SqlSessionTemplate sqlSessionTemplate);
}
