/*
 * wiki.primo
 * Copyright (C) 2013-2019 All Rights Reserved.
 */
package fast.mock.test.maven.plugin.base;

import com.alibaba.fastjson.JSON;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import fast.mock.test.core.constant.CommonConstant;
import fast.mock.test.core.entity.ConfigEntity;
import fast.mock.test.core.util.StringUtils;

import java.io.File;

/**
 * @author chenhx
 * @version AbstractPlugin.java, v 0.1 2019-06-10 14:15 chenhx
 */
public abstract class AbstractPlugin extends AbstractMojo {

    /**
     * 运行项目的target路径
     */
    @Parameter(property = "target", defaultValue = "${project.build.directory}")
    protected String target;

    /**
     * 运行项目的根路径
     */
    @Parameter(defaultValue = "${project.basedir}")
    protected File basedir;

    /**
     * 运行项目名
     */
    @Parameter(defaultValue = "${project.name}")
    protected String project;

    /**
     * 配置文件路径
     */
    @Parameter(defaultValue = "/src/main/resources/test/template/")
    protected String configPath;

    /**
     * 配置文件名称
     */
    @Parameter(defaultValue = "fast-test.ftl")
    protected String configFileName;

    /**
     * json配置文件路径
     */
    @Parameter(defaultValue = "/src/main/resources/test/template/")
    protected String jsonConfigPath;

    /**
     * json配置文件名称
     */
    @Parameter(defaultValue = "fast-mock-test.json")
    protected String jsonConfigFileName;

    /**
     * 是否将Template配置文件下载到本地，默认true
     */
    @Parameter(defaultValue = "true")
    protected Boolean isDownloadTemplateFile;

    /**
     * 是否将json配置文件下载到本地，默认true
     */
    @Parameter(defaultValue = "true")
    protected Boolean isDownloadJsonFile;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (basedir == null) {
            ConfigEntity obj = JSON.parseObject("{\"basedir\":\"D:\\\\Workspaces\\\\YunjiProjects\\\\Other\\\\primo\\\\primo-generator-mock-test\\\\primo-generator-mock-test-demo\",\"configFileName\":\"fast-test.ftl\",\"configPath\":\"/src/main/resources/test/template/\\\\\",\"isDownloadJsonFile\":true,\"isDownloadTemplateFile\":true,\"jsonConfigFileName\":\"fast-mock-test.json\",\"jsonConfigPath\":\"/src/main/resources/test/template/\\\\\",\"project\":\"primo-generator-mock-test-demo\",\"target\":\"D:\\\\Workspaces\\\\YunjiProjects\\\\Other\\\\primo\\\\primo-generator-mock-test\\\\primo-generator-mock-test-demo\\\\target\"}", ConfigEntity.class);

            basedir=obj.getBasedir();
            target=obj.getTarget();
            project=obj.getProject();
            configPath=obj.getConfigPath();
            configFileName=obj.getConfigFileName();

            jsonConfigPath=obj.getJsonConfigPath();
            jsonConfigFileName=obj.getJsonConfigFileName();
            isDownloadTemplateFile=obj.getIsDownloadTemplateFile();
            isDownloadJsonFile=obj.getIsDownloadJsonFile();
        }

        CommonConstant.CONFIG_ENTITY.setBasedir(basedir);
        CommonConstant.CONFIG_ENTITY.setTarget(target);
        CommonConstant.CONFIG_ENTITY.setProject(project);
        CommonConstant.CONFIG_ENTITY.setConfigPath(StringUtils.addSeparator(configPath));
        CommonConstant.CONFIG_ENTITY.setConfigFileName(configFileName);

        CommonConstant.CONFIG_ENTITY.setJsonConfigPath(StringUtils.addSeparator(jsonConfigPath));
        CommonConstant.CONFIG_ENTITY.setJsonConfigFileName(jsonConfigFileName);
        CommonConstant.CONFIG_ENTITY.setIsDownloadTemplateFile(isDownloadTemplateFile);
        CommonConstant.CONFIG_ENTITY.setIsDownloadJsonFile(isDownloadJsonFile);


        getLog().info("111----"+ JSON.toJSONString(CommonConstant.CONFIG_ENTITY));
    }
}
