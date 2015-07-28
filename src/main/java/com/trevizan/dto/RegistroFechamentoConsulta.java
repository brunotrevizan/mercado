package com.trevizan.dto;

public class RegistroFechamentoConsulta {

	private String mes;
	private String ano;

	public RegistroFechamentoConsulta() {
	}

	public RegistroFechamentoConsulta(String mes, String ano) {
		this.mes = mes;
		this.ano = ano;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

}
