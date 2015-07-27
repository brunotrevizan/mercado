package com.trevizan.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.trevizan.entities.RegistroFechamento;
import com.trevizan.service.RegistroFechamentoService;

@Named
@ViewScoped
public class FechamentoWebBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private RegistroFechamentoService registroFechamentoService;

	private RegistroFechamento registroFechamento;

	@PostConstruct
	public void init() {
		setRegistroFechamento(new RegistroFechamento());
	}
	
	public void salvarRegistro(){
		registroFechamentoService.salvarRegistro(registroFechamento);
		registroFechamento = new RegistroFechamento();
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro cadastrado com sucesso", ""));
	}

	public RegistroFechamento getRegistroFechamento() {
		return registroFechamento;
	}

	public void setRegistroFechamento(RegistroFechamento registroFechamento) {
		this.registroFechamento = registroFechamento;
	}

}
