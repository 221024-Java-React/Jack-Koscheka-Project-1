package manager;

import java.util.Optional;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import ticket.Ticket;
import user.User;
import user.UserController;

public class ManagerController
{	
	private static void getTickets(Context context, Optional<Ticket.Status> status)
	{
		UserController.getTickets(context, status, User.Role.MANAGER);
	}
	
	private static void updateTicket(Context context, Ticket.Status status)
	{
		UserController.updateTicket(context, status, User.Role.MANAGER);
	}
	
	public static Handler tickets = (context) -> {
		
		getTickets(context, Optional.empty());
	};
	
	public static Handler pendingTickets = (context) -> {
		
		getTickets(context, Optional.of(Ticket.Status.PENDING));
	};
	
	public static Handler deniedTickets = (context) -> {
		
		getTickets(context, Optional.of(Ticket.Status.DENIED));
	};
	
	public static Handler approvedTickets = (context) -> {
		
		getTickets(context, Optional.of(Ticket.Status.APPROVED));
	};
	
	public static Handler denyTicket = (context) -> {
		
		updateTicket(context, Ticket.Status.DENIED);
	};
	
	public static Handler approveTicket = (context) -> {
		
		updateTicket(context, Ticket.Status.APPROVED);
	};

}
