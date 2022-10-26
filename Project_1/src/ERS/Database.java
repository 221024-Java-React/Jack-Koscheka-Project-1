package ERS;

import java.util.HashMap;

public class Database
{
	private HashMap<Username, User> users;
	
	public Database()
	{
		this.users = new HashMap<Username, User>();
	}
	
	public void register(Username username, Password password)
	{
		User user = this.users.get(username);
		
		if (user == null)
		{
			user = new User(password);
			users.put(username, user);
			
			System.out.println("User Successfully Created!");
		}
		else
			System.out.println("User Already Exists. Please Register A Unique Username.");
	}
	
	public User login(Username username, Password password)
	{
		User user = this.users.get(username);
		
		if (user == null)
		{
			System.out.println("Username [" + username.toString() + "] Does Not Exist."
					+ " Please Use A Registered Username Or Register If Account Has Not Yet Been Created");
			return null;
		}
		else if (user.getPassword().equals(password))
		{
			System.out.println("Logged Into [" + username.toString() + "] Successfully!");
			return user;
		}
		else
		{
			System.out.println("Password Was Incorrect. Please Try Again.");
			return null;
		}
	}
	
	
//	public User getUser(Username username)
//	{
//		return this.users.get(username);
//	}
}
