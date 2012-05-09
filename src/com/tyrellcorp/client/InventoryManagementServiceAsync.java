package com.tyrellcorp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface InventoryManagementServiceAsync
{

	void getReplicantIdentifier(AsyncCallback<String> callback);

	void getAvailableModels(AsyncCallback<String> callback);

}
