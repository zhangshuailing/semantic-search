package nk.gk.wyl.semanticsearch.config;

import nk.gk.wyl.semanticsearch.api.SysService;
import nk.gk.wyl.semanticsearch.controller.SearchController;
import nk.gk.wyl.semanticsearch.util.IkUtil;
import nk.gk.wyl.sql.api.SqlService;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Configuration
@Order(10)
public class InitRunner {

    @Bean
    public int run(){
        new StaticData();
        new IkUtil();
        return 0;
    }

}
