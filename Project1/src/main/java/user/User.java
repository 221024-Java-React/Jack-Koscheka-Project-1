package user;

import exceptions.UserNotManagerException;

public class User
{
	public enum Role { EMPLOYEE, MANAGER };
	
	int id;
	Role role;
	UserInfo userInfo;
	
	public User(UserInfo userInfo)
	{
		this.id = 0;
		this.role = Role.EMPLOYEE;
		this.userInfo = userInfo;
	}
	
	public int getID() { return this.id; }
	
	public Role getRole() { return this.role; }
	
	public UserInfo getUserInfo() { return this.userInfo; }
	
	public void setRole(User user, Role role) throws UserNotManagerException
	{
		if (this.role == Role.MANAGER)
			user.role = role;
		else
			throw new UserNotManagerException();
	}
	
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
