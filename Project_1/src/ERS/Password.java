package ERS;

public class Password
{
	private String password;
	
	public Password(String password)
	{
		this.password = password;
	}
	
	public boolean equals(Password password)
	{
		return this.password.equals(password);
	}

}
