/*
* fast-mock-test
*/
package fast.mock.test.demo.service.impl2;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.alibaba.testable.core.annotation.MockDiagnose;
import com.alibaba.testable.core.annotation.MockMethod;
import com.alibaba.testable.core.model.LogLevel;
import com.alibaba.testable.core.tool.TestableTool;
import org.junit.*;
import java.math.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.*;
import java.lang.*;
import static org.mockito.ArgumentMatchers.*;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import fast.mock.test.demo.mapper.TableShardingMapper;
import fast.mock.test.demo.entity.User;
import fast.mock.test.demo.entity.User;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.IPage;
import fast.mock.test.demo.query.TableShardingQueryBo;
import fast.mock.test.demo.service.IUserService;
import fast.mock.test.demo.service.impl.TableShardingServiceImpl;
import java.util.List;
import fast.mock.test.demo.query.TableShardingQueryBo.QueryBoExt;
import fast.mock.test.demo.query.UserQueryBo;

/**
* MyServiceImplMock
*
* @author 
* @date 2021-08-02 16:15:12
*/
@MockDiagnose(LogLevel.VERBOSE)
public class MyServiceImplMock  {

    /**
     * 按照条件查询所有
     */
    @MockMethod(targetClass = IUserService.class, targetMethod = "listUser")
    private List<User> listUser(UserQueryBo queryTmp) {
        List<User> objList = new ArrayList<>();
        switch ((String) TestableTool.MOCK_CONTEXT.get("case")) {
        case "case1":
            User user = new User();
            user.setId(0);
            user.setName("");
            user.setPassword("");
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            user.setPastTime(new Date());
            user.setSex(0);
            user.setPhone("");
            user.setEmail("");
            user.setDescribe("");
            user.setRole(0);
            user.setShorA((short)0);
            user.setBigDecimalsd(new BigDecimal(0));

            objList.add(user);
            return objList;
        default:
            return any();
        }
    }
    /**
     * 
     */
    @MockMethod(targetClass = TableShardingServiceImpl.class, targetMethod = "getListMap1")
    private List<Map<String,Map<Integer,User>>> getListMap1(int aTmp) {
        List<Map<String,Map<Integer,User>>> mapList = new ArrayList<>(1);
        switch ((String) TestableTool.MOCK_CONTEXT.get("case")) {
        case "case1":
            Map<String, Map<Integer, User>> objectMap = new HashMap<>(1);
            Map<Integer, User> map = new HashMap<>(1);
            User user = new User();
            user.setId(0);
            user.setName("");
            user.setPassword("");
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            user.setPastTime(new Date());
            user.setSex(0);
            user.setPhone("");
            user.setEmail("");
            user.setDescribe("");
            user.setRole(0);
            user.setShorA((short)0);
            user.setBigDecimalsd(new BigDecimal(0));
            map.put(0, user);

            objectMap.put("", map);
            mapList.add(objectMap);
            return mapList;
        default:
            return any();
        }
    }
    /**
     * 查询单个的，注意，条件需要有唯一建
     */
    @MockMethod(targetClass = IUserService.class, targetMethod = "getUser")
    private User getUser(UserQueryBo queryTmp) {
        User user = new User();
        switch ((String) TestableTool.MOCK_CONTEXT.get("case")) {
        case "case1":
            user.setId(0);
            user.setName("");
            user.setPassword("");
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            user.setPastTime(new Date());
            user.setSex(0);
            user.setPhone("");
            user.setEmail("");
            user.setDescribe("");
            user.setRole(0);
            user.setShorA((short)0);
            user.setBigDecimalsd(new BigDecimal(0));
            return user;
        default:
            return any();
        }
    }

}
