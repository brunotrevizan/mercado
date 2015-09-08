package com.trevizan.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.trevizan.dto.RegistroFechamentoConsulta;
import com.trevizan.dto.RegistroGrafico;
import com.trevizan.entities.RegistroFechamento;
import com.trevizan.service.RegistroFechamentoService;
import com.trevizan.util.ValorFormatter;

@Named
@SessionScoped
@TransactionManagement(TransactionManagementType.CONTAINER)
public class RegistroFechamentoBusiness implements RegistroFechamentoService {

	private static final long serialVersionUID = 350473379576921699L;

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	@Transactional
	public void salvarRegistro(RegistroFechamento registroFechamento) {
		entityManager.persist(registroFechamento);
		entityManager.flush();
	}

	@Override
	@Transactional
	public void excluirRegistro(RegistroFechamento registroFechamento) {
		registroFechamento = entityManager.find(RegistroFechamento.class, registroFechamento.getIdRegistroFechamento());
		entityManager.remove(registroFechamento);
		entityManager.flush();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<RegistroFechamento> buscarRegistrosCaixa(RegistroFechamentoConsulta registroFechamentoConsulta) {
		return entityManager.createNamedQuery(RegistroFechamento.REGISTROS_CAIXA_POR_MES_E_ANO)
					.setParameter("ano", Integer.parseInt(registroFechamentoConsulta.getAno()))
					.setParameter("mes", Integer.parseInt(registroFechamentoConsulta.getMes()))
					.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<RegistroFechamento> buscarRegistrosGastos(RegistroFechamentoConsulta registroFechamentoConsulta) {
		return entityManager.createNamedQuery(RegistroFechamento.REGISTROS_GASTOS_POR_MES_E_ANO)
				.setParameter("ano", Integer.parseInt(registroFechamentoConsulta.getAno()))
				.setParameter("mes", Integer.parseInt(registroFechamentoConsulta.getMes()))
				.getResultList();
	}

	@Override
	public String getTotalRegistros(List<RegistroFechamento> registros) {
		BigDecimal totalRegistros = getTotalLista(registros);
		return ValorFormatter.formatarValor(totalRegistros.doubleValue(), true);
	}

	private BigDecimal getTotalLista(List<RegistroFechamento> registros) {
		BigDecimal totalRegistros = BigDecimal.ZERO;
		for (RegistroFechamento registroFechamento : registros) {
			totalRegistros = totalRegistros.add(registroFechamento.getValor());
		}
		return totalRegistros;
	}

	@Override
	public String getLucro(List<RegistroFechamento> registrosCaixa) {
		BigDecimal totalRegistros = getTotalLista(registrosCaixa);
		totalRegistros = totalRegistros.multiply(new BigDecimal(0.3));
		return ValorFormatter.formatarValor(totalRegistros.doubleValue(), true);
	}

	@Override
	public String totalCaixaMenosGastos(List<RegistroFechamento> registrosCaixa, List<RegistroFechamento> registrosGastos) {
		BigDecimal totalCaixa = getTotalLista(registrosCaixa);
		BigDecimal totalGastos = getTotalLista(registrosGastos);
		return ValorFormatter.formatarValor(totalCaixa.subtract(totalGastos).doubleValue(), true);
	}

	@Override
	public String getTotalCaixaAnual(String ano) {
		BigDecimal totalCaixaAnual = buscarTotalCaixaPorAno(ano);
		return ValorFormatter.formatarValor(totalCaixaAnual.doubleValue(), true);
	}
	
	@Override
	public String getTotalGastosAnual(String ano) {
		BigDecimal totalGastosAnual = buscarTotalGastosPorAno(ano);
		return ValorFormatter.formatarValor(totalGastosAnual.doubleValue(), true);
	}
	private BigDecimal buscarTotalGastosPorAno(String ano){
		return (BigDecimal) entityManager.createNamedQuery(RegistroFechamento.REGISTROS_GASTOS_POR_ANO)
				.setParameter("ano", Integer.parseInt(ano))
				.getSingleResult();
	}
	private BigDecimal buscarTotalCaixaPorAno(String ano){
		return (BigDecimal) entityManager.createNamedQuery(RegistroFechamento.REGISTROS_CAIXA_POR_ANO)
				.setParameter("ano", Integer.parseInt(ano))
				.getSingleResult();
	}

	@Override
	public String getLucroAnual(String ano) {
		BigDecimal totalCaixaAno = buscarTotalCaixaPorAno(ano);
		totalCaixaAno = totalCaixaAno.multiply(new BigDecimal(0.3));
		return ValorFormatter.formatarValor(totalCaixaAno.doubleValue(), true);
	}

	@Override
	public String totalCaixaMenosGastosAnual(String ano) {
		BigDecimal totalCaixaAnual = buscarTotalCaixaPorAno(ano);
		BigDecimal totalGastosAnual = buscarTotalGastosPorAno(ano);
		return ValorFormatter.formatarValor(totalCaixaAnual.subtract(totalGastosAnual).doubleValue(), true);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<RegistroGrafico> buscarRegistrosGraficoAnual(String ano, String tipo) {
		List<RegistroGrafico> registrosGrafico = new ArrayList<RegistroGrafico>();
		List<Object[]> registros =  entityManager.createNativeQuery(RegistroFechamento.getConsultaRegistrosAnualPorMes(tipo))
									.setParameter("ano", Integer.parseInt(ano))
									.getResultList();
		for (Object[] object : registros) {
			RegistroGrafico registroGrafico = new RegistroGrafico();
			registroGrafico.setLabel(object[0].toString());
			registroGrafico.setValor((BigDecimal) object[1]);
			registrosGrafico.add(registroGrafico);
		}
		return registrosGrafico;
	}

	@Override
	public BigDecimal buscarValorMaximoGraficoBalancoGeral(String ano) {
		try{
			return (BigDecimal) entityManager.createNativeQuery(RegistroFechamento.getConsultaValorMaximoMesPorAno("CAIXA"))
					.setParameter("ano", Integer.parseInt(ano))
					.getSingleResult();
		}catch (Exception e) {
			e.printStackTrace();
			return BigDecimal.ZERO;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> buscarAnosDisponiveis() {
		return entityManager.createNamedQuery(RegistroFechamento.ANOS_REGISTROS).getResultList();
	}
	
}
