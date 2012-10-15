package org.metricminer.infra.interceptor;

import java.util.Arrays;

import org.metricminer.controller.UserController;
import org.metricminer.infra.session.UserSession;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.ioc.RequestScoped;
import br.com.caelum.vraptor.resource.ResourceMethod;
import br.com.caelum.vraptor.validator.ValidationMessage;

@Intercepts
@RequestScoped
public class LoggedUserInterceptor implements Interceptor {
	
	private final UserSession session;
	private final Result result;
	private final Validator validator;

	public LoggedUserInterceptor(UserSession session, Validator validator, Result result) {
		this.session = session;
		this.validator = validator;
		this.result = result;
	}

	@Override
	public boolean accepts(ResourceMethod method) {
		return method.containsAnnotation(LoggedUserAccess.class);
	}

	@Override
	public void intercept(InterceptorStack stack, ResourceMethod method,
			Object resource) throws InterceptionException {
		if (session.isLoggedIn()) {
			ValidationMessage message = new ValidationMessage("You must login",
                    "loggedUser");
			result.include("errors", Arrays.asList(message));
			result.redirectTo(UserController.class).loginForm();
		} else {
			stack.next(method, resource);
		}
	}

}
