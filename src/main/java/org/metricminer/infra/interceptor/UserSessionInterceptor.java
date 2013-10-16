package org.metricminer.infra.interceptor;

import org.metricminer.infra.session.UserSession;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.resource.ResourceMethod;

@Intercepts
public class UserSessionInterceptor implements Interceptor{

	
	private Result result;
	private UserSession usersession;

	public UserSessionInterceptor(Result result, UserSession usersession) {
		this.result = result;
		this.usersession = usersession;
		
	}
	
	@Override
	public boolean accepts(ResourceMethod arg0) {
		return true;
	}

	@Override
	public void intercept(InterceptorStack stack, ResourceMethod method,
			Object controller) throws InterceptionException {

		result.include("userSession", usersession);
		stack.next(method, controller);
	}

}
