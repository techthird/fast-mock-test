/*
* fast-mock-test
*/
package ${javaClassDTO.packageName};

import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.testable.core.annotation.MockDiagnose;
import com.alibaba.testable.core.annotation.MockMethod;
import com.alibaba.testable.core.model.LogLevel;
import com.alibaba.testable.core.tool.TestableTool;
import org.junit.*;
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
public class ${javaClassDTO.modelNameUpperCamelTestClass} extends BaseTestCase {

    @Autowired
    private ${javaClassDTO.modelNameUpperCamel} ${javaClassDTO.modelNameLowerCamel};

    /** Case---------------------------------Start-------------------------------- */
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
        // ${method.comment!""}
        ${method.returnType} obj = ${javaClassDTO.modelNameLowerCamel}.${method.methodName}(<#list method.javaParameterDTOList as parameter><#if parameter_index==0>${parameter.name}<#else>,${parameter.name}</#if></#list>);

        /** 4、断言 */
        /*
        Assert.assertNotNull(obj);
        Assert.assertEquals(new Object(), obj);
        */
    }
</#list>

    /** Case---------------------------------End-------------------------------- */



    /** Mock数据-----------------------------Start----------------------------- */

    @MockDiagnose(LogLevel.VERBOSE)
    public static class Mock {
<#--遍历方法-->
<#list javaClassDTO.javaMethodDTOList as method>
    <#if method.javaMockMethodInfoDTOList??>
        <#list method.javaMockMethodInfoDTOList as mockMethInfo>
            <#if mockMethInfo.returnType=='void'>
    // void
            <#else >
        /**
         * ${mockMethInfo.comment!""}
         */
        @MockMethod(targetClass = ${mockMethInfo.classType}.class, targetMethod = "${mockMethInfo.returnMethodName}")
        private ${mockMethInfo.genericValue!"void"} ${mockMethInfo.returnMethodName}(<#if mockMethInfo.javaParameterDTOList??><#list mockMethInfo.javaParameterDTOList as mockParameter><#if mockParameter_index==0>${mockParameter.type!""} ${mockParameter.name}<#else>,${mockParameter.type!""} ${mockParameter.name}</#if></#list></#if>) {
                <#if mockMethInfo.isBaseDataType>
                <#--基础数据类型-->
                <#elseif mockMethInfo.javaGenericModel.isCustomClass && mockMethInfo.genericValue?contains(">>>")
                && mockMethInfo.genericValue?contains("List") && mockMethInfo.genericValue?contains("Map")>
                <#--DataRO<List<Map<Boolean,User>>> dataRO = new DataRO<>();-->
            ${mockMethInfo.javaGenericModel.objectModelList[0].objectName}<List<Map<${mockMethInfo.javaGenericModel.objectModelList[3].objectName},${mockMethInfo.javaGenericModel.objectModelList[4].objectName}>>> data = new ${mockMethInfo.javaGenericModel.objectModelList[0].objectName}<>();
                <#elseif mockMethInfo.javaGenericModel.isCustomClass && mockMethInfo.genericValue?contains(">>") && mockMethInfo.genericValue?contains("List")>
                <#--DataRO<List<User>> dataRO = new DataRO<>();-->
            ${mockMethInfo.javaGenericModel.objectModelList[0].objectName}<List<${mockMethInfo.javaGenericModel.objectModelList[2].objectName}>> data = new ${mockMethInfo.javaGenericModel.objectModelList[0].objectName}<>();
                <#elseif mockMethInfo.javaGenericModel.isCustomClass && mockMethInfo.genericValue?contains(">>") && mockMethInfo.genericValue?contains("Map")>
                <#--DataRO<Map<Integer,User>> dataRO = new DataRO<>();-->
            ${mockMethInfo.javaGenericModel.objectModelList[0].objectName}<Map<${mockMethInfo.javaGenericModel.objectModelList[2].objectName},${mockMethInfo.javaGenericModel.objectModelList[3].objectName}>> data = new ${mockMethInfo.javaGenericModel.objectModelList[0].objectName}<>();
                <#elseif mockMethInfo.javaGenericModel.isCustomClass && mockMethInfo.genericValue?contains(">")>
                <#--DataRO<User> dataRO = new DataRO<>();-->
            ${mockMethInfo.javaGenericModel.objectModelList[0].objectName}<${mockMethInfo.javaGenericModel.objectModelList[1].objectName}> data = new ${mockMethInfo.javaGenericModel.objectModelList[0].objectName}<>();
                <#elseif (mockMethInfo.genericValue) ?? && mockMethInfo.genericValue?contains(">>>")
            && mockMethInfo.genericValue?contains("List") && mockMethInfo.genericValue?contains("Map")>
                <#--List<Map<Integer,Map<Boolean,User>>> dataRO = new ArrayList<>(1);-->
            List<Map<${mockMethInfo.javaGenericModel.objectModelList[2].objectName},Map<${mockMethInfo.javaGenericModel.objectModelList[4].objectName},${mockMethInfo.javaGenericModel.objectModelList[5].objectName}>>> mapList = new ArrayList<>(1);
                <#elseif mockMethInfo.genericValue?contains(">>") && mockMethInfo.genericValue?contains("List") && mockMethInfo.genericValue?contains("Map")>
                <#--List<Map<Boolean,User>> dataRO = new ArrayList<>(1);-->
            List<Map<${mockMethInfo.javaGenericModel.objectModelList[2].objectName},${mockMethInfo.javaGenericModel.objectModelList[3].objectName}>> mapList = new ArrayList<>(1);
                <#elseif mockMethInfo.genericValue?contains("List") && mockMethInfo.genericValue?contains(">")>
                <#--List<User> dataRO = new ArrayList<>(1);-->
            ${mockMethInfo.genericValue} objList = new ArrayList<>();
                <#elseif (mockMethInfo.genericValue) ?? && mockMethInfo.genericValue?contains("Map")>
                <#--Map<Integer,User> dataRO = new HashMap<>(1);-->
            ${mockMethInfo.genericValue} mapObj = new HashMap<>(1);
                <#elseif mockMethInfo.javaGenericModel.isCustomClass>
                <#--User user = new User();-->
            ${mockMethInfo.genericValue} ${mockMethInfo.genericValue?uncap_first} = new ${mockMethInfo.genericValue}();
                <#else>
            // 其他Mock情况需自行实现
                </#if>
            switch ((String) TestableTool.MOCK_CONTEXT.get("case")) {
            case "case1":
                <#if mockMethInfo.isBaseDataType>
                return ${mockMethInfo.defaultValue};
                <#elseif mockMethInfo.javaGenericModel.isCustomClass && mockMethInfo.genericValue?contains(">>>")
                && mockMethInfo.genericValue?contains("List") && mockMethInfo.genericValue?contains("Map")>
                <#--DataRO<List<Map<Boolean,User>>> data = new DataRO<>();-->
                List<Map<${mockMethInfo.javaGenericModel.objectModelList[3].objectName}, ${mockMethInfo.javaGenericModel.objectModelList[4].objectName}>> list = new ArrayList<>(1);
                Map<${mockMethInfo.javaGenericModel.objectModelList[3].objectName}, ${mockMethInfo.javaGenericModel.objectModelList[4].objectName}> map = new HashMap<>(1);
                <#assign objName ="${mockMethInfo.javaGenericModel.objectModelList[4].objectName?uncap_first}">
                ${mockMethInfo.javaGenericModel.objectModelList[4].objectName} ${objName} = new ${mockMethInfo.javaGenericModel.objectModelList[4].objectName}();
                <#list mockMethInfo.javaGenericModel.objectModelList[4].objectParameterList as cParameter>
                <#--设置内部值-->
                ${objName}.set${cParameter.upName}(<#if cParameter.value??>${cParameter.value}<#else >new ${cParameter.type}()</#if>);
                </#list>

                map.put(${mockMethInfo.javaGenericModel.objectModelList[3].defaultValue}, ${objName});
                list.add(map);
                data.setData(list);

                return data;
                <#elseif mockMethInfo.javaGenericModel.isCustomClass && mockMethInfo.genericValue?contains(">>") && mockMethInfo.genericValue?contains("List")>
                <#--DataRO<List<User>> dataRO = new DataRO<>();-->
                List<${mockMethInfo.javaGenericModel.objectModelList[2].objectName}> list = new ArrayList<>(1);
                <#assign objName ="${mockMethInfo.javaGenericModel.objectModelList[2].objectName?uncap_first}">
                ${mockMethInfo.javaGenericModel.objectModelList[2].objectName} ${objName} = new ${mockMethInfo.javaGenericModel.objectModelList[2].objectName}();
                <#list mockMethInfo.javaGenericModel.objectModelList[2].objectParameterList as cParameter>
                <#--设置内部值-->
                ${objName}.set${cParameter.upName}(<#if cParameter.value??>${cParameter.value}<#else >new ${cParameter.type}()</#if>);
                </#list>

                list.add(${objName});
                data.setData(list);

                return data;
                <#elseif mockMethInfo.javaGenericModel.isCustomClass && mockMethInfo.genericValue?contains(">>") && mockMethInfo.genericValue?contains("Map")>
                <#--DataRO<Map<Integer,User>> dataRO = new DataRO<>();-->
                Map<${mockMethInfo.javaGenericModel.objectModelList[2].objectName}, ${mockMethInfo.javaGenericModel.objectModelList[3].objectName}> map = new HashMap<>(1);
                <#assign objName ="${mockMethInfo.javaGenericModel.objectModelList[3].objectName?uncap_first}">
                ${mockMethInfo.javaGenericModel.objectModelList[3].objectName} ${objName} = new ${mockMethInfo.javaGenericModel.objectModelList[3].objectName}();
                <#list mockMethInfo.javaGenericModel.objectModelList[3].objectParameterList as cParameter>
                <#--设置内部值-->
                ${objName}.set${cParameter.upName}(<#if cParameter.value??>${cParameter.value}<#else >new ${cParameter.type}()</#if>);
                </#list>

                map.put(${mockMethInfo.javaGenericModel.objectModelList[2].defaultValue}, ${objName});
                data.setData(map);

                return data;
                <#elseif mockMethInfo.javaGenericModel.isCustomClass && mockMethInfo.genericValue?contains(">")>
                <#--DataRO<User> dataRO = new DataRO<>();-->
                    <#if mockMethInfo.javaGenericModel.isBaseDataType>
                data.setData(${mockMethInfo.javaGenericModel.defaultValue});
                return data;
                    <#else>
                        <#assign objName ="${mockMethInfo.javaGenericModel.objectModelList[1].objectName?uncap_first}">
                ${mockMethInfo.javaGenericModel.objectModelList[1].objectName} ${objName} = new ${mockMethInfo.javaGenericModel.objectModelList[1].objectName}();
                        <#list mockMethInfo.javaGenericModel.objectModelList[1].objectParameterList as cParameter>
                        <#--设置内部值-->
                ${objName}.set${cParameter.upName}(<#if cParameter.value??>${cParameter.value}<#else >new ${cParameter.type}()</#if>);
                        </#list>

                data.setData(${objName});
                return data;
                    </#if>
                <#elseif (mockMethInfo.genericValue) ?? && mockMethInfo.genericValue?contains(">>>")
                && mockMethInfo.genericValue?contains("List") && mockMethInfo.genericValue?contains("Map")>
                <#--List<Map<Integer,Map<Boolean,User>>> dataRO = new ArrayList<>(1);-->
                Map<${mockMethInfo.javaGenericModel.objectModelList[2].objectName}, Map<${mockMethInfo.javaGenericModel.objectModelList[4].objectName}, ${mockMethInfo.javaGenericModel.objectModelList[5].objectName}>> objectMap = new HashMap<>(1);
                Map<${mockMethInfo.javaGenericModel.objectModelList[4].objectName}, ${mockMethInfo.javaGenericModel.objectModelList[5].objectName}> map = new HashMap<>(1);
                <#assign objName ="${mockMethInfo.javaGenericModel.objectModelList[5].objectName?uncap_first}">
                ${mockMethInfo.javaGenericModel.objectModelList[5].objectName} ${objName} = new ${mockMethInfo.javaGenericModel.objectModelList[5].objectName}();
                <#list mockMethInfo.javaGenericModel.objectModelList[5].objectParameterList as cParameter>
                <#--设置内部值-->
                ${objName}.set${cParameter.upName}(<#if cParameter.value??>${cParameter.value}<#else >new ${cParameter.type}()</#if>);
                </#list>
                map.put(${mockMethInfo.javaGenericModel.objectModelList[4].defaultValue}, ${objName});

                objectMap.put(${mockMethInfo.javaGenericModel.objectModelList[2].defaultValue}, map);
                mapList.add(objectMap);
                return mapList;
                <#elseif mockMethInfo.genericValue?contains(">>") && mockMethInfo.genericValue?contains("List") && mockMethInfo.genericValue?contains("Map")>
                <#--List<Map<Boolean,User>> dataRO = new ArrayList<>(1);-->
                Map<${mockMethInfo.javaGenericModel.objectModelList[2].objectName}, ${mockMethInfo.javaGenericModel.objectModelList[3].objectName}> map = new HashMap<>(1);
                    <#assign objName ="${mockMethInfo.javaGenericModel.objectModelList[3].objectName?uncap_first}">
                ${mockMethInfo.javaGenericModel.objectModelList[3].objectName} ${objName} = new ${mockMethInfo.javaGenericModel.objectModelList[3].objectName}();
                    <#list mockMethInfo.javaGenericModel.objectModelList[3].objectParameterList as cParameter>
                    <#--设置内部值-->
                ${objName}.set${cParameter.upName}(<#if cParameter.value??>${cParameter.value}<#else >new ${cParameter.type}()</#if>);
                    </#list>

                map.put(${mockMethInfo.javaGenericModel.objectModelList[2].defaultValue}, ${objName});
                mapList.add(map);
                return mapList;
                <#elseif mockMethInfo.genericValue?contains("List") && mockMethInfo.genericValue?contains(">")>
                <#--List<User> dataRO = new ArrayList<>(1);-->
                <#assign objName ="${mockMethInfo.javaGenericModel.objectModelList[1].objectName?uncap_first}">
                ${mockMethInfo.javaGenericModel.objectModelList[1].objectName} ${objName} = new ${mockMethInfo.javaGenericModel.objectModelList[1].objectName}();
                <#list mockMethInfo.javaGenericModel.objectModelList[1].objectParameterList as cParameter>
                <#--设置内部值-->
                ${objName}.set${cParameter.upName}(<#if cParameter.value??>${cParameter.value}<#else >new ${cParameter.type}()</#if>);
                </#list>

                objList.add(${objName});
                return objList;
                <#elseif (mockMethInfo.genericValue) ?? && mockMethInfo.genericValue?contains("Map")>
                <#--Map<Integer,User> dataRO = new HashMap<>(1);-->
                <#assign objName ="${mockMethInfo.javaGenericModel.objectModelList[2].objectName?uncap_first}">
                ${mockMethInfo.javaGenericModel.objectModelList[2].objectName} ${objName} = new ${mockMethInfo.javaGenericModel.objectModelList[2].objectName}();
                <#list mockMethInfo.javaGenericModel.objectModelList[2].objectParameterList as cParameter>
                <#--设置内部值-->
                ${objName}.set${cParameter.upName}(<#if cParameter.value??>${cParameter.value}<#else >new ${cParameter.type}()</#if>);
                </#list>

                mapObj.put(${mockMethInfo.javaGenericModel.objectModelList[1].defaultValue}, ${objName});
                return mapObj;
                <#elseif mockMethInfo.javaGenericModel.isCustomClass>
                <#--User user = new User();-->
                <#assign objName ="${mockMethInfo.javaGenericModel.objectModelList[0].objectName?uncap_first}">
                <#list mockMethInfo.javaGenericModel.objectModelList[0].objectParameterList as cParameter>
                <#--设置内部值-->
                ${objName}.set${cParameter.upName}(<#if cParameter.value??>${cParameter.value}<#else >new ${cParameter.type}()</#if>);
                </#list>
                return ${objName};
                <#else>
                // 其他Mock情况需自行实现
                </#if>
            default:
                return any();
            }
        }
            </#if>
        </#list>
    </#if>
</#list>
    }
    /** Mock返回数据-----------------------------End----------------------------- */
}
