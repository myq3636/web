package com.jx.tennis.framework.shiro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.jx.tennis.common.ShiroConstants;
import com.jx.tennis.common.exception.user.CaptchaException;
import com.jx.tennis.common.exception.user.UserBlockedException;
import com.jx.tennis.common.exception.user.UserNotExistsException;
import com.jx.tennis.common.utils.security.ShiroUtils;
import com.jx.tennis.project.system.user.domain.User;
import com.jx.tennis.project.system.user.domain.UserStatus;
import com.jx.tennis.service.UserService;
import com.jx.tennis.util.DateUtils;
import com.jx.tennis.util.ServletUtils;


/**
 * 登录校验方法
 * 
 * @author ruoyi
 */
@Component
public class LoginService
{
    @Autowired
    private PasswordService passwordService;

    @Autowired
    private UserService userService;

    /**
     * 登录
     */
    public User login(String username, String password)
    {
        // 验证码校验
        if (!StringUtils.isEmpty(ServletUtils.getRequest().getAttribute(ShiroConstants.CURRENT_CAPTCHA)))
        {
            throw new CaptchaException();
        }
        // 用户名或密码为空 错误
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
        {
            throw new UserNotExistsException();
        }        

        // 查询用户信息
        User user = userService.selectUserByLoginName(username);

        if (user == null || UserStatus.DELETED.getCode().equals(user.getDelFlag()))
        {
            throw new UserNotExistsException();
        }

        passwordService.validate(user, password);

        if (UserStatus.DISABLE.getCode().equals(user.getStatus()))
        {
            throw new UserBlockedException(user.getRemark());
        }
        recordLoginInfo(user);
        return user;
    }    

    /**
     * 记录登录信息
     */
    public void recordLoginInfo(User user)
    {
        user.setLoginIp(ShiroUtils.getIp());
        user.setLoginDate(DateUtils.getNowDate());
    }

}
