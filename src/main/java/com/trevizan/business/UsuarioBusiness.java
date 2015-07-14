package com.trevizan.business;

import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.trevizan.entities.Usuario;
import com.trevizan.service.UsuarioService;

@Named
@SessionScoped
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UsuarioBusiness implements UsuarioService {

	private static final long serialVersionUID = 350473379576921699L;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void salvarUsuario(Usuario usuario) {
		entityManager.persist(usuario);
		entityManager.flush();
	}

}
