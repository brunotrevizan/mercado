package com.trevizan.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "cliente")
@NamedQueries({
		@NamedQuery(name = Cliente.COUNT_CLIENTES_POR_NOME, query = "SELECT COUNT(c)"
				+ " FROM Cliente c " + "WHERE c.nome = :nome"),
		@NamedQuery(name = Cliente.CLIENTES_POR_NOME, query = "SELECT c " + " FROM Cliente c "
				+ "WHERE upper(c.nome) LIKE upper(:nome) ORDER BY nome"),
		@NamedQuery(name = Cliente.BUSCAR_TODOS_CLIENTES, query = "SELECT c FROM Cliente c ORDER BY nome"),
		@NamedQuery(name = Cliente.COUNT_CLIENTES, query = "SELECT COUNT(c) FROM Cliente c") 
})
public class Cliente {
	public static final String COUNT_CLIENTES_POR_NOME = "COUNT_CLIENTES_POR_NOME";
	public static final String CLIENTES_POR_NOME = "CLIENTES_POR_NOME";
	public static final String BUSCAR_TODOS_CLIENTES = "BUSCAR_TODOS_CLIENTES";
	public static final String COUNT_CLIENTES = "COUNT_CLIENTES";

	@Id
	@SequenceGenerator(name = "cliente_seq", sequenceName = "cliente_seq", allocationSize = 1)
	@GeneratedValue(generator = "cliente_seq", strategy = GenerationType.AUTO)
	@Column(name = "id_cliente")
	private Long idCliente;

	@Column(name = "nome")
	private String nome;

	@Column(name = "telefone")
	private String telefone;

	@OneToOne(optional=false)
    @JoinColumn(name="id_conta", unique=true, nullable=false, updatable=false)
	private Conta conta;

	public Cliente() {
	}

	public Cliente(Long idCliente, String nome, Conta conta) {
		this.idCliente = idCliente;
		this.nome = nome;
		this.conta = conta;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@Override
	public String toString() {
		return "Cliente [idCliente=" + idCliente + ", nome=" + nome + ", telefone=" + telefone
				+ ", conta=" + conta + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((conta == null) ? 0 : conta.hashCode());
		result = prime * result + ((idCliente == null) ? 0 : idCliente.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((telefone == null) ? 0 : telefone.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (conta == null) {
			if (other.conta != null)
				return false;
		} else if (!conta.equals(other.conta))
			return false;
		if (idCliente == null) {
			if (other.idCliente != null)
				return false;
		} else if (!idCliente.equals(other.idCliente))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (telefone == null) {
			if (other.telefone != null)
				return false;
		} else if (!telefone.equals(other.telefone))
			return false;
		return true;
	}

}
