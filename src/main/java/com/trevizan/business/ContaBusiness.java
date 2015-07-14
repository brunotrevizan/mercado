package com.trevizan.business;

import java.util.List;

import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.trevizan.entities.Cliente;
import com.trevizan.entities.Conta;
import com.trevizan.entities.Registro;
import com.trevizan.service.ContaService;

@Named
@SessionScoped
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ContaBusiness implements ContaService {

	private static final long serialVersionUID = 350473379576921699L;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void salvarConta(Conta conta) {
		entityManager.persist(conta);
		entityManager.flush();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Registro> buscarRegistrosPorCliente(Cliente cliente) {
		return entityManager.createNamedQuery(Registro.REGISTRO_POR_CONTA)
				.setParameter("idConta",cliente.getConta().getIdConta())
				.getResultList();
	}

}
