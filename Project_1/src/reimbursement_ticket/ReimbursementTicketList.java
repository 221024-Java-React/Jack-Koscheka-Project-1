package reimbursement_ticket;

import java.util.HashMap;
import java.util.Queue;
import java.util.List;
import java.util.LinkedList;

import user.User;

public class ReimbursementTicketList
{
	private Queue<ReimbursementTicket> pendingTickets;
	private HashMap<User, List<ReimbursementTicket>> userTickets;
	
	private List<ReimbursementTicket> newReimbursementTicketList()
	{
		return new LinkedList<ReimbursementTicket>();
	}
	
	private void updateNextTicket(ReimbursementTicket.Status status)
	{
		ReimbursementTicket reimbursementTicket = this.pendingTickets.poll();
		
		if (reimbursementTicket == null)
			System.err.println("Ticket Cannot Be " + status.name().toString()
					+ " Because There Are No Pending Objects");
		else
			reimbursementTicket.setStatus(status);
	}
	
	public ReimbursementTicketList()
	{
		this.pendingTickets = new LinkedList<ReimbursementTicket>();
		this.userTickets = new HashMap<User, List<ReimbursementTicket>>();
	}
	
	public void submitTicket(User user, ReimbursementTicket ticket)
	{
		List<ReimbursementTicket> userList = this.userTickets.get(user);
		
		if (userList == null)
		{
			userList = newReimbursementTicketList();
			
			this.userTickets.put(user, userList);
		}
		
		userList.add(ticket);
		
		this.pendingTickets.add(ticket);
	}
	
	public final List<ReimbursementTicket> getTickets(User user)
	{
		List<ReimbursementTicket> userList = this.userTickets.get(user);
		
		if (userList == null)
		{
			System.out.println("User Does Not Have Any Tickets");
			
			return null;
		}
		else
			return userList;
	}
	
	public final List<ReimbursementTicket> getTickets(User user, ReimbursementTicket.Status filterStatus)
	{
		List<ReimbursementTicket> userList = getTickets(user);
		
		if (userList == null)
			return null;
		else
		{
			List<ReimbursementTicket> filteredList = newReimbursementTicketList();
			
			for (ReimbursementTicket ticket : userList)
				if (ticket.getStatus() == filterStatus)
					filteredList.add(ticket);
			
			return filteredList;
		}
	}
	
	public ReimbursementTicket viewNextTicket() { return this.pendingTickets.peek(); }
	
	public void approveNextTicket() { updateNextTicket(ReimbursementTicket.Status.APPROVED); }
	
	public void denyNextTicket() { updateNextTicket(ReimbursementTicket.Status.DENIED); }
	
}
