package com.smbms.service;

import com.smbms.dao.RoleMapper;
import com.smbms.pojo.Role;
import com.smbms.pojo.RoleExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public List<Role> queryRoleList() {
        RoleExample roleExample = new RoleExample();
        return roleMapper.selectByExample(roleExample);
    }
}
