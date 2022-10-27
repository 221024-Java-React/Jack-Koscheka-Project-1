package user;

public class User
{
	public enum Role { EMPLOYEE, MANAGER };
	
	private Role role;
	private Password password;
	
	public User(Password password)
	{
		this.role = Role.EMPLOYEE;
		this.password = password;
	}
	
	public Role getRole() { return this.role; }
	
	public Password getPassword() { return this.password; }
	
	
	
}
