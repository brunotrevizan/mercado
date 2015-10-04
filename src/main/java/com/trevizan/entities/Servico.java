package com.trevizan.entities;

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
@Table(name = "servico")
@NamedQueries({
	@NamedQuery(name = Servico.SERVICOS_SEGUNDA_VIA_POR_ANO, 
			query = "SELECT s "
				+ " FROM Servico s " 
				+ "WHERE "
				+ "YEAR(s.dataRegistro) = :ano "
				+ "AND s.tipoServico = 'SEGUNDA_VIA'"),
	@NamedQuery(name = Servico.SERVICOS_XEROX_POR_ANO, 
			query = "SELECT s "
				+ " FROM Servico s " 
				+ "WHERE "
				+ "YEAR(s.dataRegistro) = :ano "
				+ "AND s.tipoServico = 'XEROX'"),
	@NamedQuery(name = Servico.SERVICOS_SEGUNDA_VIA_POR_MES_E_ANO, 
			query = "SELECT s "
				+ " FROM Servico s " 
				+ "WHERE "
				+ "YEAR(s.dataRegistro) = :ano "
				+ "AND MONTH(s.dataRegistro) = :mes "
				+ "AND s.tipoServico = 'SEGUNDA_VIA'"),
	@NamedQuery(name = Servico.SERVICOS_XEROX_POR_MES_E_ANO, 
			query = "SELECT s "
				+ " FROM Servico s " 
				+ "WHERE "
				+ "YEAR(s.dataRegistro) = :ano "
				+ "AND MONTH(s.dataRegistro) = :mes "
				+ "AND s.tipoServico = 'XEROX'"),
	@NamedQuery(name = Servico.ANOS_SERVICOS, 
			query = "SELECT DISTINCT CAST(YEAR(s.dataRegistro) AS string) "
				+ " FROM Servico s "),
	@NamedQuery(name = Servico.QUANTIDADE_SERVICOS_SEGUNDA_VIA_POR_ANO, 
			query = "SELECT SUM(s.quantidade) "
					+ " FROM Servico s " 
					+ "WHERE "
					+ "YEAR(s.dataRegistro) = :ano "
					+ "AND s.tipoServico = 'SEGUNDA_VIA'"),
	@NamedQuery(name = Servico.QUANTIDADE_SERVICOS_XEROX_POR_ANO, 
			query = "SELECT SUM(s.quantidade) "
					+ " FROM Servico s " 
					+ "WHERE "
					+ "YEAR(s.dataRegistro) = :ano "
					+ "AND s.tipoServico = 'XEROX'"),
	@NamedQuery(name = Servico.SERVICO_POR_DIA_E_TIPO, 
		query = "SELECT servico "
				+ " FROM Servico servico " 
				+ "WHERE "
				+ "servico.dataRegistro = :data "
				+ "AND servico.tipoServico = :tipo")
})
public class Servico {
	public static final String SERVICOS_SEGUNDA_VIA_POR_ANO = "SERVICOS_SEGUNDA_VIA_POR_ANO";
	public static final String SERVICOS_XEROX_POR_ANO = "SERVICOS_XEROX_POR_ANO";
	public static final String SERVICOS_SEGUNDA_VIA_POR_MES_E_ANO = "SERVICOS_SEGUNDA_VIA_POR_MES_E_ANO";
	public static final String SERVICOS_XEROX_POR_MES_E_ANO = "SERVICOS_XEROX_POR_MES_E_ANO";
	public static final String ANOS_SERVICOS = "ANOS_SERVICOS";
	public static final String QUANTIDADE_SERVICOS_SEGUNDA_VIA_POR_ANO = "QUANTIDADE_SERVICOS_SEGUNDA_VIA_POR_ANO";
	public static final String QUANTIDADE_SERVICOS_XEROX_POR_ANO = "QUANTIDADE_SERVICOS_XEROX_POR_ANO";
	public static final String SERVICO_POR_DIA_E_TIPO = "SERVICO_POR_DIA_E_TIPO";

	@Id
	@SequenceGenerator(name = "servico_seq", sequenceName = "servico_seq", allocationSize = 1)
	@GeneratedValue(generator = "servico_seq", strategy = GenerationType.AUTO)
	@Column(name = "id_servico")
	private Long idServico;

	@Column(name = "quantidade")
	private Integer quantidade;

	@Column(name = "tipo")
	@Check(constraints = "tipo IN ('XEROX','SEGUNDA_VIA')")
	private String tipoServico;

	@Column(name = "data_registro")
	private Date dataRegistro;

	public Servico() {
	}

	public Servico(Integer quantidade, String tipoServico, Date dataRegistro) {
		this.quantidade = quantidade;
		this.tipoServico = tipoServico;
		this.dataRegistro = dataRegistro;
	}

	public Long getIdServico() {
		return idServico;
	}

	public void setIdServico(Long idServico) {
		this.idServico = idServico;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public String getTipoServico() {
		return tipoServico;
	}

	public void setTipoServico(String tipoServico) {
		this.tipoServico = tipoServico;
	}

	public Date getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(Date dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public static String getConsultaValorMaximoMesPorAno(){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT max(s.quantidade) AS qtd ");
		sql.append(" FROM ");
		sql.append(" servico s ");
		sql.append("  WHERE  ");
		sql.append(" EXTRACT(YEAR from s.data_registro) = :ano ");
		return sql.toString();
	}
	
	public static String getConsultaRegistrosAnualPorMes(String tipo){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT CAST(EXTRACT(MONTH FROM s.data_registro) AS INTEGER) as mes, ");
		sql.append(" sum(s.quantidade) AS qtd ");
		sql.append(" FROM ");
		sql.append(" servico s ");
		sql.append("  WHERE  ");
		sql.append(" EXTRACT(YEAR from s.data_registro) = :ano ");
		sql.append(" AND s.tipo = '"+tipo+"' ");
		sql.append("GROUP BY 1 ");
		sql.append("ORDER BY 1 ");
		return sql.toString();
	}
	
}
