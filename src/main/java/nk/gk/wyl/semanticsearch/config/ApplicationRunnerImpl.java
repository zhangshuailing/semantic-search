package nk.gk.wyl.semanticsearch.config;

import nk.gk.wyl.semanticsearch.api.SysService;
import nk.gk.wyl.semanticsearch.controller.SearchController;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ApplicationRunnerImpl implements ApplicationRunner {
    @Resource
    private SysService sysService;
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("项目启动成功");
        // new SearchController("11");
        System.out.println("开始加载参数");
        sysService.init(sqlSessionTemplate);
    }
}
