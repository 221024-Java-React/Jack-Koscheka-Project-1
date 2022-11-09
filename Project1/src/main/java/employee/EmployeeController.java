package employee;

import java.util.Optional;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import ticket.Ticket;
import user.User;
import user.UserController;

public class EmployeeController
{
	private static void getTickets(Context context, Optional<Ticket.Status> status)
	{
		UserController.getTickets(context, status, User.Role.EMPLOYEE);
	}
	
	private static void addTicket(Context context)
	{
		UserController.addTicket(context, User.Role.EMPLOYEE);
	}
	
	private static void deleteTicket(Context context)
	{
		UserController.deleteTicket(context, User.Role.EMPLOYEE);
	}
	
	public static Handler tickets = (context) -> {
		
		EmployeeController.getTickets(context, Optional.empty());
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
	
	public static Handler addTicket = (context) -> {
		
		addTicket(context);
	};
	
	public static Handler deleteTicket = (context) -> {
		
		deleteTicket(context);
	};

}
