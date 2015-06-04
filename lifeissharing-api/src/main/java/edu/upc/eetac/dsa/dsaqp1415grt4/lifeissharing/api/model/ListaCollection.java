package edu.upc.eetac.dsa.dsaqp1415grt4.lifeissharing.api.model;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;

import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLink.Style;
import org.glassfish.jersey.linking.InjectLinks;

import edu.upc.eetac.dsa.dsaqp1415grt4.lifeissharing.api.ListaResource;
import edu.upc.eetac.dsa.dsaqp1415grt4.lifeissharing.api.MediaType;


public class ListaCollection {
	@InjectLinks({
		@InjectLink(resource = ListaResource.class, style = Style.ABSOLUTE, rel = "create-lista", title = "Create lista", type = MediaType.LIFE_API_LISTA) })
	private List<Link> links;
	private List<Lista> listas;
	
	public ListaCollection() {
		super();
		listas = new ArrayList<>();
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public List<Lista> getListas() {
		return listas;
	}

	public void setListas(List<Lista> listas) {
		this.listas = listas;
	}
	
	public void addLista(Lista lista) {
		listas.add(lista);
	}
}
