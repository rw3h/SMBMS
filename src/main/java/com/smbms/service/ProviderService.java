package com.smbms.service;

import com.github.pagehelper.PageInfo;
import com.smbms.pojo.Provider;
import com.smbms.pojo.vo.ProviderVo;

import java.util.List;

/**
 * @program: SMBMS
 * @description
 * @author: rw3h
 * @create: 2020-05-06 08:29
 **/
public interface ProviderService {
    //
    List<Provider> getProviderList();
    //获取供应商列表
    PageInfo<Provider> getProList(Integer pageNum, Integer pageSize, String proCode, String proName);

    //根据id获取供应商
    Provider getProById(Long id);

    //修改供应商
    int modifyProvider(Provider provider);

    //添加供应商
    int addProvider(Provider provider);

    int deleteProvider(Long id);

    ProviderVo getProVoById(Long id);
}
