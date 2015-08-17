package com.trevizan.dto;

import java.math.BigDecimal;

public class RegistroGrafico {

	private String label;
	private BigDecimal valor;

	public RegistroGrafico() {
	}

	public RegistroGrafico(String label, BigDecimal valor) {
		this.label = label;
		this.valor = valor;
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

}
