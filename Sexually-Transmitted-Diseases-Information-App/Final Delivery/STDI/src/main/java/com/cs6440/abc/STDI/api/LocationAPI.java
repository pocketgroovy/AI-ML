package com.cs6440.abc.STDI.api;

import ca.uhn.fhir.rest.gclient.TokenClientParam;
import org.hl7.fhir.dstu3.model.*;

import java.util.List;

public class LocationAPI extends BaseAPI {

    public LocationAPI(String baseUrl) {
        super(baseUrl);
    }

    public Location getLocationForID(String code){
        Bundle bundle = client.search().forResource(Location.class)
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

    public Location parseBundleEntry(Bundle.BundleEntryComponent entryComponent) {
        Resource resource = entryComponent.getResource();
        Location location = null;
        if (resource.getResourceType().equals(ResourceType.Location)) {
            location = (Location) entryComponent.getResource();
        }
        return location;
    }
}
