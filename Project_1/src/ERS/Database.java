package ERS;

import java.util.HashMap;

public class Database
{
	private HashMap<Username, User> users;
	
	public User getUser(Username username)
	{
		return this.users.get(username);
	}
}
