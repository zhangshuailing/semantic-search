package nk.gk.wyl.semanticsearch.impl;

import nk.gk.wyl.semanticsearch.api.SysService;
import nk.gk.wyl.semanticsearch.config.SysConfig;
import nk.gk.wyl.sql.api.SqlService;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SysServiceImpl implements SysService {
    @Autowired
    private SqlService sqlService;
    @Value("${uploadFile_path}")
    private String path;
    /**
     * 初始化参数
     *
     * @param sqlSessionTemplate
     */
    @Override
    public void init(SqlSessionTemplate sqlSessionTemplate) {
        try {
            Map<String,Object> obj  = sqlService.get(sqlSessionTemplate,"config_info","path",null);
            if(obj!=null && !obj.isEmpty()){
                String name = obj.get("name").toString();
                if("".equals(name)){
                    SysConfig.setUploadFile_path(path);
                }else{

                    SysConfig.setUploadFile_path(name);
                }
            }else {
                SysConfig.setUploadFile_path(path);
            }
        } catch (Exception e) {
            SysConfig.setUploadFile_path(path);
            e.printStackTrace();
        }

        try {
            Map<String,Object> obj  = sqlService.get(sqlSessionTemplate,"config_info","file_suffix",null);
            if(obj!=null && !obj.isEmpty()){
                String name = obj.get("name").toString();
                SysConfig.setFileSuffix(name);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 初始化参数
     *
     * @param sqlSessionTemplate
     */
    @Override
    public void copy(SqlSessionTemplate sqlSessionTemplate) {
        try {
            Map<String,Object> obj  = sqlService.get(sqlSessionTemplate,"ques_info","path",null);
            if(obj!=null && !obj.isEmpty()){
                Map<String,Object> save = new HashMap<>();
                save.put("image",obj.get("image"));
                save.put("goin_id",obj.get("goin_id"));
                //sqlService.update(sqlSessionTemplate,)
            }else {

            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
