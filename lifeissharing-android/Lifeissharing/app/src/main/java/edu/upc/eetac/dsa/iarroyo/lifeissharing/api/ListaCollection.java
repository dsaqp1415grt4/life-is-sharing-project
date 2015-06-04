package edu.upc.eetac.dsa.iarroyo.lifeissharing.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nacho on 30/05/15.
 */
public class ListaCollection {

    private List<Lista> listas;


    public List<Lista> getListas() {
        return listas;
    }

    public void setListas(List<Lista> listas) {
        this.listas = listas;
    }


    public Map<String, Link> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }


    private Map<String, Link> links = new HashMap<String, Link>();

    public ListaCollection() {
        super();
        listas = new ArrayList<>();
    }

    public void addLista(Lista lista) {
        listas.add(lista);
    }
}
