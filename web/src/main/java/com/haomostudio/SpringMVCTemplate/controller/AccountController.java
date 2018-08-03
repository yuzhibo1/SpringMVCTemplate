package com.haomostudio.SpringMVCTemplate.controller;

import com.haomostudio.SpringMVCTemplate.dao.impl.IHmUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName(类名) : MatchString
 * @Description(描述) : TODO
 * @author(作者) ：lenovo
 * @date (开发日期)      ：2018/8/2
 */
public class AccountController {
    @Autowired
    IHmUserService hmUserService;


    @RequestMapping("/Login")
    @ResponseBody
    public Map<String, Object> loginShiro(HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> result = new HashMap<String, Object>();
        String loginId = request.getParameter("loginId");
        String pwd = request.getParameter("password");
        //此处密码为明文在登ShiroUserAccountUtil.getSubject().login时会替换为密文
        UsernamePasswordToken token = new UsernamePasswordToken(loginId, pwd);
        try {
            //4、登录，即身份验证
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
        } catch (AuthenticationException e) {
            result.put("success", false);
            result.put("msg","用户名或密码错误");
            return result;
        }
        return result;
    }
}
