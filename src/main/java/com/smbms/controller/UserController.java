package com.smbms.controller;

import com.github.pagehelper.PageInfo;
import com.smbms.exception.UserException;
import com.smbms.pojo.Role;
import com.smbms.pojo.User;
import com.smbms.service.RoleService;
import com.smbms.service.RoleServiceImpl;
import com.smbms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jws.WebParam;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    //获取用户列表
    @RequestMapping("/list")
    public String queryUserList(String pageIndex, Model model, String queryName, Long queryUserRole) {
        int pageNum = 1;
        if (pageIndex != null) {
            pageNum = Integer.parseInt(pageIndex);
        }
        PageInfo<User> userPageInfo = userService.selectUserList(pageNum, 5, queryName, queryUserRole);
        List<Role> roles = roleService.queryRoleList();
        if (userPageInfo == null) {
            model.addAttribute("msg", "未找到用户列表");
            return "error";
        }
        model.addAttribute("userPageInfo", userPageInfo);
        model.addAttribute("roleList", roles);
        return "userlist";
    }

    //修改密码
    @RequestMapping("/modifyPwd")
    public String modifyPwd(String newpassword, HttpSession session, Model model) {
        User user = (User) session.getAttribute("loginUser");
        Long id = user.getId();
        int i = userService.modifyPwd(id, newpassword);
        if (i <= 0) {
            model.addAttribute("msg", "修改失败");
            return "error";
        }
        session.removeAttribute("loginUser");
        return "redirect:/login.jsp";
    }

    //验证密码
    @RequestMapping("/verifyPwd")
    @ResponseBody
    public Map<String, String> verifyPwd(String oldpassword, HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        Map<String, String> map = new HashMap<String, String>();
        String result = "";
        //如果session失效，即未登录
        if (loginUser == null) {
            result = "sessionerror";
        } else if ("".equals(oldpassword)) {
            result = "error";
        } else if (oldpassword.equals(loginUser.getUserPassword())) {
            result = "true";
        } else {
            result = "false";
        }
        map.put("result", result);
        return map;
    }

    //查看用户
    @RequestMapping("/view")
    public String userView(Long uid, Model model) {
        User user = userService.selectUserById(uid);
        model.addAttribute("user", user);
        return "userview";
    }

    //将需要修改的用户返回到usermodify页面，并跳转
    @RequestMapping("/modify")
    public String modifyUser(Long uid, Model model) {
        User user = userService.selectUserById(uid);
        model.addAttribute("user", user);
        return "usermodify";
    }

    //修改用户
    @RequestMapping("/save")
    public String saveUser(User user,Long uid,Model model){
        user.setId(uid);
        int i = userService.modifyUser(user);
        if (i <= 0) {
            model.addAttribute("msg", "修改失败");
            return "error";
        }
        return "redirect:/user/list";
    }

    //获取角色列表
    @RequestMapping("/roleList")
    @ResponseBody
    public List<Role> getRoleList() {
        List<Role> roles = roleService.queryRoleList();
        return roles;
    }

    //添加用户
    @RequestMapping("/add")
    public String addUser(User user, Model model){
        int i = userService.addUser(user);
        if (i <= 0) {
            model.addAttribute("msg", "添加失败");
            return "error";
        }
        return "redirect:/user/list";
    }

    //验证用户名
    @RequestMapping("/verifyUserCode")
    @ResponseBody
    public Map<String,String> verifyUserCode(String userCode){
        List<User> users = userService.selectList();
        Map<String,String> map = new HashMap<String,String>();
        for (User user : users) {
            if (userCode.equals(user.getUserCode())){
                userCode="exist";
            }
        }
        map.put("userCode",userCode);
        return map;
    }

    //删除用户
    @RequestMapping("/delete")
    @ResponseBody
    public Map<String,String> deleteUser(Long uid){
        Map<String,String> map = new HashMap<String,String>();
        String delResult="";
        User user = userService.selectUserById(uid);
        int i = userService.deleteUser(uid);
        if (user==null){
            delResult="notexist";
        }else if (i<0){
            delResult="false";
        }else {
            delResult="true";
        }
        map.put("delResult",delResult);
        return map;
    }
}
