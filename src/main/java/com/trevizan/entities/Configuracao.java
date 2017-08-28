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

@Entity
@Table(name = "configuracao")
@NamedQueries({
	@NamedQuery(name = Configuracao.CONFIGURACAO_ATIVA,
			query = "SELECT c"
					+ " FROM Configuracao c "
					+ "WHERE ativa = 't'"),
	@NamedQuery(name = Configuracao.CONFIGURACAO_POR_DATA_SERVICO,
			query = "SELECT c"
					+ " FROM Configuracao c "
					+ "WHERE :data BETWEEN DATE(c.inicio) AND DATE(c.termino) OR (:data >= DATE(c.inicio)"
					+ " AND DATE(c.termino) IS NULL)")
})
public class Configuracao {

	public static final String CONFIGURACAO_ATIVA = "CONFIGURACAO_ATIVA";

	public static final String CONFIGURACAO_POR_DATA_SERVICO = "CONFIGURACAO_POR_DATA_SERVICO";
	
	@Id
	@SequenceGenerator(name = "configuracao_seq", sequenceName = "configuracao_seq", allocationSize = 1)
	@GeneratedValue(generator = "configuracao_seq", strategy = GenerationType.AUTO)
	@Column(name = "id_configuracao")
	private Long idConfiguracao;

	@Column(name = "valor_segunda_via")
	private BigDecimal valorSegundaVia;

	@Column(name = "valor_xerox")
	private BigDecimal valorXerox;

	@Column(name = "valor_impressao_foto")
	private BigDecimal valorImpressaoFoto;
	
	@Column(name = "ativa")
	private boolean ativa;

	@Column(name = "inicio")
	private Date inicio;
	
	@Column(name = "termino")
	private Date termino;
	
	public Configuracao() {
	}

	public Configuracao(BigDecimal valorSegundaVia, BigDecimal valorXerox, Date inicio, Date termino) {
		this.valorSegundaVia = valorSegundaVia;
		this.valorXerox = valorXerox;
		this.inicio = inicio;
		this.termino = termino;
	}
	
	public Configuracao(BigDecimal valorSegundaVia, BigDecimal valorXerox) {
		this.valorSegundaVia = valorSegundaVia;
		this.valorXerox = valorXerox;
	}

	public Long getIdConfiguracao() {
		return idConfiguracao;
	}

	public void setIdConfiguracao(Long idConfiguracao) {
		this.idConfiguracao = idConfiguracao;
	}

	public BigDecimal getValorSegundaViaBigDecimal() {
		return valorSegundaVia;
	}
	
	public BigDecimal getValorSegundaVia() {
		return valorSegundaVia;
	}

	public void setValorSegundaVia(BigDecimal valorSegundaVia) {
		this.valorSegundaVia = valorSegundaVia;
	}

	public BigDecimal getValorXerox() {
		return valorXerox;
	}
	
	public BigDecimal getValorXeroxBigDecimal() {
		return valorXerox;
	}

	public void setValorXerox(BigDecimal valorXerox) {
		this.valorXerox = valorXerox;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getTermino() {
		return termino;
	}

	public void setTermino(Date termino) {
		this.termino = termino;
	}

	public boolean isAtiva() {
		return ativa;
	}

	public void setAtiva(boolean ativa) {
		this.ativa = ativa;
	}

	public BigDecimal getValorImpressaoFoto() {
		return valorImpressaoFoto;
	}

	public void setValorImpressaoFoto(BigDecimal valorImpressaoFoto) {
		this.valorImpressaoFoto = valorImpressaoFoto;
	}

}
