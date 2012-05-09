package com.tyrellcorp.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tyrellcorp.shared.Replicant;

public interface ProductionManagementServiceAsync
{

	void getProductionManifest(AsyncCallback<List<Replicant>> callback);

	void addTo(Replicant repl, AsyncCallback<Void> callback);

}
