package ticket;

public abstract class Ticket
{
	public enum Status { DENIED, PENDING, APPROVED };
	
	int id;
	int userID;
	Status status;
	
	public Ticket()
	{
		this.id = 0;
		this.userID = 0;
		this.status = Status.PENDING;
	}
	
	public void setStatus(Status status)
	{
		switch (status)
		{
		case DENIED: case APPROVED:
			this.status = status;
			break;
		default:
			System.err.println("Unable To Set Status To " + status.toString());
			break;
		}
	}
	
	public Status getStatus() { return this.status; }
	
	@Override
	public String toString()
	{
		return "status =" + status.toString();
	}
}
