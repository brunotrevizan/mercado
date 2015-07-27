package com.trevizan.service;

import java.io.Serializable;

import javax.inject.Named;

import com.trevizan.entities.RegistroFechamento;

@Named
public interface RegistroFechamentoService extends Serializable {
	
	public void salvarRegistro(RegistroFechamento registroFechamento);
	
	public void excluirRegistro(RegistroFechamento registroFechamento);

}
