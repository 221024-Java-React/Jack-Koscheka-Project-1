package utility;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCConnection
{
	private static JDBCConnection util;
	
	private static Properties props = new Properties();
	
	private JDBCConnection() {}
	
	public static JDBCConnection getInstance()
	{
		if (util == null)
			util = new JDBCConnection();
		
		return util;
	}
	
	public Connection getConnection()
	{
		Connection connection = null;
		
		try
		{
			ClassLoader classLoader = getClass().getClassLoader();
			InputStream inputStream = classLoader.getResourceAsStream("jdbc.properties");
			
			String url = "";
			String username = "";
			String password = "";
			
			props.load(inputStream);
			
			url = props.getProperty("url");
			username = props.getProperty("username");
			password = props.getProperty("password");
			
			connection = DriverManager.getConnection(url, username, password);
			
		} catch(IOException exception) {
			exception.printStackTrace();
		} catch(SQLException exception) {
			exception.printStackTrace();
		}
		
		return connection;
	}

}
