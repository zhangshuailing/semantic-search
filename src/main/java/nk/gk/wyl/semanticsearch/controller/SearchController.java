package nk.gk.wyl.semanticsearch.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import nk.gk.wyl.semanticsearch.api.FileService;
import nk.gk.wyl.semanticsearch.api.SearchService;
import nk.gk.wyl.semanticsearch.api.SysService;
import nk.gk.wyl.semanticsearch.config.SysConfig;
import nk.gk.wyl.semanticsearch.entity.result.Response;
import nk.gk.wyl.semanticsearch.entity.search.FullSearchEntity;
import nk.gk.wyl.semanticsearch.entity.search.SearchEntity;
import nk.gk.wyl.sql.api.SqlService;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("search")
@Api(value = "语义检索接口", tags = "语义检索接口")
public class SearchController {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    @Autowired
    private SearchService searchService;

    /**
     * 搜索推荐
     * @return 返回数据
     */
    @GetMapping("rec")
    @ApiOperation(value = "搜索推荐")
    public @ResponseBody Response rec(@RequestParam(value = "num",defaultValue = "10") int num){
        try {
            return new Response().success(searchService.SearchLog(sqlSessionTemplate,num));
        } catch (Exception e) {
            e.printStackTrace();
            return new Response().error(e.getMessage());
        }
    }




    /**
     * 搜索推荐
     * @return 返回数据
     */
    @PostMapping("related-rec")
    @ApiOperation(value = "相关推荐")
    public @ResponseBody Response relatedRec(@RequestBody SearchEntity searchEntity){
        try {
            if(StringUtils.isEmpty(searchEntity.getQ())){
                return new Response().error("缺少 q 参数");
            }
            return new Response().success(searchService.relatedRecObj(sqlSessionTemplate,searchEntity.getQ()));
        } catch (Exception e) {
            e.printStackTrace();
            return new Response().error(e.getMessage());
        }
    }

    /**
     * 搜索分页
     * @return 返回数据
     */
    @PostMapping("full")
    @ApiOperation(value = "语义检索")
    public @ResponseBody Response search(@RequestBody FullSearchEntity fullSearchEntity){
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("q",fullSearchEntity.getQ());
            map.put("order",fullSearchEntity.getOrder());
            map.put("pageNo",fullSearchEntity.getPageNo()==0?1:fullSearchEntity.getPageNo());
            map.put("pageSize",fullSearchEntity.getPageSize()==0?1:fullSearchEntity.getPageSize());
            return new Response().success(searchService.page(sqlSessionTemplate,map));
        } catch (Exception e) {
            e.printStackTrace();
            return new Response().error(e.getMessage());
        }
    }
    @Autowired
    private FileService fileService;



    /**
     * 文档下载
     * @return 返回数据
     */
    @GetMapping("download")
    @ApiOperation(value = "文档下载")
    public @ResponseBody Response download(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "file_path") String filePath){
        try {
            if("".equals(filePath)){
                return new Response().error("缺少 file_path 参数");
            }
            fileService.download(request,response, SysConfig.getUploadFile_path(),filePath);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return new Response().error(e.getMessage());
        }
    }

    /**
     * 图片预览
     * @return 返回数据
     */
    @GetMapping("viewPic")
    @ApiOperation(value = "图片预览")
    public @ResponseBody Response viewPic(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "file_path") String filePath){
        try {
            if("".equals(filePath)){
                return new Response().error("缺少 file_path 参数");
            }
            fileService.viewPic(response, SysConfig.getUploadFile_path(),filePath);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return new Response().error(e.getMessage());
        }
    }


    /**
     * 获取goin url
     * @return 返回数据
     */
    @GetMapping("goin")
    @ApiOperation(value = "获取goin url")
    public @ResponseBody Response list(@RequestParam(value = "id" ,defaultValue = "",required = false) String id){
        try {
            return  new Response().success(searchService.findList(sqlSessionTemplate,id));
        } catch (Exception e) {
            e.printStackTrace();
            return new Response().error(e.getMessage());
        }
    }


    /**
     * 搜索推荐
     * @return 返回数据
     */
    @PostMapping("related-graph")
    @ApiOperation(value = "相关图片推荐")
    public @ResponseBody Response relatedGraph(@RequestBody SearchEntity searchEntity){
        try {
            if(StringUtils.isEmpty(searchEntity.getQ())){
                return new Response().error("缺少 q 参数");
            }
            return new Response().success(searchService.relatedGraph(sqlSessionTemplate,searchEntity.getQ()));
        } catch (Exception e) {
            e.printStackTrace();
            return new Response().error(e.getMessage());
        }
    }

}
