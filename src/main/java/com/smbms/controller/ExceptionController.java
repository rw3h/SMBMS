package com.smbms.controller;

import com.smbms.exception.UserException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    //编译异常处理器
    @ExceptionHandler
    public String handleException(Model model, Exception e){
        model.addAttribute("msg","编译异常--"+e.getMessage());
        return "error";
    }

    //运行时异常处理器
    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(Model model, Exception e){
        model.addAttribute("msg","运行时异常--"+e.getMessage());
        return "error";
    }

    //用户异常处理器
    @ExceptionHandler(UserException.class)
    public String handleUserException(Model model, Exception e){
        model.addAttribute("msg","用户异常--"+e.getMessage());
        return "error";
    }


}
