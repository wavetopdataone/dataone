package com.cn.wavetop.dataone.config.shiro;


import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager securityManager){
        ShiroFilterFactoryBean bean=new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);
//        LinkedHashMap<String,String> filterMap=new LinkedHashMap<>();
//        filterMap.put("/index","authc");
//        filterMap.put("/login","anon");
        //bean.setFilterChainDefinitionMap(filterMap);
        //自定义拦截器
        Map<String, Filter> customFilterMap = new LinkedHashMap<>();
        customFilterMap.put("corsAuthenticationFilter", new CORSAuthenticationFilter());
        bean.setFilters(customFilterMap);
        return bean;
    }


    @Bean("securityManager")
    public SecurityManager securityManager(@Qualifier("myShiroRelam") MyShiroRelam myShiroRelam,@Qualifier("sessionManager")SessionManager sessionManager){
        DefaultWebSecurityManager manager=new DefaultWebSecurityManager();
        //manager.setCacheManager(cacheManager());
        manager.setCacheManager(ehCacheManager());
        manager.setSessionManager(sessionManager);
        manager.setRealm(myShiroRelam);
        return manager;
    }

    @Bean("myShiroRelam")
    public MyShiroRelam  myShiroRelam(@Qualifier("credentialsMatcher") CredentialMatcher matcher){
      MyShiroRelam myShiroRelam=new MyShiroRelam();
      myShiroRelam.setCredentialsMatcher(matcher);
        return myShiroRelam;
    }

    @Bean("credentialsMatcher")
    public CredentialMatcher credentialsMatcher(){
        return new CredentialMatcher();
    }
    //开启shiro aop注解支持，不开启的话权限验证就会失效

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager( securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator creator=new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }
    /**
     * Session Manager：会话管理
     * 即用户登录后就是一次会话，在没有退出之前，它的所有信息都在会话中；
     * 会话可以是普通JavaSE环境的，也可以是如Web环境的；
     */
    @Bean("sessionManager")
    public SessionManager sessionManager(){
        ShiroSessionManager sessionManager = new ShiroSessionManager();
        sessionManager.setSessionDAO(new EnterpriseCacheSessionDAO());

//        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//        sessionManager.setSessionDAO(sessionDAO());
//        //设置session过期时间
//        sessionManager.setGlobalSessionTimeout(60 * 60 * 1000);
//        sessionManager.setSessionValidationSchedulerEnabled(true);
//        // 去掉shiro登录时url里的JSESSIONID
//        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }
    @Bean
    public CacheManager cacheManager() {
        return new MemoryConstrainedCacheManager();
    }

    @Bean
    public EhCacheManager ehCacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return cacheManager;
    }
    @Bean
    public SessionDAO sessionDAO() {
        return new MemorySessionDAO();//使用默认的MemorySessionDAO
    }



//    //配置异常处理，不配置的话没有权限后台报错，前台不会跳转到403页面
//    @Bean(name="simpleMappingExceptionResolver")
//    public SimpleMappingExceptionResolver
//    createSimpleMappingExceptionResolver() {
//        SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
//        Properties mappings = new Properties();
//        mappings.setProperty("DatabaseException", "databaseError");//数据库异常处理
//        mappings.setProperty("UnauthorizedException","403");
//        simpleMappingExceptionResolver.setExceptionMappings(mappings);  // None by default
//        simpleMappingExceptionResolver.setDefaultErrorView("error");    // No default
//        simpleMappingExceptionResolver.setExceptionAttribute("ex");     // Default is "exception"
//        return simpleMappingExceptionResolver;
//    }

}
