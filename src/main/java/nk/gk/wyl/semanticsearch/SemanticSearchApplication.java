package nk.gk.wyl.semanticsearch;

import nk.gk.wyl.excel.api.ExcelService;
import nk.gk.wyl.excel.impl.ExcelServiceImpl;
import nk.gk.wyl.sql.api.SqlService;
import nk.gk.wyl.sql.impl.SqlServiceImpl;
import org.apdplat.word.WordSegmenter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;

import javax.servlet.MultipartConfigElement;
import java.io.File;

// 引入外部的jar 里面有mapper.xml 需要MapperScan扫描mapper  *  表示多层
@MapperScan(basePackages = {"nk.gk.wyl.sql.mapper", "cn.golaxy.jg.module.**.mapper"})
@SpringBootApplication
public class SemanticSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SemanticSearchApplication.class, args);
    }
    @Bean
    public SqlService getSqlService(){
        return new SqlServiceImpl();
    }
    @Bean
    public ExcelService getExcelService(){
        return new ExcelServiceImpl();
    }
    @Bean
    public WordSegmenter getEordSegmenter(){
        return new WordSegmenter();
    }

}
