package com.trevizan.business;

import java.util.List;

import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.trevizan.entities.Cliente;
import com.trevizan.entities.Conta;
import com.trevizan.exception.ClienteBusinessException;
import com.trevizan.service.ClienteService;
import com.trevizan.service.ContaService;

@Named
@SessionScoped
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ClienteBusiness implements ClienteService {

	private static final long serialVersionUID = 350473379576921699L;

	@PersistenceContext
	private EntityManager entityManager;
	
	@Inject 
	ContaService contaService;

	@Override
	@Transactional
	public void salvarCliente(Cliente cliente) throws ClienteBusinessException {
		if(!existeClienteCadastrado(cliente)){
			cliente.setConta(criarContaCliente());
			entityManager.persist(cliente);
			entityManager.flush();
			
		} else {
			throw new ClienteBusinessException("Já existe um cliente cadastrado com o nome '" + cliente.getNome() + "'.");
		}
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> buscarClientes() {
		return entityManager.createNamedQuery(Cliente.BUSCAR_TODOS_CLIENTES).getResultList();
	}
	
	private boolean existeClienteCadastrado(Cliente cliente) {
		Long countCliente = (Long) entityManager.createNamedQuery(Cliente.COUNT_CLIENTES_POR_NOME)
										.setParameter("nome", cliente.getNome())
										.getSingleResult();
		return countCliente.compareTo(0L) == 1 ;
	}

	private Conta criarContaCliente() {
		Conta conta = new Conta();
		contaService.salvarConta(conta);
		return conta;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Cliente> buscarClientesPorNome(String nomeBusca) {
		return entityManager.createNamedQuery(Cliente.CLIENTES_POR_NOME)
						.setParameter("nome", "%" + nomeBusca + "%")
						.getResultList();
	}

	public Cliente buscarClientePorId(Long idCiente) {
		return entityManager.find(Cliente.class, idCiente);
	}

	@Override
	@Transactional
	public void excluirCliente(Cliente cliente) {
		cliente = entityManager.find(Cliente.class, cliente.getIdCliente());
		entityManager.remove(cliente);
		entityManager.flush();
	}

}
