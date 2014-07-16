package org.iiitb.action.announcements;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.iiitb.action.dao.InterestDAO;
import org.iiitb.action.dao.impl.InterestDAOImpl;
import org.iiitb.model.layout.Interest;
import org.iiitb.model.layout.NewsItem;
import org.iiitb.util.ConnectionPool;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class AddAnnouncementAction extends ActionSupport
{
	private Map<Integer, String> li;
	public String execute() throws SQLException
	{
		li=new LinkedHashMap<Integer, String>();
		
		/*
		Connection cn=ConnectionPool.getConnection();
		InterestDAO interestDAO=new InterestDAOImpl();
		for(Interest i:interestDAO.getAllInterests(cn))
			li.put(i.getId(), i.getName());
		ConnectionPool.freeConnection(cn);
		*/
		
		Gson gson=new GsonBuilder().create();
		Client client = Client.create();
		WebResource webResource = client.resource("http://localhost:8080/api.sis/rest/interests");
		String resp=webResource.accept(MediaType.APPLICATION_JSON).get(String.class);
		Interest[] ia=gson.fromJson(resp, Interest[].class);
		for(Interest i:ia)
			li.put(i.getId(), i.getName());
		
		return "success";
	}
	public Map<Integer, String> getLi() {
		return li;
	}
	public void setLi(Map<Integer, String> li) {
		this.li = li;
	}
	
}
