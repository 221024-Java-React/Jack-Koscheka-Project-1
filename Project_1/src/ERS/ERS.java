package ERS;

public class ERS
{
	Database database;
	
	private void register(Username username, Password password)
	{
		User user = this.database.getUser(username);
		
		if (user == null)
		{
			// TODO add user to list
		}
		else
		{
			System.out.println("User Already Exists. Please Register A Unique Username.");
		}
	}
	
	private User login(Username username, Password password)
	{
		User user = this.database.getUser(username);
		
		if (user == null)
		{
			System.out.println("Username [" + username + "] Does Not Exist. Please Use A Registered Username Or "
					+ "Register If Account Has Not Yet Been Created");
			return null;
		}
		else if (user.getPassword().equals(password))
		{
			System.out.println("Successfully Logged In!");
			return user;
		}
		else
		{
			System.out.println("Password Was Incorrect. Please Try Again.");
			return null;
		}
	}
	
	public static void main(String args[])
	{
		ERS ers = new ERS();
		
		System.out.println("Hello World!");
	}
}
