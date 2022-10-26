package ERS;

public class User
{
	private enum Role { EMPLOYEE, MANAGER };
	
	private Role role;
	private Password password;
	
	public User(Password password)
	{
		this.role = Role.EMPLOYEE;
		this.password = password;
	}
	
	public Password getPassword()
	{
		return this.password;
	}
	
}
