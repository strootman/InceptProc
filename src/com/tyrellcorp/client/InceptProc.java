package com.tyrellcorp.client;

import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
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
public class InceptProc implements EntryPoint {
	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final CopyOfGreetingServiceAsync nexusService = GWT.create(CopyOfGreetingService.class);
	private final InventoryManagementServiceAsync invService = GWT.create(InventoryManagementService.class);
	private final ProductionManagementServiceAsync prodService = GWT.create(ProductionManagementService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		final Label lbl = new Label();

		final ListBox modelDropDown = new ListBox();

		final Label lblUuid = new Label("{no identifier defined}");
		final Button btnGen = new Button("Generate");
		
		final TextBox tBoxName = new TextBox();
		
		final Button btnAdd = new Button("Add");
		final Button btnProd = new Button("Total");


		// TODO Add a flex table to display current production ready replicants
		RootPanel.get("testLabelContainer").add(lbl);
		RootPanel.get("modelDropDownContainer").add(modelDropDown);

		RootPanel.get("uuidLabelContainer").add(lblUuid);
		RootPanel.get("uuidButtonContainer").add(btnGen);

		RootPanel.get("nameFieldContainer").add(tBoxName);

		RootPanel.get("addToProdContainer").add(btnAdd);
		RootPanel.get("prodContainer").add(btnProd);

		invService.getAvailableModels(new AsyncCallback<String>() {
			@Override
			public void onSuccess(String result) {
				JSONValue obj = JSONParser.parseLenient(result);
				JSONObject modelList = obj.isObject();
				if ((modelList != null) && modelList.containsKey("models") && (modelList.get("models").isArray() != null)) {
					JSONArray models = modelList.get("models").isArray();
					for (int i = 0; i < models.size(); i++) {
						JSONObject model = models.get(i).isObject();
						if (model.containsKey("name") && (model.get("name").isString() != null)) {
							modelDropDown.addItem(model.get("name").isString().stringValue());
						}
					}
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("Failed to parse JSON");
			}
		});

		btnGen.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				invService.getReplicantIdentifier(new AsyncCallback<String>() {
					@Override
					public void onSuccess(String result) {
						lblUuid.setText(result);
					}

					@Override
					public void onFailure(Throwable caught) {
						GWT.log("Failed to retrieve replicant identifier");
					}
				});
			}
		});

		btnProd.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				prodService.getProductionManifest(new AsyncCallback<List<Replicant>>() {

							@Override
							public void onFailure(Throwable caught) {
								GWT.log("OH GEEZ!?!?!?  WHAT NOT!?!");
							}

							@Override
							public void onSuccess(List<Replicant> result) {
								GWT.log(" We have "
										+ result.size()
										+ " replicants ready for the Off-World Colonies");
							}
						});
			}
		});

		btnAdd.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Perform validation of replicant at some point.
				String model = modelDropDown.getItemText(modelDropDown.getSelectedIndex());
				Date incept = new Date();
				incept.setYear(incept.getYear() + 4);
				final Replicant r = new Replicant(model, tBoxName.getText(), lblUuid.getText(), incept);
				prodService.addTo(r, new AsyncCallback<Void>() {

					@Override
					public void onSuccess(Void result) {
						// TODO Add replicant to flex table
						GWT.log("Added replicant " + r.toString());
					}

					@Override
					public void onFailure(Throwable caught) {
						GWT.log(caught.getLocalizedMessage());
					}
				});
			}
		});

		nexusService.greetServer("JF Sebastian", new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				GWT.log("OH NO!");
			}

			@Override
			public void onSuccess(String result) {
				lbl.setText(result);
			}
		});
	}
}
