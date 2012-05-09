package com.tyrellcorp.server;

import java.util.UUID;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tyrellcorp.client.InventoryManagementService;

@SuppressWarnings("serial")
public class InventoryManagementServiceImpl extends RemoteServiceServlet implements InventoryManagementService
{
	@Override
	public String getAvailableModels()
	{
		String json = "{ \"models\": [ {\"id\": \"FEE-57885\",\"name\": \"NEXUS-5\"}," +
					  				  "{\"id\": \"FEE-57886\",\"name\": \"NEXUS-6\"}," +
					  				  "{\"id\": \"FEE-57887\",\"name\": \"NEXUS-7\"}] }";
		return json;
	}

	@Override
	public String getReplicantIdentifier()
	{
		return UUID.randomUUID().toString();
	}
}
