package com.smbms.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smbms.dao.UserMapper;

import com.smbms.pojo.User;
import com.smbms.pojo.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    @Qualifier("userMapper")
    private UserMapper userMapper;

    //登陆功能
    @Override
    public User loginUser(String userCode, String userPassword) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUserCodeEqualTo(userCode);
        criteria.andUserPasswordEqualTo(userPassword);
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() < 1) {
            return null;
        }
        return users.get(0);
    }

    //获取用户列表
    @Override
    public PageInfo<User> selectUserList(Integer pageNum, Integer pageSize, String userName, Long userRole) {
        PageHelper.startPage(pageNum, pageSize);
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if (userName != null) {
            criteria.andUserNameLike("%" + userName + "%");
        }
        if (userRole != null) {
            criteria.andUserRoleEqualTo(userRole);
        }
        List<User> users = userMapper.selectByExample(userExample);
        for (User user : users) {
            user.setAge(new Date().getYear() - user.getBirthday().getYear());
        }
        PageInfo<User> userPageInfo = new PageInfo<>(users);
        return userPageInfo;
    }

    //修改密码
    @Override
    public int modifyPwd(Long id, String newPassword) {
        User user = new User();
        user.setId(id);
        user.setUserPassword(newPassword);
        return userMapper.updateByPrimaryKeySelective(user);
    }

    //查看用户详情
    @Override
    public User selectUserById(Long id){
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }

    //修改用户
    @Override
    public int modifyUser(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    //添加用户
    @Override
    public int addUser(User user) {
        return userMapper.insertSelective(user);
    }

    //查看所用用户
    @Override
    public List<User> selectList() {
        UserExample example = new UserExample();
        return userMapper.selectByExample(example);
    }

    //删除用户
    @Override
    public int deleteUser(Long id) {
        return userMapper.deleteByPrimaryKey(id);
    }
}
