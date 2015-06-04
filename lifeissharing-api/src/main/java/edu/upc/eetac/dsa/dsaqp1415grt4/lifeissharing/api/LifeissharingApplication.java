package edu.upc.eetac.dsa.dsaqp1415grt4.lifeissharing.api;

import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class LifeissharingApplication extends ResourceConfig{

	public LifeissharingApplication() {
		super();
		register(DeclarativeLinkingFeature.class);
	}
}
