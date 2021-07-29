package fast.mock.test.demo;

import com.alibaba.fastjson.JSON;
import com.venusplan.framework.api.DataRO;
import com.yunji.item.api.IItemService;
import fast.mock.test.demo.entity.User;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 陈贵勇
 * @date 2021/5/20 新建
 */
public class ComTest2 {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException {
      /*  List<Map<String,Map<Integer,User>>> mapList = new ArrayList<>(1);
        Map<String, Map<Integer, User>> objectMap = new HashMap<>(1);
        Map<Integer, User> map = new HashMap<>(1);
        User User = new User();
        User.setId(1);
        User.setName("fdafd");
        map.put(0, User);

        objectMap.put("2", map);
        mapList.add(objectMap);

        System.out.println(JSON.toJSONString(mapList));
        System.out.println(JSON.parseArray(JSON.toJSONString(mapList),User.class));

        DataRO<User> dataRO = new DataRO<>();*/

        Class<?> cls = IItemService.class;//Class.forName("testJavaSE.Person");
        Constructor con = cls.getDeclaredConstructor();
        System.out.println("得到了Person的构造函数");
        //创建Person实例
        System.out.println("创建了一个person对象");
        //获得Person的Method对象,参数为方法名,参数列表的类型Class对象
        Method method = cls.getDeclaredMethod("eat", String.class);
        System.out.println("得到了Person的eat方法");


    }


}
