package com.cs6440.abc.STDI.api;

import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.HumanName;
import org.hl7.fhir.dstu3.model.Practitioner;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class PractitionerAPITest {
    private String serverBase ="http://hapi.fhir.org/baseDstu3";

    @Test
    public void getPractitionerIDs() {
        PractitionerAPI practitionerAPI = new PractitionerAPI(serverBase);
        String[] specialty = {"408443003"};
        Set<String> practitioners = practitionerAPI.getPractitionerRoleIDs("specialty", specialty);
        Set<String> expected_practitioner = new HashSet<>();
        expected_practitioner.add("Practitioner/cf-1553667253795");
        expected_practitioner.add("Practitioner/1700782");
        expected_practitioner.add("Practitioner/1082651");
        assertEquals(expected_practitioner, practitioners);
    }

    @Test
    public void getAllActivePractitionerRoleBundleForSpecialty() {
        PractitionerAPI practitionerAPI = new PractitionerAPI(serverBase);
        String[] specialty = {"408443003"};
        Bundle bundle = practitionerAPI.getAllActivePractitionerRoleBundleWithLocation("specialty",specialty);
        System.out.println(bundle.getTotal());
        assertEquals(8, bundle.getTotal());
    }

    @Test
    public void getPractitionerRoleIDsFromBundle() {
        PractitionerAPI practitionerAPI = new PractitionerAPI(serverBase);
        String[] specialty = {"408443003"};
        Bundle bundle = practitionerAPI.getAllActivePractitionerRoleBundleWithLocation("specialty",specialty);
        Set<String> practitioners = practitionerAPI.getPractitionerRoleIDsFromBundle(bundle);
        Set<String> expected_practitioner = new HashSet<>();
        expected_practitioner.add("Practitioner/cf-1553667253795");
        expected_practitioner.add("Practitioner/1700782");
        expected_practitioner.add("Practitioner/1082651");
        assertEquals(expected_practitioner, practitioners);
    }

    @Test
    public void getAllEntriesFromBundleIncludeLocation() {
        PractitionerAPI practitionerAPI = new PractitionerAPI(serverBase);
        String[] specialty = {"408443003"};
        Bundle bundle = practitionerAPI.getAllActivePractitionerRoleBundleWithLocation("specialty",specialty);
        List<Bundle.BundleEntryComponent> practitioners = practitionerAPI.getAllEntriesFromBundle(bundle);
        System.out.println(practitioners.get(0));
        assertEquals(11, practitioners.size());
    }

    @Test
    public void getPractitionerForID() {
        PractitionerAPI practitionerAPI = new PractitionerAPI(serverBase);
        Practitioner practitioner = practitionerAPI.getPractitionerForID("1082651");
        String family = "Goldman";
        String given = "Isaac";
        List<HumanName> names = practitioner.getName();

        assertEquals(family, names.get(0).getFamily());
        assertEquals(given, names.get(0).getGiven().get(0).toString());
    }
}