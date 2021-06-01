package fast.mock.test.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fast.mock.test.demo.query.TableShardingQueryBo;
import fast.mock.test.demo.query.UserQueryBo;

/**
 * <p>
 * 分片表1  服务实现类
 * </p>
 *
 * @author chenhx
 * @since 2020-12-29
 */
public interface MyService {

    public IPage<UserQueryBo> pageTestParent(TableShardingQueryBo query, int pageNo, int pageSize);



}

