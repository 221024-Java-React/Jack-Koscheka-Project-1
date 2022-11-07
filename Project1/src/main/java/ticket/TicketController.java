package ticket;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.http.Handler;

public class TicketController
{
	private static TicketController controller;
	private ObjectMapper objectMapper;
	
	private TicketController()
	{
		this.objectMapper = new ObjectMapper();
	}
	
	private static TicketController getInstance()
	{
		if (controller == null)
			controller = new TicketController();
		
		return controller;
	}
	
	public static Handler reimbursementRequest = (context) -> {
		
		TicketController controller = getInstance();
		
		ReimbursementTicket request = controller.objectMapper.readValue(context.body(), ReimbursementTicket.class);
		
		// TODO add the request
		
		System.out.println("Request Recieved!");
		
		context.status(200);
		
//		User user = AuthenticationService.login(userInfo);
//		
//		if (user == null)
//		{
//			context.status(404);
//			context.result("No Users Have The Specified Email And Password Combination");
//		}
//		else
//		{
//			context.status(200);
//			context.result(controller.objectMapper.writeValueAsString(user));
//		}
	};
}
