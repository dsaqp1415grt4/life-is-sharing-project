package edu.upc.eetac.dsa.dsaqp1415grt4.lifeissharing.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;



@Path("/dummy/{username}")
public class ListResourceDummy {
	@GET
	@Path("/listas")
	@Produces(MediaType.LIFE_API_LISTA_COLLECTION)
	public String getListasUsuario(
			@PathParam("username") String username) {
		return "{\"listas\":[{\"creador\":\"NachoTelematic\",\"fecha_creacion\":1432046072000,\"idlista\":2,\"items\":[],\"nombre\":\"Cena\",\"ultima_modificacion\":1432046072000}]}";
	}
}
