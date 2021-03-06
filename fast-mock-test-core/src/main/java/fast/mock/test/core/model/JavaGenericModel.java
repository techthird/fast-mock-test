/*
 * wiki.primo
 * Copyright (C) 2013-2019 All Rights Reserved.
 */
package fast.mock.test.core.model;

import lombok.Data;

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
    private List<String> genericFullyQualifiedNameList;

    /** 对象参数列表 */
    private List<ObjectModel> objectModelList;

    // 是否属于基础数据类型
    private Boolean isBaseDataType;
    // 是否为自定义类 true:自定义
    private Boolean isCustomClass;
    // 默认值
    private String defaultValue;


}
