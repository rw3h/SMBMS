package com.smbms.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smbms.dao.ProviderMapper;
import com.smbms.pojo.Provider;
import com.smbms.pojo.ProviderExample;
import com.smbms.pojo.vo.ProviderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: SMBMS
 * @description
 * @author: rw3h
 * @create: 2020-05-06 08:30
 **/
@Service
public class ProviderServiceImpl implements ProviderService {
    @Autowired
    private ProviderMapper providerMapper;

    @Override
    public List<Provider> getProviderList() {
        ProviderExample example = new ProviderExample();
        return providerMapper.selectByExample(example);
    }

    //获取供应商列表
    @Override
    public PageInfo<Provider> getProList(Integer pageNum,Integer pageSize,String proCode,String proName) {
        PageHelper.startPage(pageNum,pageSize);
        ProviderExample example = new ProviderExample();
        ProviderExample.Criteria criteria = example.createCriteria();
        if (proCode!=null){
            criteria.andProCodeLike("%"+proCode+"%");
        }
        if (proName!=null){
            criteria.andProNameLike("%"+proName+"%");
        }
        List<Provider> providers = providerMapper.selectByExample(example);
        PageInfo<Provider> proPageInfo = new PageInfo<>(providers);
        return proPageInfo;
    }

    //根据id获取供应商
    @Override
    public Provider getProById(Long id) {
        return providerMapper.selectByPrimaryKey(id);
    }

    //修改供应商
    @Override
    public int modifyProvider(Provider provider) {
        return providerMapper.updateByPrimaryKeySelective(provider);
    }

    //添加供应商
    @Override
    public int addProvider(Provider provider) {
        return providerMapper.insertSelective(provider);
    }

    //删除用户
    @Override
    public int deleteProvider(Long id) {
        return providerMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ProviderVo getProVoById(Long id) {
        return providerMapper.selectProviderVo(id);
    }


}
