package main;

import io.javalin.Javalin;
import controllers.EmployeeController;
import controllers.ManagerController;
import controllers.UserController;

public class JavalinRunner
{
	private static void addUserControls(Javalin app)
	{
		app.post("/user/register", UserController.register);
		app.post("/user/login", UserController.login);
		app.get("/user/session", UserController.session);
		app.post("/user/logout", UserController.logout);
		app.delete("/user/delete", UserController.delete);
	}
	
	private static void addEmployeeControls(Javalin app)
	{
		app.get("/user/tickets", EmployeeController.tickets);
		app.get("/user/tickets/pending", EmployeeController.pendingTickets);
		app.get("/user/tickets/denied", EmployeeController.deniedTickets);
		app.get("/user/tickets/approved", EmployeeController.approvedTickets);
		app.post("/user/tickets/add", EmployeeController.addTicket);
		app.delete("/user/tickets/delete", EmployeeController.deleteTicket);
	}
	
	private static void addManagerControls(Javalin app)
	{
		app.get("/tickets", ManagerController.tickets);
		app.get("/tickets/pending", ManagerController.pendingTickets);
		app.get("/tickets/denied", ManagerController.deniedTickets);
		app.get("/tickets/approved", ManagerController.approvedTickets);
		app.patch("/tickets/deny", ManagerController.denyTicket);
		app.patch("/tickets/approve", ManagerController.approveTicket);
		app.patch("/user/manager", ManagerController.makeManager);
		app.patch("/user/employee", ManagerController.makeEmployee);
	}
	
	private static void addControls(Javalin app)
	{
		addUserControls(app);
		
		addEmployeeControls(app);
		
		addManagerControls(app);
	}
	
	public static void run()
	{
		Javalin app = Javalin.create(config -> {
			config.plugins.enableCors(cors -> {
				cors.add(it -> {
					it.anyHost();
				});
			});
		});
		
		addControls(app);
		
		app.start(8192);
	}
}
