package fast.mock.test.core.util;

/**
 * @author 陈贵勇
 * @date 2021/4/15 新建
 */
public class CommonUtils {

    /**
     * 是否属于java数据类型
     *
     * @param name
     * @return
     * @author 陈贵勇  2021年04月15日 新建
     */
    public static boolean isJavaDataType(String name){
        return "boolean".equals(name) || "byte".equals(name) || "char".equals(name) || "short".equals(name) ||
                "int".equals(name) || "long".equals(name) || "float".equals(name) || "double".equals(name) ||
                "Boolean".equals(name) || "Byte".equals(name) || "Character".equals(name) || "Short".equals(name) ||
                "Integer".equals(name) || "Long".equals(name) || "Float".equals(name) || "Double".equals(name) ||
                "void".equals(name) || "String".equals(name) || "Object".equals(name) || "BigDecimal".equals(name);
    }

    /**
     * 判断一个类是JAVA类型还是用户定义类型
     * @param clz 完整的包名.类名 如java.lang.String
     * @return true 非java类型
     */
    public static boolean isJavaClass(String clz) {
        try {
            return clz != null && Class.forName(clz).getClassLoader() == null;
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 首字母大写
     * @param str
     * @return
     */
    public static String upperCase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
    public static void main(String[] args){
        String millis = System.currentTimeMillis()+"";
        System.out.println(millis);
        System.out.println(millis.substring(millis.length()-6,millis.length()));
    }
}
