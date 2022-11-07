package ticket;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import user.User;
import user.UserDAO;
import user.UserInfo;
import utility.JDBCConnection;

public class TicketDAO
{
	private static TicketDAO ticketDAO;
	private JDBCConnection connectionUtil;
	
	private static int getNextID()
	{
		Connection connection = getInstance().connectionUtil.getConnection();
		
		int id = 0;
		
		String sql = "SELECT MAX(id) FROM tickets";
		
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
	
	public static void addTicket(ReimbursementTicket ticket, UserInfo userInfo)
	{
		User user = UserDAO.getUser(userInfo);
		
		if (user == null)
		{
			// TODO throw user not found exception
			
			return;
		}
		
		ticket.id = getNextID();
		
		Connection connection = getInstance().connectionUtil.getConnection();
		
		final Price price = ticket.price;
		
		String sql = "INSERT INTO tickets (id, userID, status, dollars, cents, description) VALUES "
				+ "(" + ticket.id + ", " + user.getID() + ", " + ticket.status.ordinal() + ", "
				+ price.dollars + ", " + price.cents + ", '" + ticket.description + "')";
		
		try
		{
			Statement statement = connection.createStatement();
			
			statement.execute(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Ticket> getAllTickets(Ticket.Status status)
	{
		List<Ticket> tickets = new ArrayList<Ticket>();
		
		Connection connection = getInstance().connectionUtil.getConnection();
		
		String sql = "SELECT * FROM tickets WHERE status = " + status.ordinal();
		
		try
		{
			Statement statement = connection.createStatement();
			
			ResultSet result = statement.executeQuery(sql);
			
			while (result.next())
			{
				// IMPORTANT: JDBC indexes from 1 NOT 0
				
				Price price = new Price((long) result.getInt(4), (byte) result.getInt(5));
				Ticket ticket = new ReimbursementTicket(price, result.getString(6));
				
				ticket.id = result.getInt(1);
				ticket.userID = result.getInt(2);
				ticket.status = Ticket.Status.values()[result.getInt(3)];
				
				tickets.add(ticket);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return tickets;
	}
	
	public static List<Ticket> getAllTickets(UserInfo userInfo)
	{
		User user = UserDAO.getUser(userInfo);
		
		if (user == null)
		{
			// TODO throw user not found exception
			
			return null;
		}
		
		List<Ticket> tickets = new ArrayList<Ticket>();
		
		Connection connection = getInstance().connectionUtil.getConnection();
		
		String sql = "SELECT * FROM tickets WHERE userID = " + user.getID();
		
		try
		{
			Statement statement = connection.createStatement();
			
			ResultSet result = statement.executeQuery(sql);
			
			while (result.next())
			{
				// IMPORTANT: JDBC indexes from 1 NOT 0
				
				Price price = new Price((long) result.getInt(4), (byte) result.getInt(5));
				Ticket ticket = new ReimbursementTicket(price, result.getString(6));
				
				ticket.id = result.getInt(1);
				ticket.userID = result.getInt(2);
				ticket.status = Ticket.Status.values()[result.getInt(3)];
				
				tickets.add(ticket);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return tickets;
	}
	
	public static void deleteTicket(ReimbursementTicket ticket)
	{
		Connection connection = getInstance().connectionUtil.getConnection();
		
		String sql = "DELETE FROM tickets WHERE id = " + ticket.id;
		
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
		
		String sql = "UPDATE users SET status = " + ticket.status.ordinal() + " WHERE id = " + ticket.id;
		
		try
		{
			Statement statement = connection.createStatement();
			
			statement.execute(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
