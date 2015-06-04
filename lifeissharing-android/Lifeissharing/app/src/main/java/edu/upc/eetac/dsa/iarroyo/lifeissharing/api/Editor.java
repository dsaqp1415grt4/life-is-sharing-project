package edu.upc.eetac.dsa.iarroyo.lifeissharing.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nacho on 4/06/15.
 */
public class Editor {

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;

    public Map<String, Link> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }

    private Map<String, Link> links = new HashMap<String, Link>();
}
