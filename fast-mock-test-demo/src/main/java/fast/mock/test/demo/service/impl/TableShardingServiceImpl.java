package fast.mock.test.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fast.mock.test.demo.entity.TableSharding;
import fast.mock.test.demo.mapper.TableShardingMapper;
import fast.mock.test.demo.query.TableShardingQueryBo;
import fast.mock.test.demo.service.ITableShardingService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 分片表1  服务实现类
 * </p>
 *
 * @author chenhx
 * @since 2020-12-29
 */
@Service
public class TableShardingServiceImpl extends ServiceImpl<TableShardingMapper, TableSharding> implements ITableShardingService {
    /**
     * 按照条件分页查询
     * @param query
     */
    @Override
    public IPage<TableSharding> page(TableShardingQueryBo query, int pageNo, int pageSize){
        if(query==null){
            return new Page<>();
        }
        IPage<TableSharding> page = new Page<>();
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        QueryWrapper<TableSharding> queryWrapper = query.buildQuery();
        return this.page(page, queryWrapper);
    }

    @Override
    public TableSharding getOne(TableShardingQueryBo query) {
        QueryWrapper<TableSharding> queryWrapper = query.buildQuery();
        return this.getOne(queryWrapper);
    }

    @Override
    public IPage<TableSharding> page(QueryWrapper<TableSharding> queryWrapper, int pageNo, int pageSize) {
        if(queryWrapper==null){
            return new Page<>();
        }
        IPage<TableSharding> page = new Page<>();
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        return this.page(page, queryWrapper);
    }

    @Override
    public boolean update(TableSharding entity,TableShardingQueryBo query) {
        if(entity==null || query==null){
            return false;
        }
        return this.update(entity,query.buildQuery());
    }

    @Override
    public boolean updateByColumn(TableSharding entity, String column, Object value) {
        QueryWrapper<TableSharding> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(column,value);
        return this.update(entity, queryWrapper);
    }

    @Override
    public List<TableSharding> list(TableShardingQueryBo query) {
        if(query==null){
            return new ArrayList<>(2);
        }
        return this.list(query.buildQuery());
    }

    /**
     * 测试Int
     * @param query
     */
    public Integer testInt(TableShardingQueryBo query) {
        return 0;
    }

    public int testInt2(boolean flag) {
        return 0;
    }

    /**
     * 测试string
     * @param query
     */
    public String testString() {
        return null;
    }

    /**
     * 测试testBoolean
     */
    public boolean testBoolean(Double d) {
        return false;
    }

}
