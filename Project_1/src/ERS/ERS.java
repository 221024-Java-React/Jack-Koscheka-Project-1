package ERS;

public class ERS
{
	Database database;
	
	ERS()
	{
		this.database = new Database();
	}
	
	private void register(Username username, Password password)
	{
		this.database.register(username, password);
	}
	
	private User login(Username username, Password password)
	{
		return this.database.login(username, password);
	}
	
	public static void main(String args[])
	{
		ERS ers = new ERS();
		
		Email email = new Email("test@gmail.com");
		Username username = new Username(email);
		Password password = new Password("password");
		
		ers.login(username, password);
		
		ers.register(username, password);
		
		ers.login(username, null);
		ers.login(username, password);
	}
}
