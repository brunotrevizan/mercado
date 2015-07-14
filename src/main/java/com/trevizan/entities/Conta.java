package com.trevizan.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "conta")
public class Conta {

	@Id
	@SequenceGenerator(name = "conta_seq", sequenceName = "conta_seq", allocationSize = 1)
	@GeneratedValue(generator = "conta_seq", strategy = GenerationType.AUTO)
	@Column(name = "id_conta")
	private Long idConta;

	@OneToOne(optional=false, mappedBy="conta")
	private Cliente cliente;

	@OneToMany(mappedBy="conta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Registro> registros;

	public Conta() {
	}

	public Conta(Long idConta, Cliente cliente, List<Registro> registros) {
		this.idConta = idConta;
		this.cliente = cliente;
		this.registros = registros;
	}

	public Long getIdConta() {
		return idConta;
	}

	public void setIdConta(Long idConta) {
		this.idConta = idConta;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Registro> getRegistros() {
		return registros;
	}

	public void setRegistros(List<Registro> registros) {
		this.registros = registros;
	}

}
