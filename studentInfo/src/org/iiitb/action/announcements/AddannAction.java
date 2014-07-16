package org.iiitb.action.announcements;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import javax.naming.NamingException;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.interceptor.SessionAware;
import org.iiitb.model.layout.AnnouncementsItem;
import org.iiitb.model.layout.Interest;
import org.iiitb.util.ConnectionPool;
import org.iiitb.util.Constants;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class AddannAction extends ActionSupport implements SessionAware

{
	private String name;
	private String details;
	private int interest;

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
			String query = "insert into announcement(name,details) values(?,?)";

			preStmt = conn.prepareStatement(query);
			preStmt.setString(1, name);
			preStmt.setString(2, details);
			
			if (preStmt.executeUpdate() > 0)
			{
				String q="insert into announcement_interest(interest_id, announcement_id) values(?, (select max(announcement_id) from announcement));";
				PreparedStatement p=conn.prepareStatement(q);
				p.setInt(1, interest);
				if(p.executeUpdate()>0)
					ret = SUCCESS;
				else
					ret = ERROR;
			}
			else
				ret = ERROR;
				*/
			
			
			Gson gson=new GsonBuilder().create();
			Client client = Client.create();
			WebResource webResource = client.resource("http://localhost:8080/api.sis/rest/announcements/add");
			webResource.accept(MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, gson.toJson(new AnnouncementsItem(name, details, interest)));
			ret = SUCCESS;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionPool.freeConnection(conn);
		}
		return ret;

	}

	public void validate() {

	
		if (StringUtils.isEmpty(details)) {
			addFieldError("details ", "details cannot be blank");
		}

		if (StringUtils.isEmpty(name)) {
			addFieldError("name", "name cannot be blank");
		}

	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session=session;

	}

	public int getInterest() {
		return interest;
	}

	public void setInterest(int interest) {
		this.interest = interest;
	}
}