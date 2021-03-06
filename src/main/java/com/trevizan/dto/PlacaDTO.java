package com.trevizan.dto;

public class PlacaDTO {
	private String nome;
	private String valor;
	private boolean segundaPlaca;
	private boolean virada;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public boolean isSegundaPlaca() {
		return segundaPlaca;
	}

	public void setSegundaPlaca(boolean segundaPlaca) {
		this.segundaPlaca = segundaPlaca;
	}

	public boolean isVirada() {
		return virada;
	}

	public void setVirada(boolean virada) {
		this.virada = virada;
	}

}