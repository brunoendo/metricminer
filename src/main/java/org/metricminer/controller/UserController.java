package org.metricminer.controller;

import org.metricminer.infra.dao.UserDao;
import org.metricminer.infra.encryptor.Encryptor;
import org.metricminer.infra.session.UserSession;
import org.metricminer.model.User;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.http.route.Router;
import br.com.caelum.vraptor.validator.ValidationMessage;

@Resource
public class UserController {

	private final Result result;
	private final Validator validator;
	private final Encryptor encryptor;
	private final UserDao dao;
	private final UserSession session;
	private Router router;

	public UserController(Result result, Validator validator,
			Encryptor encryptor, UserDao dao, UserSession session, Router router) {
		this.result = result;
		this.validator = validator;
		this.encryptor = encryptor;
		this.dao = dao;
		this.session = session;
		this.router = router;
	}

	@Get("/login")
	public void loginForm() {
	}

	@Post("/login")
	public void login(String email, String password, String redirectUrl) {
		result.include("redirectUrl", redirectUrl);
		User user = dao.findByEmail(email);
		validatePassword(email, password);
		validator.onErrorRedirectTo(UserController.class).loginForm();
		password = encryptor.encrypt(password);
		if (user != null && user.getPassword().equals(password)) {
			session.login(user);
			redirectToRightUrl(redirectUrl);
			return;
		}
		result.include("email", email);
		validator.add(new ValidationMessage(
				"The email or password you entered is incorrect.", "error"));
		validator.onErrorRedirectTo(UserController.class).loginForm();
	}

	private void redirectToRightUrl(String redirect) {
		if (redirect != null && !redirect.isEmpty()) {
			result.redirectTo(redirect);
		} else {
			result.redirectTo(IndexController.class).index();
		}
	}

	private void validatePassword(String email, String password) {
		if (password == null || password.isEmpty()) {
			result.include("email", email);
			validator.add(new ValidationMessage("The email or password you entered is incorrect.",
							"error"));
		}
	}

	@Get("/signup")
	public void userForm() {
	}

	@Post("/signup")
	public void registerUser(final User user) {
		validator.validate(user);
		result.include("user", user);
		validator.onErrorRedirectTo(UserController.class).userForm();
		if (!user.isValid()) {
			validator.add(new ValidationMessage(
					"Password confirmation don't match.", "error"));
		}
		validator.onErrorRedirectTo(UserController.class).userForm();

		user.encryptPassword(encryptor);
		dao.save(user);

		result.include("message", "You can login into MetricMiner now.");
		result.redirectTo(UserController.class).loginForm();
	}
	
	@Get("/logout")
	public void logout() {
		session.logout();
		result.redirectTo(IndexController.class).index();
	}
}
