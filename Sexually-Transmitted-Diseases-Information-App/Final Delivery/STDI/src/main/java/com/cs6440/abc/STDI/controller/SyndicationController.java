package com.cs6440.abc.STDI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;


@Controller
@RequestMapping(value="/syndication")
public class SyndicationController {

    private String appMode;
    @Autowired
    public SyndicationController(Environment environment){
        appMode = environment.getProperty("app-mode");
    }

    @RequestMapping(value = "/cdc", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("datetime", new Date());
        model.addAttribute("username", "Across The Board Champion");
        model.addAttribute("appName", "STDI");
        model.addAttribute("mode", appMode);
        return "syndication/cdc";
    }

    @RequestMapping(value = "/bacterialvaginosis", method = RequestMethod.GET)
    public String bacterialvaginosis(Model model) {
        model.addAttribute("datetime", new Date());
        model.addAttribute("username", "Across The Board Champion");
        model.addAttribute("appName", "STDI");
        model.addAttribute("mode", appMode);
        return "syndication/bacterialvaginosis";
    }

    @RequestMapping(value = "/chlamydia", method = RequestMethod.GET)
    public String chlamydia(Model model) {
        model.addAttribute("datetime", new Date());
        model.addAttribute("username", "Across The Board Champion");
        model.addAttribute("appName", "STDI");
        model.addAttribute("mode", appMode);
        return "syndication/chlamydia";
    }

    @RequestMapping(value = "/congenitalsyphilis", method = RequestMethod.GET)
    public String congenitalsyphilis(Model model) {
        model.addAttribute("datetime", new Date());
        model.addAttribute("username", "Across The Board Champion");
        model.addAttribute("appName", "STDI");
        model.addAttribute("mode", appMode);
        return "syndication/congenitalsyphilis";
    }

    @RequestMapping(value = "/genitalherpes", method = RequestMethod.GET)
    public String genitalHerpes(Model model) {
        model.addAttribute("datetime", new Date());
        model.addAttribute("username", "Across The Board Champion");
        model.addAttribute("appName", "STDI");
        model.addAttribute("mode", appMode);
        return "syndication/genitalherpes";
    }

    @RequestMapping(value = "/trichomoniasis", method = RequestMethod.GET)
    public String trichomoniasis(Model model) {
        model.addAttribute("datetime", new Date());
        model.addAttribute("username", "Across The Board Champion");
        model.addAttribute("appName", "STDI");
        model.addAttribute("mode", appMode);
        return "syndication/trichomoniasis";
    }

    @RequestMapping(value = "/gonorrhea", method = RequestMethod.GET)
    public String gonorrhea(Model model) {
        model.addAttribute("datetime", new Date());
        model.addAttribute("username", "Across The Board Champion");
        model.addAttribute("appName", "STDI");
        model.addAttribute("mode", appMode);
        return "syndication/gonorrhea";
    }

    @RequestMapping(value = "/hiv", method = RequestMethod.GET)
    public String hiv(Model model) {
        model.addAttribute("datetime", new Date());
        model.addAttribute("username", "Across The Board Champion");
        model.addAttribute("appName", "STDI");
        model.addAttribute("mode", appMode);
        return "syndication/hiv";
    }

    @RequestMapping(value = "/pelvic", method = RequestMethod.GET)
    public String pelvic(Model model) {
        model.addAttribute("datetime", new Date());
        model.addAttribute("username", "Across The Board Champion");
        model.addAttribute("appName", "STDI");
        model.addAttribute("mode", appMode);
        return "syndication/pelvic";
    }

    @RequestMapping(value = "/syphilis", method = RequestMethod.GET)
    public String syphilis(Model model) {
        model.addAttribute("datetime", new Date());
        model.addAttribute("username", "Across The Board Champion");
        model.addAttribute("appName", "STDI");
        model.addAttribute("mode", appMode);
        return "syndication/syphilis";
    }

    @RequestMapping(value = "/treatment", method = RequestMethod.GET)
    public String treatment(Model model) {
        model.addAttribute("datetime", new Date());
        model.addAttribute("username", "Across The Board Champion");
        model.addAttribute("appName", "STDI");
        model.addAttribute("mode", appMode);
        return "syndication/treatment";
    }


}
