/*
 * wiki.primo
 * Copyright (C) 2013-2019 All Rights Reserved.
 */
package fast.mock.test.core.model;

import fast.mock.test.core.dto.JavaParameterDTO;
import lombok.Data;

import java.util.List;

/**
 * java泛型
 *
 */
@Data
public class ObjectModel {

    /** 对象名称 */
    private String objectName;
    /** 对象完整名称 */
    private String objectFullyName;

    /** 对象参数列表 */
    private List<JavaParameterDTO> objectParameterList;
    // 是否属于基础数据类型
    private Boolean isBaseDataType;
    // 是否为自定义类 true:自定义
    private Boolean isCustomClass;
    // 默认值
    private String defaultValue;

}
