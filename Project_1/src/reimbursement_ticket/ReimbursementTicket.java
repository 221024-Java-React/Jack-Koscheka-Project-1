package reimbursement_ticket;

import general.Price;

public class ReimbursementTicket
{
	public enum Status { DENIED, PENDING, APPROVED };
	
	public final Price price;
	public final String description;
	private Status status;
	
	public ReimbursementTicket(Price price, String description)
	{
		this.price = price;
		this.description = description;
		this.status = Status.PENDING;
	}
	
	void setStatus(Status status)
	{
		switch (status)
		{
		case DENIED: case APPROVED:
			this.status = status;
			break;
		default:
			System.err.println("Unable To Set Status To " + status.name().toString());
			break;
		}
	}
	
	public Status getStatus() { return this.status; }
}
