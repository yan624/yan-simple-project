package io.github.yan624.simpleproject.config.shiro;

import io.github.yan624.simpleproject.config.factory.FilterChainDefinitionMapBuilder;
import io.github.yan624.simpleproject.shiro.MyRealm;
import io.github.yan624.utils.format.LogPrintingTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @Auther: Zhu Chongyan
 * @Date: 2018/8/8 23:02
 * @Version: 1.0
 */
@Configuration
@Slf4j
public class ShiroConfiguration {

    /**
     * 过期时间30天
     */
    public static final int TIMEOUT = 2592000;
    private ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") DefaultWebSecurityManager manager,
                                              FilterChainDefinitionMapBuilder filterChainDefinitionMapBuilder) {
        shiroFilter.setSecurityManager(manager);
        // 配置登录的url和登录成功的url
        shiroFilter.setLoginUrl("/login.html");

        //配置自定义filter
        shiroFilter.setFilters(filterMap());
        log.info(new LogPrintingTemplate()
                .type("shiroFilterMap accomplishment...")
                .print());

        // 配置访问权限
        Map<String, String> filterChainDefinitionMap = filterChainDefinitionMapBuilder
                .buildFilterChainDefinitionMap();
        shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);

        log.info(new LogPrintingTemplate()
                .type("ShiroFilterFactoryBean accomplishment...")
                .print());
        return shiroFilter;
    }

    @Bean
    public FilterChainDefinitionMapBuilder filterChainDefinitionMapBuilder() {
        return new FilterChainDefinitionMapBuilder();
    }

    /**
     * shiro的过滤器的映射配置
     * @return
     */
    public Map<String, Filter> filterMap(){
        Map<String, Filter> map = new HashMap<>();
//        map.put("ssoUser", shiroSSOFilter);
//        map.put("perms", new TipsPermissionsAuthorizationFilter());
//        map.put("roles", new TipsRolesAuthorizationFilter());
        return map;
    }

    //---------------------------------------start filer configuration----------------------------------------

    //---------------------------------------end filer configuration------------------------------------------

    //---------------------------------------start securityManager------------------------------------------
    // 配置核心安全事务管理器
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager(@Qualifier("myRealm") Realm realm,
                                                     @Qualifier("cookieRememberMeManager") RememberMeManager rememberMeManager,
                                                     @Qualifier("webSessionManager") DefaultWebSessionManager webSessionManager) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(realm);
        manager.setSessionManager(webSessionManager);
        manager.setRememberMeManager(rememberMeManager);
        log.info("shiro securityManager accomplishment...");
        return manager;
    }

    // 配置自定义的权限登录器
    @Bean(name = "myRealm")
    public MyRealm myRealm(@Qualifier("credentialsMatcher") CredentialsMatcher matcher) {
        MyRealm myRealm = new MyRealm();
        myRealm.setCredentialsMatcher(matcher);
        return myRealm;
    }

    // 配置均可通过的的密码比较器
    @Bean(name = "credentialsMatcher")
    public CredentialsMatcher credentialsMatcher() {
        return new SimpleCredentialsMatcher();
    }

    //记住我的管理器
    @Bean(name = "cookieRememberMeManager")
    public CookieRememberMeManager cookieRememberMeManager(@Qualifier("simpleCookie") SimpleCookie simpleCookie) {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(simpleCookie);
        return cookieRememberMeManager;
    }

    //记住我的cookie
    @Bean("simpleCookie")
    public SimpleCookie rememberMeCookie() {
        // System.out.println("ShiroConfiguration.rememberMeCookie()");
        // 这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setMaxAge(TIMEOUT);
        return simpleCookie;
    }

    @Bean(name = "webSessionManager")
    public DefaultWebSessionManager webSessionManager(@Qualifier("sessionDAO") SessionDAO sessionDAO) {
        DefaultWebSessionManager webSessionManager = new DefaultWebSessionManager();
        webSessionManager.setSessionDAO(sessionDAO);
        return webSessionManager;
    }

    @Bean(name = "sessionDAO")
    public SessionDAO SessionDAO() {
        return new MemorySessionDAO();
    }
    //---------------------------------------end securityManager--------------------------------------------


    @Bean
    public ModularRealmAuthenticator authenticator() {
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return authenticator;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            @Qualifier("securityManager") DefaultWebSecurityManager manager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(manager);
        return advisor;
    }

}