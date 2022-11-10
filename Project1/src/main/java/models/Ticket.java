package models;

public abstract class Ticket
{
	public enum Status { DENIED, PENDING, APPROVED };
	
	private int ticketID;
	private int userID;
	private Status status;
	
	// Construct
	
	public Ticket()
	{
		this.ticketID = 0;
		this.userID = 0;
		this.status = Status.PENDING;
	}
	
	// Set
	
	public void setTicketID(int ticketID) { this.ticketID = ticketID; }
	
	public void setUserID(int userID) { this.userID = userID; }
	
	public void setStatus(Status status) { this.status = status; }
	
	// Get
	
	public int getTicketID() { return this.ticketID; }
	
	public int getUserID() { return this.userID; }
	
	public Status getStatus() { return this.status; }
	
	@Override
	public String toString()
	{
		return "status =" + status.toString();
	}
}
