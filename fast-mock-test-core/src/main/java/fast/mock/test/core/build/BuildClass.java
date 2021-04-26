/*
 * wiki.primo
 * Copyright (C) 2013-2019 All Rights Reserved.
 */
package fast.mock.test.core.build;

import fast.mock.test.core.dto.JavaClassDTO;
import com.thoughtworks.qdox.model.JavaClass;

/**
 * 构建整个测试类
 *
 * @author chenhx
 * @version BuildClass.java, v 0.1 2019-07-04 15:23 chenhx
 */
public interface BuildClass {

    /**
     * 构建整个类
     * @param javaClass java类数据
     * @return 返回模板需要的信息 javaClassDTO
     */
    JavaClassDTO build(JavaClass javaClass);

}
