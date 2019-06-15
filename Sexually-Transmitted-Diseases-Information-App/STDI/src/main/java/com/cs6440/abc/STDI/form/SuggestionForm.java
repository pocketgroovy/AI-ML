package com.cs6440.abc.STDI.form;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class SuggestionForm implements Serializable {
    private static final long serialVersionUID = 9147292130879991660L;

    @NotNull(message = "Please select your year of birth.")
    private String yearOfBirth;

//    @Pattern(regexp = "Female|Male|Other")

    @NotNull(message = "Please select your gender.")
    @Pattern(regexp = "male|female|other")
    private String gender;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(String yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSexualPartners() {
        return sexualPartners;
    }

    public void setSexualPartners(String sexualPartners) {
        this.sexualPartners = sexualPartners;
    }

    public String getPregnant() {
        return pregnant;
    }

    public void setPregnant(String pregnant) {
        this.pregnant = pregnant;
    }

    public String getHivPositive() {
        return hivPositive;
    }

    public void setHivPositive(String hivPositive) {
        this.hivPositive = hivPositive;
    }

//    @Pattern(regexp = "Female|Male|Other")
    private String sexualPartners;

    @NotNull(message = "Please select whether you are expecting a baby.")
    @Pattern(regexp = "Yes|No")
    private String pregnant;

    private String hivPositive;

    @NotNull(message = "Please select whether you ever had sex with men.")
    @Pattern(regexp = "Yes|No")
    private String eswm;

    public String getEswm() {
        return eswm;
    }

    public void setEswm(String eswm) {
        this.eswm = eswm;
    }

    public String getCswm() {
        return cswm;
    }

    public void setCswm(String cswm) {
        this.cswm = cswm;
    }

    @NotNull(message = "Please select whether you are currently having sex with men.")
    @Pattern(regexp = "Yes|No")
    private String cswm;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

}
