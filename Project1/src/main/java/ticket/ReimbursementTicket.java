package ticket;

public class ReimbursementTicket extends Ticket
{
	private Price price;
	private String description;
	
	// Construct
	
	public ReimbursementTicket() { this(new Price(), ""); }
	
	public ReimbursementTicket(Price price, String description)
	{
		super();
		
		this.price = price;
		this.description = description;
	}
	
	// Set
	
	public void setPrice(Price price) { this.price = price; }
	
	public void setDescription(String description) { this.description = description; }
	
	
	// Get
	
	public Price getPrice() { return this.price; }
	
	public String getDescription() { return this.description; }
	
	@Override
	public String toString()
	{
		return "{ price = " + this.price.toString() + ", description = " + this.description.toString()
				+ ", " + super.toString() + " }";
	}
}
