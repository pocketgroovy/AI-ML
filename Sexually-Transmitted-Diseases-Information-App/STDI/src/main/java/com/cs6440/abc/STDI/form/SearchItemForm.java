package com.cs6440.abc.STDI.form;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class SearchItemForm implements Serializable {
    private static final long serialVersionUID = 495580981675757450L;
    @NotBlank
    @Size(min = 1, max = 10)
    private String postalCode;


    @NotEmpty(message = "Must Select At Least One Specialty")
    private String[] specialties;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String[] getSpecialties() {
        return specialties;
    }

    public void setSpecialties(String[] specialties) {
        this.specialties = specialties;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

}
