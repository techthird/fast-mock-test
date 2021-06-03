/*
 * wiki.primo
 * Copyright (C) 2013-2019 All Rights Reserved.
 */
package fast.mock.test.core.build;

import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import fast.mock.test.core.constant.CommonConstant;
import fast.mock.test.core.constant.InitConstant;
import fast.mock.test.core.dto.JavaMethodDTO;
import fast.mock.test.core.dto.JavaMethodExceptionsDTO;
import fast.mock.test.core.info.JavaClassInfo;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;

import java.util.*;

/**
 * 构建类中的方法
 *
 * @author chenhx
 * @version BuildClassMethodImpl.java, v 0.1 2019-06-27 16:37 chenhx
 */
public class BuildClassMethodImpl {

    private static Log log = new SystemStreamLog();

    /**
     * 构建测试类核心方法
     *
     * @param javaClass        被测试类的信息
     * @param javaGenInfoModel 贯穿本次类构造，记录类信息
     * @return 方法DTO集合
     */
    public static List<JavaMethodDTO> build(JavaClass javaClass,
                                            JavaClassInfo javaGenInfoModel) {
        List<JavaMethodDTO> javaMethodDTOList = new ArrayList<>();

        Map<String, Integer> methodMap = javaGenInfoModel.getMethodMap();
        //获取方法集合
        List<JavaMethod> javaMethodList = javaClass.getMethods();
        // mock方法set 用以排除
        Set<String> mockMethodSet = new HashSet<>(javaMethodList.size());
        //遍历类中的方法
        for (JavaMethod javaMethod : javaMethodList) {

            JavaMethodDTO javaMethodDTO = new JavaMethodDTO();

            //获取方法名称
            String methodName = javaMethod.getName();
            javaMethodDTO.setMethodName(methodName);

            //处理重名方法
            methodDdealingWithRenaming(methodMap, methodName, javaMethodDTO);

            //获取方法返回类型
            JavaClass returnValue = javaMethod.getReturns();
            String returnValueStr = returnValue.getFullyQualifiedName();
            javaMethodDTO.setReturnFullyType(returnValueStr);
            returnValueStr = InitConstant.getAbbreviation(returnValueStr);
            javaMethodDTO.setReturnType(returnValueStr);
            javaMethodDTO.setComment(javaMethod.getComment());

            Set<String> set = new HashSet<>();
            set.add(returnValue.getFullyQualifiedName());
            javaGenInfoModel.getImplementsJavaPackageMap().put(returnValueStr, set);


            //排除静态方法和私有方法 排除非指定的方法
            if (excludeMethod(javaMethod)) {
                continue;
            }

            //方法参数的设置，包装类设置属性 默认值
            BuildClassMethodParameteImpl.build(javaMethod, javaGenInfoModel, javaMethodDTO,javaClass);


            //方法抛出的异常
            List<JavaMethodExceptionsDTO> javaMethodExceptionsDTOS = getJavaExceptionsDTOList(javaMethod);
            javaMethodDTO.setJavaMethodExceptionsDTOList(javaMethodExceptionsDTOS);

            //Mock方法的设置
            BuildMockClassMethodImpl.buildMock(javaGenInfoModel, javaMethod, javaMethodDTO, mockMethodSet);


            javaMethodDTOList.add(javaMethodDTO);
        }
        return javaMethodDTOList;
    }


    /**
     * 处理重名方法
     *
     * @param methodMap     测试方法名称出现的次数，如果有多个重名方法，方法后面接上数字
     * @param methodName    方法名称
     * @param javaMethodDTO 方法展示信息
     */
    private static void methodDdealingWithRenaming(Map<String, Integer> methodMap, String methodName, JavaMethodDTO javaMethodDTO) {
        javaMethodDTO.setMethodTestName(methodName + "Test");
        if (methodMap.containsKey(methodName)) {
            Integer t = methodMap.get(methodName);
            javaMethodDTO.setMethodTestName(javaMethodDTO.getMethodTestName() + t);
            methodMap.put(methodName, ++t);
        } else {
            methodMap.put(methodName, 1);
        }
    }


    /**
     * 排除静态方法和私有方法  排除非指定的方法
     *
     * @param javaMethod 类方法信息
     * @return true-进行排除，false-不是静态方法和私有方法
     */
    private static boolean excludeMethod(JavaMethod javaMethod) {
        // 排除非指定的方法
        if(CommonConstant.CONFIG_ENTITY.getTestMethods()!=null){
            List<String> listMethod = Arrays.asList(CommonConstant.CONFIG_ENTITY.getTestMethods().split(","));
            if (listMethod.size() > 0 && !listMethod.contains(javaMethod.getName())) {
                return true;
            }
        }

        //是否是静态方法
        boolean mStatic = javaMethod.isStatic();
        if (mStatic) {
            return true;
        }
        boolean isPublic = javaMethod.isPublic();
        return !isPublic;
    }


    /**
     * 方法抛出的异常
     *
     * @param javaMethod
     * @return
     */
    private static List<JavaMethodExceptionsDTO> getJavaExceptionsDTOList(JavaMethod javaMethod) {
        List<JavaClass> exceptions = javaMethod.getExceptions();
        List<JavaMethodExceptionsDTO> javaMethodExceptionsDTOS = new ArrayList<>();
        for (JavaClass exception : exceptions) {
            JavaMethodExceptionsDTO javaMethodExceptionsDTO = new JavaMethodExceptionsDTO();
            javaMethodExceptionsDTO.setType(exception.getFullyQualifiedName());
            javaMethodExceptionsDTOS.add(javaMethodExceptionsDTO);
        }
        return javaMethodExceptionsDTOS;
    }


}
