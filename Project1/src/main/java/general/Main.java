package general;

import java.util.List;

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
		ers.submitTicket(user, new ReimbursementTicket(new Price("2048.01"), "Basic Request"));
		
		final List<ReimbursementTicket> tickets = ers.getTickets(user);
		
		System.out.println(tickets.toString());
		
		final List<ReimbursementTicket> filteredTickets = ers.getTickets(user, ReimbursementTicket.Status.APPROVED);
		
		System.out.println(filteredTickets.toString());
		
	}
}
