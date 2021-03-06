package fast.mock.test.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import fast.mock.test.demo.entity.User;
import fast.mock.test.demo.query.UserQueryBo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author chenhx
 * @since 2020-12-29
 */
public interface IUserService extends IService<User> {
    /**
     * 按照条件分页查询
     * @param query 查询条件
     */
    IPage<User> page(UserQueryBo query, int pageNo, int pageSize);

    /**
     * 查询单个的，注意，条件需要有唯一建
     * @param query
     * @return
     */
    User getUser(UserQueryBo query);

    /**
     * 按照条件更新
     * @param entity
     * @param query
     * @return
     */
    boolean update(User entity,UserQueryBo query);

    /**
     * 通过一个相等的条件，修改数据
     * @param entity 修改的数据
     * @param column 数据库列名
     * @param value 条件值
     * @return true-修改成功
     */
    boolean updateByColumn(User entity,String column,Object value);

    /**
     * 按照条件查询所有
     * @param query 查询条件
     */
    List<User> listUser(UserQueryBo query);

}
