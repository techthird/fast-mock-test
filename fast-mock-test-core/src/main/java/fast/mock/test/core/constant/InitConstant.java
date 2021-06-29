/*
 * wiki.primo
 * Copyright (C) 2013-2019 All Rights Reserved.
 */
package fast.mock.test.core.constant;

import fast.mock.test.core.util.StringUtils;
import fast.mock.test.core.enums.BaseTypeEnum;
import org.apache.commons.lang3.RandomUtils;
import org.apache.maven.plugin.logging.Log;
import fast.mock.test.core.log.MySystemStreamLog;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 初始化的值
 *
 * @author chenhx
 * @version InitConstant.java, v 0.1 2019-06-12 11:19 chenhx
 */
public class InitConstant {

    /**
     * 对应类型转换
     */
    public static final Map<String, String> MAPPING = new HashMap<>();
    /**
     * 导入的包 - 集合接口，对应的实现类
     */
    public static final Map<String, String> COLLECTION_VALUE_IMPORT = new HashMap<>();
    /**
     * 导入的包 - 集合接口，对应的实现类 - 全限定名称对象
     */
    public static final Map<String, String> FULLY_COLLECTION_VALUE_IMPORT = new HashMap<>();
    /**
     * 导入包需要排除的包名
     */
    public static final Set<String> EXCLUDE_IMPORT_TYPE = new HashSet<>();
    /**
     * 在源码类上有该注解，能够自动在class文件中生成set方法的注解全限定名称,类级别注解
     */
    public static final Set<String> CLASS_ANNOTATION_AUTO_SET = new HashSet<>();
    /**
     * 对应类型的默认值
     */
    private static final Map<String, String> VALUE = new HashMap<>();
    private static Log log = new MySystemStreamLog();

    static {
        //初始化类型转换
        initMapping();
        //初始化默认值
        initValue();
        //初始化排除的包
        initExclude();
        //初始化集合的默认值
        initCollectionValue();
        //初始化生成set的注解
        initAnnotationSet();
    }

    /**
     * 通过全限定名获取简称
     *
     * @param type 全限定名称
     * @return 简称
     */
    public static String getAbbreviation(String type) {
        if (StringUtils.isEmpty(type)) {
            return type;
        }
        if (type.contains(".")) {
            type = type.substring(type.lastIndexOf(".") + 1);
        }
        return MAPPING.getOrDefault(type, type);
    }

    /**
     * 初始化生成set的注解
     */
    private static void initAnnotationSet() {
        CLASS_ANNOTATION_AUTO_SET.add("lombok.Data");
        CLASS_ANNOTATION_AUTO_SET.add("lombok.Setter");
    }

    private static void initCollectionValue() {
        COLLECTION_VALUE_IMPORT.put("List", "java.util.ArrayList");
        COLLECTION_VALUE_IMPORT.put("Set", "java.util.HashSet");
        COLLECTION_VALUE_IMPORT.put("Map", "java.util.HashMap");
        //设置实现类
        COLLECTION_VALUE_IMPORT.put("Collection", "java.util.ArrayList");

        //导入的包
        FULLY_COLLECTION_VALUE_IMPORT.put("java.util.Collection", "java.util.ArrayList");
        FULLY_COLLECTION_VALUE_IMPORT.put("java.util.List", "java.util.ArrayList");
        FULLY_COLLECTION_VALUE_IMPORT.put("java.util.Set", "java.util.HashSet");
        FULLY_COLLECTION_VALUE_IMPORT.put("java.util.Map", "java.util.HashMap");

        //其他抽象类的实现
        FULLY_COLLECTION_VALUE_IMPORT.put("com.baomidou.mybatisplus.core.conditions.Wrapper", "com.baomidou.mybatisplus.core.conditions.query.QueryWrapper");
        FULLY_COLLECTION_VALUE_IMPORT.put("com.baomidou.mybatisplus.core.metadata.IPage", "com.baomidou.mybatisplus.extension.plugins.pagination.Page");

    }

