package com.smbms.service;

import com.github.pagehelper.PageInfo;
import com.smbms.pojo.User;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

public interface UserService {
    //登陆
    User loginUser(String userCode,String userPassword);
    //查找用户列表
    PageInfo<User> selectUserList(Integer pageNum, Integer pageSize,String userName,Long userRole);
    //修改密码
    int modifyPwd(Long id,String newPassword);
    //查看用户详情
    User selectUserById(Long id);
    //修改用户
    int modifyUser(User user);
    //添加用户
    int addUser(User user);
    //查找所有的user
    List<User> selectList();
    //删除用户
    int deleteUser(Long id);
}
