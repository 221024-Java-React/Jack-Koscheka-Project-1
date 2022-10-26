package ERS;

public class Password
{
	private String passwordString;
	
	public Password(String passwordString)
	{
		this.passwordString = passwordString;
	}
	
	public boolean equals(Password password)
	{
		if (password == null)
			return false;
		else
			return this.passwordString.equals(password.passwordString);
	}

}
