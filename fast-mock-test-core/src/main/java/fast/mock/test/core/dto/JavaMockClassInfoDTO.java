/*
 * wiki.primo
 * Copyright (C) 2013-2019 All Rights Reserved.
 */
package fast.mock.test.core.dto;

import fast.mock.test.core.base.BaseCanUserType;
import lombok.Data;

/**
 * 需要mock的类信息
 *
 * @author chenhx
 * @version JavaMockClassInfoDTO.java, v 0.1 2019-06-11 14:35 chenhx
 */
@Data
public class JavaMockClassInfoDTO extends BaseCanUserType {
    /**
     * 属性名称
     */
    private String name;
    /**
     * 父类类型
     */
    private String parentClassFullyType;
    /*
     * 需要mock的方法
     */
//    private List<JavaMockMethodInfoDTO> javaMockMethodInfoDTOList;
}
