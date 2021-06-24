/*
 * wiki.primo
 * Copyright (C) 2013-2019 All Rights Reserved.
 */
package fast.mock.test.maven.plugin;

import com.alibaba.fastjson.JSON;
import fast.mock.test.core.constant.CommonConstant;
import fast.mock.test.core.util.FileUtils;
import fast.mock.test.core.util.UUIDUtils;
import fast.mock.test.core.json.JsonConfig;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.InputStream;

/**
 * 初始化配置文件
 *
 * @author chenhx
 * @version InitPlugin.java, v 0.1 2019-06-10 14:16 chenhx
 */
public class DownFile {

    private static Log log = new SystemStreamLog();

    private static String tmpFtlFileName = "fast-test-" + UUIDUtils.getID() + ".ftl";
    /**
     * 下载模板文件
     */
    public static void downTemplateFile() {
        if (!CommonConstant.CONFIG_ENTITY.getIsDownloadTemplateFile()) {
            CommonConstant.CONFIG_ENTITY.setConfigPath("");
            CommonConstant.CONFIG_ENTITY.setConfigFileName(tmpFtlFileName);
        }

        // 下载文件到本地
        String configPath = CommonConstant.CONFIG_ENTITY.getConfigPath();
        String path = CommonConstant.CONFIG_ENTITY.getBasedir().getPath() + configPath;
        try {
            FileUtils.downLoadFile(CommonConstant.CONFIG_ENTITY.getConfigFileName(), path, "fast-mock.ftl");
        } catch (Exception e) {
            log.error("下载配置文件出现异常", e);
        }
    }
    public static void removeTemplateFile() {
        if (!CommonConstant.CONFIG_ENTITY.getIsDownloadTemplateFile()) {
            String configPath = CommonConstant.CONFIG_ENTITY.getConfigPath();
            String path = CommonConstant.CONFIG_ENTITY.getBasedir().getPath() + configPath;
            File file = new File(path + File.separator + CommonConstant.CONFIG_ENTITY.getConfigFileName());
            if (file.exists()) {
                boolean delete = file.delete();
                if(!delete){
                    log.error("删除临时配置文件失败，路径：" + file.getPath() + ",文件名：" + file.getName());
                }else{
                    log.info("删除临时配置文件成功，路径：" + file.getPath() + ",文件名：" + file.getName());
                }
            }
        }
    }

    /**
     * 下载json文件
     */
    public static void downJsonFile() {
        try {
            String jsonStr = "";
            if (!CommonConstant.CONFIG_ENTITY.getIsDownloadJsonFile()) {
                //创建目录
                ClassPathResource classPathResource = new ClassPathResource("fast-mock.json");
                //得到输入流
                InputStream inputStream = classPathResource.getInputStream();
                jsonStr = FileUtils.readInputStreamToString(inputStream);
            }else {
                String configPath = CommonConstant.CONFIG_ENTITY.getJsonConfigPath();
                String path = CommonConstant.CONFIG_ENTITY.getBasedir().getPath() + configPath;
                FileUtils.downLoadFile(CommonConstant.CONFIG_ENTITY.getJsonConfigFileName(), path, "fast-mock.json");

                //下载说明
//                FileUtils.downLoadFile("fast-mock-test-description.md", path, "fast-mock-test-description.md");

                //读取json配置到实体中
                jsonStr = FileUtils.readFileToString(path + File.separator + CommonConstant.CONFIG_ENTITY.getJsonConfigFileName());
            }
            log.info("读取的json配置文件内容为:" + jsonStr);
            JsonConfig jsonConfig = JSON.parseObject(jsonStr, JsonConfig.class);
            CommonConstant.CONFIG_ENTITY.setJsonConfig(jsonConfig);
        } catch (Exception e) {
            log.error("下载JSON配置文件/读取/解析json实体出现异常", e);
        }

    }
}
