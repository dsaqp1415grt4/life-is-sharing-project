package edu.upc.eetac.dsa.iarroyo.lifeissharing.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nacho on 30/05/15.
 */
public class LifeissharingRootAPI {

    private Map<String, Link> links;

    public LifeissharingRootAPI() {
        links = new HashMap<String, Link>();
    }

    public Map<String, Link> getLinks() {
        return links;
    }

}
