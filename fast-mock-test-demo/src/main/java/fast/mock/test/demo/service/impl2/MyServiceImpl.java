package fast.mock.test.demo.service.impl2;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fast.mock.test.demo.entity.TableSharding;
import fast.mock.test.demo.entity.User;
import fast.mock.test.demo.query.TableShardingQueryBo;
import fast.mock.test.demo.query.UserQueryBo;
import fast.mock.test.demo.service.impl.TableShardingServiceImpl;
import fast.mock.test.demo.service.impl.UserServiceImpl;
import fast.mock.test.demo.service.impl.UserTemplateServiceImpl;
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
@Service
public class MyServiceImpl{

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserTemplateServiceImpl userTemplateService;
    @Autowired
    private TableShardingServiceImpl tableShardingService;

    /**
     * 按照条件分页查询
     * @param query
     */
    public IPage<UserQueryBo> pageTestParent(TableShardingQueryBo query, int pageNo, int pageSize){
        if(query==null){
            return new Page<>();
        }
        IPage<TableSharding> page = new Page<>();
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        QueryWrapper<TableSharding> queryWrapper = query.buildQuery();
        IPage<User> page1 = userService.page(null, 0, 0);
        List<User> list = userService.listUser(new UserQueryBo());
        userTemplateService.update222(null, null);
        tableShardingService.testInt(null);
        tableShardingService.testInt2(false);
        tableShardingService.testString();
        tableShardingService.testBoolean(0D);
        UserQueryBo userQueryBo = new UserQueryBo();
        userQueryBo.setEmail("fjdljf@qq.com");
        userQueryBo.setId(12000);

        Page<UserQueryBo> objectPage = new Page<>();
        objectPage.addOrder(new OrderItem().setColumn("测试Column"));
        return objectPage;
    }

    public Integer ones(int pageNo,Integer pageSize){
        IPage<TableSharding> page = new Page<>();
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        User user = userService.getOne(null);
        userTemplateService.update222(null, null);
        tableShardingService.testInt(null);
        tableShardingService.testInt2(false);
        tableShardingService.testString();
        return 12;
    }

    public Map<String,Object> maps(long pageNo, Integer pageSize){
        Map<String, User> user = userService.getUserMap(null);
        Map<String, Object> user2 = userService.getUserMap2(null , false);
        List<User> list = userService.listUser(new UserQueryBo());
        userTemplateService.update222(null, null);
        tableShardingService.testInt(null);
        tableShardingService.testInt2(false);
        tableShardingService.testString();
        return user2;
    }


}

