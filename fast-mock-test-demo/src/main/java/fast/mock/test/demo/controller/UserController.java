package fast.mock.test.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fast.mock.test.demo.entity.User;
import fast.mock.test.demo.query.TableShardingQueryBo;
import fast.mock.test.demo.query.UserQueryBo;
import fast.mock.test.demo.result.ResultModel;
import fast.mock.test.demo.service.IUserService;
import fast.mock.test.demo.service.impl2.MyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *   前端控制器
 * </p>
 *
 * @author chenhx
 * @since 2020-12-29
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private MyServiceImpl myService;

    /**
     * 添加对象
     *
     * @param user 对象
     * @return ResultModel统一响应结果
     */
    @PostMapping("save")
    @ResponseBody
    public ResultModel<Object> save(User user) {
        if(user==null){
            return ResultModel.parameterError();
        }
        iUserService.save(user);
        return ResultModel.success();
    }

    /**
     * 根据ID进行删除
     *
     * @param id 主键
     * @return ResultModel统一响应结果
     */
    @GetMapping("removeById")
    @ResponseBody
    public ResultModel<Object> delete(@RequestParam Integer id) {
        if(id==null || id<0){
            return ResultModel.parameterError();
        }
        myService.pageTestParent(new TableShardingQueryBo(), 0, 1);
        boolean success = iUserService.removeById(id);
        if(success) {
            return ResultModel.success();
        }else {
            return ResultModel.fail();
        }
    }

    /**
     * 根据ID进行修改对象
     *
     * @param user 对象中必须有ID主键
     * @return ResultModel统一响应结果
     */
    @PostMapping("update")
    @ResponseBody
    public ResultModel<Object> update(User user) {
        if(user==null || user.getId()==null){
            return ResultModel.parameterError();
        }
        boolean success = iUserService.updateById(user);
        if(success) {
            return ResultModel.success();
        }else {
            return ResultModel.fail();
        }
    }

    /**
     * 查询详情
     *
     * @param id 主键
     * @return ResultModel统一响应结果
     */
    @GetMapping("getById")
    @ResponseBody
    public ResultModel<User> getById(@RequestParam Integer id) {
        if(id==null || id<0){
            return ResultModel.parameterError();
        }
        User user = iUserService.getById(id);
        return ResultModel.success(user);
    }

    /**
     * 分页查询
     *
     * @return ResultModel统一响应结果
     */
    @PostMapping("page")
    @ResponseBody
    public ResultModel<IPage<User>> page(UserQueryBo queryBo, int pageNo, int pageSize) {
        if(queryBo==null){
            return ResultModel.parameterError();
        }
        IPage<User> templateIPage = iUserService.page(queryBo,pageNo,pageSize);
        return ResultModel.success(templateIPage);
    }

}
