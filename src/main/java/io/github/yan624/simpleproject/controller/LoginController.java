package io.github.yan624.simpleproject.controller;

import io.github.yan624.common.base.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: Zhu Chongyan
 * @program: simple project
 * @Date: 2019/03/27 15:29
 */
@RestController
@RequestMapping("/")
@Slf4j
public class LoginController extends BaseController {

    @GetMapping("login.html")
    public ModelAndView showLogin(){
        return new ModelAndView(thymeleaf("login"));
    }

    @PostMapping("login")
    public ModelAndView login(String account, String password, String rememberMe){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(account, password);
        if (!subject.isAuthenticated()) {
            try {
                if (rememberMe.equals("on")) {
                    token.setRememberMe(true);
                }
            } catch (Exception e) {
            }
            try {
                subject.login(token);
            } catch (UnknownAccountException unknownException) {
                throw new UnknownAccountException(unknownException.getMessage());
            } catch (AuthenticationException authcException) {
                throw new AuthenticationException(authcException.getMessage());
            } catch (ShiroException root) {
                throw new ShiroException(root.getMessage());
            } catch (Exception e) {
                log.error("登录时发生了未知的shiro异常", e);
                e.printStackTrace();
            }
        }
        return new ModelAndView(redirect("index.html"));
    }
}
