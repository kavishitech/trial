package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;

import model.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import service.UserService;

public class UserController extends Controller {

	@Inject
	UserService userService;

	public Result register(final Http.Request request) {

		try {
			JsonNode inputJson = request.body().asJson();
			User user = Json.fromJson(inputJson, User.class);

			userService.registerUser(user);
			return ok("Success");
		} catch (Throwable ex) {

			return badRequest(ex.getMessage());

		}
	}

}
