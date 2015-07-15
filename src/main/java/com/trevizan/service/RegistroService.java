package com.trevizan.service;

import java.io.Serializable;

import javax.inject.Named;

import com.trevizan.entities.Cliente;
import com.trevizan.entities.Registro;

@Named
public interface RegistroService extends Serializable {
	
	public void salvarRegistro(Registro registro);

	public void excluirRegistro(Registro registro);

	public String buscarTotalDevedorCliente(Cliente cliente);

	public String getMaiorCompraCliente(Cliente cliente);

	public String getMenorCompraCliente(Cliente cliente);

	public boolean verificarClienteSaldoPositivo(Cliente cliente);

}
