package com.trevizan.business;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.trevizan.dto.RegistroGrafico;
import com.trevizan.dto.ServicosConsulta;
import com.trevizan.entities.Configuracao;
import com.trevizan.entities.RegistroFechamento;
import com.trevizan.entities.Servico;
import com.trevizan.service.ConfiguracaoService;
import com.trevizan.service.ServicosService;
import com.trevizan.type.TipoServico;
import com.trevizan.util.ValorFormatter;

@Named
@SessionScoped
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ServicosBusiness implements ServicosService {

	private static final long serialVersionUID = 350473379576921699L;

	@PersistenceContext
	private EntityManager entityManager;
	
	@Inject
	private ConfiguracaoService configuracaoService;
	
	@Override
	@Transactional
	public void salvarServico(Servico servico) {
		entityManager.persist(servico);
		entityManager.flush();
	}

	@Override
	@Transactional
	public void excluirServico(Servico servico) {
		servico = entityManager.find(Servico.class, servico.getIdServico());
		entityManager.remove(servico);
		entityManager.flush();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Servico> buscarRegistrosSegundaVia(ServicosConsulta servicosConsulta) {
		return entityManager.createNamedQuery(Servico.SERVICOS_SEGUNDA_VIA_POR_MES_E_ANO)
					.setParameter("ano", Integer.parseInt(servicosConsulta.getAno()))
					.setParameter("mes", Integer.parseInt(servicosConsulta.getMes()))
					.getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Servico> buscarRegistrosXerox(ServicosConsulta servicosConsulta) {
		return entityManager.createNamedQuery(Servico.SERVICOS_XEROX_POR_MES_E_ANO)
					.setParameter("ano", Integer.parseInt(servicosConsulta.getAno()))
					.setParameter("mes", Integer.parseInt(servicosConsulta.getMes()))
					.getResultList();
	}

	@Override
	public String getTotalValorServico(List<Servico> servicos, String tipo) {
		BigDecimal valorTotal = BigDecimal.ZERO;
		for (Servico servico : servicos) {
			Configuracao configuracao = configuracaoService.buscarConfiguracaoPorDataServico(servico.getDataRegistro());
			if(tipo.equals(TipoServico.SEGUNDA_VIA.getValor())){
				valorTotal = valorTotal.add(configuracao.getValorSegundaViaBigDecimal().multiply(new BigDecimal(servico.getQuantidade())));
			} else if(tipo.equals(TipoServico.XEROX.getValor())) {
				valorTotal = valorTotal.add(configuracao.getValorXeroxBigDecimal().multiply(new BigDecimal(servico.getQuantidade())));
			}
		}
		return ValorFormatter.formatarValor(valorTotal.doubleValue(), false);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<String> buscarAnosDisponiveis() {
		return entityManager.createNamedQuery(RegistroFechamento.ANOS_REGISTROS).getResultList();
	}

	@Override
	public BigDecimal buscarValorMaximoGraficoBalancoGeral(String ano) {
		try{
			return (BigDecimal) entityManager.createNativeQuery(Servico.getConsultaValorMaximoMesPorAno())
					.setParameter("ano", Integer.parseInt(ano))
					.getSingleResult();
		}catch (Exception e) {
			return BigDecimal.ZERO;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<RegistroGrafico> buscarRegistrosGraficoAnual(String ano, String tipo) {
		List<RegistroGrafico> registrosGrafico = new ArrayList<RegistroGrafico>();
		List<Object[]> registros =  entityManager.createNativeQuery(Servico.getConsultaRegistrosAnualPorMes(tipo))
									.setParameter("ano", Integer.parseInt(ano))
									.getResultList();
		for (Object[] object : registros) {
			RegistroGrafico registroGrafico = new RegistroGrafico();
			registroGrafico.setLabel(object[0].toString());
			registroGrafico.setQuantidade((BigInteger) object[1]);
			registrosGrafico.add(registroGrafico);
		}
		return registrosGrafico;
	}

	@Override
	public String getTotalSegundaViaAnual(String ano) {
		Integer totalSegundaViaAnual = buscarTotalSegundaViaAnual(ano).intValue();
		return totalSegundaViaAnual.toString();
	}
	
	private Long buscarTotalSegundaViaAnual(String ano){
		return (Long) entityManager.createNamedQuery(Servico.QUANTIDADE_SERVICOS_SEGUNDA_VIA_POR_ANO)
				.setParameter("ano", Integer.parseInt(ano))
				.getSingleResult();
	}

	@Override
	public String getTotalXeroxAnual(String ano) {
		Integer totalXeroxAnual = buscarTotalXeroxAnual(ano).intValue();
		return totalXeroxAnual.toString();
	}
	
	private Long buscarTotalXeroxAnual(String ano){
		return (Long) entityManager.createNamedQuery(Servico.QUANTIDADE_SERVICOS_XEROX_POR_ANO)
				.setParameter("ano", Integer.parseInt(ano))
				.getSingleResult();
	}

	@Override
	public String getValorTotalSegundaViaAnual(String ano) {
		BigDecimal totalSegundaViaAnual = buscarTotalValorServicoAnual(ano, TipoServico.SEGUNDA_VIA.getValor());
		return ValorFormatter.formatarValor(totalSegundaViaAnual.doubleValue(), true);
	}
	
	@Override
	public String getValorTotalXeroxAnual(String ano) {
		BigDecimal totalSegundaViaAnual = buscarTotalValorServicoAnual(ano, TipoServico.XEROX.getValor());
		return ValorFormatter.formatarValor(totalSegundaViaAnual.doubleValue(), true);
	}

	@SuppressWarnings("unchecked")
	private BigDecimal buscarTotalValorServicoAnual(String ano, String tipo) {
		BigDecimal valorTotal = BigDecimal.ZERO;
		List<Servico> servicos = entityManager.createNamedQuery(Servico.SERVICOS_SEGUNDA_VIA_POR_ANO).setParameter("ano", Integer.parseInt(ano)).getResultList();
		for (Servico servico : servicos) {
			Configuracao configuracao = configuracaoService.buscarConfiguracaoPorDataServico(servico.getDataRegistro());
			if(tipo.equals(TipoServico.SEGUNDA_VIA.getValor())){
				valorTotal = valorTotal.add(configuracao.getValorSegundaViaBigDecimal().multiply(new BigDecimal(servico.getQuantidade())));
			} else if(tipo.equals(TipoServico.XEROX.getValor())) {
				valorTotal = valorTotal.add(configuracao.getValorXeroxBigDecimal().multiply(new BigDecimal(servico.getQuantidade())));
			}
		}
		return valorTotal;
	}

	@Override
	public String getTotalXeroxMes(int mes, int ano) {
		ServicosConsulta servicosConsulta = montarServicoConsulta(mes, ano);
		List<Servico> servicos = buscarRegistrosXerox(servicosConsulta);
		return getTotalValorServico(servicos, TipoServico.XEROX.getValor());
	}
	
	@Override
	public String getTotalSegundaViaMes(int mes, int ano) {
		ServicosConsulta servicosConsulta = montarServicoConsulta(mes, ano);
		List<Servico> servicos = buscarRegistrosSegundaVia(servicosConsulta);
		return getTotalValorServico(servicos, TipoServico.SEGUNDA_VIA.getValor());
	}

	private ServicosConsulta montarServicoConsulta(int mes, int ano) {
		ServicosConsulta servicosConsulta = new ServicosConsulta();
		servicosConsulta.setMes(String.valueOf(mes));
		servicosConsulta.setAno(String.valueOf(ano));
		return servicosConsulta;
	}

}