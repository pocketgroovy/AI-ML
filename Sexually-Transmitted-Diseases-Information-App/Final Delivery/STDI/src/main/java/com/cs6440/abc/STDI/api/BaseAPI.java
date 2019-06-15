package com.cs6440.abc.STDI.api;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.dstu3.model.Bundle;

import java.util.List;

public class BaseAPI {
    IGenericClient client;

    public BaseAPI(String baseUrl){
        FhirContext ctx = FhirContext.forDstu3();
        client = ctx.newRestfulGenericClient(baseUrl);
    }

    public List<Bundle.BundleEntryComponent> getAllEntriesFromBundle(Bundle bundle){
        List<Bundle.BundleEntryComponent> entryList = bundle.getEntry();
        Bundle nextPage = bundle;
        while (nextPage.getLink(Bundle.LINK_NEXT) != null) {
            // load next page
            nextPage = client.loadPage().next(nextPage).execute();
            entryList.addAll(bundle.getEntry());
        }
        return entryList;
    }
}
