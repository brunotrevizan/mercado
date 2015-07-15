package com.trevizan.business;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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

	public boolean verificaUsuarioPodeLogar(String email, String senha) {
		try {
			Usuario usuario = (Usuario) entityManager.createNamedQuery(Usuario.USUARIO_POR_EMAIL_E_SENHA)
					.setParameter("email", email)
					.setParameter("senha", convertStringToMd5(senha))
					.getSingleResult();
			return usuario != null;
		} catch (NoResultException e) {
			return false;
		}
	}
	
	private String convertStringToMd5(String valor) {
		MessageDigest mDigest;
		try {
			mDigest = MessageDigest.getInstance("MD5");

			byte[] valorMD5 = mDigest.digest(valor.getBytes("UTF-8"));

			StringBuffer sb = new StringBuffer();
			for (byte b : valorMD5) {
				sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();

		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

}
