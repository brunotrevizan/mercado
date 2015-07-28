package com.trevizan.bean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.trevizan.dto.RegistroFechamentoConsulta;
import com.trevizan.entities.RegistroFechamento;
import com.trevizan.service.RegistroFechamentoService;

@Named
@ViewScoped
public class FechamentoWebBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private RegistroFechamentoService registroFechamentoService;

	private RegistroFechamento registroFechamento;
	private RegistroFechamentoConsulta registroFechamentoConsulta;
	private List<RegistroFechamento> registrosCaixa;
	private List<RegistroFechamento> registrosGastos;

	@PostConstruct
	public void init() {
		setRegistroFechamento(new RegistroFechamento());
		inicializarRegistroFechamentoConsulta();
		popularRegistros();
	}

	public void popularRegistros() {
		setRegistrosCaixa(registroFechamentoService.buscarRegistrosCaixa(registroFechamentoConsulta));
		setRegistrosGastos(registroFechamentoService.buscarRegistrosGastos(registroFechamentoConsulta));
	}

	private void inicializarRegistroFechamentoConsulta() {
		setRegistroFechamentoConsulta(new RegistroFechamentoConsulta());
		getRegistroFechamentoConsulta().setAno(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
		getRegistroFechamentoConsulta().setMes(String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1));
	}
	
	public void salvarRegistro(){
		registroFechamentoService.salvarRegistro(registroFechamento);
		registroFechamento = new RegistroFechamento();
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro cadastrado com sucesso", ""));
	}
	
	public void excluirRegistro(RegistroFechamento registroFechamento){
		registroFechamentoService.excluirRegistro(registroFechamento);
	}

	public boolean desabilitarComboAno(){
		return registroFechamentoConsulta.getMes() == null || "".equals(registroFechamentoConsulta.getMes());
	}
	
	public RegistroFechamento getRegistroFechamento() {
		return registroFechamento;
	}

	public void setRegistroFechamento(RegistroFechamento registroFechamento) {
		this.registroFechamento = registroFechamento;
	}

	public RegistroFechamentoConsulta getRegistroFechamentoConsulta() {
		return registroFechamentoConsulta;
	}

	public void setRegistroFechamentoConsulta(RegistroFechamentoConsulta registroFechamentoConsulta) {
		this.registroFechamentoConsulta = registroFechamentoConsulta;
	}

	public List<RegistroFechamento> getRegistrosCaixa() {
		return registrosCaixa;
	}

	public void setRegistrosCaixa(List<RegistroFechamento> registrosCaixa) {
		this.registrosCaixa = registrosCaixa;
	}

	public List<RegistroFechamento> getRegistrosGastos() {
		return registrosGastos;
	}

	public void setRegistrosGastos(List<RegistroFechamento> registrosGastos) {
		this.registrosGastos = registrosGastos;
	}

}
