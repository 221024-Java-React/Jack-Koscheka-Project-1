package services;

import java.util.List;
import java.util.Optional;

import daos.TicketDAO;
import daos.UserDAO;
import models.ReimbursementTicket;
import models.Ticket;
import models.User;
import models.UserInfo;

public class UserService
{
	public static boolean register(UserInfo userInfo)
	{
		if (UserDAO.getUser(userInfo.getEmail()) == null)
		{
			UserDAO.addUser(userInfo);
			
			return true;
		}
		else
			return false;
	}
	
	public static User login(UserInfo userInfo)
	{
		return UserDAO.getUser(userInfo);
	}
	
	public static User session(int userID)
	{
		return UserDAO.getUser(userID);
	}
	
	public static boolean delete(int userID)
	{
		return UserDAO.deleteUser(userID);
	}
	
	public static List<Ticket> tickets(int userID, Optional<Ticket.Status> status,
			User.Role authorization) throws NullPointerException
	{
		User user = UserDAO.getUser(userID);
		
		if (user.getRole() == authorization)
		{
			switch (authorization)
			{
			case EMPLOYEE:
				return TicketDAO.getAllTickets(Optional.of(userID), status);
			case MANAGER:
				return TicketDAO.getAllTickets(Optional.empty(), status);
			default:
				return null;
			}
		}
		else
			return null;
	}
	
	public static boolean addTicket(int userID, ReimbursementTicket ticket, User.Role authorization)
			throws NullPointerException
	{
		User user = UserDAO.getUser(userID);
		
		if (user.getRole() == authorization && ticket.getDescription() != null && ticket.getDescription() != ""
				&& ticket.getPrice().getAmount() > 0.0)
		{
			ticket.setUserID(userID);
			
			TicketDAO.addTicket(ticket);
			
			return true;
		}
		else
			return false;
	}
	
	public static boolean deleteTicket(int userID, ReimbursementTicket ticket, User.Role authorization)
			throws NullPointerException
	{
		User user = UserDAO.getUser(userID);
		
		if (user.getRole() == authorization)
		{
			ticket.setUserID(userID);
			
			TicketDAO.deleteTicket(ticket);
			
			return true;
		}
		else
			return false;
	}
	
	public static boolean updateTicket(int userID, ReimbursementTicket ticket, Ticket.Status status,
			User.Role authorization) throws NullPointerException
	{
		User user = UserDAO.getUser(userID);
		
		if (user.getRole() == authorization && ticket.getStatus() == Ticket.Status.PENDING)
		{
			ticket.setStatus(status);
			
			TicketDAO.updateTicket(ticket);
			
			return true;
		}
		else
			return false;
	}
	
	public static boolean updateUser(int userID, User user, User.Role role,
			User.Role authorization) throws NullPointerException
	{
		User currentUser = UserDAO.getUser(userID);
		
		if (currentUser.getRole() == authorization)
		{
			user.setRole(role);
			
			UserDAO.updateUser(user);
			
			return true;
		}
		else
			return false;
	}
}
