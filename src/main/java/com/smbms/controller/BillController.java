package com.smbms.controller;

import com.github.pagehelper.PageInfo;
import com.smbms.pojo.Bill;
import com.smbms.pojo.Provider;
import com.smbms.pojo.User;
import com.smbms.service.BillService;
import com.smbms.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.security.jca.ProviderList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: SMBMS
 * @description
 * @author: rw3h
 * @create: 2020-05-06 11:39
 **/
@Controller
@RequestMapping("/bill")
public class BillController {
    @Autowired
    private BillService billService;
    @Autowired
    private ProviderService providerService;

    //获取订单列表
    @RequestMapping("/list")
    public String billList(Model model,Integer pageIndex,String queryProductName,Long queryProviderId,Integer queryIsPayment){
        Integer pageNum=1;
        if (pageIndex!=null){
            pageNum=pageIndex;
        }
        PageInfo<Bill> billPageInfo = billService.getBillList(pageNum, 5, queryProductName, queryProviderId, queryIsPayment);
        List<Provider> providerList = providerService.getProviderList();
        model.addAttribute("providerList",providerList);
        model.addAttribute("billPageInfo",billPageInfo);
        return "billlist";
    }

    //返回供应商列表
    @RequestMapping("/proList")
    @ResponseBody
    public List<Provider> proList(){
        return providerService.getProviderList();
    }

    //添加订单
    @RequestMapping("/add")
    public String addBill(Bill bill,Model model){
        int i = billService.addBill(bill);
        if (i <= 0) {
            model.addAttribute("msg", "添加失败");
            return "error";
        }
        return "redirect:/bill/list";
    }

    //查看订单详情
    @RequestMapping("/view")
    public String viewBill(Long billid,Model model){
        Bill bill = billService.viewBill(billid);
        model.addAttribute("bill",bill);
        return "billview";
    }

    //将订单的信息返回到billmodify页面
    @RequestMapping("/modify")
    public String modifyBill(Long billid,Model model){
        Bill bill = billService.viewBill(billid);
        model.addAttribute("bill",bill);
        return "billmodify";
    }

    //保存修改
    @RequestMapping("/save")
    public String saveBill(Long id,Bill bill,Model model){
        bill.setId(id);
        int i = billService.modifyBill(bill);
        if (i <= 0) {
            model.addAttribute("msg", "添加失败");
            return "error";
        }
        return "redirect:/bill/list";
    }

    //删除订单
    @RequestMapping("/delete")
    @ResponseBody
    public Map<String,String > deleteBill(Long billid){
        Map<String,String> map = new HashMap<String,String>();
        String delResult="";
        Bill bill = billService.viewBill(billid);
        int i = billService.deleteBill(billid);
        if (bill==null){
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
