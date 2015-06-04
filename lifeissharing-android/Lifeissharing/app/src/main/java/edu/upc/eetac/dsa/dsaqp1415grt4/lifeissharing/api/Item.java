package edu.upc.eetac.dsa.iarroyo.lifeissharing.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nacho on 30/05/15.
 */
public class Item {

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIditem() {
        return iditem;
    }

    public void setIditem(int iditem) {
        this.iditem = iditem;
    }

    public Map<String, Link> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }

    private String description;
    private int id;
    private int iditem;
    private Map<String, Link> links = new HashMap<String, Link>();
}
