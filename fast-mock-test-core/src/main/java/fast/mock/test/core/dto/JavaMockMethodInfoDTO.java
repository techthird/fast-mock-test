/*
 * wiki.primo
 * Copyright (C) 2013-2019 All Rights Reserved.
 */
package fast.mock.test.core.dto;

import lombok.Data;
import fast.mock.test.core.model.JavaGenericModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 需要mock的方法的信息
 *
 * @author chenhx
 * @version JavaMockClassInfoDTO.java, v 0.1 2019-06-11 14:35 chenhx
 */
@Data
public class JavaMockMethodInfoDTO {
    /**
     * 父类类型 - 全限定名
     */
    private String parentClassFullyType;
    /**
     * 调用该方法的属性变量名称
     */
    private String fieldName;
    /**
     * 类的类型 - 全限定类型
     */
    private String classType;

    /**
     * 方法名称
     */
    private String name;

    /**
     * 方法参数-入参
     */
    private List<JavaParameterDTO> javaParameterDTOList = new ArrayList<>();
    /**
     * 方法参数-出参
     */
    private List<JavaParameterDTO> javaReturnParameterDTOList = new ArrayList<>();

    /**
     * 方法返回参数类型 - 全限定名称
     */
    private String returnFullyType;
    /**
     * 方法返回参数类型 名称
     */
    private String returnType;
    /**返回值 方法名称*/
    private String returnMethodName;
    /**注释*/
    private String comment;

    /**
     * 是否存在多个 方法，方法名一直且参数数量一致
     */
    private Boolean isHaveMoreMethod;

    /**泛型值 IPage<User>*/
    private String genericValue;
    /**泛型列表 list map */
    private List<JavaGenericModel> javaGenericModelList;
    // 是否属于基础数据类型
    private Boolean isBaseDataType;
    // 默认值
    private String defaultValue;


}
