package user;

public class User
{
	public enum Role { EMPLOYEE, MANAGER };
	
	private int userID;
	private Role role;
	private UserInfo userInfo;
	
	// Construct
	
	public User() { this(0, new UserInfo()); }
	
	public User(int userID, UserInfo userInfo)
	{
		this.userID = userID;
		this.role = Role.EMPLOYEE;
		this.userInfo = userInfo;
	}
	
	// Set
	
	public void setUserID(int userID) { this.userID = userID; }
	
	public void setRole(Role role) { this.role = role; }
	
	public void setUserInfo(UserInfo userInfo) { this.userInfo = userInfo; }
	
	// Get
	
	public int getUserID() { return this.userID; }
	
	public Role getRole() { return this.role; }
	
	public UserInfo getUserInfo() { return this.userInfo; }
	
	@Override
	public boolean equals(Object object)
	{
		if (object == null || !(object instanceof User))
			return false;
		
		User user = (User) object;
		
		if (this.role == user.role && this.userInfo.equals(user.userInfo))
			return true;
		else
			return false;
	}
	
}
