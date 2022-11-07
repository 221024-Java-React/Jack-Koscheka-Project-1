package javalin;

import io.javalin.Javalin;
import ticket.TicketController;

public class JavalinRunner
{
	public static void run()
	{
		Javalin app = Javalin.create(config -> {
			config.plugins.enableCors(cors -> {
				cors.add(it -> {
					it.anyHost();
				});
			});
		});
		
		app.post("/user/register", JavalinController.register);
		app.post("/user/login", JavalinController.login);
		app.post("/employee/reimbursement_ticket/add_request", TicketController.reimbursementRequest);
		
		app.start(8192);
	}
}
