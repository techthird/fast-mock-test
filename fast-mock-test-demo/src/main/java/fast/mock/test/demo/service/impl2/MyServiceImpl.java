package fast.mock.test.demo.service.impl2;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunji.item.api.IItemService;
import fast.mock.test.demo.entity.TableSharding;
import fast.mock.test.demo.entity.User;
import fast.mock.test.demo.mapper.TableShardingMapper;
import fast.mock.test.demo.query.TableShardingQueryBo;
import fast.mock.test.demo.query.UserQueryBo;
import fast.mock.test.demo.service.ITableShardingService;
import fast.mock.test.demo.service.IUserService;
import fast.mock.test.demo.service.MyService;
import fast.mock.test.demo.service.impl.TableShardingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 分片表1  服务实现类
 * </p>
 *
 * @author chenhx
 * @since 2020-12-29
 */
@Service("myService")
public class MyServiceImpl {

    @Autowired
    private IItemService itemService;

    public List<User> selectBenchItemStock2(List<Integer> itemIds) {
        itemService.assembleSimpleShopItemBoListForApp(null, 1);
        return null;
    }






}