    private static void initExclude() {
        EXCLUDE_IMPORT_TYPE.add("java.lang.Integer");
        EXCLUDE_IMPORT_TYPE.add("java.lang.Class");
        EXCLUDE_IMPORT_TYPE.add("java.lang.Long");
        EXCLUDE_IMPORT_TYPE.add("java.lang.Double");
        EXCLUDE_IMPORT_TYPE.add("java.lang.String");
        EXCLUDE_IMPORT_TYPE.add("java.lang.Boolean");
        EXCLUDE_IMPORT_TYPE.add("java.lang.Float");
        EXCLUDE_IMPORT_TYPE.add("java.lang.Object");
        EXCLUDE_IMPORT_TYPE.add("java.lang.Short");
        EXCLUDE_IMPORT_TYPE.add("java.lang.StringBuffer");
        EXCLUDE_IMPORT_TYPE.add("java.lang.StringBuilder");
        EXCLUDE_IMPORT_TYPE.add("java.lang.Void");

        EXCLUDE_IMPORT_TYPE.add("Integer");
        EXCLUDE_IMPORT_TYPE.add("Class");
        EXCLUDE_IMPORT_TYPE.add("Long");
        EXCLUDE_IMPORT_TYPE.add("Double");
        EXCLUDE_IMPORT_TYPE.add("String");
        EXCLUDE_IMPORT_TYPE.add("Boolean");
        EXCLUDE_IMPORT_TYPE.add("Byte");
        EXCLUDE_IMPORT_TYPE.add("Float");
        EXCLUDE_IMPORT_TYPE.add("Object");
        EXCLUDE_IMPORT_TYPE.add("Short");
        EXCLUDE_IMPORT_TYPE.add("StringBuffer");
        EXCLUDE_IMPORT_TYPE.add("StringBuilder");

        EXCLUDE_IMPORT_TYPE.add("int");
        EXCLUDE_IMPORT_TYPE.add("long");
        EXCLUDE_IMPORT_TYPE.add("double");
        EXCLUDE_IMPORT_TYPE.add("float");
        EXCLUDE_IMPORT_TYPE.add("boolean");
        EXCLUDE_IMPORT_TYPE.add("char");
        EXCLUDE_IMPORT_TYPE.add("byte");
        EXCLUDE_IMPORT_TYPE.add("short");

        EXCLUDE_IMPORT_TYPE.add("T");
        EXCLUDE_IMPORT_TYPE.add("B");
        EXCLUDE_IMPORT_TYPE.add("M");
        EXCLUDE_IMPORT_TYPE.add("F");
    }

    private static void initMapping() {
        MAPPING.put("java.lang.Integer", "Integer");
        MAPPING.put("java.lang.Class", "Class");
        MAPPING.put("java.lang.Long", "Long");
        MAPPING.put("java.lang.Double", "Double");
        MAPPING.put("java.lang.String", "String");
        MAPPING.put("java.lang.Boolean", "Boolean");
        MAPPING.put("java.lang.Byte", "Byte");
        MAPPING.put("java.lang.Float", "Float");
        MAPPING.put("java.lang.Object", "Object");
        MAPPING.put("java.lang.Short", "Short");
        MAPPING.put("java.lang.StringBuffer", "StringBuffer");
        MAPPING.put("java.lang.StringBuilder", "StringBuilder");
        MAPPING.put("java.lang.Void", "Void");

        MAPPING.put("T", "Object");
        MAPPING.put("B", "Object");
        MAPPING.put("M", "Object");
        MAPPING.put("F", "Object");
    }

    private static void initValue() {
        VALUE.put("String", "\"\"");
        VALUE.put("Integer", "0");
        VALUE.put("Long", "0L");
        VALUE.put("Double", "0.0");
        VALUE.put("Float", "0.0f");
        VALUE.put("Boolean", "true");
        VALUE.put("Character", "'0'");
        VALUE.put("Byte", "0");
        VALUE.put("Short", "(short)0");

        VALUE.put("int", "0");
        VALUE.put("long", "0L");
        VALUE.put("double", "0.0");
        VALUE.put("float", "0.0f");
        VALUE.put("boolean", "true");
        VALUE.put("char", "'0'");
        VALUE.put("byte", "0");
        VALUE.put("short", "(short)0");

        VALUE.put("StringBuffer", "new StringBuffer(\"\")");
        VALUE.put("StringBuilder", "new StringBuilder(\"\")");

        VALUE.put("Class", "null");
        VALUE.put("T", "new Object()");
        VALUE.put("B", "new Object()");
        VALUE.put("M", "new Object()");
        VALUE.put("F", "new Object()");

        VALUE.put("Object", "new Object()");

        VALUE.put("BigDecimal", "new BigDecimal(0)");

    }

    /**
     * 获取默认的值
     *
     * @param type 简称
     * @return 默认值，没有找到返回null
     */
    public static String getValue(String type) {
        if (CommonConstant.CONFIG_ENTITY.getIsSetBasicTypesRandomValue()) {
            //设置默认值
            if (BaseTypeEnum.getByValue(type) != null) {
                return getRandomValue(type);
            }
        }
        return VALUE.getOrDefault(type, null);
    }

