package org.metricminer.infra.interceptor;

import java.net.HttpRetryException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

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
	private HttpServletRequest request;

	public LoggedUserInterceptor(UserSession session, Result result, HttpServletRequest request) {
		this.session = session;
		this.result = result;
		this.request = request;
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
			String path = request.getRequestURI().substring(request.getContextPath().length());
			result.include("errors", Arrays.asList(message));
			result.include("redirectUrl", path);
			result.redirectTo(UserController.class).loginForm();
		} else {
			stack.next(method, resource);
		}
	}

}
