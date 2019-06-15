package com.cs6440.abc.STDI.util;

import com.cs6440.abc.STDI.form.BookingForm;
import com.cs6440.abc.STDI.form.SearchItemForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping(value="/load")
public class Loading {
    @RequestMapping(value = "/booking", method = RequestMethod.POST)
    public String loadBooking(SearchItemForm form, Model model) {
        model.addAttribute("postalCode", form.getPostalCode());
        model.addAttribute("specialties", form.getSpecialties());
        return "booking/loading";
    }

    @RequestMapping(value = "/mail", method = RequestMethod.POST)
    public String loadBooking(BookingForm form, Model model) {
        model.addAttribute("firstName", form.getFirstName());
        model.addAttribute("lastName", form.getLastName());
        model.addAttribute("appointmentDate", form.getAppointmentDate());
        model.addAttribute("senderEmail", form.getSenderEmail());
        model.addAttribute("recipientEmail", form.getRecipientEmail());
        model.addAttribute("practitionerName", form.getPractitionerName());
        model.addAttribute("fareaNl2br", form.getFareaNl2br());

        return "mail/loading";
    }
}
