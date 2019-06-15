package com.cs6440.abc.STDI.util;

import com.cs6440.abc.STDI.api.LocationAPI;
import com.cs6440.abc.STDI.api.PractitionerAPI;
import com.cs6440.abc.STDI.model.PractitionerDTO;
import org.hl7.fhir.dstu3.model.*;

import java.util.List;


public class PractitionerRoleParser {
    private String baseUrl ="http://hapi.fhir.org/baseDstu3";

    public PractitionerRole parseBundleEntry(Bundle.BundleEntryComponent entryComponent) {
        Resource resource = entryComponent.getResource();
        PractitionerRole practitionerRole = null;
        if (resource.getResourceType().equals(ResourceType.PractitionerRole)) {
            practitionerRole = (PractitionerRole) entryComponent.getResource();
        }
        return practitionerRole;
    }

    public PractitionerDTO parseToDTO(PractitionerRole practitionerRole) {
        String specialty = getSpecialty(practitionerRole);
        String displayName = getDisplayName(practitionerRole);
        String phone = getContact(ContactPoint.ContactPointSystem.PHONE, practitionerRole);
        String email = getContact(ContactPoint.ContactPointSystem.EMAIL, practitionerRole);

        // if practitioner name is missing or any contact information is not available, don't bother.
        if(displayName.isEmpty() || (phone.isEmpty() && email.isEmpty())) return null;

        Location location = getLocationFromReference(practitionerRole);
        Address address;
        String postalCode = "";
        String addressTxt = "";
        if(location!=null) {
            address = location.getAddress();
            postalCode = address.getPostalCode();
            addressTxt = address == null ? "address not found" : address.getText() + "\n" +
                    address.getCity() + ", " + address.getState();
        }
        PractitionerDTO practitionerDTO = new PractitionerDTO(displayName, specialty, phone, email,
                addressTxt, postalCode);
        return practitionerDTO;
    }

    private String getSpecialty(PractitionerRole practitionerRole){
        String specialty = "";
        List<CodeableConcept> specialtyList = practitionerRole.getSpecialty();
        if (specialtyList != null) {
            List<Coding> codings = specialtyList.get(0).getCoding();
            if (codings.size() > 0) {
                specialty = codings.get(0).getDisplay();
            }
        }
        return specialty;
    }

    private String getDisplayName(PractitionerRole practitionerRole){
        Reference practitionerRef = practitionerRole.getPractitioner();
        String display = "";
        if(practitionerRef != null) {
            String reference = practitionerRef.getReference();
            if (reference != null) {
                String practitionerRefValues = practitionerRef.getReference();
                if (practitionerRefValues != null) {
                    String[] typeId = practitionerRefValues.split("/");
                    PractitionerAPI practitionerAPI = new PractitionerAPI(baseUrl);
                    String id = String.valueOf(typeId[1]);
                    Practitioner practitioner = practitionerAPI.getPractitionerForID(id);
                    List<HumanName> names = practitioner.getName();
                    display = names.get(0).getNameAsSingleString();
                }
            }
        }
        return display;
    }

    private Location getLocationFromReference(PractitionerRole practitionerRole){
        Location location = null;
        String locationRef = getLocationRef(practitionerRole);
        if(locationRef!=null) {
            String locationId = getLocationId(locationRef);
            if(locationId!=null) {
                location = getLocation(locationId);
            }
        }
        return location;
    }

    private String getLocationRef(PractitionerRole practitionerRole){
        Reference locationRef = practitionerRole.getLocationFirstRep();
        String locationRefValues = "";
        if (locationRef != null) {
           locationRefValues = locationRef.getReference();
        }
        return locationRefValues;
    }

    private String getLocationId(String locationReference) {
        String[] locationId = locationReference.split("/");
        return String.valueOf(locationId[1]);
    }

    private Location getLocation(String id) {
        LocationAPI locationAPI = new LocationAPI(baseUrl);
        return locationAPI.getLocationForID(id);
    }


    private String getContact(ContactPoint.ContactPointSystem system, PractitionerRole practitionerRole){
        List<ContactPoint> contacts = practitionerRole.getTelecom();
        String contactForm = "";
        if (contacts.size() > 0) {
            for (ContactPoint contact : contacts) {
                ContactPoint.ContactPointSystem contactPointSystem = contact.getSystem();
                if(contactPointSystem == system) {
                    contactForm = contact.getValue();
                }
            }
        }
        return contactForm;
    }
}
