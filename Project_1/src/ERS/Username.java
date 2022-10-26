package ERS;

public class Username
{
	private Email email;
	
	public Username(Email email)
	{
		this.email = email;
	}
	
	@Override
	public String toString()
	{
		return this.email.toString();
	}
	
}
