package ticket;

public class Price
{
	long dollars;
	byte cents;
	
	private int setDollars(String amount)
	{
		final int length = amount.length();
		
		int dollarStartIndex = 0;
		
		while (dollarStartIndex < length && !Character.isDigit(amount.charAt(dollarStartIndex)))
			dollarStartIndex++;
		
		if (dollarStartIndex >= length)
		{
			System.err.println("Invalid Amount. Please Enter A Valid Price");
			
			this.dollars = 0;
			this.cents = 0;
			
			return -1;
		}
		else
		{
			int dollarEndIndex = dollarStartIndex + 1;
			
			while (dollarEndIndex < length && Character.isDigit(amount.charAt(dollarEndIndex)))
				dollarEndIndex++;
			
			this.dollars = Long.parseLong(amount.substring(dollarStartIndex, dollarEndIndex));
			
			return dollarEndIndex + 1;
		}
	}
	
	private void setCents(String amount, int centStartIndex)
	{
		int length = amount.length();
		
		while (centStartIndex < length && !Character.isDigit(amount.charAt(centStartIndex)))
			centStartIndex++;
		
		if (centStartIndex >= length)
			this.cents = 0;
		else
		{
			length = length <= (centStartIndex + 2) ? length  : (centStartIndex + 2);
			
			int centEndIndex = centStartIndex + 1;
			
			while (centEndIndex < length && Character.isDigit(amount.charAt(centEndIndex)))
				centEndIndex++;
			
			this.cents = Byte.parseByte(amount.substring(centStartIndex, centEndIndex));
		}
	}
	
	public Price(String amount) { set(amount); }
	
	public Price(long dollars, byte cents)
	{
		this.dollars = dollars;
		this.cents = cents;
	}
	
	public Price() { this((long) 0, (byte) 0); }
	
	public void set(String amount)
	{
		int index = setDollars(amount);
		
		if (index < 0)
			return;
		else
			setCents(amount, index);
	}
	
	public long getDollars() { return this.dollars; }
	
	public byte getCents() { return this.cents; }
	
	public double getAmount() { return ((double) this.dollars) + (this.cents * (1.0 / 100.0)); }
	
	@Override
	public String toString() { return "$" + this.dollars + "." + this.cents; }
}
