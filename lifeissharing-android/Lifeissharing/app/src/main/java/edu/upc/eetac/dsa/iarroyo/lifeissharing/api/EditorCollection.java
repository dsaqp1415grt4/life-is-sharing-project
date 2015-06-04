package edu.upc.eetac.dsa.iarroyo.lifeissharing.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nacho on 4/06/15.
 */
public class EditorCollection {

    public List<Editor> getEditores() {
        return editores;
    }

    public void setEditores(List<Editor> editores) {
        this.editores = editores;
    }

    private List<Editor> editores;

    public Map<String, Link> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }



    private Map<String, Link> links = new HashMap<String, Link>();

    public EditorCollection() {
        super();
        editores = new ArrayList<>();
    }

    public void addEditor(Editor editor) {
        editores.add(editor);
    }



}
