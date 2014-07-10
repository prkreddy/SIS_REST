package org.iiitb.action.friends;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
//import org.iiitb.action.dao.LayoutDAO;
import org.iiitb.action.dao.StudentDAO;
//import org.iiitb.action.dao.impl.LayoutDAOImpl;
import org.iiitb.action.dao.impl.StudentDAOImpl;
import org.iiitb.model.StudentInfo;
import org.iiitb.model.User;
import org.iiitb.model.layout.AnnouncementsItem;
import org.iiitb.model.layout.NewsItem;
import org.iiitb.util.ConnectionPool;
import org.iiitb.util.Constants;
import org.iiitb.util.RestClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author prashanth
 * 
 */
public class MyFriendsAction extends ActionSupport implements SessionAware
{
	/**
	 * 
	 */
	private StudentInfo myProfile;
	private List<StudentInfo> students;
	private Map<String, Object> session;

	private List<NewsItem> allNews;
	private List<AnnouncementsItem> announcements;
	// private LayoutDAO layoutDAO = new LayoutDAOImpl();
	private String lastLoggedOn;

	public StudentInfo getMyProfile()
	{
		return myProfile;
	}

	public void setMyProfile(StudentInfo myProfile)
	{
		this.myProfile = myProfile;
	}

	public List<StudentInfo> getStudents()
	{
		return students;
	}

	public void setStudents(List<StudentInfo> students)
	{
		this.students = students;
	}

	public List<NewsItem> getAllNews()
	{
		return allNews;
	}

	public void setAllNews(List<NewsItem> allNews)
	{
		this.allNews = allNews;
	}

	public List<AnnouncementsItem> getAnnouncements()
	{
		return announcements;
	}

	public void setAnnouncements(List<AnnouncementsItem> announcements)
	{
		this.announcements = announcements;
	}

	public String execute() throws SQLException
	{
		RestClient restClient = new RestClient();

		User user = (User) session.get("user");
		if (user != null)
		{

			// StudentInfo studentInfo =
			// studentDao.getStudentByUserId(user.getUserId());

			// rest call start
			String output = restClient.callGetService("friends/studentid/" + user.getUserId());
			// rest call end

			StudentInfo studentInfo = getStudentInfo(output);

			if (studentInfo != null)
			{

				setMyProfile(studentInfo);

				setStudents(getListOfStudents(restClient.callGetService("friends/studentfriends/" + user.getUserId())));

				setLastLoggedOn((String) this.session.get(Constants.LAST_LOGGED_ON));
				RestClient rc = new RestClient();
				Gson gson = new GsonBuilder().create();
				allNews = new ArrayList<NewsItem>();
				for (NewsItem ni : gson.fromJson(rc.callGetService("news"), NewsItem[].class))
					allNews.add(ni);
				announcements = new ArrayList<AnnouncementsItem>();
				for (AnnouncementsItem ai : gson.fromJson(rc.callGetService("announcements/users/" + user.getUserId()), AnnouncementsItem[].class))
					announcements.add(ai);
			}
			else
			{
				return ERROR;
			}

		}

		return SUCCESS;
	}

	@Override
	public void setSession(Map<String, Object> session)
	{
		this.session = session;

	}

	public String getLastLoggedOn()
	{
		return lastLoggedOn;
	}

	public void setLastLoggedOn(String lastLoggedOn)
	{
		this.lastLoggedOn = lastLoggedOn;
	}

	List<StudentInfo> getListOfStudents(String ouput)
	{
		System.out.println("*******************************************************************");

		List<StudentInfo> students = new ArrayList<StudentInfo>();

		try
		{
			JSONArray jsonArray = new JSONArray(ouput);
			JSONObject jsonObject = null;

			for (int i = 0; i < jsonArray.length(); i++)
			{
				jsonObject = jsonArray.getJSONObject(i);
				StudentInfo info = new StudentInfo();
				info.setStudentId(Integer.parseInt(jsonObject.optString("studentId")));

				info.setDob(jsonObject.optString("dob"));

				info.setRollNo(jsonObject.optString("rollNo"));
				info.setName(jsonObject.optString("name"));
				students.add(info);

				System.out.println(jsonObject);

			}

			System.out.println(jsonObject);

		}
		catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("*******************************************************************");
		return students;
	}

	StudentInfo getStudentInfo(String ouput)
	{
		System.out.println("*******************************************************************");
		StudentInfo info = null;

		try
		{
			JSONObject jsonObject = new JSONObject(ouput);

			info = new StudentInfo();
			info.setStudentId(Integer.parseInt(jsonObject.optString("studentId")));

			info.setDob(jsonObject.optString("dob"));

			info.setRollNo(jsonObject.optString("rollNo"));
			info.setName(jsonObject.optString("name"));

			System.out.println(jsonObject);

		}
		catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("*******************************************************************");
		return info;
	}

}
