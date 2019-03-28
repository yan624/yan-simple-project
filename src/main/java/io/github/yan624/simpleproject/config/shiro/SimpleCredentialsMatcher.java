package io.github.yan624.simpleproject.config.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

/**
 * @Author: Zhu Chongyan
 * @program: simple project
 * @Date: 2019/03/27 16:14
 */
public class SimpleCredentialsMatcher implements CredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        Object submittedPassword = token.getCredentials();
        Object storedCredentials = info.getCredentials();
        if(submittedPassword instanceof char[]){
            if(String.copyValueOf((char[]) submittedPassword).equals(storedCredentials)) {
                return true;
            }
        }else{
            if(submittedPassword.equals(storedCredentials))
                return true;
        }
        return false;
    }
}
