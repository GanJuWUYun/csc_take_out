package com.csc.tackout.Ipml;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csc.tackout.entity.User;
import com.csc.tackout.mapper.UserMapper;
import com.csc.tackout.service.UserService;
import org.springframework.stereotype.Service;

/**
 * ClassName:UserServiceIpml
 * Package:com.csc.tackout.Ipml
 * Description:
 *
 * @Date:5/8/2022 16:33
 * @Author:2394279444@qq.com
 */
@Service
public class UserServiceIpml extends ServiceImpl<UserMapper, User> implements UserService {
}
