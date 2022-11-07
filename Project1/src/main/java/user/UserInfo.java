package user;

public class UserInfo
{
	String email, password;
	
	public UserInfo()
	{
		this.email = null;
		this.password = null;
	}
	
	public UserInfo(String email, String password)
	{
		this.email = email;
		this.password = password;
	}
	
	public String getEmail() { return this.email; }
	
	public String getPassword() { return this.password; }
	
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
	
	@Override
	public String toString()
	{
		return "{ email = " + this.email.toString() + ", password = " + this.password.toString() + "}";
	}
}
