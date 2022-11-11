package daos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import models.Price;
import models.ReimbursementTicket;
import models.Ticket;
import utility.JDBCConnection;

public class TicketDAO
{
	private static TicketDAO ticketDAO;
	private JDBCConnection connectionUtil;
	
	private TicketDAO()
	{
		connectionUtil = JDBCConnection.getInstance();
	}
	
	private static TicketDAO getInstance()
	{
		if (ticketDAO == null)
			ticketDAO = new TicketDAO();
		
		return ticketDAO;
	}
	
	private static int getNextID()
	{
		Connection connection = getInstance().connectionUtil.getConnection();
		
		int id = 0;
		
		String sql = "SELECT MAX(ticketID) FROM tickets";
		
		try
		{
			Statement statement = connection.createStatement();
			
			ResultSet result = statement.executeQuery(sql);
			
			if (result.next())
				id = result.getInt(1) + 1;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id;
	}
	
	private static List<Ticket> getAllTicketsSQL(String sql)
	{
		List<Ticket> tickets = new ArrayList<Ticket>();
		
		Connection connection = getInstance().connectionUtil.getConnection();
		
		try
		{
			Statement statement = connection.createStatement();
			
			ResultSet result = statement.executeQuery(sql);
			
			while (result.next())
			{
				Price price = new Price((long) result.getInt(4), (byte) result.getInt(5));
				Ticket ticket = new ReimbursementTicket(price, result.getString(6));
				
				ticket.setTicketID(result.getInt(1));
				ticket.setUserID(result.getInt(2));
				ticket.setStatus(Ticket.Status.values()[result.getInt(3)]);
				
				tickets.add(ticket);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return tickets;
	}
	
	private static Ticket getTicketsSQL(String sql)
	{
		Ticket ticket = null;
		
		Connection connection = getInstance().connectionUtil.getConnection();
		
		try
		{
			Statement statement = connection.createStatement();
			
			ResultSet result = statement.executeQuery(sql);
			
			if (result.next())
			{
				Price price = new Price((long) result.getInt(4), (byte) result.getInt(5));
				ticket = new ReimbursementTicket(price, result.getString(6));
				
				ticket.setTicketID(result.getInt(1));
				ticket.setUserID(result.getInt(2));
				ticket.setStatus(Ticket.Status.values()[result.getInt(3)]);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ticket;
	}
	
	public static void addTicket(ReimbursementTicket ticket)
	{
		ticket.setTicketID(getNextID());
		
		Connection connection = getInstance().connectionUtil.getConnection();
		
		final Price price = ticket.getPrice();
		
		String sql = "INSERT INTO tickets (ticketID, userID, status, dollars, cents, description) VALUES "
				+ "(" + ticket.getTicketID() + ", " + ticket.getUserID() + ", " + ticket.getStatus().ordinal() + ", "
				+ price.getDollars() + ", " + price.getCents() + ", '" + ticket.getDescription() + "')";
		
		try
		{
			Statement statement = connection.createStatement();
			
			statement.execute(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Ticket> getAllTickets(Optional<Integer> userID, Optional<Ticket.Status> status)
	{
		StringBuilder stringBuilder = new StringBuilder("SELECT * FROM tickets");
		
		if (userID.isPresent())
		{
			stringBuilder.append(" WHERE userID = ");
			stringBuilder.append(userID.get());
			
			if (status.isPresent())
			{
				stringBuilder.append(" AND status = ");
				stringBuilder.append(status.get().ordinal());
			}
		}
		else if (status.isPresent())
		{
			stringBuilder.append(" WHERE status = ");
			stringBuilder.append(status.get().ordinal());
		}
		
		return getAllTicketsSQL(stringBuilder.toString());
	}
	
	public static void deleteTicket(ReimbursementTicket ticket)
	{
		Connection connection = getInstance().connectionUtil.getConnection();
		
		String sql;
		
		if (ticket.getTicketID() > 0)
			sql = "DELETE FROM tickets WHERE ticketID = " + ticket.getTicketID();
		else
			sql = "DELETE FROM tickets WHERE dollars = " + ticket.getPrice().getDollars() + " AND cents = "
				+ ticket.getPrice().getCents() + " AND description = '" + ticket.getDescription() + "'";
		
		try
		{
			Statement statement = connection.createStatement();
			
			statement.execute(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean updateTicket(ReimbursementTicket ticket, Ticket.Status status)
	{
		Connection connection = getInstance().connectionUtil.getConnection();
		
		String sql;
		
		StringBuilder stringBuilder = new StringBuilder("SELECT * FROM tickets WHERE ");
		
		if (ticket.getTicketID() > 0)
		{
			stringBuilder.append("ticketID = ");
			stringBuilder.append(ticket.getTicketID());
		}
		else
		{
			stringBuilder.append(" WHERE dollars = ");
			stringBuilder.append(ticket.getPrice().getDollars());
			stringBuilder.append(" AND cents = ");
			stringBuilder.append(ticket.getPrice().getCents());
			stringBuilder.append(" AND description = '");
			stringBuilder.append(ticket.getDescription());
			stringBuilder.append("'");
		}
		
		Ticket dataTicket = getTicketsSQL(stringBuilder.toString());
		
		if (dataTicket == null || dataTicket.getStatus() != Ticket.Status.PENDING)
			return false;
		
		if (ticket.getTicketID() > 0)
			sql = "UPDATE tickets SET status = " + status.ordinal() + " WHERE ticketID = " + ticket.getTicketID();
		else
			sql = "UPDATE tickets SET status = " + status.ordinal() + " WHERE dollars = "
				+ ticket.getPrice().getDollars() + " AND cents = " + ticket.getPrice().getCents()
				+ " AND description = '" + ticket.getDescription() + "'";
		
		try
		{
			Statement statement = connection.createStatement();
			
			statement.execute(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
			
			return false;
		}
		
		return true;
	}
}
