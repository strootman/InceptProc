package com.tyrellcorp.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("inventory")
public interface InventoryManagementService extends RemoteService
{
	String getReplicantIdentifier();
	
	String getAvailableModels();
}
