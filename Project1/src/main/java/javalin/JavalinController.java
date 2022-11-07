package javalin;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.http.Handler;
import service.AuthenticationService;
import ticket.ReimbursementTicket;
import user.User;
import user.UserInfo;

public class JavalinController
{
	private static JavalinController controller;
	private ObjectMapper objectMapper;
	
	private JavalinController()
	{
		this.objectMapper = new ObjectMapper();
	}
	
	private static JavalinController getInstance()
	{
		if (controller == null)
			controller = new JavalinController();
		
		return controller;
	}
	
	public static Handler register = (context) -> {
		
		JavalinController controller = getInstance();
		
		UserInfo userInfo = controller.objectMapper.readValue(context.body(), UserInfo.class);
		
		if (AuthenticationService.register(userInfo))
		{
			context.status(201);
			context.result("User \"" + userInfo.getEmail() + "\" Was Successfully Created!");
		}
		else
		{
			context.status(406);
			context.result("User For Email \"" + userInfo.getEmail() + "\" Already Exists");
		}
	};
	
	public static Handler login = (context) -> {
		
		JavalinController controller = getInstance();
		
		UserInfo userInfo = controller.objectMapper.readValue(context.body(), UserInfo.class);
		
		User user = AuthenticationService.login(userInfo);
		
		if (user == null)
		{
			context.status(404);
			context.result("No Users Have The Specified Email And Password Combination");
		}
		else
		{
			context.status(200);
			context.result(controller.objectMapper.writeValueAsString(user));
		}
	};
	
	
	
	
}
