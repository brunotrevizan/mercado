package com.trevizan.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.trevizan.entities.Cliente;
import com.trevizan.exception.ClienteBusinessException;
import com.trevizan.service.ClienteService;
import com.trevizan.service.UsuarioService;

@Named
@ViewScoped
public class ClientesWebBean implements Serializable {

	private static final long serialVersionUID = -1120239980034439511L;

	@Inject
	private UsuarioService usuarioService;

	@Inject
	private ClienteService clienteService;

	private Cliente cliente;

	private List<Cliente> clientes;
	
	private String nomeBusca;

	@PostConstruct
	public void initialize() {
		setCliente(new Cliente());
		buscarClientes();
	}

	public void buscarClientes() {
		setClientes(clienteService.buscarClientes());
	}
	
	public void buscarClientesPorNome() {
		setClientes(clienteService.buscarClientesPorNome(nomeBusca));
	}
	
	public void excluirCliente(Cliente cliente){
		clienteService.excluirCliente(cliente);
		buscarClientes();
	}
	
	public void editarCliente(Cliente cliente){
		this.cliente = cliente;
	}
	
	public void fecharEdicaoCliente(){
		cliente = new Cliente();
	}

	public void salvarCliente() {
		try {
			clienteService.salvarCliente(cliente);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente '" + cliente.getNome()+ "' cadastrado com sucesso.", ""));
			cliente = new Cliente();
			buscarClientes();
		} catch (ClienteBusinessException e) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
	}
	
	public void salvarEditarCliente() {
		try {
			clienteService.salvarEditarCliente(cliente);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente '" + cliente.getNome()+ "' editado com sucesso.", ""));
			cliente = new Cliente();
		} catch (ClienteBusinessException e) {
			buscarClientes();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public ClienteService getClienteService() {
		return clienteService;
	}

	public void setClienteService(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public String getNomeBusca() {
		return nomeBusca;
	}

	public void setNomeBusca(String nomeBusca) {
		this.nomeBusca = nomeBusca;
	}
}
