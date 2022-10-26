package user;

public class Email
{
	private String emailString;
	
	private boolean isValid(String emailString)
	{
		int index = emailString.indexOf('@');
		
		if (index < 1)
			return false;
		
		index = emailString.indexOf('.', index);
		
		return index > 2 && index < emailString.length();
	}
	
	public Email(String emailString)
	{
		if (isValid(emailString))
			this.emailString = emailString;
		else
			System.out.println("Email \"" + emailString.toString() + "\" Is Invalid. Please Enter A Valid Email.");
	}
	
	@Override
	public String toString()
	{
		return this.emailString.toString();
	}
}
