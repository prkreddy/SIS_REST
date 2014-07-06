package edu.iiitb.rest.model;

public class AnnouncementsItem
{
	private String name;
	private String details;
	
	public AnnouncementsItem(String name, String details)
	{
		this.name=name;
		this.details=details;
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
