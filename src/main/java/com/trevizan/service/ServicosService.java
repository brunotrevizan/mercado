package com.trevizan.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.inject.Named;

import com.trevizan.dto.RegistroGrafico;
import com.trevizan.dto.ServicosConsulta;
import com.trevizan.entities.Servico;

@Named
public interface ServicosService extends Serializable {
	
	void salvarServico(Servico servico);

	void excluirServico(Servico servico);

	List<Servico> buscarRegistrosSegundaVia(ServicosConsulta servicosConsulta);

	List<Servico> buscarRegistrosXerox(ServicosConsulta servicosConsulta);

	String getTotalValorServico(List<Servico> servicos, String tipo);

	List<String> buscarAnosDisponiveis();

	Integer buscarValorMaximoGraficoBalancoGeral(String ano);

	List<RegistroGrafico> buscarRegistrosGraficoAnual(String ano, String tipo);

	String getTotalSegundaViaAnual(String ano);

	String getTotalXeroxAnual(String ano);

	String getValorTotalSegundaViaAnual(String ano);

	String getValorTotalXeroxAnual(String ano);

	String getTotalXeroxMes(int mes, int ano);

	String getTotalSegundaViaMes(int mes, int ano);

	List<Servico> buscarRegistrosImpressaoFoto(ServicosConsulta servicosConsulta);

	String getTotalImpressaoFotoAnual(String ano);

	String getValorTotalImpressaoFotoAnual(String ano);

}
