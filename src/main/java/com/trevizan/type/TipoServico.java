package com.trevizan.type;

public enum TipoServico {
	SEGUNDA_VIA("SEGUNDA_VIA"), XEROX("XEROX");

	private String valor;

	TipoServico(String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}

}
