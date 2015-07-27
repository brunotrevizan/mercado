package com.trevizan.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "registro_fechamento")
public class RegistroFechamento {

	@Id
	@SequenceGenerator(name = "registro_fechamento_seq", sequenceName = "registro_fechamento_seq", allocationSize = 1)
	@GeneratedValue(generator = "registro_fechamento_seq", strategy = GenerationType.AUTO)
	@Column(name = "id_registro_fechamento")
	private Long idRegistroFechamento;

	@Column(name = "valor")
	private BigDecimal valor;

	@Column(name = "data_registro")
	private Date dataRegistro;

	public RegistroFechamento() {
	}
	
	public RegistroFechamento(Long idRegistroFechamento, BigDecimal valor, Date dataRegistro) {
		this.idRegistroFechamento = idRegistroFechamento;
		this.valor = valor;
		this.dataRegistro = dataRegistro;
	}

	public Long getIdRegistroFechamento() {
		return idRegistroFechamento;
	}

	public void setIdRegistroFechamento(Long idRegistroFechamento) {
		this.idRegistroFechamento = idRegistroFechamento;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Date getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(Date dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

}
