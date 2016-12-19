package cn.tju.scs.service.impl;

import cn.tju.scs.domain.User;
import cn.tju.scs.mapper.UserMapper;
import cn.tju.scs.service.IUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表  服务实现类
 * </p>
 *
 * @author daisygao
 * @since 2016-12-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
	
}
