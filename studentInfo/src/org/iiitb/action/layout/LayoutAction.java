package org.iiitb.action.layout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opensymphony.xwork2.ActionSupport;

import org.apache.struts2.interceptor.SessionAware;
import org.iiitb.util.ConnectionPool;
import org.iiitb.util.Constants;
import org.iiitb.action.dao.LayoutDAO;
import org.iiitb.action.dao.impl.LayoutDAOImpl;
import org.iiitb.model.User;
import org.iiitb.model.layout.*;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class LayoutAction extends ActionSupport implements SessionAware
{
	private Map<String, Object> session;
	private User user=null;
	private List<NewsItem> allNews;
	private List<AnnouncementsItem> announcements;
	private String lastLoggedOn;
	
	private LayoutDAO layoutDAO=new LayoutDAOImpl();
	
	public String execute() throws SQLException
	{
		if(session!=null)
			user = (User)session.get("user");
		if(user != null)
		{
			/*
			Connection cn=ConnectionPool.getConnection();
			allNews=layoutDAO.getAllNews(cn);
			announcements=layoutDAO.getAnnouncements(cn, Integer.parseInt(user.getUserId()));
			setLastLoggedOn((String) this.session.get(Constants.LAST_LOGGED_ON));
			ConnectionPool.freeConnection(cn);
			*/
			Gson gson=new GsonBuilder().create();
			ClientConfig config = new DefaultClientConfig();
		    Client client = Client.create(config);
		    WebResource service = client.resource(
		    		UriBuilder.fromUri("http://localhost:8080/api.sis").build());
		    
		    allNews=new ArrayList<NewsItem>();
		    for(NewsItem ni:gson.fromJson(
				    		service.path("rest").path("news").accept(MediaType.APPLICATION_JSON).get(String.class),
				    		NewsItem[].class))
		    	allNews.add(ni);
		    
		    announcements=new ArrayList<AnnouncementsItem>();
		    for(AnnouncementsItem ai:gson.fromJson(
		    		service.path("rest").path("announcements").path("users").path(user.getUserId()).accept(MediaType.APPLICATION_JSON).get(String.class),
		    		AnnouncementsItem[].class))
		    	announcements.add(ai);
		}
		return "success";
	}

	public List<NewsItem> getAllNews() {
		return allNews;
	}

	public List<AnnouncementsItem> getAnnouncements() {
		return announcements;
	}
	
	public User getUser()
	{
		return this.user;
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		this.session=arg0;
	}

	public String getLastLoggedOn()
	{
		return lastLoggedOn;
	}

	public void setLastLoggedOn(String lastLoggedOn)
	{
		this.lastLoggedOn = lastLoggedOn;
	}
}
