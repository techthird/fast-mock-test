/*
* fast-test
*/
package ${javaClassDTO.packageName};

import org.junit.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.*;
import java.lang.*;
<#list javaClassDTO.javaImplementsDTOList as implements>
import ${implements.type};
</#list>


/**
* ${javaClassDTO.modelNameUpperCamelTestClass}
*
* Case说明：xxx
* @author ${javaClassDTO.author!''}
* @date ${javaClassDTO.date!''}
*/
@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.management.*")
public class ${javaClassDTO.modelNameUpperCamelTestClass} {
@InjectMocks
private ${javaClassDTO.modelNameUpperCamel} ${javaClassDTO.modelNameLowerCamel};
<#--遍历mock的类-->
<#list javaClassDTO.javaMockClassInfoDTOList as mockClass>
    @Mock
    private ${mockClass.type} ${mockClass.name};
</#list>

<#--遍历方法-->
<#list javaClassDTO.javaMethodDTOList as method>
    @Test
    public void ${method.methodTestName}() <#list method.javaMethodExceptionsDTOList as exceptions><#if exceptions_index==0>throws ${exceptions.type}<#else>,${exceptions.type}</#if></#list>{
        /** 1、组装测试接口的参数 */
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

        /** 2、MOCK接口 */
    <#if method.javaMockMethodInfoDTOList??>
    <#--定义变量-->
        <#assign mockThis = 0>
        <#list method.javaMockMethodInfoDTOList as mockMethInfo>
            <#if mockMethInfo.fieldName==javaClassDTO.modelNameLowerCamel>
                // mock当前测试类方法
                <#if mockThis==0>
                    ${javaClassDTO.modelNameLowerCamel} = PowerMockito.spy(${javaClassDTO.modelNameLowerCamel});
                    <#assign mockThis = 1>
                </#if>
            </#if>
        // ${mockMethInfo.comment!""}
            <#if mockMethInfo.returnType=='void'>
                <#if mockMethInfo.isHaveMoreMethod>
        PowerMockito.doNothing().when(${mockMethInfo.fieldName}).${mockMethInfo.name}(<#if mockMethInfo.javaParameterDTOList??><#list mockMethInfo.javaParameterDTOList as mockParameter>${mockParameter.value}4<#if mockParameter_index==0>${mockParameter.value!"new ${mockParameter.type}()"}<#else>,${mockParameter.value!"new ${mockParameter.type}()"}</#if></#list></#if>);
                <#else >
        PowerMockito.doNothing().when(${mockMethInfo.fieldName}).${mockMethInfo.name}(<#if mockMethInfo.javaParameterDTOList??><#list mockMethInfo.javaParameterDTOList as mockParameter>${mockParameter.value}3<#if mockParameter_index==0>${mockParameter.value!"new ${mockParameter.type}()"}<#else>,${mockParameter.value!"new ${mockParameter.type}()"}</#if></#list></#if>);
                </#if>
            <#else >
                <#if mockMethInfo.isHaveMoreMethod>
        PowerMockito.doReturn(${mockMethInfo.returnMethodName}()).when(${mockMethInfo.fieldName}).${mockMethInfo.name}(<#if mockMethInfo.javaParameterDTOList??><#list mockMethInfo.javaParameterDTOList as mockParameter><#if mockParameter_index==0>${mockParameter.value!"new ${mockParameter.type}()"}<#else>,${mockParameter.value!"new ${mockParameter.type}()"}</#if></#list></#if>);
                <#else >
        PowerMockito.doReturn(${mockMethInfo.returnMethodName}()).when(${mockMethInfo.fieldName}).${mockMethInfo.name}(<#if mockMethInfo.javaParameterDTOList??><#list mockMethInfo.javaParameterDTOList as mockParameter><#if mockParameter_index==0>${mockParameter.value!"new ${mockParameter.type}()"}<#else>,${mockParameter.value!"new ${mockParameter.type}()"}</#if></#list></#if>);
                </#if>
            </#if>
        </#list>
    </#if>

        /** 3、调用方法 */
        // ${method.comment!""}
    ${method.returnType} obj = ${javaClassDTO.modelNameLowerCamel}.${method.methodName}(<#list method.javaParameterDTOList as parameter><#if parameter_index==0>${parameter.name}<#else>,${parameter.name}</#if></#list>);

        /** 4、断言 */
        /*
        Assert.assertNotNull(obj);
        Assert.assertEquals(new Object(), obj);
        */

    }
</#list>


    /** Mock返回数据-----------------------------start----------------------------- */

<#--遍历方法-->
<#list javaClassDTO.javaMethodDTOList as method>
    <#if method.javaMockMethodInfoDTOList??>
    <#--定义变量-->
        <#assign mockThis = 0>
        <#list method.javaMockMethodInfoDTOList as mockMethInfo>
            <#if mockMethInfo.returnType=='void'>
    // void
            <#else >

    /**
     * ${mockMethInfo.comment!""}
     */
    private ${mockMethInfo.genericValue} ${mockMethInfo.returnMethodName}() {
                <#if mockMethInfo.genericValue?contains("List")>
                    ${mockMethInfo.genericValue} objList = new ArrayList<>();
                    <#list mockMethInfo.javaGenericModelList as javaGenericModel>
                        <#assign objName1 ="${javaGenericModel.genericName?uncap_first}">
                        ${javaGenericModel.genericName} ${objName1} = new ${javaGenericModel.genericName}();
                        <#list javaGenericModel.javaGenericParameterDTOList as cParameter>
                        <#--设置内部值-->
                            ${objName1}.set${cParameter.upName}(<#if cParameter.value??>${cParameter.value}<#else >new ${cParameter.type}()</#if>);
                        </#list>
                    </#list>

        objList.add(${objName1});
        return objList;
                <#elseif mockMethInfo.genericValue?contains("Map")>
                    ${mockMethInfo.genericValue} mapObj = new HashMap<>();
                    <#if mockMethInfo.javaGenericModelList[0].isBaseDataType && mockMethInfo.javaGenericModelList[1].isBaseDataType>
        mapObj.put(${mockMethInfo.javaGenericModelList[0].defaultValue},${mockMethInfo.javaGenericModelList[1].defaultValue});
                    <#else>
                        <#assign thisDefaultValue ="">
                        <#list mockMethInfo.javaGenericModelList as javaGenericModel>
                            <#if javaGenericModel.isBaseDataType>
                                <#assign thisDefaultValue = "${(javaGenericModel.defaultValue)!''}">
                            <#else>
                                <#assign objName2 ="${javaGenericModel.genericName?uncap_first}">
                                ${javaGenericModel.genericName} ${objName2} = new ${javaGenericModel.genericName}();
                                <#list javaGenericModel.javaGenericParameterDTOList as cParameter>
                                <#--设置内部值-->
                                    ${objName2}.set${cParameter.upName}(<#if cParameter.value??>${cParameter.value}<#else >new ${cParameter.type}()</#if>);
                                </#list>
                            </#if>
                        </#list>
        mapObj.put(${thisDefaultValue},${objName2});
                    </#if>

        return mapObj;
                <#elseif mockMethInfo.isBaseDataType>
        return ${mockMethInfo.defaultValue};
                <#elseif mockMethInfo.javaReturnParameterDTOList?? && (mockMethInfo.javaReturnParameterDTOList?size > 0)>
                    <#assign objName3 ="${mockMethInfo.genericValue?uncap_first}">
                    ${mockMethInfo.genericValue} ${objName3} = new ${mockMethInfo.genericValue}();
                    <#list mockMethInfo.javaReturnParameterDTOList as cParameter>
                    <#--设置内部值-->
                        ${objName3}.set${cParameter.upName}(<#if cParameter.value??>${cParameter.value}<#else >new ${cParameter.type}()</#if>);
                    </#list>

        return ${objName3};
                <#else>
        // 其他Mock情况需自行实现
        return null;
                </#if>
    }
            </#if>
        </#list>
    </#if>
</#list>

    /** Mock返回数据-----------------------------end----------------------------- */

}
