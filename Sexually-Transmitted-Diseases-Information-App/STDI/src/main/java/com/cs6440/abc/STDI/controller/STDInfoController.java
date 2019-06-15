package com.cs6440.abc.STDI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
public class STDInfoController {
    private String appMode;

    @Autowired
    public STDInfoController(Environment environment){
        appMode = environment.getProperty("app-mode");
    }

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("datetime", new Date());
        model.addAttribute("username", "Across The Board Champion");
        model.addAttribute("appName", "STDI");
        model.addAttribute("mode", appMode);

        return "index";
    }
}
