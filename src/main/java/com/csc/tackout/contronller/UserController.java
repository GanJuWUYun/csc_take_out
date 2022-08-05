package com.csc.tackout.contronller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.csc.tackout.common.R;
import com.csc.tackout.entity.User;
import com.csc.tackout.service.UserService;
import com.csc.tackout.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * ClassName:UserContronller
 * Package:com.csc.tackout.contronller
 * Description:
 *
 * @Date:5/8/2022 16:58
 * @Author:2394279444@qq.com
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user,HttpSession session){
        //获取手机号
        String phone = user.getPhone();
        if(StringUtils.isNotEmpty(phone)){
            //生成随机的4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();

            log.info("code={}",code);

            //需要将生成的验证码保存到session
            session.setAttribute(phone,code);
            return R.success("手机验证码发送成功");
        }
        return R.error("发送失败");
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map map , HttpSession session){
        //获取手机号
        String phone = map.get("phone").toString();
        //获取验证码
        String code = map.get("code").toString();
        //从session中获取保存的验证码
        Object codeInSession = session.getAttribute(phone);
        //进行验证码的比对
        if(codeInSession!=null && codeInSession.equals(code)){
            //如果比对成功就说明登录成功
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);
            User user = userService.getOne(queryWrapper);
            if (user == null){
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user",user.getId());
            return R.success(user);
        }



        return R.error("登录失败");
    }
}
