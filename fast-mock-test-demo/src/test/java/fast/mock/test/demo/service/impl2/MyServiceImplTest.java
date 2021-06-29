/*
* fast-mock-test
*/
package fast.mock.test.demo.service.impl2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.alibaba.testable.core.annotation.MockDiagnose;
import com.alibaba.testable.core.model.ClassType;
import com.alibaba.testable.core.annotation.MockMethod;
import com.alibaba.testable.core.model.LogLevel;
import com.alibaba.testable.core.tool.TestableTool;
import com.alibaba.testable.core.annotation.MockWith;
import org.junit.*;
import java.math.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.*;
import java.lang.*;
import static org.mockito.ArgumentMatchers.*;
import fast.mock.test.demo.mapper.TableShardingMapper;
import fast.mock.test.demo.entity.User;
import fast.mock.test.demo.entity.User;
import fast.mock.test.demo.entity.TableSharding;
import fast.mock.test.demo.service.ITableShardingService;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.core.metadata.IPage;
import fast.mock.test.demo.query.TableShardingQueryBo;
import fast.mock.test.demo.service.IUserService;
import fast.mock.test.demo.entity.TableSharding;
import fast.mock.test.demo.query.TableShardingQueryBo.QueryBoExt;
import fast.mock.test.demo.query.UserQueryBo;

/**
* MyServiceImplTest
*
* @author 
* @date 2021-06-29 14:20:15
*/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@MockWith(value=MyServiceImplMock.class, treatAs = ClassType.TestClass)
public class MyServiceImplTest /*extends BaseTestCase*/ {

    @Autowired
    private MyServiceImpl myServiceImpl;

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

}
