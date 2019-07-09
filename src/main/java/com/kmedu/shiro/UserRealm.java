package com.kmedu.shiro;

import com.kmedu.domain.User;
import com.kmedu.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    //执行授权逻辑
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权逻辑");
        //给资源进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //添加资源的授权字符串
        //info.addStringPermission("user:add");

        //到数据库查询当前登录用户的授权字符串
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        User dbUser = userService.findById(user.getId());
        info.addStringPermission(dbUser.getPerms());
        return info;
    }
//执行认证逻辑
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0)
            throws AuthenticationException {
        System.out.println("执行认证逻辑");
        //假设数据库的用户名和密码
//        String name = "admin";
//        String password = "admin";

        //编写shiro判断逻辑，判断用户名和密码
        //判断用户名
        UsernamePasswordToken token = (UsernamePasswordToken)arg0;

        User user = userService.findByName(token.getUsername());

        if(user==null){
            //用户名不存在
            return null; //shiro底层会抛出UnknownAccountException
        }
        //判断密码
        return new SimpleAuthenticationInfo(user,user.getPassword(),"");
    }

}
