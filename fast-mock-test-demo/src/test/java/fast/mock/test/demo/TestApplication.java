package fast.mock.test.demo;

import fast.mock.test.demo.query.TableShardingQueryBo;
import fast.mock.test.demo.service.impl2.MyServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FastMockTestDemoApplication.class)
public class TestApplication extends AbstractJUnit4SpringContextTests {
    @Resource
    private MyServiceImpl myService;


    @BeforeClass
    public static void setUp() throws Exception {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        System.setProperty("config_env", "local");
    }

    @Test
    public void test1() {
        myService.pageTestParent(new TableShardingQueryBo(), 0, 1);
    }

}
