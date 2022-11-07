package user;

public class UserInfo
{
	Email email;
	Password password;
	
	public UserInfo(Email email, Password password)
	{
		this.email = email;
		this.password = password;
	}
	
	public Email getEmail() { return this.email; }
	
	public Password getPassword() { return this.password; }
	
	@Override
	public boolean equals(Object object)
	{
		if (object == null || !(object instanceof UserInfo))
			return false;
		
		UserInfo userInfo = (UserInfo) object;
		
		if (this.email.equals(userInfo.email) && this.password.equals(userInfo.password))
			return true;
		else
			return false;
	}
}
