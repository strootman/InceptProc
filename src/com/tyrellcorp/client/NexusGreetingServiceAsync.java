package com.tyrellcorp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>NexusGreetingService</code>.
 */
public interface NexusGreetingServiceAsync
{
	void greetServer(String input, AsyncCallback<String> callback) throws IllegalArgumentException;
}
