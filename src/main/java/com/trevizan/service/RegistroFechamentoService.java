package com.trevizan.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;

import com.trevizan.dto.RegistroFechamentoConsulta;
import com.trevizan.entities.RegistroFechamento;

@Named
public interface RegistroFechamentoService extends Serializable {
	
	public void salvarRegistro(RegistroFechamento registroFechamento);
	
	public void excluirRegistro(RegistroFechamento registroFechamento);

	public List<RegistroFechamento> buscarRegistrosCaixa(RegistroFechamentoConsulta registroFechamentoConsulta);

	public List<RegistroFechamento> buscarRegistrosGastos(RegistroFechamentoConsulta registroFechamentoConsulta);

}
