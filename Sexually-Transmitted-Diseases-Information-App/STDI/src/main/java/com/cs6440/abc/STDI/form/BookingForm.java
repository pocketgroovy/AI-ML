package com.cs6440.abc.STDI.form;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class BookingForm implements Serializable {

    private static final long serialVersionUID = 9147292130879991660L;

    @NotBlank
    @Size(min = 1, max = 50)
    private String firstName;

    @NotBlank
    @Size(min = 1, max = 50)
    private String lastName;

    private String appointmentDate;

    private String commentArea;

    @NotBlank
    @Email
    private String senderEmail;

    @NotBlank
    @Email
    private String recipientEmail;


    private String practitionerName;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getCommentArea() {
        return commentArea;
    }

    public void setCommentArea(String commentArea) {
        this.commentArea = commentArea;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getRecipientEmail() { return recipientEmail; }

    public void setRecipientEmail(String recipientEmail) { this.recipientEmail = recipientEmail; }

    public String getFareaNl2br() {
        if (StringUtils.isNotEmpty(this.commentArea)) {
            return this.commentArea.replaceAll("\n", "<br/>");
        }
        return "";
    }

    public String getPractitionerName() { return practitionerName; }

    public void setPractitionerName(String practitionerName) { this.practitionerName = practitionerName; }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

}
