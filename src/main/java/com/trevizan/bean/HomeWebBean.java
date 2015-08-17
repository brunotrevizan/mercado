package com.trevizan.bean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.trevizan.service.ClienteService;
import com.trevizan.service.RegistroService;

@Named
@ViewScoped
public class HomeWebBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ClienteService clienteService;
	
	@Inject
	private RegistroService registroService;
	
	public String totalClientesCadastrados(){
		return clienteService.totalClientesCadastrados();
	}
	
	public String totalAReceber(){
		return registroService.totalAReceber();
	}
}
