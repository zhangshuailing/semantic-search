package nk.gk.wyl.semanticsearch.impl;

import nk.gk.wyl.semanticsearch.api.FileService;
import nk.gk.wyl.semanticsearch.util.file.FileUtil;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {

    /**
     * 文件下载
     *
     * @param request
     * @param response
     * @param root_path
     * @param path
     * @throws Exception
     */
    @Override
    public void download(HttpServletRequest request, HttpServletResponse response, String root_path, String path) throws Exception {
        FileUtil.download(request,response,root_path,path);
    }

    /**
     * 图片预览
     *
     * @param response
     * @param root_path
     * @param path
     * @throws Exception
     */
    @Override
    public void viewPic(HttpServletResponse response, String root_path, String path) throws Exception {
        String format = path.substring(path.lastIndexOf(".") + 1);
        File file = new File(root_path + path);
        try {
            if (file.exists() && file.isFile()) {
                //读取图片文件流
                BufferedImage image = ImageIO.read(new FileInputStream(file));
                //将图片写到输出流
                ImageIO.write(image, format, response.getOutputStream());
            } else {
                file = new File(root_path + "default.png");
                //读取图片文件流
                BufferedImage image = ImageIO.read(new FileInputStream(file));
                //将图片写到输出流
                ImageIO.write(image, format, response.getOutputStream());
            }

        } catch (IOException e) {
            throw new Exception("预览失败");
        }
    }
}
