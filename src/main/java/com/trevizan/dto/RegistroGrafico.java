package com.trevizan.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class RegistroGrafico {

	private String label;
	private BigDecimal valor;
	private BigInteger quantidade;

	public RegistroGrafico() {
	}

	public RegistroGrafico(String label, BigDecimal valor, BigInteger quantidade) {
		this.label = label;
		this.valor = valor;
		this.quantidade = quantidade;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigInteger getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigInteger quantidade) {
		this.quantidade = quantidade;
	}

}
