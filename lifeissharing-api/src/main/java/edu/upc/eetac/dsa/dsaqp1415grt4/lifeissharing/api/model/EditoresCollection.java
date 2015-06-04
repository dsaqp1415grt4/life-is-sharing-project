package edu.upc.eetac.dsa.dsaqp1415grt4.lifeissharing.api.model;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;

public class EditoresCollection {

	private List<Link> links;
	private List<Editores> editores;
	
		
	public List<Editores> getEditores() {
		return editores;
	}

	public void setEditores(List<Editores> editores) {
		this.editores = editores;
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}
		
	public EditoresCollection() {
		super();
		editores = new ArrayList<>();
	}
	
	public void addEditor(Editores editor) {
		editores.add(editor);
	}
	

}