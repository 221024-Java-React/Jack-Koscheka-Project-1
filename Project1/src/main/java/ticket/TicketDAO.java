package ticket;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
		
		String sql = "DELETE FROM tickets WHERE ticketID = " + ticket.getTicketID();
		
		try
		{
			Statement statement = connection.createStatement();
			
			statement.execute(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateTicket(ReimbursementTicket ticket)
	{
		Connection connection = getInstance().connectionUtil.getConnection();
		
		String sql = "UPDATE users SET status = " + ticket.getStatus().ordinal()
				+ " WHERE ticketID = " + ticket.getTicketID();
		
		try
		{
			Statement statement = connection.createStatement();
			
			statement.execute(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
