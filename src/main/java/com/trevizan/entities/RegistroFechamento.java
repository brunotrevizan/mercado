package com.trevizan.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Check;

@Entity
@Table(name = "registro_fechamento")
@NamedQueries({
	@NamedQuery(name = RegistroFechamento.REGISTROS_CAIXA_POR_MES_E_ANO, 
			query = "SELECT r "
				+ " FROM RegistroFechamento r " 
				+ "WHERE "
				+ "YEAR(r.dataRegistro) = :ano "
				+ "AND MONTH(r.dataRegistro) = :mes "
				+ "AND r.tipoRegistro = 'CAIXA'"),
	@NamedQuery(name = RegistroFechamento.REGISTROS_GASTOS_POR_MES_E_ANO, 
				query = "SELECT r "
						+ " FROM RegistroFechamento r " 
						+ "WHERE "
						+ "YEAR(r.dataRegistro) = :ano "
						+ "AND MONTH(r.dataRegistro) = :mes "
						+ "AND r.tipoRegistro = 'GASTOS'")
})
public class RegistroFechamento {

	public static final String REGISTROS_CAIXA_POR_MES_E_ANO = "REGISTROS_CAIXA_POR_MES_E_ANO";
	public static final String REGISTROS_GASTOS_POR_MES_E_ANO = "REGISTROS_GASTOS_POR_MES_E_ANO";

	@Id
	@SequenceGenerator(name = "registro_fechamento_seq", sequenceName = "registro_fechamento_seq", allocationSize = 1)
	@GeneratedValue(generator = "registro_fechamento_seq", strategy = GenerationType.AUTO)
	@Column(name = "id_registro_fechamento")
	private Long idRegistroFechamento;

	@Column(name = "tipo")
	@Check(constraints = "tipo IN ('CAIXA','GASTOS')")
	private String tipoRegistro;

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

	public String getTipoRegistro() {
		return tipoRegistro;
	}

	public void setTipoRegistro(String tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}

}
