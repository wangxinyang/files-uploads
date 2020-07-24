package com.wxy.controller;

import com.wxy.entity.User;
import com.wxy.service.UserServie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserServie userServie;

    // 登录方法
    @PostMapping("login")
    public String login(User user, HttpSession httpSession) {
        User userDB = userServie.login(user);
        if (userDB != null) {
            httpSession.setAttribute("user", userDB);
            return "redirect:/file/showAll";
        } else {
            return "redirect:/index";
        }
    }
}
