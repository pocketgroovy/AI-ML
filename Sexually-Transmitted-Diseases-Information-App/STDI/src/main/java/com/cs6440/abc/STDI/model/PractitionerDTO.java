package com.cs6440.abc.STDI.model;

public class PractitionerDTO {
    private String name;
    private String speciality;
    private String phone;
    private String email;
    private String address;
    private String postalCode;

    public PractitionerDTO(String name, String speciality, String phone, String email,
                           String address, String postalCode) {
        this.speciality = speciality;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.postalCode = postalCode;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String name) {
        this.speciality = speciality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String name) {
        this.phone = name;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getPostalCode() { return postalCode; }

    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    @Override
    public String toString() {
        return "Practitioner{" + "speciality=" + speciality + ", name=" + name +
                ", phone=" + phone +  ", email="+email + '}';
    }
}
