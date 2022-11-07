package javalin;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.http.Handler;
import service.AuthenticationService;
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
			// TODO register successful
			
			System.out.println(userInfo);
			
			context.status(201);
			context.result(controller.objectMapper.writeValueAsString(userInfo));
		}
		else
		{
			// TODO register unsuccessful
			
			System.out.println("User Already Exists");
			
			context.status(404);
		}
	};
	
	public static Handler login = (context) -> {
		
		JavalinController controller = getInstance();
		
		UserInfo userInfo = controller.objectMapper.readValue(context.body(), UserInfo.class);
		
		User user = AuthenticationService.login(userInfo);
		
		if (user == null)
		{
			// TODO login unsuccessful
			
			System.out.println("User Does Not Exist");
			
			context.status(404);
		}
		else
		{
			// TODO login successful
			
			System.out.println("User Found");
			
			context.status(201);
			context.result(controller.objectMapper.writeValueAsString(user));
		}
	};
	
	
	
	
}