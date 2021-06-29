/*
 * wiki.primo
 * Copyright (C) 2013-2019 All Rights Reserved.
 */
package fast.mock.test.core.util;

import fast.mock.test.core.constant.CommonConstant;
import org.apache.maven.plugin.logging.Log;
import fast.mock.test.core.log.MySystemStreamLog;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 包名，包处理工具类
 *
 * @author chenhx
 * @version PackageUtil.java, v 0.1 2019-06-10 11:02 chenhx
 */
public class PackageUtils {
    private static Log log = new MySystemStreamLog();

    /**
     * 获取某包下所有类
     *
     * @param basedir      运行项目的根路径
     * @param packageName  包名
     * @param childPackage 是否遍历子包
     * @return 类的完整名称
     */
    public static List<String> getClassName(String basedir, String packageName, boolean childPackage) {
        String packagePath = basedir + CommonConstant.JAVA_MAIN_SRC + packageName.replace(".", "/");
        return getClassNameByFile(packagePath, childPackage);
    }

    /**
     * 从项目文件获取某包下所有类
     *
     * @param filePath     文件路径 - 文件绝对路径
     * @param childPackage 是否遍历子包
     * @return 类的完整名称
     */
    public static List<String> getClassNameByFile(String filePath, boolean childPackage) {
        log.debug("从项目文件获取某包下所有类filePath=" + filePath);
        List<String> myClassName = new ArrayList<>();
        File file = new File(filePath);
        File[] childFiles = file.listFiles();
        if (childFiles == null) {
            log.error("文件下的类为空");
            return myClassName;
        }
        for (File childFile : childFiles) {
            if (childFile.isDirectory()) {
                if (childPackage) {
                    myClassName.addAll(getClassNameByFile(childFile.getPath(), childPackage));
                }
            } else {
                String childFilePath = childFile.getPath();
                if (childFilePath.endsWith(".java")) {
                    myClassName.add(childFilePath);
                }
            }
        }
        return myClassName;
    }


    /**
     * 从jar获取某包下所有类
     *
     * @param jarPath      jar文件路径
     * @param childPackage 是否遍历子包
     * @return 类的完整名称
     */
    private static List<String> getClassNameByJar(String jarPath, boolean childPackage) {
        List<String> myClassName = new ArrayList<>();
        String[] jarInfo = jarPath.split("!");
        String jarFilePath = jarInfo[0].substring(jarInfo[0].indexOf("/"));
        String packagePath = jarInfo[1].substring(1);
        try {
            JarFile jarFile = new JarFile(jarFilePath);
            Enumeration<JarEntry> entrys = jarFile.entries();
            while (entrys.hasMoreElements()) {
                JarEntry jarEntry = entrys.nextElement();
                String entryName = jarEntry.getName();
                if (entryName.endsWith(".class")) {
                    if (childPackage) {
                        if (entryName.startsWith(packagePath)) {
                            entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
                            myClassName.add(entryName);
                        }
                    } else {
                        int index = entryName.lastIndexOf("/");
                        String myPackagePath;
                        if (index != -1) {
                            myPackagePath = entryName.substring(0, index);
                        } else {
                            myPackagePath = entryName;
                        }
                        if (myPackagePath.equals(packagePath)) {
                            entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
                            myClassName.add(entryName);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myClassName;
    }

    /**
     * 从所有jar中搜索该包，并获取该包下所有类
     *
     * @param urls         URL集合
     * @param packagePath  包路径
     * @param childPackage 是否遍历子包
     * @return 类的完整名称
     */
    private static List<String> getClassNameByJars(URL[] urls, String packagePath, boolean childPackage) {
        List<String> myClassName = new ArrayList<String>();
        if (urls != null) {
            for (URL url : urls) {
                String urlPath = url.getPath();
                // 不必搜索classes文件夹
                if (urlPath.endsWith("classes/")) {
                    continue;
                }
                String jarPath = urlPath + "!/" + packagePath;
                myClassName.addAll(getClassNameByJar(jarPath, childPackage));
            }
        }
        return myClassName;
    }
}
