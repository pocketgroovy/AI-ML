package com.cs6440.abc.STDI.controller;

import com.cs6440.abc.STDI.form.BookingForm;
import com.cs6440.abc.STDI.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;

@Controller
@RequestMapping(value="/email")
public class EmailController {
    final static Logger logger = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    private EmailService emailService;

    private final String appMail = "stdiabc@gmail.com";

    @RequestMapping("send")
    String send(@Validated @ModelAttribute BookingForm form, BindingResult result, Model model, final Locale locale) {
        if (result.hasErrors()) {
            model.addAttribute("validationError", "validation error");
            return new BookingController().appointment(form, model,form.getPractitionerName(), form.getRecipientEmail()
            );
        }
        try {
            String senderName  = form.getFirstName() + " " + form.getLastName();
            String senderEmail = form.getSenderEmail();
            String recipientEmail = form.getRecipientEmail();
            String appointmentDate = form.getAppointmentDate();
            String practitionerName = form.getPractitionerName();
            String comment = form.getFareaNl2br();
            this.emailService.sendHTMLMail(appMail, senderName, senderEmail, recipientEmail, appointmentDate,
                    practitionerName, comment, locale);
            return "mail/sent";
        }catch(Exception ex) {
            return "Error in sending email: "+ex;
        }
    }
}
