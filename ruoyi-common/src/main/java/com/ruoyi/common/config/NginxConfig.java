package com.ruoyi.common.config;

import com.obs.services.ObsClient;
import com.obs.services.model.PutObjectResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 读取项目相关配置
 *
 * @author danny
 */
@Component
@ConfigurationProperties(prefix = "nginx")
public class NginxConfig {
    private static boolean enabled;
    private static String path;
    private static String url;

    public static boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        NginxConfig.enabled = enabled;
    }



    public void setPath(String path) {
        NginxConfig.path = path;
    }


    public void setUrl(String url) {
        NginxConfig.url = url;
    }

    public static String getPath() {
        return path;
    }

    public static String getUrl() {
        return url;
    }
}
