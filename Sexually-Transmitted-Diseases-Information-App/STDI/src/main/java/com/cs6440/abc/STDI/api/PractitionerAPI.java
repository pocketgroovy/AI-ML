package com.cs6440.abc.STDI.api;

import ca.uhn.fhir.model.api.Include;
import ca.uhn.fhir.rest.gclient.TokenClientParam;
import org.hl7.fhir.dstu3.model.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
public class PractitionerAPI extends BaseAPI{
    public PractitionerAPI(String baseUrl){
        super(baseUrl);
    }

    public Set<String> getPractitionerRoleIDs(String type, String[] codes){
        Bundle bundle = getAllActivePractitionerRoleBundleWithLocation(type, codes);
        return getPractitionerRoleIDsFromBundle(bundle);
    }

    public Bundle getAllActivePractitionerRoleBundleWithLocation(String type, String[] codes){
        Bundle bundle = client.search().forResource(PractitionerRole.class)
                .where(new TokenClientParam(type).exactly().systemAndValues("http://snomed.info/sct", codes))
                .where(new TokenClientParam("active").exactly().code("true"))
                .include(new Include("PractitionerRole:location"))
                .returnBundle(org.hl7.fhir.dstu3.model.Bundle.class)
                .execute();
        return bundle;
    }

    public Practitioner getPractitionerForID(String code){
        Bundle bundle = client.search().forResource(Practitioner.class)
                .where(new TokenClientParam("_id").exactly().
                        code(code))
                .returnBundle(org.hl7.fhir.dstu3.model.Bundle.class)
                .execute();
        List<Bundle.BundleEntryComponent> entries = getAllEntriesFromBundle(bundle);
        if(entries.size() > 1 || entries.size()==0){
            return null;
        }
        return parseBundleEntry(entries.get(0));
    }

    public Practitioner parseBundleEntry(Bundle.BundleEntryComponent entryComponent) {
        Resource resource = entryComponent.getResource();
        Practitioner practitioner = null;
        if (resource.getResourceType().equals(ResourceType.Practitioner)) {
            practitioner = (Practitioner) entryComponent.getResource();
        }
        return practitioner;
    }

    public  Set<String> getPractitionerRoleIDsFromBundle(Bundle bundle){
        Set<String> practitionerIDs = new HashSet<String>();
        getPractitionerRoleIDs(bundle, practitionerIDs);
        // if there are more pages, go through all of them
        Bundle nextPage = bundle;
        while (nextPage.getLink(Bundle.LINK_NEXT) != null) {
            // load next page
            nextPage = client.loadPage().next(nextPage).execute();
            getPractitionerRoleIDs(nextPage, practitionerIDs);
        }

        return practitionerIDs;
    }

    private void getPractitionerRoleIDs(Bundle page, Set<String> practitioners) {
        List<Bundle.BundleEntryComponent> practitionerBundle = page.getEntry();
        for (Bundle.BundleEntryComponent practitioner : practitionerBundle) {
            Property practitionerProp = practitioner.getResource().getChildByName("practitioner");
            if(practitionerProp != null){
                List<Base> subjects = practitionerProp.getValues();
                for (Base subject : subjects) {
                    if (subject != null) {
                        List<Base> references = subject.getChildByName("reference").getValues();
                        for (Base reference : references) {
                            if (reference.toString().contains("Practitioner")) {
                                practitioners.add(reference.toString());
                            }
                        }
                    }
                }
            }
        }
    }
}
