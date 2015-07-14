package com.trevizan.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Check;

@Entity
@Table(name = "registro")
@NamedQueries({
	@NamedQuery(name = Registro.REGISTRO_POR_CONTA, 
			query = "SELECT r "
			+ " FROM Registro r " 
			+ "WHERE r.conta.idConta = :idConta"),
	@NamedQuery(name = Registro.SOMA_REGISTRO_CLIENTE_DEBITO, 
			query = "SELECT SUM(r.valor) "
			+ " FROM Registro r " 
			+ "WHERE r.conta.idConta = :idConta "
			+ " AND r.tipoRegistro = 'Débito'"),
	@NamedQuery(name = Registro.SOMA_REGISTRO_CLIENTE_PAGAMENTO, 
			query = "SELECT SUM(r.valor) "
			+ " FROM Registro r " 
			+ "WHERE r.conta.idConta = :idConta "
			+ "AND r.tipoRegistro = 'Pagamento'"),
	@NamedQuery(name = Registro.MAIOR_COMPRA_CLIENTE, 
			query = "SELECT MAX(r.valor) "
				+ " FROM Registro r " 
				+ "WHERE r.conta.idConta = :idConta"),
	@NamedQuery(name = Registro.MENOR_COMPRA_CLIENTE, 
				query = "SELECT MIN(r.valor) "
					+ " FROM Registro r " 
					+ "WHERE r.conta.idConta = :idConta")
})
public class Registro {

	public static final String REGISTRO_POR_CONTA = "REGISTRO_POR_CONTA";

	public static final String SOMA_REGISTRO_CLIENTE_DEBITO = "SOMA_REGISTRO_CLIENTE_DEBITO";
	
	public static final String SOMA_REGISTRO_CLIENTE_PAGAMENTO = "SOMA_REGISTRO_CLIENTE_PAGAMENTO";
	
	public static final String MAIOR_COMPRA_CLIENTE = "MAIOR_COMPRA_CLIENTE";
	
	public static final String MENOR_COMPRA_CLIENTE = "MENOR_COMPRA_CLIENTE";

	@Id
	@SequenceGenerator(name = "registro_seq", sequenceName = "registro_seq", allocationSize = 1)
	@GeneratedValue(generator = "registro_seq", strategy = GenerationType.AUTO)
	@Column(name = "id_registro")
	private Long idRegistro;

	@ManyToOne
	@JoinColumn(name="conta_id_conta", nullable=false, updatable=false)
	private Conta conta;

	@Column(name = "valor")
	private BigDecimal valor;

	@Column(name = "tipo")
	@Check(constraints = "tipo IN ('DEBITO','PAGAMENTO')")
	private String tipoRegistro;
	
	@Column(name = "data_registro")
	private Date dataRegistro;

	public Registro() {
	}

	public Registro(Long idRegistro, Conta conta, BigDecimal valor, String tipoRegistro, Date dataRegistro) {
		this.idRegistro = idRegistro;
		this.conta = conta;
		this.valor = valor;
		this.tipoRegistro = tipoRegistro;
		this.dataRegistro = dataRegistro;
	}

	public Long getIdRegistro() {
		return idRegistro;
	}

	public void setIdRegistro(Long idRegistro) {
		this.idRegistro = idRegistro;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getTipoRegistro() {
		return tipoRegistro;
	}

	public void setTipoRegistro(String tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}

	public Date getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(Date dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

}
