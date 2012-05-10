package com.tyrellcorp.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet2")
public interface NexusGreetingService extends RemoteService
{
	String greetServer(String name) throws IllegalArgumentException;
}
