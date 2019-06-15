package com.cs6440.abc.STDI.model;

import com.cs6440.abc.STDI.api.PractitionerAPI;
import com.cs6440.abc.STDI.util.PractitionerRoleParser;
import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.PractitionerRole;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PractitionerRoleParserTest {
    private String serverBase ="http://hapi.fhir.org/baseDstu3";


    @Test
    public void parseBundleEntry() {
        PractitionerAPI practitionerAPI = new PractitionerAPI(serverBase);
        String[] specialty = {"408443003"};
        Bundle bundle = practitionerAPI.getAllActivePractitionerRoleBundleWithLocation("specialty",specialty);
        List<Bundle.BundleEntryComponent> practitioners = practitionerAPI.getAllEntriesFromBundle(bundle);
        PractitionerRoleParser practitionerRoleParser = new PractitionerRoleParser();
        PractitionerRole practitionerRole = practitionerRoleParser.parseBundleEntry(practitioners.get(0));
        String theSystem ="http://snomed.info/sct";
        String theCode = "408443003";
        String theDisplay = "General medical practice";

        assertEquals(theCode, practitionerRole.getSpecialty().get(0).getCoding().get(0).getCode());
        assertEquals(theSystem, practitionerRole.getSpecialty().get(0).getCoding().get(0).getSystem());
        assertEquals(theDisplay, practitionerRole.getSpecialty().get(0).getCoding().get(0).getDisplay());
    }


    @Test
    public void parseBundleEntry_count() {
        PractitionerAPI practitionerAPI = new PractitionerAPI(serverBase);
        String[] specialty = {"408443003"};
        Bundle bundle = practitionerAPI.getAllActivePractitionerRoleBundleWithLocation("specialty",specialty);
        List<Bundle.BundleEntryComponent> practitioners = practitionerAPI.getAllEntriesFromBundle(bundle);
        assertEquals(11, practitioners.size());
    }
}
