package io.github.yan624.simpleproject.shiro;

import io.github.yan624.simpleproject.config.properties.SystemConfigProperties;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;

/**
 * @Author: Zhu Chongyan
 * @program: study-video
 * @Date: 2019/03/27 15:10
 */
public class MyRealm extends AuthorizingRealm {
    @Autowired
    private SystemConfigProperties systemConfigProperties;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken simpleToken = (UsernamePasswordToken) token;
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo();
        return simpleAuthenticationInfo;
    }
}
