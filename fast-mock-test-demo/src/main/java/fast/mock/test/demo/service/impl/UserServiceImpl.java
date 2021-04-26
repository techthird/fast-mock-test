package fast.mock.test.demo.service.impl;

import fast.mock.test.demo.entity.User;
import fast.mock.test.demo.mapper.UserMapper;
import fast.mock.test.demo.query.UserQueryBo;
import fast.mock.test.demo.service.IUserService;
import fast.mock.test.demo.entity.User;
import fast.mock.test.demo.mapper.UserMapper;
import fast.mock.test.demo.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import fast.mock.test.demo.query.UserQueryBo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *   服务实现类
 * </p>
 *
 * @author chenhx
 * @since 2020-12-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    /**
     * 按照条件分页查询
     * @param query
     */
    @Override
    public IPage<User> page(UserQueryBo query, int pageNo, int pageSize){
        if(query==null){
            return new Page<>();
        }
        IPage<User> page = new Page<>();
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        return null;
    }

    @Override
    public User getUser(UserQueryBo query) {
        QueryWrapper<User> queryWrapper = query.buildQuery();
        return this.getOne(queryWrapper);
    }

    /**
     * 测试map注解
     * @param query
     */
    public Map<String,User> getUserMap(UserQueryBo query) {
        QueryWrapper<User> queryWrapper = query.buildQuery();
        return null;
    }
    public Map<String,Object> getUserMap2(UserQueryBo query, boolean bool) {
        QueryWrapper<User> queryWrapper = query.buildQuery();
        return null;
    }

    @Override
    public boolean update(User entity,UserQueryBo query) {
        if(entity==null || query==null){
            return false;
        }
        return this.update(entity,query.buildQuery());
    }

    @Override
    public boolean updateByColumn(User entity, String column, Object value) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(column,value);
        return this.update(entity, queryWrapper);
    }

    /**
     * 查询用户列表
     * @param query
     */
    @Override
    public List<User> listUser(UserQueryBo query) {
        if(query==null){
            return new ArrayList<>(2);
        }
        return this.list(query.buildQuery());
    }

}
