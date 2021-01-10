package com.shawn.admin.controller;


import com.shawn.jooo.framework.base.BaseController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

    @Value("${app.indexUrl}")
    private String appIndexUrl;


    @RequestMapping("/success")
    public String index() {
        return redirect(appIndexUrl);
    }

    @RequestMapping("/status")
    @ResponseBody
    public boolean status() {
        return true;
    }


}
