package fast.mock.test.demo.service.impl2;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fast.mock.test.demo.entity.TableSharding;
import fast.mock.test.demo.entity.User;
import fast.mock.test.demo.query.TableShardingQueryBo;
import fast.mock.test.demo.query.UserQueryBo;
import fast.mock.test.demo.service.IUserService;
import fast.mock.test.demo.service.MyService;
import fast.mock.test.demo.service.impl.TableShardingServiceImpl;
import fast.mock.test.demo.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 分片表1  服务实现类
 * </p>
 *
 * @author chenhx
 * @since 2020-12-29
 */
@Service("myService")
public class MyServiceImpl implements MyService{

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private IUserService userService2;
    @Autowired
    private TableShardingServiceImpl tableShardingService;

    /**
     * 按照条件分页查询
     * @param query
     */
    @Override
    public IPage<UserQueryBo> pageTestParent(TableShardingQueryBo query, int pageNo, int pageSize){
        if(query==null){
            return new Page<>();
        }
        IPage<TableSharding> page = new Page<>();
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        QueryWrapper<TableSharding> queryWrapper = query.buildQuery();
        UserQueryBo userQueryBo1 = new UserQueryBo();
        userQueryBo1.setId(10000);
        userQueryBo1.setEmail("test222@qq.com");
        userQueryBo1.setName("fdaf222222222222d打发第三方");

        tableShardingService.getDataRo3(22);
        tableShardingService.getDataRo2(null);
        tableShardingService.getMapDataRO(null);
        tableShardingService.getDataRo1(0);
        tableShardingService.getListMap1(1);
        tableShardingService.getListMap2(2);
        tableShardingService.getListMap2222(2);
        tableShardingService.getListMap2333(2);
        tableShardingService.getListMap2322233(2);
        tableShardingService.testString(2);
        tableShardingService.getPage1(2);
        tableShardingService.getPage2(2);
        tableShardingService.getDataRO22(2);
        tableShardingService.testInt2(false);





        UserQueryBo userQueryBo = new UserQueryBo();
        userQueryBo.setEmail("fjdljf@qq.com");
        userQueryBo.setId(12000);

        Page<UserQueryBo> objectPage = new Page<>();
        objectPage.addOrder(new OrderItem().setColumn("测试Column"));
        return objectPage;
    }


    public Map<String,Object> maps(long pageNo, Integer pageSize,Double aa,Boolean bo){
        Map<String, User> user = userService.getUserMap(null);
        List<Map<String, User>> listMap = userService.getListMap(1);
        return null;
    }

    public void maps2(long pageNo){
        List<User> user = userService2.listUser(null);
        List<Map<String, User>> listMap = userService.getListMap(1);
        tableShardingService.getDataRO22(2);
        tableShardingService.getDataRO22222(2);
    }




}