    /**
     * 返回随机值
     *
     * @param type 简称
     * @return 随机值
     */
    private static String getRandomValue(String type) {
        if (StringUtils.isEmpty(type)) {
            return null;
        }
        //获取默认值
        if (BaseTypeEnum.String_type.getValue().equals(type)) {
            String strValue = CommonConstant.CONFIG_ENTITY.getSetStringRandomRange();
            int len = 10;
            try {
                len = Integer.valueOf(strValue);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            return "\"" + StringUtils.getRandomString(len) + "\"";
        } else if (BaseTypeEnum.Integer_type.getValue().equals(type)
                || BaseTypeEnum.int_type.getValue().equals(type)
        ) {
            int a = 0;
            int b = 1000;
            String intValue = CommonConstant.CONFIG_ENTITY.getSetIntRandomRange();
            try {
                if (intValue.contains(",")) {
                    //范围
                    String[] intValues = intValue.split(",");
                    if (intValues.length == 2) {
                        a = Integer.valueOf(intValue.split(",")[0].trim());
                        b = Integer.valueOf(intValue.split(",")[1].trim());
                    } else {
                        log.error("setIntRandomRange配置错误，请输入正确的范围。当前配置值：" + intValue);
                    }
                } else {
                    //                固定值
                    a = Integer.valueOf(intValue.trim());
                    b = Integer.valueOf(intValue.trim());
                }
            } catch (NumberFormatException e) {
                log.error("setIntRandomRange配置错误，请输入正确的范围。当前配置值：" + intValue, e);
            }
            int rand = RandomUtils.nextInt(a, b);
            return "" + rand;
        } else if (BaseTypeEnum.Double_type.getValue().equals(type)
                || BaseTypeEnum.double_type.getValue().equals(type)
        ) {
            double rand = RandomUtils.nextDouble(0.00, 10000.00);
            return "" + rand;
        } else if (BaseTypeEnum.Float_type.getValue().equals(type)
                || BaseTypeEnum.float_type.getValue().equals(type)
        ) {
            float rand = RandomUtils.nextFloat(0.00f, 10000.00f);
            return "" + rand + "f";
        } else if (BaseTypeEnum.Boolean_type.getValue().equals(type)
                || BaseTypeEnum.boolean_type.getValue().equals(type)
        ) {
            boolean rand = RandomUtils.nextBoolean();
            String booValue = CommonConstant.CONFIG_ENTITY.getSetBooleanRandomRange();
            if ("true".equals(booValue.trim())) {
                rand = true;
            } else if ("false".equals(booValue.trim())) {
                rand = false;
            }
            return "" + rand;
        } else if (BaseTypeEnum.Long_type.getValue().equals(type)
                || BaseTypeEnum.long_type.getValue().equals(type)
        ) {
            long a = 0L;
            long b = 10000L;
            String longValue = CommonConstant.CONFIG_ENTITY.getSetLongRandomRange();
            try {
                if (longValue.contains(",")) {
                    //范围
                    String[] intValues = longValue.split(",");
                    if (intValues.length == 2) {
                        a = Long.valueOf(longValue.split(",")[0].trim());
                        b = Long.valueOf(longValue.split(",")[1].trim());
                    } else {
                        log.error("setLongRandomRange配置错误，请输入正确的范围。当前配置值：" + longValue);
                    }
                } else {
                    //                固定值
                    a = Integer.valueOf(longValue.trim());
                    b = Integer.valueOf(longValue.trim());
                }
            } catch (NumberFormatException e) {
                log.error("setLongRandomRange配置错误，请输入正确的范围。当前配置值：" + longValue, e);
            }
            long rand = RandomUtils.nextLong(a, b);
            return "" + rand + "L";
        } else if (BaseTypeEnum.Character_type.getValue().equals(type)
                || BaseTypeEnum.char_type.getValue().equals(type)
        ) {
            return "'" + StringUtils.getRandomChar() + "'";
        } else if (BaseTypeEnum.Byte_type.getValue().equals(type)
                || BaseTypeEnum.byte_type.getValue().equals(type)) {
            int rand = RandomUtils.nextInt(0, 1);
            return "" + rand;
        } else if (BaseTypeEnum.Short_type.getValue().equals(type)
                || BaseTypeEnum.short_type.getValue().equals(type)) {
            int rand = RandomUtils.nextInt(0, 127);
            return "(short)" + rand;
        } else if (BaseTypeEnum.bigDecimal_type.getValue().equals(type)) {
            return "new BigDecimal(\"0\")";
        } else if (BaseTypeEnum.localDateTime.getValue().equals(type)) {
            return "LocalDateTime.now()";
        }
        return null;
    }

}
