/*
* fast-mock-test
*/
package ${javaClassDTO.packageName};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.alibaba.testable.core.annotation.MockDiagnose;
import com.alibaba.testable.core.model.ClassType;
import com.alibaba.testable.core.annotation.MockMethod;
import com.alibaba.testable.core.model.LogLevel;
import com.alibaba.testable.core.tool.TestableTool;
import com.alibaba.testable.core.annotation.MockWith;
import org.junit.*;
import java.math.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.*;
import java.lang.*;
import static org.mockito.ArgumentMatchers.*;
<#list javaClassDTO.javaImplementsDTOList as implements>
import ${implements.type};
</#list>

/**
* ${javaClassDTO.modelNameUpperCamelTestClass}
*
* @author ${javaClassDTO.author!''}
* @date ${javaClassDTO.date!''}
*/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@MockWith(value=${javaClassDTO.modelNameUpperCamelMockClass}.class, treatAs = ClassType.TestClass)
public class ${javaClassDTO.modelNameUpperCamelTestClass} /*extends BaseTestCase*/ {

    @Autowired
    private ${javaClassDTO.modelNameUpperCamel} ${javaClassDTO.modelNameLowerCamel};

<#--遍历方法-->
<#list javaClassDTO.javaMethodDTOList as method>
    @Test
    public void ${method.methodTestName}() <#list method.javaMethodExceptionsDTOList as exceptions><#if exceptions_index==0>throws ${exceptions.type}<#else>,${exceptions.type}</#if></#list>{
        /** 1、设置Case名称 */
        TestableTool.MOCK_CONTEXT.put("case", "case1");

        /** 2、组装测试接口的参数 */
    <#list method.javaParameterDTOList as parameter>
    <#--判断是否是自定义参数-->
        <#if parameter.customType>
        <#--获取内部参数-->
            <#if parameter.isInterface>
            <#--参数是接口-->
        ${parameter.type} ${parameter.name} =
                <#if parameter.value??>
                    ${parameter.value}
                <#else >
                <#--判断是否能够使用简称-->
                    <#if parameter.subClassCanUserType>
                new ${parameter.subClassType}();
                    <#else >
                new ${parameter.subClassFullyType}();
                    </#if>
                </#if>
            <#else >
        ${parameter.type} ${parameter.name} = <#if parameter.value??>${parameter.value}<#else >new ${parameter.type}()</#if>;
            </#if>
            <#list parameter.javaParameterDTOList as cParameter>
            <#--设置内部值-->
        ${parameter.name}.set${cParameter.upName}(<#if cParameter.value??>${cParameter.value}<#else >new ${cParameter.type}()</#if>);
            </#list>
        <#else >

        <#--获取内部参数-->
            <#if parameter.isInterface>
            <#--参数是接口-->
        ${parameter.type} ${parameter.name} =
                <#if parameter.value??>
                    ${parameter.value}
                <#else >
                <#--判断是否能够使用简称-->
                    <#if parameter.subClassCanUserType>
                new ${parameter.subClassType}();
                    <#else >
                new ${parameter.subClassFullyType}();
                    </#if>
                </#if>
            <#else >
        ${parameter.type} ${parameter.name} = <#if parameter.value??>${parameter.value}<#else >new ${parameter.type}()</#if>;
            </#if>
        </#if>
    </#list>

        /** 3、调用方法 */
        /* ${method.comment!""} */
        <#if !(method.returnType?contains("void"))>${method.returnType} obj = </#if>${javaClassDTO.modelNameLowerCamel}.${method.methodName}(<#list method.javaParameterDTOList as parameter><#if parameter_index==0>${parameter.name}<#else>,${parameter.name}</#if></#list>);

        /** 4、断言 */
        /*
        Assert.assertNotNull(obj);
        Assert.assertEquals(new Object(), obj);
        */
    }
</#list>

}
