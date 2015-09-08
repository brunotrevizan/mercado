package com.trevizan.business;

import java.math.BigDecimal;
import java.util.Date;

import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.trevizan.entities.Configuracao;
import com.trevizan.service.ConfiguracaoService;

@Named
@SessionScoped
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ConfiguracaoBusiness implements ConfiguracaoService {

	private static final long serialVersionUID = 350473379576921699L;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void salvarConfiguracao(Configuracao configuracao) {
		inativarConfiguracoesAnteriores();
		configuracao.setAtiva(true);
		configuracao.setInicio(new Date());
		entityManager.persist(configuracao);
		entityManager.flush();
	}

	private void inativarConfiguracoesAnteriores() {
		Configuracao configuracao = buscarConfiguracaoAtiva();
		if (configuracao.getIdConfiguracao() != null) {
			configuracao.setAtiva(false);
			configuracao.setTermino(new Date());
			entityManager.merge(configuracao);
		}
		entityManager.flush();
	}

	@Override
	public Configuracao buscarConfiguracaoAtiva() {
		try {
			return (Configuracao) entityManager.createNamedQuery(Configuracao.CONFIGURACAO_ATIVA).getSingleResult();
		} catch (NoResultException e) {
			return new Configuracao(BigDecimal.ZERO, BigDecimal.ZERO);
		}
	}

	@Override
	public Configuracao buscarConfiguracaoPorDataServico(Date dataServico) {
		try {
			return (Configuracao) entityManager.createNamedQuery(Configuracao.CONFIGURACAO_POR_DATA_SERVICO)
							.setParameter("data", dataServico)
							.getSingleResult();
		} catch (NoResultException e) {
			return new Configuracao(BigDecimal.ZERO, BigDecimal.ZERO);
		}
	}
}
