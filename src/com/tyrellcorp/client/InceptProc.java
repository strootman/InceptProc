package com.tyrellcorp.client;

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
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.tyrellcorp.shared.FieldVerifier;
import com.tyrellcorp.shared.Replicant;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class InceptProc implements EntryPoint {
	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final NexusGreetingServiceAsync nexusService = GWT.create(NexusGreetingService.class);
	private final InventoryManagementServiceAsync invService = GWT.create(InventoryManagementService.class);
	private final ProductionManagementServiceAsync prodService = GWT.create(ProductionManagementService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		final Label lblGreet = new Label();
		final ListBox dropDownModels = new ListBox();
		final Label lblUuid = new Label("{no identifier defined}");
		final Button btnGenId = new Button("Generate");
		
		final TextBox tBoxReplName = new TextBox();
		
		final Button btnAddRepl = new Button("Add");
		final Button btnShowProd = new Button("Total");
		
		final Label lblError = new Label("");
		lblError.setStyleName("errorMessage");
		lblError.setVisible(false);

		RootPanel.get("testLabelContainer").add(lblGreet);
		RootPanel.get("modelDropDownContainer").add(dropDownModels);

		RootPanel.get("uuidLabelContainer").add(lblUuid);
		RootPanel.get("uuidButtonContainer").add(btnGenId);

		RootPanel.get("nameFieldContainer").add(tBoxReplName);

		RootPanel.get("addToProdContainer").add(btnAddRepl);
		RootPanel.get("prodContainer").add(btnShowProd);
		RootPanel.get("errorLabelContainer").add(lblError);

		invService.getAvailableModels(new AsyncCallback<String>() {
			@Override
			public void onSuccess(String result) {
				// TODO Improve JSON parsing
				JSONValue obj = JSONParser.parseLenient(result);
				JSONObject modelList = obj.isObject();
				if ((modelList != null) && modelList.containsKey("models") && (modelList.get("models").isArray() != null)) {
					JSONArray models = modelList.get("models").isArray();
					for (int i = 0; i < models.size(); i++) {
						JSONObject model = models.get(i).isObject();
						if (model.containsKey("name") && (model.get("name").isString() != null)) {
							dropDownModels.addItem(model.get("name").isString().stringValue());
						}
					}
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("Failed to parse JSON");
			}
		});

		btnGenId.addClickHandler(new ClickHandler() {
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

		btnShowProd.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				prodService.getProductionManifest(new AsyncCallback<List<Replicant>>() {
					@Override
					public void onSuccess(List<Replicant> result) {
						// TODO Wire these UI elements to CSS
						final ListBox productionReplicants = new ListBox(false);
						for(Replicant r : result){
							productionReplicants.addItem(r.toString());
						}
						productionReplicants.setVisibleItemCount(result.size());

						// Create panel for popup, add label and list box
						final VerticalPanel vPanel = new VerticalPanel();
						final Label prodReplLabel = new Label("Replicants ready for the Off-World Colonies");
						vPanel.add(prodReplLabel);
						vPanel.add(productionReplicants);

						final PopupPanel  popup = new PopupPanel(true);
						popup.setWidget(vPanel);
						popup.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
							public void setPosition(int offsetWidth, int offsetHeight) {
								int left = (btnShowProd.getAbsoluteLeft() + btnShowProd.getOffsetWidth() - offsetWidth);
								int top = (btnShowProd.getAbsoluteTop() + btnShowProd.getOffsetHeight() - offsetHeight);
								popup.setPopupPosition(left, top);
							}
						});
						popup.show();
					}
					
					@Override
					public void onFailure(Throwable caught) {
						GWT.log("Failed to retrieve replicant production manifest");
					}
				});
			}
		});

		btnAddRepl.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// FIXME Found an error here, need to reproduce.
				String model = dropDownModels.getItemText(dropDownModels.getSelectedIndex());
				if(!FieldVerifier.isValidName(tBoxReplName.getText())){
					tBoxReplName.selectAll();
					lblError.setText("Invalid Replicant Name");
					lblError.setVisible(true);
				}else{
					lblError.setVisible(false);
					lblError.setText("");
					final Replicant r = new Replicant(model, tBoxReplName.getText(), lblUuid.getText());
					prodService.addTo(r, new AsyncCallback<Void>() {
						@Override
						public void onSuccess(Void result) {
							GWT.log("Added replicant " + r.toString());
						}

						@Override
						public void onFailure(Throwable caught) {
							GWT.log(caught.getLocalizedMessage());
						}
					});
				}
			}
		});

		nexusService.greetServer("JF Sebastian", new AsyncCallback<String>() {
			@Override
			public void onSuccess(String result) {
				lblGreet.setText(result);
			}
			@Override
			public void onFailure(Throwable caught) {
				GWT.log("OH NO!");
			}
		});
	}
}
