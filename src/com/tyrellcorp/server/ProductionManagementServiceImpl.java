package com.tyrellcorp.server;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tyrellcorp.client.ProductionManagementService;
import com.tyrellcorp.shared.Replicant;

@SuppressWarnings({ "serial", "deprecation" })
public class ProductionManagementServiceImpl extends RemoteServiceServlet implements ProductionManagementService
{
	// This is just HORRIBLE way to hold state - again, it's Los Angeles 2019. 
	public static final List<Replicant> productionReplicants = new ArrayList<Replicant>();
	
	static { 
		productionReplicants.add(new Replicant("NEXUS-6", "Batty, Roy", "N6MAA10816", new Date(2015, 4, 5)));
		productionReplicants.add(new Replicant("NEXUS-6", "Kowalski, Leon", "N6MAC41717", new Date(2015, 4, 5)));
		productionReplicants.add(new Replicant("NEXUS-6", "Zhora", "N6FAB61216", new Date(2015, 4, 5)));
		productionReplicants.add(new Replicant("NEXUS-6", "Rachael", UUID.randomUUID().toString(), new Date(2018, 4, 5)));
	}

	public List<Replicant> getProductionManifest() {
		return productionReplicants;
	}
	
	public void addTo(Replicant repl) {
		productionReplicants.add(repl);
	}	
}
