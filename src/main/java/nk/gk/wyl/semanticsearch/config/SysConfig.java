package nk.gk.wyl.semanticsearch.config;

/**
 * 系统参数配置
 */
public class SysConfig {
    // 文件路径
    private static String uploadFile_path;

    public static String getUploadFile_path() {
        return uploadFile_path;
    }

    public static void setUploadFile_path(String uploadFile_path) {
        SysConfig.uploadFile_path = uploadFile_path;
    }
}
