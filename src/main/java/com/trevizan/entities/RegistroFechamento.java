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
						+ "AND r.tipoRegistro = 'GASTOS'"),
	@NamedQuery(name = RegistroFechamento.REGISTROS_CAIXA_POR_ANO, 
				query = "SELECT SUM(r.valor) "
						+ " FROM RegistroFechamento r " 
						+ "WHERE "
						+ "YEAR(r.dataRegistro) = :ano "
						+ "AND r.tipoRegistro = 'CAIXA'"),
	@NamedQuery(name = RegistroFechamento.REGISTROS_GASTOS_POR_ANO, 
				query = "SELECT SUM(r.valor) "
						+ " FROM RegistroFechamento r " 
						+ "WHERE "
						+ "YEAR(r.dataRegistro) = :ano "
						+ "AND r.tipoRegistro = 'GASTOS'"),
	@NamedQuery(name = RegistroFechamento.ANOS_REGISTROS, 
				query = "SELECT DISTINCT CAST(YEAR(r.dataRegistro) AS string) "
						+ " FROM RegistroFechamento r ORDER BY 1")
})
public class RegistroFechamento {

	public static final String REGISTROS_CAIXA_POR_MES_E_ANO = "REGISTROS_CAIXA_POR_MES_E_ANO";
	public static final String REGISTROS_GASTOS_POR_MES_E_ANO = "REGISTROS_GASTOS_POR_MES_E_ANO";
	public static final String REGISTROS_CAIXA_POR_ANO = "REGISTROS_CAIXA_POR_ANO";
	public static final String REGISTROS_GASTOS_POR_ANO = "REGISTROS_GASTOS_POR_ANO";
	public static final String ANOS_REGISTROS = "ANOS_REGISTROS";

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
	
	public static String getConsultaRegistrosAnualPorMes(String tipo){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT CAST(EXTRACT(MONTH FROM r.data_registro) AS INTEGER) as mes, ");
		sql.append(" sum(r.valor) AS valor ");
		sql.append(" FROM ");
		sql.append(" registro_fechamento r ");
		sql.append("  WHERE  ");
		sql.append(" EXTRACT(YEAR from r.data_registro) = :ano ");
		sql.append(" AND r.tipo = '"+tipo+"' ");
		sql.append("GROUP BY 1 ");
		sql.append("ORDER BY 1 ");
		return sql.toString();
	}
	
	public static String getConsultaValorMaximoMesPorAno(String tipo){
		StringBuilder sql = new StringBuilder();
		sql.append(" select max(valor) from (");
		sql.append(" SELECT");
		sql.append("         sum(r.valor) AS valor,");
		sql.append("          EXTRACT(MONTH from r.data_registro) as mes");
		sql.append("     FROM");
		sql.append("         registro_fechamento r   ");
		sql.append("                 WHERE ");
		sql.append("                     EXTRACT(YEAR "); 
		sql.append("                 from ");
		sql.append("                     r.data_registro) = :ano");
		sql.append("                     AND r.tipo = '"+ tipo + "'" );
		sql.append("                             group by mes ");
		sql.append("                             order by mes) as valorMaximoMes ");
		return sql.toString();
	}

}
