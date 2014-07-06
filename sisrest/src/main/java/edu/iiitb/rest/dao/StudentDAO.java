package edu.iiitb.rest.dao;

import java.sql.SQLException;
import java.util.List;

import org.codehaus.jettison.json.JSONException;

import edu.iiitb.rest.model.StudentInfo;

/**
 * @author prashanth
 * 
 */
public interface StudentDAO
{

	public StudentInfo getStudentByRollNo(String rollNo) throws SQLException; 

	public StudentInfo getStudentByUserId(String rollNo) throws SQLException; 

	public List<StudentInfo> getFriends(String rollno) throws SQLException;

	public String findRelationShip(String rollNo, String friendNo) throws SQLException;

	public boolean addFriend(String rollNo, String friendNo) throws SQLException;
	
	public List<String> getStudents();
}
