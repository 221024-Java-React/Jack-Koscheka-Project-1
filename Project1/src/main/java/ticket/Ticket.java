package ticket;

public abstract class Ticket
{
	public enum Status { DENIED, PENDING, APPROVED };
	
	public int id;
	public int userID;
	Status status;
	
	public Ticket()
	{
		this.id = 0;
		this.userID = 0;
		this.status = Status.PENDING;
	}
	
	public void setStatus(Status status)
	{
		this.status = status;
	}
	
	public Status getStatus() { return this.status; }
	
	@Override
	public String toString()
	{
		return "status =" + status.toString();
	}
}
