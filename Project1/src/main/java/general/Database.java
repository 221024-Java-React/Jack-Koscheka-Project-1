package general;

import java.util.HashMap;
import java.util.List;

import reimbursement_ticket.ReimbursementTicket;
import reimbursement_ticket.ReimbursementTicketList;

import user.Password;
import user.User;
import user.Username;

public class Database
{
	private HashMap<Username, User> users;
	private ReimbursementTicketList tickets;
	
	public Database()
	{
		this.users = new HashMap<Username, User>();
		this.tickets = new ReimbursementTicketList();
	}
	
	public void register(Username username, Password password)
	{
		User user = this.users.get(username);
		
		if (user == null)
		{
			user = new User(password);
			users.put(username, user);
			
			System.out.println("User Successfully Created!");
		}
		else
			System.out.println("User Already Exists. Please Register A Unique Username.");
	}
	
	public User login(Username username, Password password)
	{
		User user = this.users.get(username);
		
		if (user == null)
		{
			System.out.println("Username \"" + username.toString() + "\" Does Not Exist."
					+ " Please Use A Registered Username Or Register If Account Has Not Yet Been Created");
			return null;
		}
		else if (user.getPassword().equals(password))
		{
			System.out.println("Logged Into \"" + username.toString() + "\" Successfully!");
			return user;
		}
		else
		{
			System.out.println("Password Was Incorrect. Please Try Again.");
			return null;
		}
	}
	
	public void submitTicket(User user, ReimbursementTicket ticket)
	{
		if (user.getRole() == User.Role.EMPLOYEE)
			this.tickets.submitTicket(user, ticket);
		else
			System.out.println("User Is Not An Employee. Only Employees Are Able To Submit Tickets");
	}
	
	public final List<ReimbursementTicket> getTickets(User user)
	{
		if (user.getRole() == User.Role.EMPLOYEE)
			return this.tickets.getTickets(user);
		else
		{
			System.out.println("User Is Not An Employee. Only Employees Are Able To Get Tickets");
			
			return null;
		}
	}
	
	public final List<ReimbursementTicket> getTickets(User user, ReimbursementTicket.Status filterStatus)
	{
		if (user.getRole() == User.Role.EMPLOYEE)
			return this.tickets.getTickets(user, filterStatus);
		else
		{
			System.out.println("User Is Not An Employee. Only Employees Are Able To Get Tickets");
			
			return null;
		}
	}
	
	public ReimbursementTicket viewNextTicket(User user)
	{
		if (user.getRole() == User.Role.MANAGER)
			return this.tickets.viewNextTicket();
		else
		{
			System.out.println("User Is Not An Manager. Only Managers Are Able To View Tickets");
			
			return null;
		}
	}
	
	public void approveNextTicket(User user)
	{
		if (user.getRole() == User.Role.MANAGER)
			this.tickets.approveNextTicket();
		else
			System.out.println("User Is Not An Manager. Only Managers Are Able To Approve Tickets");
	}
	
	public void denyNextTicket(User user)
	{
		if (user.getRole() == User.Role.MANAGER)
			this.tickets.denyNextTicket();
		else
			System.out.println("User Is Not An Manager. Only Managers Are Able To Deny Tickets");
	}
}
