package user;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
	
	public static void addUser(User user)
	{
		Connection connection = getInstance().connectionUtil.getConnection();
		
		UserInfo userInfo = user.getUserInfo();
		
		String sql = "INSERT INTO users (role, email, password) VALUES " + "(" + user.role.ordinal()
			+ ", '" + userInfo.email + "', '" + userInfo.password + "')";
		
		try
		{
			Statement statement = connection.createStatement();
			
			statement.execute(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static User getUser(UserInfo userInfo)
	{
		User user = null;
		
		Connection connection = getInstance().connectionUtil.getConnection();
		
		String sql = "SELECT * FROM users WHERE email = " + userInfo.email.toString()
				+ " AND password = " + userInfo.password;
		
		try
		{
			Statement statement = connection.createStatement();
			
			ResultSet result = statement.executeQuery(sql);
			
			if (result.next())
			{
				// IMPORTANT: JDBC indexes from 1 NOT 0
				
				user = new User(userInfo);
				
				user.id = result.getInt(1);
				user.role = User.Role.values()[result.getInt(2)];
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
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
			{
				// IMPORTANT: JDBC indexes from 1 NOT 0
				
				UserInfo userInfo = new UserInfo(result.getString(3), result.getString(4));
				User user = new User(userInfo);
				
				user.id = result.getInt(1);
				user.role = User.Role.values()[result.getInt(2)];
				
				users.add(user);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return users;
	}
	
	public static void deleteUser(UserInfo userInfo)
	{
		Connection connection = getInstance().connectionUtil.getConnection();
		
		String sql = "DELETE FROM people WHERE email = " + userInfo.email
				+ " AND password = " + userInfo.password;
		
		try
		{
			Statement statement = connection.createStatement();
			
			statement.execute(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateUser(User user)
	{
		Connection connection = getInstance().connectionUtil.getConnection();
		
		UserInfo userInfo = user.userInfo;
		
		String sql = "UPDATE users SET role = " + user.role.ordinal() + ", email = '" + userInfo.email
				+ "', password = '" + userInfo.password + "' WHERE id = " + user.id;
		
		try
		{
			Statement statement = connection.createStatement();
			
			statement.execute(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
