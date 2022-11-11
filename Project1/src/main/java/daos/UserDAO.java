package daos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.User;
import models.UserInfo;
import utility.JDBCConnection;

public class UserDAO
{
	private static UserDAO userDAO;
	private JDBCConnection connectionUtil;
	
	private UserDAO()
	{
		connectionUtil = JDBCConnection.getInstance();
	}
	
	private static UserDAO getInstance()
	{
		if (userDAO == null)
			userDAO = new UserDAO();
		
		return userDAO;
	}
	
	private static int getNextID()
	{
		Connection connection = getInstance().connectionUtil.getConnection();
		
		int id = 0;
		
		String sql = "SELECT MAX(userID) FROM users";
		
		try
		{
			Statement statement = connection.createStatement();
			
			ResultSet result = statement.executeQuery(sql);
			
			if (result.next())
				id = result.getInt(1) + 1;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id;
	}
	
	private static User getUserResult(ResultSet result) throws SQLException
	{
		UserInfo userInfo = new UserInfo(result.getString(3), result.getString(4));
		User user = new User(result.getInt(1), userInfo);
		user.setRole(User.Role.values()[result.getInt(2)]);
		
		return user;
	}
	
	private static User getUserSQL(String sql)
	{
		Connection connection = getInstance().connectionUtil.getConnection();
		
		try
		{
			Statement statement = connection.createStatement();
			
			ResultSet result = statement.executeQuery(sql);
			
			if (result.next())
				return getUserResult(result);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void addUser(UserInfo userInfo)
	{
		User user = new User(getNextID(), userInfo);
		
		Connection connection = getInstance().connectionUtil.getConnection();
		
		String sql = "INSERT INTO users (userID, role, email, password) VALUES " + "(" + user.getUserID() + ", "
				+ user.getRole().ordinal() + ", '" + userInfo.getEmail() + "', '" + userInfo.getPassword() + "')";
		
		try
		{
			Statement statement = connection.createStatement();
			
			statement.execute(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static User getUser(int userID)
	{
		return getUserSQL("SELECT * FROM users WHERE userID = " + userID);
	}
	
	public static User getUser(String email)
	{
		return getUserSQL("SELECT * FROM users WHERE email = '" + email + "'");
	}
	
	public static User getUser(UserInfo userInfo)
	{
		return getUserSQL("SELECT * FROM users WHERE email = '" + userInfo.getEmail()
				+ "' AND password = '" + userInfo.getPassword() + "'");
	}
	
	public static List<User> getAllUsers()
	{
		List<User> users = new ArrayList<User>();
		
		Connection connection = getInstance().connectionUtil.getConnection();
		
		String sql = "SELECT * FROM users";
		
		try
		{
			Statement statement = connection.createStatement();
			
			ResultSet result = statement.executeQuery(sql);
			
			while (result.next())
				users.add(getUserResult(result));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return users;
	}
	
	public static boolean deleteUser(int userID)
	{
		Connection connection = getInstance().connectionUtil.getConnection();
		
		String sql = "DELETE FROM users WHERE userID = " + userID;
		
		try
		{
			Statement statement = connection.createStatement();
			
			statement.execute(sql);
			
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static void updateUser(User user)
	{
		Connection connection = getInstance().connectionUtil.getConnection();
		
		UserInfo userInfo = user.getUserInfo();
		
		String sql;
		
		if (user.getUserID() > 0)
			sql = "UPDATE users SET role = " + user.getRole().ordinal() + " WHERE userID = " + user.getUserID();
		else
			sql = "UPDATE users SET role = " + user.getRole().ordinal() + " WHERE email = '" + userInfo.getEmail() + "'";
		
		try
		{
			Statement statement = connection.createStatement();
			
			statement.execute(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
