package com.smbms.service;

import com.github.pagehelper.PageInfo;
import com.smbms.pojo.Bill;

import java.util.List;

/**
 * @program: SMBMS
 * @description
 * @author: rw3h
 * @create: 2020-05-07 11:22
 **/
public interface BillService {
    PageInfo<Bill> getBillList(Integer pageNum, Integer pageSize, String productName, Long providerId, Integer isPayment);

    int addBill(Bill bill);

    Bill viewBill(Long id);

    int modifyBill(Bill bill);


    int deleteBill(Long id);
}
