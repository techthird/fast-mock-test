/*
 * wiki.primo
 * Copyright (C) 2013-2019 All Rights Reserved.
 */
package fast.mock.test.core.constant;

import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;
import fast.mock.test.core.entity.ConfigEntity;
import com.thoughtworks.qdox.JavaProjectBuilder;

import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author chenhx
 * @version CommonConstant.java, v 0.1 2019-06-11 13:58 chenhx
 */
public class CommonConstant {
    /**
     * 生成测试类的对应类名 后缀
     */
    public static final String TEST_CLASS_SUFFIX = "Test";
    /**
     * Mock类的对应类名 后缀
     */
    public static final String MOCK_CLASS_SUFFIX = "Mock";
    /**
     * 项目文件路径
     */
    public static final String JAVA_MAIN_SRC = "/src/main/java/";
    /**
     * 测试类路径
     */
    public static final String JAVA_TEST_SRC = "/src/test/java/";

    /**
     * 日期
     */
    public static final String DATE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    /**
     * 项目中配置的属性数据
     */
    public static final ConfigEntity CONFIG_ENTITY = new ConfigEntity();

    /**
     * 获取类库
     * 预加载类信息
     */
    public static JavaProjectBuilder javaProjectBuilder = new JavaProjectBuilder();

    /**
     * URL类加载器
     */
    public static URLClassLoader urlClassLoader = null;

    /**
     * JavaClassMethod map
     * key:类名 key:方法+参数
     */
    public static Map<String, Map<String,Method>> javaClassMap = new HashMap<>();

    /**
     * 需要跳过的包的类，不进行设置默认值,默认值设置为null
     * 第三方类，无法进行加载的类需要该设置
     * 1.0.0+ 删除
     */
    @Deprecated
    public static Set<String> skipPackage = new HashSet<>();

    public static Method getJavaMethod(String className, JavaMethod javaMethod) throws ClassNotFoundException {
        StringBuffer parameterType = new StringBuffer();
        javaMethod.getParameters().forEach(parameter -> { parameterType.append(parameter.getCanonicalName()); });

        String keyMethod = javaMethod.getName() + parameterType.toString();
        Map<String, Method> methodMap = javaClassMap.get(className);
        if (methodMap == null) {
            methodMap = new HashMap<>();
            Method[] declaredMethods = CommonConstant.urlClassLoader.loadClass(className).getDeclaredMethods();
            for (Method declaredMethod : declaredMethods) {
                StringBuffer parameterTypeTmp = new StringBuffer();
                Arrays.stream(declaredMethod.getParameters()).forEach(parameter -> { parameterTypeTmp.append(parameter.getType().getName()); });
                methodMap.put(declaredMethod.getName() + parameterTypeTmp.toString(), declaredMethod);
            }
            javaClassMap.put(className, methodMap);
        }
        return javaClassMap.get(className).get(keyMethod);
    }

}
