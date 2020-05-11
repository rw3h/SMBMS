package com.smbms.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smbms.dao.BillMapper;
import com.smbms.pojo.Bill;
import com.smbms.pojo.BillExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: SMBMS
 * @description
 * @author: rw3h
 * @create: 2020-05-07 11:23
 **/
@Service
public class BillServiceImpl implements BillService {
    @Autowired
    private BillMapper billMapper;

    //获取订单列表
    @Override
    public PageInfo<Bill> getBillList(Integer pageNum,Integer pageSize,String productName,Long providerId,Integer isPayment) {
        PageHelper.startPage(pageNum,pageSize);
        BillExample example = new BillExample();
        BillExample.Criteria criteria = example.createCriteria();
        if (productName!=null){
            criteria.andProductNameLike("%"+productName+"%");
        }
        if (providerId!=null&&providerId>0){
            criteria.andProviderIdEqualTo(providerId);
        }
        if (isPayment!=null&&isPayment>0){
            criteria.andIsPaymentEqualTo(isPayment);
        }
        List<Bill> bills = billMapper.selectByExample(example);
        for (Bill bill : bills) {
            if (bill.getProName()==null){
                bill.setProName("无供应商");
            }
        }
        PageInfo<Bill> billPageInfo = new PageInfo<>(bills);
        return billPageInfo;
    }

    //添加订单
    @Override
    public int addBill(Bill bill) {
        return billMapper.insertSelective(bill);
    }

    //查看订单详情
    @Override
    public Bill viewBill(Long id) {
        return billMapper.selectByPrimaryKey(id);
    }

    //修改订单
    @Override
    public int modifyBill(Bill bill) {
        return billMapper.updateByPrimaryKeySelective(bill);
    }

    //删除订单
    @Override
    public int deleteBill(Long id) {
        return billMapper.deleteByPrimaryKey(id);
    }
}
