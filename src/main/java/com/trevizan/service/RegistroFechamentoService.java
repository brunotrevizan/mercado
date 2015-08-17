package com.trevizan.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.inject.Named;

import com.trevizan.dto.RegistroFechamentoConsulta;
import com.trevizan.dto.RegistroGrafico;
import com.trevizan.entities.RegistroFechamento;

@Named
public interface RegistroFechamentoService extends Serializable {
	
	public void salvarRegistro(RegistroFechamento registroFechamento);
	
	public void excluirRegistro(RegistroFechamento registroFechamento);

	public List<RegistroFechamento> buscarRegistrosCaixa(RegistroFechamentoConsulta registroFechamentoConsulta);

	public List<RegistroFechamento> buscarRegistrosGastos(RegistroFechamentoConsulta registroFechamentoConsulta);

	public String getTotalRegistros(List<RegistroFechamento> registros);

	public String getLucro(List<RegistroFechamento> registrosCaixa);

	public String totalCaixaMenosGastos(List<RegistroFechamento> registrosCaixa, List<RegistroFechamento> registrosGastos);

	public String getTotalCaixaAnual(String ano);

	public String getTotalGastosAnual(String ano);

	public String getLucroAnual(String ano);

	public String totalCaixaMenosGastosAnual(String ano);

	public List<RegistroGrafico> buscarRegistrosGraficoAnual(String ano, String tipo);

	public BigDecimal buscarValorMaximoGraficoBalancoGeral(String ano);

	public List<String> buscarAnosDisponiveis();

}
