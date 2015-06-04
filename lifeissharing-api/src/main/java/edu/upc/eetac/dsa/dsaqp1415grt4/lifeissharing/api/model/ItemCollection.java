package edu.upc.eetac.dsa.dsaqp1415grt4.lifeissharing.api.model;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;

import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.glassfish.jersey.linking.InjectLink.Style;

import edu.upc.eetac.dsa.dsaqp1415grt4.lifeissharing.api.MediaType;

public class ItemCollection {
	
		private List<Link> links;
	 
	public ItemCollection() {
		super();
		items = new ArrayList<>();
	}
	
	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}
	
	private List<Item> items;
	
	public List<Item> getItems() {		
		return items;
	}

	
	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	public void addItem(Item item) {
		items.add(item);
	}
	
	
}
