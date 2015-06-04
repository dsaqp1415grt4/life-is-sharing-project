package edu.upc.eetac.dsa.iarroyo.lifeissharing.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nacho on 30/05/15.
 */
public class ItemCollection {
    private List<Item> items;

    public Map<String, Link> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    private Map<String, Link> links = new HashMap<String, Link>();

    public ItemCollection() {
        super();
        items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }


}
