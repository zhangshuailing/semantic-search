package nk.gk.wyl.semanticsearch.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface FileService {

    /**
     * 文件下载
     * @param request
     * @param response
     * @param root_path
     * @param path
     * @throws Exception
     */
    void download(HttpServletRequest request,
                  HttpServletResponse response,
                  String root_path,String path) throws Exception;

    /**
     * 图片预览
     * @param response
     * @param root_path
     * @param path
     * @throws Exception
     */
    void viewPic(HttpServletResponse response,
                 String root_path,String path) throws Exception;


}
