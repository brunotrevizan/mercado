package com.trevizan.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;

import com.trevizan.entities.Cliente;
import com.trevizan.entities.Conta;
import com.trevizan.entities.Registro;

@Named
public interface ContaService extends Serializable {
	public void salvarConta(Conta conta);

	public List<Registro> buscarRegistrosPorCliente(Cliente cliente);
}
