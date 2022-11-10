package controllers;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import models.ReimbursementTicket;
import models.Ticket;
import models.User;
import models.UserInfo;
import services.UserService;
import utility.ObjectConverter;

public class UserController
{
	private static boolean unknownSession(Context context)
	{
		if (context.sessionAttribute("user") == null)
		{
			context.status(401);
			context.result("Please login via \'/user/login\' to gain access this request.");
			
			return true;
		}
		else
			return false;
	}
	
	private static int getUserID(Context context)
	{
		return (Integer) context.req().getSession().getAttribute("user");
	}
	
	public static void getTickets(Context context, Optional<Ticket.Status> status, User.Role authorization)
	{
		if (UserController.unknownSession(context))
			return;
		
		try {
			
			List<Ticket> tickets = UserService.tickets(getUserID(context), status, authorization);
			
			context.status(200);
			context.result(ObjectConverter.write(tickets));
			
		} catch (Exception exception) {
			context.status(401);
			context.result("Unauthorized to access tickets.");
		}
	}
	
	public static void addTicket(Context context, User.Role authorization)
	{
		if (UserController.unknownSession(context))
			return;
		
		try {
			
			if (UserService.addTicket(getUserID(context), ObjectConverter.read(context.body(),
					ReimbursementTicket.class), authorization))
			{
				context.status(200);
				context.result("Successfully added ticket!");
			}
			else
				throw new Exception();
			
		} catch (Exception exception) {
			context.status(401);
			context.result("Unauthorized to add ticket.");
		}
	}
	
	public static void deleteTicket(Context context, User.Role authorization)
	{
		if (UserController.unknownSession(context))
			return;
		
		try {
			
			if (UserService.deleteTicket(getUserID(context), ObjectConverter.read(context.body(),
					ReimbursementTicket.class), authorization))
			{
				context.status(200);
				context.result("Successfully deleted ticket!");
			}
			else
				throw new Exception();
			
		} catch (Exception exception) {
			context.status(401);
			context.result("Unauthorized to delete ticket.");
		}
	}
	
	public static void updateTicket(Context context, Ticket.Status status, User.Role authorization)
	{
		if (UserController.unknownSession(context))
			return;
		
		try {
			
			if (UserService.updateTicket(getUserID(context), ObjectConverter.read(context.body(),
					ReimbursementTicket.class), status, authorization))
			{
				context.status(200);
				context.result("Successfully updated ticket status!");
			}
			else
				throw new Exception();
			
		} catch (Exception exception) {
			context.status(401);
			context.result("Unauthorized to update the specified ticket.");
		}
	}
	
	public static void updateUser(Context context, User.Role role, User.Role authorization)
	{
		if (UserController.unknownSession(context))
			return;
		
		User user = null;
		
		boolean nullUser = false;
		
		try {
			user = ObjectConverter.read(context.body(), User.class);
		} catch (JsonProcessingException exception) {
			nullUser = true;
		}
		
		if (nullUser)
		{
			try {
				UserInfo userInfo = ObjectConverter.read(context.body(), UserInfo.class);
				user = new User(0, userInfo);
				nullUser = false;
			} catch (JsonProcessingException e) {}
		}
		
		try {
			
			if (UserService.updateUser(getUserID(context), user, role, authorization))
			{
				context.status(200);
				context.result("Successfully updated user role!");
			}
			else
				throw new Exception();
			
		} catch (Exception exception) {
			context.status(401);
			context.result("Unauthorized to update the specified user.");
		}
	}
	
	public static Handler register = (context) -> {
		
		try {
			
			if (UserService.register(ObjectConverter.read(context.body(), UserInfo.class)))
			{
				context.status(201);
				context.result("Successfully registered account!");
			}
			else
			{
				context.status(406);
				context.result("Email is already in use for an existing user.");
			}
			
		} catch (Exception exception) {
			context.status(400);
			context.result("Invalid register request.");
		}
	};
	
	public static Handler login = (context) -> {
		
		try {
			
			User user = UserService.login(ObjectConverter.read(context.body(), UserInfo.class));
			
			if (user != null)
			{
				context.req().getSession().setAttribute("user", user.getUserID());
				context.status(200);
				context.result("Successfully logged into account!");
			}
			else
			{
				context.status(404);
				context.result("No users have the specified email/password combination.");
			}
		} catch (Exception exception) {
			context.status(400);
			context.result("Invalid login request.");
		}
	};
	
	public static Handler session = (context) -> {
		
		if (unknownSession(context))
			return;
		
		try {
			
			User user = UserService.session(getUserID(context));
			
			context.status(200);
			context.result(ObjectConverter.write(user));
			
		} catch (Exception exception) {
			context.status(400);
			context.result("Unable to find data for the user for the current session.");
		}
	};
	
	public static Handler logout = (context) -> {
		
		context.req().getSession().invalidate();
		context.status(200);
		context.result("User was successfully logged out.");
	};
	
	public static Handler delete = (context) -> {
		
		if (unknownSession(context))
			return;
		
		try {
			
			if (UserService.delete(getUserID(context)))
			{
				context.req().getSession().invalidate();
				context.status(200);
				context.result("Successfully deleted user.");
			}
			else
				throw new Exception();
			
		} catch (Exception exception) {
			context.status(400);
			context.result("Unable to find data for the user for the current session.");
		}
	};

}
