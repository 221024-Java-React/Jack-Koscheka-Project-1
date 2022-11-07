package ticket;

public class ReimbursementTicket extends Ticket
{
	public final Price price;
	public final String description;
	
	public ReimbursementTicket()
	{
		super();
		
		this.price = new Price();
		this.description = "";
	}
	
	public ReimbursementTicket(Price price, String description)
	{
		super();
		
		this.price = price;
		this.description = description;
	}
	
	@Override
	public String toString()
	{
		return "{ price = " + this.price.toString() + ", description = " + this.description.toString()
				+ ", " + super.toString() + " }";
	}
}
