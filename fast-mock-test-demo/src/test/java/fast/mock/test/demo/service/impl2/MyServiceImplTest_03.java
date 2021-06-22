/*
* fast-mock-test
*/
package fast.mock.test.demo.service.impl2;

import com.alibaba.testable.core.annotation.MockDiagnose;
import com.alibaba.testable.core.annotation.MockMethod;
import com.alibaba.testable.core.model.LogLevel;
import com.alibaba.testable.core.tool.TestableTool;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.venusplan.framework.api.DataRO;
import com.venusplan.framework.api.PageRO;
import fast.mock.test.demo.BaseTestCase;
import fast.mock.test.demo.entity.User;
import fast.mock.test.demo.entity.UserTemplate;
import fast.mock.test.demo.query.TableShardingQueryBo;
import fast.mock.test.demo.query.TableShardingQueryBo.QueryBoExt;
import fast.mock.test.demo.query.UserQueryBo;
import fast.mock.test.demo.service.IUserService;
import fast.mock.test.demo.service.impl.TableShardingServiceImpl;
import fast.mock.test.demo.service.impl.UserServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;

/**
* MyServiceImplTest_02
*
* @author 
* @date 2021-06-21 12:02:37
*/
public class MyServiceImplTest_03 extends BaseTestCase {

    @Autowired
    private MyServiceImpl myServiceImpl;

    /** Case---------------------------------Start-------------------------------- */
    @Test
    public void pageTestParentTest() {
        /** 1、设置Case名称 */
        TestableTool.MOCK_CONTEXT.put("case", "case1");

        /** 2、组装测试接口的参数 */
        TableShardingQueryBo query = new TableShardingQueryBo();
        query.setQueryBoExt(new QueryBoExt());
        query.setId(0);
        query.setName("");
        query.setAge(0);
        query.setCreateTime(new Date());
        query.setToken("");

        int pageNo = 0;

        int pageSize = 0;

        /** 3、调用方法 */
        /* 按照条件分页查询 */
        IPage obj = myServiceImpl.pageTestParent(query,pageNo,pageSize);

        /** 4、断言 */
        /*
        Assert.assertNotNull(obj);
        Assert.assertEquals(new Object(), obj);
        */
    }
    @Test
    public void mapsTest() {
        /** 1、设置Case名称 */
        TestableTool.MOCK_CONTEXT.put("case", "case1");

        /** 2、组装测试接口的参数 */

        long pageNo = 0L;

        Integer pageSize = 0;

        Double aa = 0.0;

        Boolean bo = true;

        /** 3、调用方法 */
        /*  */
        Map obj = myServiceImpl.maps(pageNo,pageSize,aa,bo);

        /** 4、断言 */
        /*
        Assert.assertNotNull(obj);
        Assert.assertEquals(new Object(), obj);
        */
    }
    @Test
    public void maps2Test() {
        /** 1、设置Case名称 */
        TestableTool.MOCK_CONTEXT.put("case", "case1");

        /** 2、组装测试接口的参数 */

        long pageNo = 0L;

        /** 3、调用方法 */
        /*  */
        myServiceImpl.maps2(pageNo);

        /** 4、断言 */
        /*
        Assert.assertNotNull(obj);
        Assert.assertEquals(new Object(), obj);
        */
    }
    @Test
    public void getComputeTest() {
        /** 1、设置Case名称 */
        TestableTool.MOCK_CONTEXT.put("case", "case1");

        /** 2、组装测试接口的参数 */

        long pageNo = 0L;

        /** 3、调用方法 */
        /*  */
        myServiceImpl.getCompute(pageNo);

        /** 4、断言 */
        /*
        Assert.assertNotNull(obj);
        Assert.assertEquals(new Object(), obj);
        */
    }

    /** Case---------------------------------End-------------------------------- */



    /** Mock数据-----------------------------Start----------------------------- */

