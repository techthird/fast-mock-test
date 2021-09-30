/*
* fast-mock-test
*/
package ${javaClassDTO.packageName};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.alibaba.testable.core.annotation.MockDiagnose;
import com.alibaba.testable.core.annotation.MockMethod;
import com.alibaba.testable.core.model.LogLevel;
import com.alibaba.testable.core.tool.TestableTool;
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
* ${javaClassDTO.modelNameUpperCamel}Mock
*
* @author ${javaClassDTO.author!''}
* @date ${javaClassDTO.date!''}
*/
@MockDiagnose(LogLevel.VERBOSE)
public class ${javaClassDTO.modelNameUpperCamelMockClass}  {

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
    private ${mockMethInfo.genericValue!"void"} ${mockMethInfo.returnMethodName}(<#if mockMethInfo.javaParameterDTOList??><#list mockMethInfo.javaParameterDTOList as mockParameter><#if mockParameter_index==0>${mockParameter.type!""} ${mockParameter.name}Tmp<#else>,${mockParameter.type!""} ${mockParameter.name}Tmp</#if></#list></#if>) {
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
            <#elseif mockMethInfo.genericValue?contains("List") && mockMethInfo.genericValue?contains(">") && mockMethInfo.javaGenericModel.isBaseDataType>
            <#--List<Integer> dataRO = new ArrayList<>(1);-->
        ${mockMethInfo.genericValue} objList = new ArrayList<>();
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
                <#if (mockMethInfo.javaGenericModel.objectModelList[4].objectParameterList)??>
                    <#list mockMethInfo.javaGenericModel.objectModelList[4].objectParameterList as cParameter>
                    <#--设置内部值-->
            ${objName}.set${cParameter.upName}(<#if cParameter.value??>${cParameter.value}<#else >new ${cParameter.type}()</#if>);
                    </#list>
                </#if>

            map.put(${mockMethInfo.javaGenericModel.objectModelList[3].defaultValue}, ${objName});
            list.add(map);
            data.setData(list);

            return data;
            <#elseif mockMethInfo.javaGenericModel.isCustomClass && mockMethInfo.genericValue?contains(">>") && mockMethInfo.genericValue?contains("List")>
            <#--DataRO<List<User>> dataRO = new DataRO<>();-->
            List<${mockMethInfo.javaGenericModel.objectModelList[2].objectName}> list = new ArrayList<>(1);
            <#assign objName ="${mockMethInfo.javaGenericModel.objectModelList[2].objectName?uncap_first}">
            ${mockMethInfo.javaGenericModel.objectModelList[2].objectName} ${objName} = new ${mockMethInfo.javaGenericModel.objectModelList[2].objectName}();
                <#if (mockMethInfo.javaGenericModel.objectModelList[2].objectParameterList)??>
                    <#list mockMethInfo.javaGenericModel.objectModelList[2].objectParameterList as cParameter>
                    <#--设置内部值-->
            ${objName}.set${cParameter.upName}(<#if cParameter.value??>${cParameter.value}<#else >new ${cParameter.type}()</#if>);
                    </#list>
                </#if>

            list.add(${objName});
            data.setData(list);

            return data;
            <#elseif mockMethInfo.javaGenericModel.isCustomClass && mockMethInfo.genericValue?contains(">>") && mockMethInfo.genericValue?contains("Map")>
            <#--DataRO<Map<Integer,User>> dataRO = new DataRO<>();-->
            Map<${mockMethInfo.javaGenericModel.objectModelList[2].objectName}, ${mockMethInfo.javaGenericModel.objectModelList[3].objectName}> map = new HashMap<>(1);
            <#assign objName ="${mockMethInfo.javaGenericModel.objectModelList[3].objectName?uncap_first}">
            ${mockMethInfo.javaGenericModel.objectModelList[3].objectName} ${objName} = new ${mockMethInfo.javaGenericModel.objectModelList[3].objectName}();
                <#if (mockMethInfo.javaGenericModel.objectModelList[3].objectParameterList)??>
                    <#list mockMethInfo.javaGenericModel.objectModelList[3].objectParameterList as cParameter>
                    <#--设置内部值-->
            ${objName}.set${cParameter.upName}(<#if cParameter.value??>${cParameter.value}<#else >new ${cParameter.type}()</#if>);
                    </#list>
                </#if>

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
                    <#if (mockMethInfo.javaGenericModel.objectModelList[1].objectParameterList)??>
                        <#list mockMethInfo.javaGenericModel.objectModelList[1].objectParameterList as cParameter>
                        <#--设置内部值-->
            ${objName}.set${cParameter.upName}(<#if cParameter.value??>${cParameter.value}<#else >new ${cParameter.type}()</#if>);
                        </#list>
                    </#if>

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
                <#if (mockMethInfo.javaGenericModel.objectModelList[5].objectParameterList)??>
                    <#list mockMethInfo.javaGenericModel.objectModelList[5].objectParameterList as cParameter>
                    <#--设置内部值-->
            ${objName}.set${cParameter.upName}(<#if cParameter.value??>${cParameter.value}<#else >new ${cParameter.type}()</#if>);
                    </#list>
                </#if>

            map.put(${mockMethInfo.javaGenericModel.objectModelList[4].defaultValue}, ${objName});

            objectMap.put(${mockMethInfo.javaGenericModel.objectModelList[2].defaultValue}, map);
            mapList.add(objectMap);
            return mapList;
            <#elseif mockMethInfo.genericValue?contains(">>") && mockMethInfo.genericValue?contains("List") && mockMethInfo.genericValue?contains("Map")>
            <#--List<Map<Boolean,User>> dataRO = new ArrayList<>(1);-->
            Map<${mockMethInfo.javaGenericModel.objectModelList[2].objectName}, ${mockMethInfo.javaGenericModel.objectModelList[3].objectName}> map = new HashMap<>(1);
                <#assign objName ="${mockMethInfo.javaGenericModel.objectModelList[3].objectName?uncap_first}">
            ${mockMethInfo.javaGenericModel.objectModelList[3].objectName} ${objName} = new ${mockMethInfo.javaGenericModel.objectModelList[3].objectName}();
                <#if (mockMethInfo.javaGenericModel.objectModelList[3].objectParameterList)??>
                    <#list mockMethInfo.javaGenericModel.objectModelList[3].objectParameterList as cParameter>
                    <#--设置内部值-->
            ${objName}.set${cParameter.upName}(<#if cParameter.value??>${cParameter.value}<#else >new ${cParameter.type}()</#if>);
                    </#list>
                </#if>

            map.put(${mockMethInfo.javaGenericModel.objectModelList[2].defaultValue}, ${objName});
            mapList.add(map);
            return mapList;
            <#elseif mockMethInfo.genericValue?contains("List") && mockMethInfo.genericValue?contains(">") && mockMethInfo.javaGenericModel.isBaseDataType>
            <#--List<Integer> dataRO = new ArrayList<>(1);-->
            <#assign objName ="${mockMethInfo.javaGenericModel.objectModelList[1].objectName?uncap_first}">
            ${mockMethInfo.javaGenericModel.objectModelList[1].objectName} ${objName} = new ${mockMethInfo.javaGenericModel.objectModelList[1].objectName}(${mockMethInfo.javaGenericModel.objectModelList[1].defaultValue});
            objList.add(${objName});
            return objList;
            <#elseif mockMethInfo.genericValue?contains("List") && mockMethInfo.genericValue?contains(">")>
            <#--List<User> dataRO = new ArrayList<>(1);-->
            <#assign objName ="${mockMethInfo.javaGenericModel.objectModelList[1].objectName?uncap_first}">
            ${mockMethInfo.javaGenericModel.objectModelList[1].objectName} ${objName} = new ${mockMethInfo.javaGenericModel.objectModelList[1].objectName}();
                <#if (mockMethInfo.javaGenericModel.objectModelList[1].objectParameterList)??>
                    <#list mockMethInfo.javaGenericModel.objectModelList[1].objectParameterList as cParameter>
                    <#--设置内部值-->
            ${objName}.set${cParameter.upName}(<#if cParameter.value??>${cParameter.value}<#else >new ${cParameter.type}()</#if>);
                    </#list>
                </#if>

            objList.add(${objName});
            return objList;
            <#elseif (mockMethInfo.genericValue) ?? && mockMethInfo.genericValue?contains("Map")>
            <#--Map<Integer,User> dataRO = new HashMap<>(1);-->
            <#assign objName ="${mockMethInfo.javaGenericModel.objectModelList[2].objectName?uncap_first}">
            ${mockMethInfo.javaGenericModel.objectModelList[2].objectName} ${objName} = new ${mockMethInfo.javaGenericModel.objectModelList[2].objectName}();
                <#if (mockMethInfo.javaGenericModel.objectModelList[2].objectParameterList)??>
                    <#list mockMethInfo.javaGenericModel.objectModelList[2].objectParameterList as cParameter>
                    <#--设置内部值-->
            ${objName}.set${cParameter.upName}(<#if cParameter.value??>${cParameter.value}<#else >new ${cParameter.type}()</#if>);
                    </#list>
                </#if>

            mapObj.put(${mockMethInfo.javaGenericModel.objectModelList[1].defaultValue}, ${objName});
            return mapObj;
            <#elseif mockMethInfo.javaGenericModel.isCustomClass>
            <#--User user = new User();-->
            <#assign objName ="${mockMethInfo.javaGenericModel.objectModelList[0].objectName?uncap_first}">
                <#if (mockMethInfo.javaGenericModel.objectModelList[0].objectParameterList)??>
                    <#list mockMethInfo.javaGenericModel.objectModelList[0].objectParameterList as cParameter>
                    <#--设置内部值-->
            ${objName}.set${cParameter.upName}(<#if cParameter.value??>${cParameter.value}<#else >new ${cParameter.type}()</#if>);
                    </#list>
                </#if>
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
