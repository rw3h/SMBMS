package com.smbms.controller;

import com.smbms.pojo.User;
import com.smbms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class loginoutController {
    @Autowired
    private UserService userService;

    @RequestMapping("/login.do")
    public String login(String userCode, String userPassword, Model model,HttpSession session){
        User user = userService.loginUser(userCode, userPassword);
        if (user==null){
            model.addAttribute("error","用户名或密码不正确");
            return "forward:login.jsp";
        }
        session.setAttribute("loginUser",user);
        return "frame";
    }

    @RequestMapping("/logout.do")
    public String logout(HttpSession session){
        session.removeAttribute("loginUser");
        return "redirect:/login.jsp";
    }
}
