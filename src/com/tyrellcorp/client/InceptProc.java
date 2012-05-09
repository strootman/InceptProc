package com.tyrellcorp.client;

import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.tyrellcorp.shared.Replicant;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class InceptProc implements EntryPoint
{
	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final CopyOfGreetingServiceAsync nexusService = GWT.create(CopyOfGreetingService.class);
	private final InventoryManagementServiceAsync invService = GWT.create(InventoryManagementService.class);
	private final ProductionManagementServiceAsync prodService = GWT.create(ProductionManagementService.class);
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad()
	{		
		// Add controls - they will likely be used by anonymous inner classes so they should be 'final'
		
		final Label lbl = new Label();

		final Button btnAdd = new Button("Add");
		final Button btnProd = new Button("Total");
		
		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element

		RootPanel.get("addToProdContainer").add(btnAdd);
		RootPanel.get("prodContainer").add(btnProd);
		RootPanel.get("testLabelContainer").add(lbl);

		btnProd.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event)
			{
				prodService.getProductionManifest(new AsyncCallback<List<Replicant>>() {

					@Override
					public void onFailure(Throwable caught)
					{
						GWT.log("OH GEEZ!?!?!?  WHAT NOT!?!");
					}

					@Override
					public void onSuccess(List<Replicant> result)
					{
						GWT.log(" We have " + result.size() + " replicants ready for the Off-World Colonies");
					}
				});
			}
		});
		
		btnAdd.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event)
			{
				//Adding null is pretty unhelpful... 
				prodService.addTo(null, new AsyncCallback<Void>()
				{
					
					@Override
					public void onSuccess(Void result)
					{
						GWT.log("Added...");
					}
					
					@Override
					public void onFailure(Throwable caught)
					{
						GWT.log(caught.getLocalizedMessage());
					}
				});
			}
		});
		
		nexusService.greetServer("JF Sebastian", new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught)
			{
				GWT.log("OH NO!");
			}

			@Override
			public void onSuccess(String result)
			{
				lbl.setText(result);
			}
		});
	}
}
