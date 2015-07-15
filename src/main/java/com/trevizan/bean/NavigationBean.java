package com.trevizan.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class NavigationBean implements Serializable {

	private static final long serialVersionUID = 1520318172495977648L;

	/**
	 * Redirect to login page.
	 * 
	 * @return Login page name.
	 */
	public String redirectToLogin() {
		return "/pages/login.xhtml?faces-redirect=true";
	}

	/**
	 * Go to login page.
	 * 
	 * @return Login page name.
	 */
	public String toLogin() {
		return "/pages/login.xhtml";
	}

	/**
	 * Redirect to info page.
	 * 
	 * @return Info page name.
	 */
	public String redirectToInfo() {
		return "/pages/info.xhtml?faces-redirect=true";
	}

	/**
	 * Go to info page.
	 * 
	 * @return Info page name.
	 */
	public String toInfo() {
		return "/pages/info.xhtml";
	}

	/**
	 * Redirect to welcome page.
	 * 
	 * @return Welcome page name.
	 */
	public String redirectToWelcome() {
		return "/pages/private/home.xhtml?faces-redirect=true";
	}

	/**
	 * Go to welcome page.
	 * 
	 * @return Welcome page name.
	 */
	public String toWelcome() {
		return "/pages/private/home.xhtml";
	}
}
