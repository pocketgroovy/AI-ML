package com.cs6440.abc.STDI.controller;
import com.cs6440.abc.STDI.form.SuggestionForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;

@Controller
@RequestMapping(value="/suggestion")
public class STDISuggestionController {
    final static Logger logger = LoggerFactory.getLogger(STDISuggestionController.class);

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @RequestMapping(value = "/ask", method = RequestMethod.GET)
    public String index(SuggestionForm form, Model model) {
        //model.addAttribute("selectItems", SELECT_ITEMS);
        return "suggestion/ask";
    }

    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public String viewConfirm(@Validated @ModelAttribute SuggestionForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("validationError", "validation error");
            return index(form, model);
        }

        return generateSuggestion(( form.getGender().equals("male") ? 0 : (form.getGender().equals("female") ? 1 : 2)),
                                    form.getEswm().equals("Yes") ? Boolean.TRUE : Boolean.FALSE,
                                    form.getCswm().equals("Yes") ? Boolean.TRUE : Boolean.FALSE,
                                    form.getYearOfBirth() == null ? 0 : Integer.parseInt(form.getYearOfBirth()),
                                    form.getPregnant().equals("Yes") ? Boolean.TRUE : Boolean.FALSE,
                                    model);
    }

    public String generateSuggestion(Integer gender,
                                     Boolean eswm,
                                     Boolean cswm,
                                     Integer yb,
                                     Boolean prg,
                                     Model model){
        List<String> test = new ArrayList<String>();
        List<String> link = new ArrayList<String>();
        List<String> code = new ArrayList<String>();


//        System.out.print(gender);
//        System.out.print(eswm);
//        System.out.print(cswm);
//        System.out.print(yb);
//        System.out.print(prg);


        if (gender == 0){
            if(eswm){
                if(cswm){

                    if(1945 <= yb && yb<= 1965){

                        test.add("Hepatitis A Vaccination");
                        link.add("https://npin.cdc.gov/pages/hepatitis-basics-0");
                        code.add("243789007");

                        test.add("Hepatitis B Vaccination");
                        link.add("https://npin.cdc.gov/pages/hepatitis-b-basics");
                        code.add("16584000");

                        test.add("Hepatitis C Testing");
                        link.add("https://npin.cdc.gov/pages/hepatitis-c-basics");
                        code.add("413107006");

                        test.add("HIV Testing at least once a year. Some may benefit from more frequent testing (e.g. every 3-6 months).");
                        link.add("https://www.cdc.gov/hiv/testing/index.html");
                        code.add("171121004");

                        test.add("Extragenital Testing");
                        link.add("http://www.ncsddc.org/resource/extragenital/");
                        code.add("No code");



                    }else{

                        test.add("Syphilis");
                        link.add("https://www.cdc.gov/std/syphilis/");
                        code.add("28902003");

                        test.add("Chlamydia");
                        link.add("https://www.cdc.gov/std/chlamydia/");
                        code.add("310861008");

                        test.add("Gonorrhea");
                        link.add("https://www.cdc.gov/std/Gonorrhea/");
                        code.add("165829005");

                        test.add("Hepatitis A Vaccination");
                        link.add("https://npin.cdc.gov/pages/hepatitis-basics-0");
                        code.add("243789007");

                        test.add("Hepatitis B Vaccination");
                        link.add("https://npin.cdc.gov/pages/hepatitis-b-basics");
                        code.add("16584000");

                        test.add("HIV Testing at least once a year. Some may benefit from more frequent testing (e.g. every 3-6 months).");
                        link.add("https://www.cdc.gov/hiv/testing/index.html");
                        code.add("171121004");

                        test.add("Extragenital Testing");
                        link.add("http://www.ncsddc.org/resource/extragenital/");
                        code.add("No code");

                    }

                }
                else{
                    if(1945 <= yb && yb<= 1965){

                        test.add("Syphilis");
                        link.add("https://www.cdc.gov/std/syphilis/");
                        code.add("28902003");

                        test.add("Chlamydia");
                        link.add("https://www.cdc.gov/std/chlamydia/");
                        code.add("310861008");

                        test.add("Gonorrhea");
                        link.add("https://www.cdc.gov/std/Gonorrhea/");
                        code.add("165829005");

                        test.add("Hepatitis C Testing");
                        link.add("https://npin.cdc.gov/pages/hepatitis-c-basics");
                        code.add("413107006");

                        test.add("HIV Testing at least once a year. Some may benefit from more frequent testing (e.g. every 3-6 months).");
                        link.add("https://www.cdc.gov/hiv/testing/index.html");
                        code.add("171121004");

                        test.add("Extragenital Testing");
                        link.add("http://www.ncsddc.org/resource/extragenital/");
                        code.add("No code");


                    }else{

                        test.add("Syphilis");
                        link.add("https://www.cdc.gov/std/syphilis/");
                        code.add("28902003");

                        test.add("Chlamydia");
                        link.add("https://www.cdc.gov/std/chlamydia/");
                        code.add("310861008");

                        test.add("Gonorrhea");
                        link.add("https://www.cdc.gov/std/Gonorrhea/");
                        code.add("165829005");

                        test.add("Hepatitis A Vaccination");
                        link.add("https://npin.cdc.gov/pages/hepatitis-basics-0");
                        code.add("243789007");

                        test.add("Hepatitis B Vaccination");
                        link.add("https://npin.cdc.gov/pages/hepatitis-b-basics");
                        code.add("16584000");

                        test.add("HIV Testing at least once a year. Some may benefit from more frequent testing (e.g. every 3-6 months).");
                        link.add("https://www.cdc.gov/hiv/testing/index.html");
                        code.add("171121004");

                        test.add("Extragenital Testing");
                        link.add("http://www.ncsddc.org/resource/extragenital/");
                        code.add("No code");


                    }
                }
            }else{
                if(1945 <= yb && yb<= 1965){

                    test.add("Chlamydia");
                    link.add("https://www.cdc.gov/std/chlamydia/");
                    code.add("310861008");

                    test.add("Gonorrhea");
                    link.add("https://www.cdc.gov/std/Gonorrhea/");
                    code.add("165829005");

                    test.add("Hepatitis B Vaccination");
                    link.add("https://npin.cdc.gov/pages/hepatitis-b-basics");
                    code.add("16584000");

                    test.add("Hepatitis C Testing");
                    link.add("https://npin.cdc.gov/pages/hepatitis-c-basics");
                    code.add("413107006");

                    test.add("HIV Testing at least once a year. Some may benefit from more frequent testing (e.g. every 3-6 months).");
                    link.add("https://www.cdc.gov/hiv/testing/index.html");
                    code.add("171121004");



                }else {

                    test.add("Chlamydia");
                    link.add("https://www.cdc.gov/std/chlamydia/");
                    code.add("310861008");

                    test.add("Hepatitis B Vaccination");
                    link.add("https://npin.cdc.gov/pages/hepatitis-b-basics");
                    code.add("16584000");

                    test.add("HIV Testing at least once a year. Some may benefit from more frequent testing (e.g. every 3-6 months).");
                    link.add("https://www.cdc.gov/hiv/testing/index.html");
                    code.add("171121004");

                }

            }
        }else if(gender == 1){
            if(prg){

                test.add("Syphilis at first prenatal visit");
                link.add("https://www.cdc.gov/std/syphilis/");
                code.add("28902003");

                test.add("Chlamydia at first prenatal visit");
                link.add("https://www.cdc.gov/std/chlamydia/");
                code.add("310861008");

                test.add("Gonorrhea");
                link.add("https://www.cdc.gov/std/Gonorrhea/");
                code.add("165829005");

                test.add("Hepatitis B Vaccination");
                link.add("https://npin.cdc.gov/pages/hepatitis-b-basics");
                code.add("16584000");

                test.add("Hepatitis C Testing");
                link.add("https://npin.cdc.gov/pages/hepatitis-c-basics");
                code.add("413107006");

                test.add("HIV Testing as early as possible in the pregnancy");
                link.add("https://www.cdc.gov/hiv/testing/index.html");
                code.add("171121004");

                test.add("Extragenital Testing");
                link.add("http://www.ncsddc.org/resource/extragenital/");
                code.add("No code");

            }else{

                if(1945 <= yb && yb<= 1965){

                    test.add("Syphilis");
                    link.add("https://www.cdc.gov/std/syphilis/");
                    code.add("28902003");

                    test.add("Chlamydia");
                    link.add("https://www.cdc.gov/std/chlamydia/");
                    code.add("310861008");

                    test.add("Gonorrhea");
                    link.add("https://www.cdc.gov/std/Gonorrhea/");
                    code.add("165829005");

                    test.add("Hepatitis B Vaccination");
                    link.add("https://npin.cdc.gov/pages/hepatitis-b-basics");
                    code.add("16584000");

                    test.add("Hepatitis C Testing");
                    link.add("https://npin.cdc.gov/pages/hepatitis-c-basics");
                    code.add("413107006");

                    test.add("HIV Testing");
                    link.add("https://www.cdc.gov/hiv/testing/index.html");
                    code.add("171121004");

                    test.add("Extragenital Testing");
                    link.add("http://www.ncsddc.org/resource/extragenital/");
                    code.add("No code");


                }else{

                    test.add("Chlamydia");
                    link.add("https://www.cdc.gov/std/chlamydia/");
                    code.add("310861008");

                    test.add("Gonorrhea");
                    link.add("https://www.cdc.gov/std/Gonorrhea/");
                    code.add("165829005");

                    test.add("Hepatitis B Vaccination");
                    link.add("https://npin.cdc.gov/pages/hepatitis-b-basics");
                    code.add("16584000");

                    test.add("HIV Testing");
                    link.add("https://www.cdc.gov/hiv/testing/index.html");
                    code.add("171121004");

                }
            }

        }else if(gender == 2){

            test.add("Syphilis");
            link.add("https://www.cdc.gov/std/syphilis/");
            code.add("28902003");

            test.add("Chlamydia");
            link.add("https://www.cdc.gov/std/chlamydia/");
            code.add("310861008");

            test.add("Gonorrhea");
            link.add("https://www.cdc.gov/std/Gonorrhea/");
            code.add("165829005");

            test.add("Hepatitis A Vaccination");
            link.add("https://npin.cdc.gov/pages/hepatitis-basics-0");
            code.add("243789007");

            test.add("Hepatitis C Testing");
            link.add("https://npin.cdc.gov/pages/hepatitis-c-basics");
            code.add("413107006");

            test.add("HIV Testing at least once a year. Some may benefit from more frequent testing (e.g. every 3-6 months).");
            link.add("https://www.cdc.gov/hiv/testing/index.html");
            code.add("171121004");

            test.add("Extragenital Testing");
            link.add("http://www.ncsddc.org/resource/extragenital/");
            code.add("No code");

            test.add("Genital Herpes");
            link.add("https://www.cdc.gov/std/Herpes/");
            code.add("No code");


        }
        model.addAttribute("test", test);
        model.addAttribute("link", link);
        model.addAttribute("code", code);

        return "suggestion/view";
    }

}
