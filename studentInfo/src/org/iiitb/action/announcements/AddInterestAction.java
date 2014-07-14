package org.iiitb.action.announcements;

import java.sql.Connection;
import java.sql.SQLException;

import javax.ws.rs.core.MediaType;

import org.iiitb.action.dao.InterestDAO;
import org.iiitb.action.dao.impl.InterestDAOImpl;
import org.iiitb.model.layout.Interest;
import org.iiitb.model.layout.NewsItem;
import org.iiitb.util.ConnectionPool;
import org.iiitb.util.RestClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class AddInterestAction extends ActionSupport
{
	private String name;
	private String details;
	
	public String execute() throws SQLException
	{
		if(!name.trim().equals("") && !details.trim().equals(""))
		{
			/*
			Connection cn=ConnectionPool.getConnection();
			InterestDAO interestDAO=new InterestDAOImpl();
			interestDAO.addInterest(cn, new Interest(name, details));
			ConnectionPool.freeConnection(cn);
			*/
			
			/*
			RestClient rc=new RestClient();
			Gson gson=new GsonBuilder().create();
			rc.callPostService(gson.toJson(new Interest(name, details)), "interests/add");
			*/
			
			Gson gson=new GsonBuilder().create();
			Client client = Client.create();
			WebResource webResource = client.resource("http://localhost:8080/api.sis/rest/interests/add");
			webResource.type(MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, gson.toJson(new Interest(name, details)));
			
			return SUCCESS;
		}
		
		if(name.trim().equals(""))
			addFieldError("name", "name cannot be empty");
			
		if(details.trim().equals(""))
			addFieldError("details", "details cannot be empty");
		
		return INPUT;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
}
