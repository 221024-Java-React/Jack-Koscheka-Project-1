package utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log
{
	private static final Logger logger = LoggerFactory.getLogger(Log.class);
	
	public static void trace(String message) { logger.trace(message); }
	
	public static void debug(String message) { logger.debug(message); }
	
	public static void info(String message) { logger.info(message); }
	
	public static void warn(String message) { logger.warn(message); }
	
	public static void error(String message) { logger.error(message); }
	
}
