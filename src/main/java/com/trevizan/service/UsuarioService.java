package com.trevizan.service;

import java.io.Serializable;

import javax.inject.Named;

import com.trevizan.entities.Usuario;

@Named
public interface UsuarioService extends Serializable {
	public void salvarUsuario(Usuario usuario);
	public boolean verificaUsuarioPodeLogar(String login, String senha);
}
