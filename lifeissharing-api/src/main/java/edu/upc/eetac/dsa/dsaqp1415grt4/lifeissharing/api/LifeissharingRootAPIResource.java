package edu.upc.eetac.dsa.dsaqp1415grt4.lifeissharing.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import edu.upc.eetac.dsa.dsaqp1415grt4.lifeissharing.api.model.LifeissharingRootAPI;


@Path("/")
public class LifeissharingRootAPIResource {

		@GET
		public LifeissharingRootAPI getRootAPI() {
			LifeissharingRootAPI api = new LifeissharingRootAPI();
			return api;
		}
}