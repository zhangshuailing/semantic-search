package nk.gk.wyl.semanticsearch.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import nk.gk.wyl.excel.api.ExcelService;
import nk.gk.wyl.excel.entity.result.Response;
import nk.gk.wyl.semanticsearch.api.SysService;
import nk.gk.wyl.semanticsearch.entity.DeleteTables;
import nk.gk.wyl.sql.api.SqlService;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("excel")
@Api(tags = "Excel导入")
public class ExcelController {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    private ExcelService excelService;
    @Autowired
    private SqlService sqlService;
    @Autowired
    private nk.gk.wyl.semanticsearch.api.ExcelService excelServiceSelf;

    @PostMapping(value = "uploadTxt",consumes = "multipart/*",headers = "content-type=multipart/form-data")
    @ApiOperation(value = "word文件上传返回文本")
    public @ResponseBody
    Response uploadTxt(MultipartFile file, @RequestParam(value = "key_row_line",defaultValue = "1") int key_row_line){
        synchronized (this){
            try {
                Map<String, List<Map<String, String>>> result = excelService.getDataByExcelStandard(file,key_row_line);
                // 数据入库
                List<Map<String, String>> list = result.get("Sheet1");
                return new Response().success(excelServiceSelf.writeData(sqlSessionTemplate,list));
            } catch (Exception e) {
                e.printStackTrace();
                return new Response().error(e.getMessage());
            }
        }

    }

    @PostMapping(value = "delete")
    @ApiOperation(value = "清除表")
    public @ResponseBody Response delete(@RequestBody DeleteTables deleteTables){
        try {
            List<String> list = deleteTables.getTables();
            if(list==null || list.size() ==0){
                return new Response().error("tables不能为null或空集合");
            }
            return new Response().success(sqlService.deleteFromTable(sqlSessionTemplate,list));
        } catch (Exception e) {
            e.printStackTrace();
            return new Response().error(e.getMessage());
        }
    }

    @Autowired
    private SysService sysService;
    /**
     * 搜索推荐
     * @return 返回数据
     */
    @GetMapping("clean")
    @ApiOperation(value = "重置参数")
    public @ResponseBody
   Response clean(){
        try {
            sysService.init(sqlSessionTemplate);
            return new Response().success(true);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response().error(e.getMessage());
        }
    }

    /**
     * 搜索推荐
     * @return 返回数据
     */
    //@GetMapping("copy")
    //@ApiOperation(value = "复制问题参数")
    public @ResponseBody Response copy(){
        try {
            sysService.init(sqlSessionTemplate);
            return new Response().success(true);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response().error(e.getMessage());
        }
    }
}
