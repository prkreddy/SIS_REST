package org.iiitb.action.news;

import java.sql.SQLException;
import java.util.Map;

import javax.naming.NamingException;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class RemovenewsAction extends ActionSupport

{
	private String name;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String execute() throws NamingException, SQLException
	{
		Client client = Client.create();
		
		WebResource webResource =
				client.resource("http://localhost:8080/api.sis/rest/news/delete/"+name.replace(" ", "%20"));
		
		ClientResponse clientResponse=webResource.delete(ClientResponse.class);
		
		if(clientResponse.getStatus()==200)	
			return SUCCESS;
		else
			return ERROR;
	}

	public void validate()
	{
		if (StringUtils.isEmpty(name))
			addFieldError("name", "name cannot be blank");
	}
}