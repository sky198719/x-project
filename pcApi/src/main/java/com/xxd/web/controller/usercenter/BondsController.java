package com.xxd.web.controller.usercenter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户中心债券.
 */
@Controller
@RequestMapping("/usercenter/bonds")
public class BondsController extends AbstractIsInvestController {

    /**
     * _
     */
    @RequestMapping(value = "/goldIngot.html")
    public String goldIngot(Model model) {

        return "usercenter/bonds/goldIngot";
    }

    /**
     * _.
     */
    @RequestMapping(value = "/monthgold.html")
    public String monthgold(Model model) {

        return "usercenter/bonds/monthgold";
    }

    /**
     * _.
     */
    @RequestMapping(value = "/monthSend.html")
    public String monthSend(Model model) {

        return "usercenter/bonds/monthSend";
    }

    /**
     * 新手标.
     */
    @RequestMapping(value = "/newtender.html")
    public String newtender(Model model) {

        return "usercenter/bonds/newtender";
    }

    /**
     * 新手专享30天
     */
    @RequestMapping(value = "/thirtytender.html")
    public String thirtytender(Model model) {

        return "usercenter/bonds/thirtytender";
    }

    /**
     * 七天大胜
     */
    @RequestMapping(value = "/sevengold.html")
    public String sevengold(Model model) {

        return "usercenter/bonds/sevengold";
    }

    /**
     * _
     */
    @RequestMapping(value = "/stepDetail.html")
    public String stepDetail(Model model) {

        return "usercenter/bonds/stepDetail";
    }

}