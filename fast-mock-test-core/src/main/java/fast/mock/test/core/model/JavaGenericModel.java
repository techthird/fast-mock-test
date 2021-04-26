/*
 * wiki.primo
 * Copyright (C) 2013-2019 All Rights Reserved.
 */
package fast.mock.test.core.model;

import lombok.Data;
import fast.mock.test.core.dto.JavaParameterDTO;

import java.util.List;

/**
 * java泛型
 *
 */
@Data
public class JavaGenericModel {

    /** 泛型名称 User*/
    private String genericName;
    /** 泛型完整名称 */
    private String genericFullyQualifiedName;

    /** 对象参数列表 */
    private List<JavaParameterDTO> javaGenericParameterDTOList;
    // 是否属于基础数据类型
    private Boolean isBaseDataType;
    // 默认值
    private String defaultValue;


}
