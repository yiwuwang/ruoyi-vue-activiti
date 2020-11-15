package com.ruoyi.common.config;

import com.obs.services.ObsClient;
import com.obs.services.model.PutObjectResult;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 读取项目相关配置
 *
 * @author danny
 */
@Component
@ConfigurationProperties(prefix = "huawei")
public class HuaWeiConfig {
    private static boolean enabled;
    private static String endPoint;
    private static String ak;
    private static String sk;
    private static String url;
    private static String bucketName;

    public static boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        HuaWeiConfig.enabled = enabled;
    }

    public void setEndPoint(String endPoint) {
        HuaWeiConfig.endPoint = endPoint;
    }

    public void setAk(String ak) {
        HuaWeiConfig.ak = ak;
    }

    public void setSk(String sk) {
        HuaWeiConfig.sk = sk;
    }


    public void setUrl(String url) {
        HuaWeiConfig.url = url;
    }


    public void setBucketName(String bucketName) {
        HuaWeiConfig.bucketName = bucketName;
    }

    private static ObsClient getObsClient() {
        return new ObsClient(ak, sk, endPoint);
    }
    public static String saveHuaWeiOBS(String baseDir, String fileName, MultipartFile file) throws IOException {
        ObsClient obsClient = getObsClient();
        PutObjectResult putObjectResult = obsClient.putObject(bucketName, baseDir+"/"+fileName, file.getInputStream());
        return url+"/"+putObjectResult.getObjectKey();
    }
}
