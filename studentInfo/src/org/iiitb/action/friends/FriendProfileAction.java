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
import org.iiitb.action.dao.LayoutDAO;
import org.iiitb.action.dao.StudentDAO;
import org.iiitb.action.dao.impl.LayoutDAOImpl;
import org.iiitb.action.dao.impl.StudentDAOImpl;
import org.iiitb.model.StudentInfo;
import org.iiitb.model.User;
import org.iiitb.model.layout.AnnouncementsItem;
import org.iiitb.model.layout.NewsItem;
import org.iiitb.util.ConnectionPool;
import org.iiitb.util.Constants;
import org.iiitb.util.RestClient;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author prashanth
 * 
 */
public class FriendProfileAction extends ActionSupport implements SessionAware
{
	private String friendNo;

	private String rollNo;

	private List<NewsItem> allNews;
	private List<AnnouncementsItem> announcements;
	private LayoutDAO layoutDAO = new LayoutDAOImpl();
	private String lastLoggedOn;

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

	public String getRollNo()
	{
		return rollNo;
	}

	public void setRollNo(String rollNo)
	{
		this.rollNo = rollNo;
	}

	StudentInfo myProfile;

	public StudentInfo getMyProfile()
	{
		return myProfile;
	}

	public void setMyProfile(StudentInfo myProfile)
	{
		this.myProfile = myProfile;
	}

	List<StudentInfo> students;

	public List<StudentInfo> getStudents()
	{
		return students;
	}

	public void setStudents(List<StudentInfo> students)
	{
		this.students = students;
	}

	String isFriend;

	public String getIsFriend()
	{
		return isFriend;
	}

	public void setIsFriend(String isFriend)
	{
		this.isFriend = isFriend;
	}

	public String getFriendNo()
	{
		return friendNo;
	}

	public void setFriendNo(String friendNo)
	{
		this.friendNo = friendNo;
	}

	StudentInfo friendProfile;

	public StudentInfo getFriendProfile()
	{
		return friendProfile;
	}

	public void setFriendProfile(StudentInfo friendProfile)
	{
		this.friendProfile = friendProfile;
	}

	public String execute() throws SQLException
	{
		String result = SUCCESS;

		RestClient restClient = new RestClient();
		// rest call start
		String output = restClient.callGetService("friends/studentrollno/" + friendNo);
		// rest call end

		StudentInfo studentInfo = getStudentInfo(output);

		// StudentInfo studentInfo = studentDao.getStudentByRollNo(friendNo);

		User user = (User) session.get("user");

		Connection connection = ConnectionPool.getConnection();
		allNews = layoutDAO.getAllNews(connection);
		announcements = layoutDAO.getAnnouncements(connection, Integer.parseInt(user.getUserId()));
		setLastLoggedOn((String) this.session.get(Constants.LAST_LOGGED_ON));
		ConnectionPool.freeConnection(connection);

		if (studentInfo == null)
		{
			result = ERROR;
		}
		else
		{
			setFriendProfile(studentInfo);

			if (studentInfo.getStudentId() == Integer.parseInt(user.getUserId()))
			{
				System.out.println("equals");
				setIsFriend(Constants.FRIEND);
			}
			else
			{
				System.out.println("notequals");
				// rest call start
				output = restClient.callGetService("friends/relation;" + "rollNo=" + user.getUserId() + ";friendNo=" + friendNo);
				// rest call end

				setIsFriend(getJsonData(output, "friendsStatus"));
				// setIsFriend(studentDao.findRelationShip(user.getUserId(),
				// friendNo));
			}
		}
		return result;
	}

	public String update()
	{

		RestClient restClient = new RestClient();
		User user = (User) session.get("user");
		// rest call start
		String output = restClient.callPostService("{\"rollNo\":\"" + user.getUserId() + "\",\"friendNo\":\"" + friendNo + "\"}", "friends/add");
		// rest call end

		System.out.println(output);
		// rest call start
		output = restClient.callGetService("friends/studentid/" + user.getUserId());
		// rest call end
		setMyProfile(getStudentInfo(output));

		// rest call start
		setStudents(getListOfStudents(restClient.callGetService("friends/studentfriends/" + user.getUserId())));
		// rest call end
		return SUCCESS;
	}

	Map<String, Object> session;

	public Map<String, Object> getSession()
	{
		return session;
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

	String getJsonData(String jsonstring, String attribute)
	{

		System.out.println("*******************************************************************");

		String data = "";
		try
		{
			JSONObject jsonObject = new JSONObject(jsonstring);

			data = jsonObject.optString(attribute);

			System.out.println(data);

		}
		catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("*******************************************************************");
		return data;

	}
}
