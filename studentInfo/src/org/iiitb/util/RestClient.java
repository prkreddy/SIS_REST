package org.iiitb.util;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class RestClient
{

	public String callPostService(String input, String serviceUri)
	{
		Client client = Client.create();

		WebResource webResource = client.resource(Constants.REST_SERVER_HOST + serviceUri);		

		ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, input);

		if (response.getStatus() != 200)
		{
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}

		String output = response.getEntity(String.class);

		return output;
	}

	public String callGetService(String uri)
	{

		Client client = Client.create();

		WebResource webResource = client.resource(Constants.REST_SERVER_HOST + uri);

		ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

		if (response.getStatus() != 200)
		{
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}

		String output = response.getEntity(String.class);

		return output;
	}

}
