package com.trevizan.business;

import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.trevizan.entities.RegistroFechamento;
import com.trevizan.service.RegistroFechamentoService;

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
}
