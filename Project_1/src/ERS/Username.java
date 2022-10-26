package ERS;

public class Username
{
	private String email;
	
	private boolean isValid(String email)
	{
		int index = email.indexOf('@');
		
		if (index < 0)
			return false;
		
		index = email.indexOf('.', index);
		
		return index > 0 && index < email.length();
	}
	
	public Username(String email)
	{
		if (isValid(email))
			this.email = email;
		else
			System.out.println("Email [" + email + "] Is Invalid. Please Enter A Valid Username.");
	}
	
}
