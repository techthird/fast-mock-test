package fast.mock.test.demo.service.impl2;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fast.mock.test.demo.entity.TableSharding;
import fast.mock.test.demo.entity.User;
import fast.mock.test.demo.mapper.TableShardingMapper;
import fast.mock.test.demo.query.TableShardingQueryBo;
import fast.mock.test.demo.query.UserQueryBo;
import fast.mock.test.demo.service.ITableShardingService;
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
    private TableShardingMapper tableShardingMapper;
    @Autowired
    private IUserService userService;
    @Autowired
    private ITableShardingService tableShardingService;

    /**
     * 按照条件分页查询
     * @param query
     */
    @Override
    public IPage<UserQueryBo> pageTestParent(TableShardingQueryBo query, int pageNo, int pageSize){
        if(query==null){
            return new Page<>();
        }
        QueryWrapper<TableSharding> queryWrapper = query.buildQuery();
        UserQueryBo userQueryBo1 = new UserQueryBo();
        userQueryBo1.setId(10000);
        userQueryBo1.setEmail("test222@qq.com");
        userQueryBo1.setName("fdaf222222222222d打发第三方");

        QueryWrapper<TableSharding> qw = new QueryWrapper<>();
        userService.getUser(null);
        userService.listUser(null);

        Page<TableSharding> page = tableShardingMapper.selectPage(new Page<>(1, 2), qw);
        UserQueryBo userQueryBo = new UserQueryBo();
        userQueryBo.setEmail("fjdljf@qq.com");
        userQueryBo.setId(12000);

        Page<UserQueryBo> objectPage = new Page<>();
        objectPage.addOrder(new OrderItem().setColumn("测试Column"));
        return objectPage;
    }








}

