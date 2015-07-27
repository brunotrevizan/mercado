package com.trevizan.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;

import com.trevizan.entities.Cliente;
import com.trevizan.exception.ClienteBusinessException;

@Named
public interface ClienteService extends Serializable {
	public void salvarCliente(Cliente cliente) throws ClienteBusinessException;

	public List<Cliente> buscarClientes();

	public List<Cliente> buscarClientesPorNome(String nomeBusca);

	public Cliente buscarClientePorId(Long idCiente);

	public void excluirCliente(Cliente cliente);

	void salvarEditarCliente(Cliente cliente) throws ClienteBusinessException;

	public String totalClientesCadastrados();
}
