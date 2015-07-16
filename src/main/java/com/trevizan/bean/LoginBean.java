package com.trevizan.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.trevizan.service.UsuarioService;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 7765876811740798583L;

	@Inject
	private UsuarioService usuarioService;
	private String username;
	private String password;
	private boolean loggedIn;

	@ManagedProperty(value = "#{navigationBean}")
	private NavigationBean navigationBean;

	/**
	 * Login operation.
	 * 
	 * @return
	 */
	public String doLogin() {
		if (usuarioService.verificaUsuarioPodeLogar(username, password)) {
			limparDadosUsuario();
			loggedIn = true;
			return navigationBean.redirectToWelcome();
		}
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"Dados incorretos!", ""));
		return navigationBean.toLogin();
	}

	private void limparDadosUsuario() {
		username= null;
		password = null;
	}

	/**
	 * Logout operation.
	 * 
	 * @return
	 */
	public void doLogout() {
		loggedIn = false;
		FacesMessage msg = new FacesMessage("Logout success!", "INFO MSG");
		msg.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext.getCurrentInstance().addMessage(null, msg);
		navigationBean.toLogin();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public void setNavigationBean(NavigationBean navigationBean) {
		this.navigationBean = navigationBean;
	}

}
