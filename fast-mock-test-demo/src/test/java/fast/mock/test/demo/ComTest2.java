package fast.mock.test.demo;

import com.alibaba.fastjson.JSON;
import com.venusplan.framework.api.DataRO;
import fast.mock.test.demo.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 陈贵勇
 * @date 2021/5/20 新建
 */
public class ComTest2 {
    public static void main(String[] args) throws ClassNotFoundException {
        List<Map<String,Map<Integer,User>>> mapList = new ArrayList<>(1);
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

        DataRO<User> dataRO = new DataRO<>();


    }


}
