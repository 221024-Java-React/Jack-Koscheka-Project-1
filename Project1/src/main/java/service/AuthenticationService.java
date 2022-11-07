package service;

import user.*;

public class AuthenticationService
{
	public static boolean register(UserInfo userInfo)
	{
		User user = UserDAO.getUser(userInfo);
		
		if (user == null)
		{
			user = new User(userInfo);
			
			UserDAO.addUser(user);
			
			return true;
		}
		else
			return false;
	}
	
	public static User login(UserInfo userInfo)
	{
		return UserDAO.getUser(userInfo);
	}
}
