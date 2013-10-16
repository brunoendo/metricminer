package org.metricminer.infra.session;

import java.io.Serializable;

import javax.servlet.http.HttpSession;

import org.metricminer.model.User;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;

@Component
@SessionScoped
public class UserSession implements Serializable {
	private static final long serialVersionUID = 1L;

	private User loggedUser;

	private HttpSession session;

	public UserSession(Result result, HttpSession session) {
		this.session = session;
	}

	public void login(User user) {
		this.loggedUser = user;
	}

	public void logout() {
		this.loggedUser = null;
	}

	public boolean isLoggedIn() {
		return loggedUser == null;
	}

	public User getUser() {
		return loggedUser;
	}

}
