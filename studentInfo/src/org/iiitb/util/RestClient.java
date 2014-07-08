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

		/*
		 * String input = "{\"event_name\":\"" + eventname +
		 * "\",\"event_start_date\":\"" + eventstartdate +
		 * "\",\"event_end_date\":\"" + eventenddate + "\",\"event_time\":\"" +
		 * eventtime + "\",\"organiser_id\":\"" + user.getUserId() +
		 * "\",\"event_type\":\"" + eventType + "\"," + "\"event_duration\":" +
		 * eventduration + ",\"event_desc\":\"" + eventDescription + "\"}";
		 */

		ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, input);

		if (response.getStatus() != 200)
		{
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}

		System.out.println("Output from Server .... \n");
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

		System.out.println("Output from Server .... \n");
		String output = response.getEntity(String.class);

		return output;
	}

}
