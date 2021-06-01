package fast.mock.test.maven.plugin;

import fast.mock.test.core.util.CommonUtils;

import java.util.Arrays;

/**
 * @author 陈贵勇
 * @date 2021/5/20 新建
 */
public class ComTest {
    public static void main(String[] args) throws ClassNotFoundException {
        String  generics = "List<Map<String,Map<Integer,ItemBo>>>";
        Arrays.asList(generics.split("<")).forEach(code -> {
            code = code.replaceAll(">", "");
            if (code.indexOf(",") != -1) {
                Arrays.asList(code.split(",")).forEach(code2 -> {
                    System.out.println(code2);
                });
            } else {
                System.out.println(code);
            }
        });

        System.out.println("==================================");
        String str = "String";

        System.out.println(CommonUtils.isJavaClass("fast.mock.test.maven.plugin.entity.ItemBo"));
        System.out.println(CommonUtils.isJavaClass("java.lang.String"));
        System.out.println(Class.forName("java.lang.String").getClassLoader());
        System.out.println(Class.forName("java.util.Arrays").getClassLoader());
        System.out.println(Class.forName("fast.mock.test.maven.plugin.entity.ItemBo").getClassLoader());

        System.out.println(isJavaClass(Integer.class)); // true
        //System.out.println(isJavaClass(Class<?> "String")); // true

    }
    /**
     * 判断一个类是JAVA类型还是用户定义类型
     * @param clz
     * @return
     */
    public static boolean isJavaClass(Class<?> clz) {
        return clz != null && clz.getClassLoader() == null;
    }


}
