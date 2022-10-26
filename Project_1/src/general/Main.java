package general;

import reimbursement_ticket.ReimbursementTicket;
import user.Email;
import user.Password;
import user.User;
import user.Username;

public class Main
{	
	public static void main(String args[])
	{
		Database ers = new Database();
		
		Email email = new Email("test@gmail.com");
		Username username = new Username(email);
		Password password = new Password("password");
		
		ers.register(username, password);

		User user = ers.login(username, password);
		Price price = new Price("120.54");
		String description = "Hello World";
		
		ReimbursementTicket ticket = new ReimbursementTicket(price, description);
		
		ers.submitTicket(user, ticket);
		
		
		
		
		
	}
}