    @MockDiagnose(LogLevel.VERBOSE)
    public static class Mock {
        /**
         * 
         */
        @MockMethod(targetClass = TableShardingServiceImpl.class, targetMethod = "getDataRo2")
        private DataRO<List<User>> getDataRo2(UserTemplate userTemplateTmp) {
            DataRO<List<User>> data = new DataRO<>();
            switch ((String) TestableTool.MOCK_CONTEXT.get("case")) {
            case "case1":
                List<User> list = new ArrayList<>(1);
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

                list.add(user);
                data.setData(list);

                return data;
            default:
                return any();
            }
        }
        /**
         * 
         */
        @MockMethod(targetClass = TableShardingServiceImpl.class, targetMethod = "getDataRo3")
        private DataRO<List<Map<Boolean,User>>> getDataRo3(int aTmp) {
            DataRO<List<Map<Boolean,User>>> data = new DataRO<>();
            switch ((String) TestableTool.MOCK_CONTEXT.get("case")) {
            case "case1":
                List<Map<Boolean, User>> list = new ArrayList<>(1);
                Map<Boolean, User> map = new HashMap<>(1);
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

                map.put(true, user);
                list.add(map);
                data.setData(list);

                return data;
            default:
                return any();
            }
        }
        /**
         * 
         */
        @MockMethod(targetClass = TableShardingServiceImpl.class, targetMethod = "getDataRO22")
        private DataRO<Integer> getDataRO22(int iTmp) {
            DataRO<Integer> data = new DataRO<>();
            switch ((String) TestableTool.MOCK_CONTEXT.get("case")) {
            case "case1":
                data.setData(0);
                return data;
            default:
                return any();
            }
        }
        /**
         * 
         */
        @MockMethod(targetClass = TableShardingServiceImpl.class, targetMethod = "getListMap2333")
        private Map<Boolean,User> getListMap2333(int aTmp) {
            Map<Boolean,User> mapObj = new HashMap<>(1);
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

                mapObj.put(true, user);
                return mapObj;
            default:
                return any();
            }
        }
        /**
         * 测试string
         */
        @MockMethod(targetClass = TableShardingServiceImpl.class, targetMethod = "testString")
        private String testString(int aTmp) {
            switch ((String) TestableTool.MOCK_CONTEXT.get("case")) {
            case "case1":
                return "";
            default:
                return any();
            }
        }
        /**
         * 
         */
        @MockMethod(targetClass = TableShardingServiceImpl.class, targetMethod = "getListMap2")
        private List<Map<Boolean,User>> getListMap2(int aTmp) {
            List<Map<Boolean,User>> mapList = new ArrayList<>(1);
            switch ((String) TestableTool.MOCK_CONTEXT.get("case")) {
            case "case1":
                Map<Boolean, User> map = new HashMap<>(1);
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

                map.put(true, user);
                mapList.add(map);
                return mapList;
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
         * 
         */
        @MockMethod(targetClass = TableShardingServiceImpl.class, targetMethod = "getMapDataRO")
        private DataRO<Map<Boolean,User>> getMapDataRO(Map map2Tmp) {
            DataRO<Map<Boolean,User>> data = new DataRO<>();
            switch ((String) TestableTool.MOCK_CONTEXT.get("case")) {
            case "case1":
                Map<Boolean, User> map = new HashMap<>(1);
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

                map.put(true, user);
                data.setData(map);

                return data;
            default:
                return any();
            }
        }
        /**
         * 
         */
        @MockMethod(targetClass = TableShardingServiceImpl.class, targetMethod = "getPage2")
        private PageRO<List<User>> getPage2(int iTmp) {
            PageRO<List<User>> data = new PageRO<>();
            switch ((String) TestableTool.MOCK_CONTEXT.get("case")) {
            case "case1":
                List<User> list = new ArrayList<>(1);
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

                list.add(user);
                data.setData(list);

                return data;
            default:
                return any();
            }
        }
        /**
         * 
         */
        @MockMethod(targetClass = TableShardingServiceImpl.class, targetMethod = "testInt2")
        private int testInt2(boolean flagTmp) {
            switch ((String) TestableTool.MOCK_CONTEXT.get("case")) {
            case "case1":
                return 0;
            default:
                return any();
            }
        }
        /**
         * 
         */
        @MockMethod(targetClass = TableShardingServiceImpl.class, targetMethod = "getDataRo1")
        private DataRO<User> getDataRo1(int aTmp) {
            DataRO<User> data = new DataRO<>();
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

                data.setData(user);
                return data;
            default:
                return any();
            }
        }
        /**
         * 
         */
        @MockMethod(targetClass = TableShardingServiceImpl.class, targetMethod = "getListMap2322233")
        private User getListMap2322233(int aTmp) {
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
        /**
         * 
         */
        @MockMethod(targetClass = TableShardingServiceImpl.class, targetMethod = "getListMap2222")
        private List<User> getListMap2222(int aTmp) {
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
        @MockMethod(targetClass = TableShardingServiceImpl.class, targetMethod = "getPage1")
        private PageRO<User> getPage1(int iTmp) {
            PageRO<User> data = new PageRO<>();
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

                data.setData(user);
                return data;
            default:
                return any();
            }
        }
        /**
         * 
         */
        @MockMethod(targetClass = UserServiceImpl.class, targetMethod = "getListMap")
        private List<Map<String,User>> getListMap(int aTmp) {
            List<Map<String,User>> mapList = new ArrayList<>(1);
            switch ((String) TestableTool.MOCK_CONTEXT.get("case")) {
            case "case1":
                Map<String, User> map = new HashMap<>(1);
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

                map.put("", user);
                mapList.add(map);
                return mapList;
            default:
                return any();
            }
        }
        /**
         * 测试map注解
         */
        @MockMethod(targetClass = UserServiceImpl.class, targetMethod = "getUserMap")
        private Map<String,User> getUserMap(UserQueryBo queryTmp) {
            Map<String,User> mapObj = new HashMap<>(1);
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

                mapObj.put("", user);
                return mapObj;
            default:
                return any();
            }
        }
        /**
         * 
         */
        @MockMethod(targetClass = TableShardingServiceImpl.class, targetMethod = "getDataRO22222")
        private DataRO<BigDecimal> getDataRO22222(int iTmp) {
            DataRO<BigDecimal> data = new DataRO<>();
            switch ((String) TestableTool.MOCK_CONTEXT.get("case")) {
            case "case1":
                data.setData(new BigDecimal(0));
                return data;
            default:
                return any();
            }
        }
        /**
         * 
         */
        @MockMethod(targetClass = TableShardingServiceImpl.class, targetMethod = "getCompute2")
        private DataRO<BigDecimal> getCompute2(int iTmp) {
            DataRO<BigDecimal> data = new DataRO<>();
            switch ((String) TestableTool.MOCK_CONTEXT.get("case")) {
            case "case1":
                data.setData(new BigDecimal(0));
                return data;
            default:
                return any();
            }
        }
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
        @MockMethod(targetClass = TableShardingServiceImpl.class, targetMethod = "getCompute")
        private DataRO<BigDecimal> getCompute(int iTmp) {
            DataRO<BigDecimal> data = new DataRO<>();
            switch ((String) TestableTool.MOCK_CONTEXT.get("case")) {
            case "case1":
                data.setData(new BigDecimal(0));
                return data;
            default:
                return any();
            }
        }
    }
    /** Mock返回数据-----------------------------End----------------------------- */
}
