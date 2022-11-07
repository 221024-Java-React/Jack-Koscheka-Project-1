package javalin;

import io.javalin.Javalin;

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
		
		app.get("/hello", ctx -> ctx.result("Hello World!"));
		
		app.post("/user/register", JavalinController.register);
		app.get("/user/login", JavalinController.login);
		
		app.start(8192);
	}
}
