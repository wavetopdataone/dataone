package com.cn.wavetop.dataone.config.shiro;


import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
        return bean;
    }
    @Bean("securityManager")
    public SecurityManager securityManager(@Qualifier("myShiroRelam") MyShiroRelam myShiroRelam){
        DefaultWebSecurityManager manager=new DefaultWebSecurityManager();
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
