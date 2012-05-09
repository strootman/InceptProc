package com.tyrellcorp.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.tyrellcorp.shared.Replicant;

@RemoteServiceRelativePath("production")
public interface ProductionManagementService extends RemoteService
{
	public List<Replicant> getProductionManifest();
	public void addTo(Replicant repl);
}
