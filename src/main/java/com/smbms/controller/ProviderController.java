package com.smbms.controller;

import com.github.pagehelper.PageInfo;
import com.smbms.pojo.Bill;
import com.smbms.pojo.Provider;
import com.smbms.pojo.User;
import com.smbms.pojo.vo.ProviderVo;
import com.smbms.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pro")
public class ProviderController {
    @Autowired
    private ProviderService providerService;

    //获取供应商列表，多条件查询以及分页
    @RequestMapping("/list")
    public String getProList(Integer pageIndex,Model model,String queryProCode,String queryProName){
        Integer pageNum=1;
        if (pageIndex!=null){
            pageNum=pageIndex;
        }
        PageInfo<Provider> proPageInfo = providerService.getProList(pageNum, 5, queryProCode, queryProName);
        model.addAttribute("proPageInfo",proPageInfo);
        return "providerlist";
    }

    //查看单独供应商详情
    @RequestMapping("/view")
    public String viewProvider(Long proid,Model model){
        Provider provider = providerService.getProById(proid);
        System.out.println(provider);
        model.addAttribute("provider",provider);
        return "providerview";
    }

    //将供应商信息返回到providermodify页面
    @RequestMapping("/modify")
    public String mofidyProvider(Long proid,Model model){
        Provider provider = providerService.getProById(proid);
        model.addAttribute("provider",provider);
        return "providermodify";
    }

    //修改供应商
    @RequestMapping("/save")
    public String saveProvider(Long proid,Provider provider,Model model){
        provider.setId(proid);
        int i = providerService.modifyProvider(provider);
        if (i <= 0) {
            model.addAttribute("msg", "修改失败");
            return "error";
        }
        return "redirect:/pro/list";
    }

    //添加供应商
    @RequestMapping("add")
    public String addProvider(Provider provider,Model model){
        int i = providerService.addProvider(provider);
        if (i <= 0) {
            model.addAttribute("msg", "修改失败");
            return "error";
        }
        return "redirect:/pro/list";
    }

    //删除供应商
    @RequestMapping("/delete")
    @ResponseBody
    public Map<String,String> deleteUser(Long proid){
        Map<String,String> map = new HashMap<String,String>();
        String delResult="";
        ProviderVo proVo = providerService.getProVoById(proid);
        List<Bill> bills = proVo.getBills();
        String size = String.valueOf(bills.size());
        if (proVo==null){
            delResult="notexist";
            map.put("delResult",delResult);
            return map;
        }
        if (bills.size()>0){
            delResult=size;
            map.put("delResult",delResult);
            return map;
        }
        int i = providerService.deleteProvider(proid);
        if (i<0){
            delResult="false";
        }else {
            delResult="true";
        }
        map.put("delResult",delResult);
        return map;
    }
}
