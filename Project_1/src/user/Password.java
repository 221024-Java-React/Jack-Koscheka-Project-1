package user;

public class Password
{
	private String passwordString;
	
	public Password(String passwordString)
	{
		this.passwordString = passwordString;
	}
	
	@Override
	public boolean equals(Object object)
	{
		if (object == null || !(object instanceof Password))
			return false;
		
		Password password = (Password) object;
		
		return this.passwordString.equals(password.passwordString);
	}

}
