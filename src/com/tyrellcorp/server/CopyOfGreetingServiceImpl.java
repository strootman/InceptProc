package com.tyrellcorp.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tyrellcorp.client.CopyOfGreetingService;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class CopyOfGreetingServiceImpl extends RemoteServiceServlet implements CopyOfGreetingService
{

	public String greetServer(String input) throws IllegalArgumentException
	{
		return "Welcome, " + input + " -  the system is ready for production entries...";
	}
}
