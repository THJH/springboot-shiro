package com.kmedu.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration //声明为配置类
public class ShiroConfig  {

    //3、创建ShiroFilterFactoryBean
    @Bean  //注入Spring
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager")DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //1、设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        /**
         *    Shiro内置过滤器，可以实现权限相关的拦截器
         *    常用的过滤器：
         *       anon: 无需认证（登录）可以访问
         *       authc: 必须认证才可以访问
         *       user: 如果使用rememberMe的功能可以直接访问
         *       perms： 该资源必须得到资源权限才可以访问
         *       role: 该资源必须得到角色权限才可以访问
         */
        LinkedHashMap<String, String> filterMap = new LinkedHashMap<String, String>();
       //对user下的资源拦截
        /*filterMap.put("/add","authc");
        filterMap.put("/update","authc");*/

        //对user下的资源拦截
        //filterMap.put("/user","authc");

        //对某一个资源放行
        filterMap.put("/testThymeleaf","anon");
        //放行login
        filterMap.put("/login","anon");

        //授权过滤器
        //注意：当前授权拦截后，shiro会自动跳转到未授权页面
        filterMap.put("/add","perms[user:add]");
        filterMap.put("/update","perms[user:update]");

        //对所有的资源拦截
        filterMap.put("/*","authc");



        //修改跳转的登录页面
        shiroFilterFactoryBean.setLoginUrl("/toLogin");

        //设置未授权提示页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        return  shiroFilterFactoryBean;
    }


    //2、创建DefaultWebSecurityManage
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManage(@Qualifier("userRealm")UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        //关联realm
        securityManager.setRealm(userRealm);

        return securityManager;
    }

    //1、创建realm
    @Bean(name = "userRealm")
    public UserRealm getTealm(){
        return new UserRealm();
    }

    //配置ShiroDialect，用于thymeleaf和shiro标签配合使用
    @Bean
    public ShiroDialect getShiroDialect(){
        return  new ShiroDialect();
    }
}
