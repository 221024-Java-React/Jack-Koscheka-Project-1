package models;

public class UserInfo
{
	private String email, password;
	
	// Construct
	
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
	
	// Set
	
	public void setEmail(String email) { this.email = email; }
	
	public void setPassword(String password) { this.password = password; }
	
	// Get
	
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
