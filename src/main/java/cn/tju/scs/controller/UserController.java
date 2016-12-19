package cn.tju.scs.controller;

import cn.tju.scs.domain.User;
import cn.tju.scs.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表  前端控制器
 * </p>
 *
 * @author daisygao
 * @since 2016-12-19
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService userService;

	@RequestMapping("insert")
	public Object insert(User user) {
        userService.insert(user);
        return user;
    }
}
