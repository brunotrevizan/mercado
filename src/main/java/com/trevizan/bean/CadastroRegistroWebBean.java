package com.trevizan.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.trevizan.entities.Cliente;
import com.trevizan.entities.Registro;
import com.trevizan.entities.types.TipoRegistro;
import com.trevizan.service.ClienteService;
import com.trevizan.service.ContaService;
import com.trevizan.service.RegistroService;
import com.trevizan.service.UsuarioService;

@Named
@ViewScoped
public class CadastroRegistroWebBean implements Serializable {

	private static final long serialVersionUID = -8861855822589790172L;

	@Inject
	private UsuarioService usuarioService;

	@Inject
	private ClienteService clienteService;
	
	@Inject
	private RegistroService registroService;

	@Inject
	private ContaService contaService;

	private Cliente cliente;
	private Registro registro;

	private List<Cliente> clientes;
	
	private List<TipoRegistro> tipos;

	@PostConstruct
	public void initialize() {
		buscarClientes();
		setTipos(popularTiposRegistro());
		registro = new Registro();
	}

	private List<TipoRegistro> popularTiposRegistro() {
		return Arrays.asList(TipoRegistro.values());
	}

	public void salvarRegistro(){
		registro.setDataRegistro(new Date());
		registroService.salvarRegistro(registro);
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro para o cliente " + registro.getConta().getCliente().getNome() + " foi inserido com sucesso.", ""));
		registro= new Registro();
	}
	
	public void buscarClientes() {
		setClientes(clienteService.buscarClientes());
	}

	public List<Registro> buscarRegistrosPorCliente() {
		return contaService.buscarRegistrosPorConta(cliente.getConta());
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

	public Registro getRegistro() {
		return registro;
	}

	public void setRegistro(Registro registro) {
		this.registro = registro;
	}

	public List<TipoRegistro> getTipos() {
		return tipos;
	}

	public void setTipos(List<TipoRegistro> tipos) {
		this.tipos = tipos;
	}

}
