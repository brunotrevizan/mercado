package com.trevizan.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@NamedQueries(value = { 
		@NamedQuery(name = Usuario.USUARIO_POR_USUARIO_E_SENHA, 
			query = "SELECT u FROM Usuario u "
			+ "WHERE u.login = :login "
			+ "AND u.senha = :senha"),
		@NamedQuery(name = Usuario.USUARIO_POR_USUARIO, 
			query = "SELECT u FROM Usuario u "
			+ "WHERE u.login = :login ")
})
@Table(name = "usuario")
public class Usuario {
	
	public static final String USUARIO_POR_USUARIO_E_SENHA = "USUARIO_POR_USUARIO_E_SENHA";
	public static final String USUARIO_POR_USUARIO = "USUARIO_POR_USUARIO";

	@Id
	@SequenceGenerator(name = "usuario_seq", sequenceName = "usuario_seq", allocationSize = 1)
	@GeneratedValue(generator = "usuario_seq", strategy = GenerationType.AUTO)
	@Column(name = "id_usuario")
	private Long idUsuario;

	@Column(name = "nome")
	private String nome;

	@Column(name = "login")
	private String login;

	@Column(name = "email")
	private String email;

	@Column(name = "senha")
	private String senha;

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
