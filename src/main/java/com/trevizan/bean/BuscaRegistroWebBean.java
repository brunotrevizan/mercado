package com.trevizan.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.trevizan.entities.Cliente;
import com.trevizan.entities.Registro;
import com.trevizan.service.ClienteService;
import com.trevizan.service.ContaService;
import com.trevizan.service.RegistroService;
import com.trevizan.service.UsuarioService;

@Named
@ViewScoped
public class BuscaRegistroWebBean implements Serializable {

	private static final String VALOR_ZERO_REAIS = "R$ 0,00";

	private static final long serialVersionUID = -8861855822589790172L;

	@Inject
	private UsuarioService usuarioService;

	@Inject
	private ClienteService clienteService;
	
	@Inject
	private ContaService contaService;
	
	@Inject
	private RegistroService registroService;
	
	private Cliente cliente;
	
	private List<Cliente> clientes;
	private List<Registro> registros;
	
	private String nomeBusca;

	@PostConstruct
	public void initialize() {
		buscarClientes();
	}
	
	public void buscarClientes() {
		setClientes(clienteService.buscarClientes());
	}
	
	public String totalDevedorCliente(){
		if(cliente != null){
			return registroService.buscarTotalDevedorCliente(cliente);
		}
		return VALOR_ZERO_REAIS;
	}
	
	public void excluirRegistro(Registro registro){
		registroService.excluirRegistro(registro);
		buscarRegistrosPorCliente();
	}
	
	public void buscarRegistrosPorCliente(){
		if(cliente != null){
			registros = contaService.buscarRegistrosPorCliente(cliente);
		}
	}
	
	public String getMaiorCompraCliente(){
		return registroService.getMaiorCompraCliente(cliente);
	}
	
	public String getMenorCompraCliente(){
		return registroService.getMenorCompraCliente(cliente);
	}
	
	public void buscarClientesPorNome() {
		setClientes(clienteService.buscarClientesPorNome(nomeBusca));
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

	public List<Registro> getRegistros() {
		return registros;
	}

	public void setRegistros(List<Registro> registros) {
		this.registros = registros;
	}
}
