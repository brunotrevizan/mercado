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
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
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
		if (existeServicoCadastradoProDia(servico)) {
			Servico servicoCadastrado = buscarServicoCadastradoProDia(servico);
			servicoCadastrado.setQuantidade(servicoCadastrado.getQuantidade() + servico.getQuantidade());
			entityManager.merge(servicoCadastrado);
			entityManager.flush();
		} else {
			entityManager.persist(servico);
			entityManager.flush();
		}
	}

	private Servico buscarServicoCadastradoProDia(Servico servico) {
		try {
			return (Servico) entityManager.createNamedQuery(Servico.SERVICO_POR_DIA_E_TIPO)
					.setParameter("data", servico.getDataRegistro())
					.setParameter("tipo", servico.getTipoServico()).getSingleResult();
		} catch (NonUniqueResultException e) {
			return (Servico) entityManager.createNamedQuery(Servico.SERVICO_POR_DIA_E_TIPO)
					.setParameter("data", servico.getDataRegistro())
					.setParameter("tipo", servico.getTipoServico()).getResultList().get(0);
		}
	}
	
	private boolean existeServicoCadastradoProDia(Servico servico) {
		Servico servicoCadastrado = null;
		try {
			servicoCadastrado = (Servico) entityManager
					.createNamedQuery(Servico.SERVICO_POR_DIA_E_TIPO)
					.setParameter("data", servico.getDataRegistro())
					.setParameter("tipo", servico.getTipoServico()).getSingleResult();
		} catch (NoResultException e) {
			return false;
		} catch (NonUniqueResultException e) {
			return true;
		}
		return servicoCadastrado != null;
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
			} else if(tipo.equals(TipoServico.IMPRESSAO_FOTO.getValor())) {
				valorTotal = valorTotal.add(configuracao.getValorImpressaoFoto().multiply(new BigDecimal(servico.getQuantidade())));
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
	public Integer buscarValorMaximoGraficoBalancoGeral(String ano) {
		try{
			return (Integer) entityManager.createNativeQuery(Servico.getConsultaValorMaximoMesPorAno())
					.setParameter("ano", Integer.parseInt(ano))
					.getSingleResult();
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
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
	
	private Long buscarTotalImpressaoFotoAnual(String ano){
		return (Long) entityManager.createNamedQuery(Servico.QUANTIDADE_SERVICOS_IMPRESSAO_FOTO_POR_ANO)
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
		List<Servico> servicos = entityManager.createNamedQuery(Servico.SERVICOS_POR_ANO_E_TIPO).setParameter("ano", Integer.parseInt(ano)).setParameter("tipo", tipo).getResultList();
		for (Servico servico : servicos) {
			Configuracao configuracao = configuracaoService.buscarConfiguracaoPorDataServico(servico.getDataRegistro());
			if(tipo.equals(TipoServico.SEGUNDA_VIA.getValor())){
				valorTotal = valorTotal.add(configuracao.getValorSegundaViaBigDecimal().multiply(new BigDecimal(servico.getQuantidade())));
			} else if(tipo.equals(TipoServico.XEROX.getValor())) {
				valorTotal = valorTotal.add(configuracao.getValorXeroxBigDecimal().multiply(new BigDecimal(servico.getQuantidade())));
			} else if(tipo.equals(TipoServico.IMPRESSAO_FOTO.getValor())) {
				valorTotal = valorTotal.add(configuracao.getValorImpressaoFoto().multiply(new BigDecimal(servico.getQuantidade())));
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Servico> buscarRegistrosImpressaoFoto(ServicosConsulta servicosConsulta) {
		return entityManager.createNamedQuery(Servico.SERVICOS_IMPRESSAO_FOTO_POR_MES_E_ANO)
				.setParameter("ano", Integer.parseInt(servicosConsulta.getAno()))
				.setParameter("mes", Integer.parseInt(servicosConsulta.getMes()))
				.getResultList();
	}

	@Override
	public String getTotalImpressaoFotoAnual(String ano) {
		Integer totalImpressaoFotoAnual = buscarTotalImpressaoFotoAnual(ano).intValue();
		return totalImpressaoFotoAnual.toString();
	}

	@Override
	public String getValorTotalImpressaoFotoAnual(String ano) {
		BigDecimal totalImpressaoFotoAnual = buscarTotalValorServicoAnual(ano, TipoServico.IMPRESSAO_FOTO.getValor());
		return ValorFormatter.formatarValor(totalImpressaoFotoAnual.doubleValue(), true);
	}

}
