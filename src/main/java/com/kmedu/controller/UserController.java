package com.kmedu.controller;

import com.kmedu.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {



    @ResponseBody
    @RequestMapping("/hello")
    public String hello(){
        System.out.println("UserController.hello()");
        return "ok";
    }

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "/login";
    }

    @RequestMapping("/noAuth")
    public String noAuth(){
        return "/noAuth";
    }


    @RequestMapping("/add")
    public String add(){
        return "user/add";
    }

    @RequestMapping("/update")
    public String update(){
        return "user/update";
    }

    /**
     * 测试thymeleaf
     */
    @RequestMapping("/testThymeleaf")
    public String testThymeleaf(Model model){
        //把数据存入model
        model.addAttribute("name", "谭华");
        //返回test.html
        return "test";
    }

    //登录的主要逻辑
    @RequestMapping("/login")
    public String login(String name,String password,Model model){
        //使用shiore编写认证操作
        //1、获取subject
        Subject subject = SecurityUtils.getSubject();
        //2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(name,password);
        //3、执行登录方法
        try {
            subject.login(token);
            System.out.println("登录成功");
            //跳转到test.html页面
            return "redirect:/testThymeleaf";
        }catch (UnknownAccountException e){
            //e.printStackTrace();
            System.out.println("登录失败,用户名不存在，");
            model.addAttribute("msg","登录失败,用户名不存在");
            return "login";
        }catch (IncorrectCredentialsException e){
            //e.printStackTrace();
            System.out.println("用户名或密码错误，登录失败");
            model.addAttribute("msg","登录失败用户名或密码错误");
            return "login";
        }
    }
}
