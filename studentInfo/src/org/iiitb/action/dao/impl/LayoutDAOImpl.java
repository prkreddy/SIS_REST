/*package org.iiitb.action.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.iiitb.action.dao.LayoutDAO;
import org.iiitb.model.layout.AnnouncementsItem;
import org.iiitb.model.layout.NewsItem;

public class LayoutDAOImpl implements LayoutDAO
{
	private static final String ALL_NEWS_QUERY = "select * from news order by news_id desc;";

	private static final String ANNOUNCEMENTS_QUERY = "select a.announcement_id, a.name, a.details "
			+ "from announcement a, "
			+ "	announcement_interest ai, "
			+ "	interest i, "
			+ "	student_interest si, "
			+ "	student s "
			+ "where s.student_id=? "
			+ "	and si.student_id=s.student_id "
			+ "	and i.interest_id=si.interest_id "
			+ "	and ai.interest_id=i.interest_id "
			+ "	and a.announcement_id=ai.announcement_id "
			+ "order by a.announcement_id desc;";

	private static final String LAST_LOGGED_ON_QUERY = "select "
			+ "user.last_logged_on " + "from user " + "where user.user_id=?";

	private static final String UPDATE_LAST_LOGGED_ON = "update user "
			+ "set last_logged_on=NOW() " + "where user_id=?";

	@Override
	public List<NewsItem> getAllNews(Connection connection) throws SQLException
	{
		ResultSet rs = connection.prepareStatement(ALL_NEWS_QUERY)
				.executeQuery();
		List<NewsItem> ln = new ArrayList<NewsItem>();
		while (rs.next())
		{
			ln.add(new NewsItem(rs.getString(2), rs.getString(3)));
		}
		rs.close();
		return ln;
	}

	@Override
	public List<AnnouncementsItem> getAnnouncements(Connection connection,
			int userId) throws SQLException
	{
		PreparedStatement ps = connection.prepareStatement(ANNOUNCEMENTS_QUERY);
		ps.setInt(1, userId);
		ResultSet rs = ps.executeQuery();
		List<AnnouncementsItem> la = new ArrayList<AnnouncementsItem>();
		while (rs.next())
		{
			la.add(new AnnouncementsItem(rs.getString(2), rs.getString(3)));
		}
		rs.close();
		return la;
	}

	
	 * (non-Javadoc)
	 * 
	 * @see org.iiitb.action.dao.LayoutDAO#getLastLoggedOn(java.sql.Connection,
	 * int)
	 
	@Override
	public String getLastLoggedOn(Connection connection, int userId)
			throws SQLException
	{
		PreparedStatement ps = connection
				.prepareStatement(LAST_LOGGED_ON_QUERY);
		ps.setInt(1, userId);
		ResultSet rs = ps.executeQuery();

		String lastLoggedOn = "-";
		rs.next();
		if (rs.getDate("last_logged_on") != null)
		{
			lastLoggedOn = rs.getTimestamp("last_logged_on").toString();
			lastLoggedOn = lastLoggedOn.substring(0, lastLoggedOn.length() - 5);
			System.out.println("Last logged on : " + lastLoggedOn);
		}
		rs.close();
		return lastLoggedOn;
	}

	public void setLastLoggedOn(Connection connection, int userId)
			throws SQLException
	{
		PreparedStatement ps = connection
				.prepareStatement(UPDATE_LAST_LOGGED_ON);
		ps.setInt(1, userId);
		ps.executeUpdate();
	}

}
*/