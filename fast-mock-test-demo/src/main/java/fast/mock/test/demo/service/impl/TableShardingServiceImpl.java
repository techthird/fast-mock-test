package fast.mock.test.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.venusplan.framework.api.DataRO;
import com.venusplan.framework.api.PageRO;
import fast.mock.test.demo.entity.TableSharding;
import fast.mock.test.demo.entity.User;
import fast.mock.test.demo.entity.UserTemplate;
import fast.mock.test.demo.mapper.TableShardingMapper;
import fast.mock.test.demo.query.TableShardingQueryBo;
import fast.mock.test.demo.service.ITableShardingService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
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
    public TableSharding getOne2() {
        System.out.println("222222");
        QueryWrapper<TableSharding> queryWrapper = new TableShardingQueryBo().buildQuery();
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
    public String testString(int a) {
        return null;
    }

    public List<Map<String,Map<Integer,User>>> getListMap1(int a) {
        return null;
    }
    public List<Map<Boolean,User>> getListMap2(int a) {
        return null;
    }
    public Map<Boolean,User> getListMap2333(int a) {
        return null;
    }
    public User getListMap2322233(int a) {
        return null;
    }
    public List<User> getListMap2222(int a) {
        return null;
    }
    public DataRO<User> getDataRo1(int a) {
        return null;
    }
    public DataRO<List<User>> getDataRo2(UserTemplate userTemplate) {
        return null;
    }
    public DataRO<Map<Boolean,User>> getMapDataRO(Map<String,User> map2) {
        DataRO<Map<Boolean,User>> dataRO = new DataRO<>();
        Map<Boolean, User> map = new HashMap<>(1);
        User object = new User();
        object.setId(11);
        object.setName("11");
        map.put(false,object);
        dataRO.setData(map);
        return dataRO;
    }
    public DataRO<List<Map<Boolean,User>>> getDataRo3(int a) {
        DataRO<List<Map<Boolean,User>>> dataRO = new DataRO<>();
        List<Map<Boolean, User>> list = new ArrayList<>(1);
        Map<Boolean, User> map = new HashMap<>(1);
        User object = new User();
        object.setId(11);
        object.setName("11");
        map.put(false,object);
        list.add(map);
        dataRO.setData(list);
        return null;
    }

    /**
     * 测试testBoolean
     */
    public boolean testBoolean(Double d) {
        return false;
    }

    public PageRO<User> getPage1(int i) {
        return null;
    }
    public PageRO<List<User>> getPage2(int i) {
        return null;
    }
    public DataRO<Integer> getDataRO22(int i) {
        return null;
    }

    public DataRO<BigDecimal> getDataRO22222(int i) {
        return null;
    }
}
