package edu.upc.eetac.dsa.dsaqp1415grt4.lifeissharing.api.model;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;

import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLink.Style;
import org.glassfish.jersey.linking.InjectLinks;

import edu.upc.eetac.dsa.dsaqp1415grt4.lifeissharing.api.LifeissharingRootAPIResource;
import edu.upc.eetac.dsa.dsaqp1415grt4.lifeissharing.api.ListaResource;
import edu.upc.eetac.dsa.dsaqp1415grt4.lifeissharing.api.MediaType;




public class Lista {
	@InjectLinks({
		@InjectLink(value = "/listas/{idlista}", style = Style.ABSOLUTE, rel = "self", title = "self-lista", type = MediaType.LIFE_API_LISTA, bindings = { @Binding(name = "idlista", value = "${instance.idlista}")}),
		@InjectLink(value = "/listas/{idlista}/items", style = Style.ABSOLUTE, rel = "items", title = "Todos los items de la lista", type = MediaType.LIFE_API_LISTA_COLLECTION, bindings = { @Binding(name = "idlista", value = "${instance.idlista}") }),
		@InjectLink(value = "/listas/{idlista}/editores", style = Style.ABSOLUTE, rel = "editores", title = "Todos los editores de la lista", type = MediaType.LIFE_API_LISTA_COLLECTION, bindings = { @Binding(name = "idlista", value = "${instance.idlista}") })})
	private List<Link> links;
	private int idlista;
	private String nombre;
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	private long fecha_creacion;
	private long ultima_modificacion;
	private String creador;
	
//	private List<Item> items;
	
/*	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	public void addItem(Item item) {
		items.add(item);
	}
*/
	public Lista() {
		super();
	//	items = new ArrayList<>();
	}
	
	public List<Link> getLinks() {
		return links;
	}
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	
	public int getIdlista() {
		return idlista;
	}
	public void setIdlista(int idlista) {
		this.idlista = idlista;
	}
	
	public long getFecha_creacion() {
		return fecha_creacion;
	}
	public void setFecha_creacion(long fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}
	
	public long getUltima_modificacion() {
		return ultima_modificacion;
	}
	public void setUltima_modificacion(long ultima_modificacion) {
		this.ultima_modificacion = ultima_modificacion;
	}
	
	public String getCreador() {
		return creador;
	}
	public void setCreador(String creador) {
		this.creador = creador;
	}

}
