package com.smbms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/jump")
public class JumpController {

    @RequestMapping("/pwdmodify")
    public String jumpPwdModify(){
        return "pwdmodify";
    }

    @RequestMapping("/useradd")
    public String jumpUserAdd(){
        return "useradd";
    }

    @RequestMapping("/provideradd")
    public String jumpProviderAdd(){
        return "provideradd";
    }

    @RequestMapping("/billadd")
    public String jumpBillAdd(){
        return "billadd";
    }
}
