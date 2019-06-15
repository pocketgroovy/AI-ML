package com.cs6440.abc.STDI.util;

import com.cs6440.abc.STDI.model.PractitionerDTO;

import java.util.ArrayList;
import java.util.List;

public class PractitionerLocator {

    public List<PractitionerDTO> locateNearbyPostalCode(List<PractitionerDTO> practitionerList, String postalCode){
        List<PractitionerDTO> nearByPractitioners = new ArrayList<>();
        for(PractitionerDTO practitioner : practitionerList){
            if(practitioner.getPostalCode()!=null && practitioner.getPostalCode().equals(postalCode)){
                nearByPractitioners.add(practitioner);
            }
        }
        return nearByPractitioners;
    }
}
