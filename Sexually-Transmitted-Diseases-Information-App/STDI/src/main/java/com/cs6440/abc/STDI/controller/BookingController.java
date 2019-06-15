package com.cs6440.abc.STDI.controller;

import com.cs6440.abc.STDI.api.PractitionerAPI;
import com.cs6440.abc.STDI.form.BookingForm;
import com.cs6440.abc.STDI.form.SearchItemForm;
import com.cs6440.abc.STDI.model.PractitionerDTO;
import com.cs6440.abc.STDI.util.PractitionerLocator;
import com.cs6440.abc.STDI.util.PractitionerRoleParser;
import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.PractitionerRole;
import org.hl7.fhir.dstu3.model.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value="/booking")
public class BookingController {

    final static Logger logger = LoggerFactory.getLogger(BookingController.class);
    private String baseUrl ="http://hapi.fhir.org/baseDstu3";

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @RequestMapping(value = "/show_practitioner", method = RequestMethod.POST)
    public String showPractitioner(@Validated @ModelAttribute SearchItemForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("validationError", "validation error");
            return searchItem(form, model);
        }
        String[] specialties = form.getSpecialties();
        String postalCode = form.getPostalCode();
        List<PractitionerDTO> practitionerList = getPractitionerList(specialties);
        PractitionerLocator practitionerLocator = new PractitionerLocator();
        List<PractitionerDTO> nearByPractitioners = practitionerLocator.locateNearbyPostalCode(practitionerList, postalCode);
        // if no practitioners found nearby the postal code, show all the list
        List<PractitionerDTO> listToShow =  nearByPractitioners.size() > 0 ? nearByPractitioners : practitionerList;
        model.addAttribute("practitioners", listToShow);
        return "booking/showPractitioner";
    }

    @RequestMapping(value = "/search_item", method = RequestMethod.GET)
    public String searchItem(SearchItemForm form, Model model) {
        Map<String, String> specialtiesList = getSpecialtiesList();
        model.addAttribute("specialtiesList", specialtiesList);
        return "booking/searchItem";
    }

    @RequestMapping(value = "/appointment", method = RequestMethod.GET)
    public String appointment(BookingForm form, Model model, @RequestParam("practitionerName") String practitionerName,
                              @RequestParam("recipientEmail") String recipientEmail) {
        System.out.println(recipientEmail + ", " + practitionerName);
        model.addAttribute("practitionerName", practitionerName);
        form.setPractitionerName(practitionerName);
        model.addAttribute("recipientEmail", recipientEmail);
        form.setRecipientEmail(recipientEmail);
        return "booking/appointment";
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public String confirm(@Validated @ModelAttribute BookingForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("validationError", "validation error");
            return appointment(form, model, form.getPractitionerName(), form.getRecipientEmail());
        }
        return "booking/confirm";
    }

    private List<PractitionerDTO> getPractitionerList(String[] specialties){
        List<PractitionerDTO> practitionerList = new ArrayList<>();
        PractitionerAPI practitionerAPI = new PractitionerAPI(baseUrl);
        Bundle bundle = practitionerAPI.getAllActivePractitionerRoleBundleWithLocation("specialty", specialties);
        List<Bundle.BundleEntryComponent>  entries = practitionerAPI.getAllEntriesFromBundle(bundle);

        PractitionerRoleParser practitionerRoleParser = new PractitionerRoleParser();
        for(Bundle.BundleEntryComponent entry : entries){
            PractitionerRole practitionerRole = practitionerRoleParser.parseBundleEntry(entry);
            if(practitionerRole!= null){
                Reference practitioner = practitionerRole.getPractitioner();
                if(practitioner!= null) {
                    PractitionerDTO practitionerDTO = practitionerRoleParser.parseToDTO(practitionerRole);
                    if (practitionerDTO!=null) {
                        practitionerList.add(practitionerRoleParser.parseToDTO(practitionerRole));
                    }
                }
            }
        }
        return practitionerList;
    }

    private Map<String, String> getSpecialtiesList(){
        Map<String, String> specialtiesList = new HashMap<>();
        specialtiesList.put("Infectious diseases physician", "76899008");
        specialtiesList.put("Consultant gynecology and obstetrics", "158969006");
        return specialtiesList;
    }
}
