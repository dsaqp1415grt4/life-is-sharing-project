package edu.upc.eetac.dsa.dsaqp1415grt4.lifeissharing.api.model;

import java.util.List;

import javax.ws.rs.core.Link;

import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLink.Style;
import org.glassfish.jersey.linking.InjectLinks;

import edu.upc.eetac.dsa.dsaqp1415grt4.lifeissharing.api.LifeissharingRootAPIResource;
import edu.upc.eetac.dsa.dsaqp1415grt4.lifeissharing.api.ListaResource;
import edu.upc.eetac.dsa.dsaqp1415grt4.lifeissharing.api.MediaType;


public class LifeissharingRootAPI {
	@InjectLinks({
		@InjectLink(resource = LifeissharingRootAPIResource.class, style = Style.ABSOLUTE, rel = "self bookmark home", title = "Life Root API"),
		@InjectLink(resource = ListaResource.class, style = Style.ABSOLUTE, rel = "collection", title = "Ultimas listas", type = MediaType.LIFE_API_LISTA_COLLECTION),
		@InjectLink(resource = ListaResource.class, style = Style.ABSOLUTE, rel = "create-lista", title = "Crear nueva lista", type = MediaType.LIFE_API_LISTA) })
	private List<Link> links;

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

}
