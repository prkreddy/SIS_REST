package org.iiitb.action.news;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import javax.naming.NamingException;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.interceptor.SessionAware;
import org.iiitb.model.layout.NewsItem;
import org.iiitb.util.ConnectionPool;
import org.iiitb.util.RestClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class RemovenewsAction extends ActionSupport implements SessionAware

{

	private String name;


	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private Map<String, Object> session;

	public Map<String, Object> getSession() {
		return session;
	}

	public String execute() throws NamingException, SQLException {
		
		String ret = ERROR;
		Connection conn = ConnectionPool.getConnection();

		PreparedStatement preStmt = null;
		try {
			/*
			String query = "delete from news where name=?";

			preStmt = conn.prepareStatement(query);
			preStmt.setString(1, name);
			
			if (preStmt.executeUpdate() > 0)
				ret = SUCCESS;
			else
				ret = ERROR;
				*/
			
			/*
			RestClient rc=new RestClient();
			Gson gson=new GsonBuilder().create();
			rc.callPostService(gson.toJson(new NewsItem(name, "")), "news/delete");
			*/
			
			Gson gson=new GsonBuilder().create();
			Client client = Client.create();
			WebResource webResource = client.resource("http://localhost:8080/api.sis/rest/news/delete/"+name.replace(" ", "%20"));
			webResource.type(MediaType.APPLICATION_JSON)
					.delete(ClientResponse.class);
			
			ret=SUCCESS;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionPool.freeConnection(conn);
		}
		return ret;

	}

	public void validate() {

		if (StringUtils.isEmpty(name)) {
			addFieldError("name", "name cannot be blank");
		}

	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session=session;

	}
}