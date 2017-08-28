package com.trevizan.type;

public enum TipoServico {
	SEGUNDA_VIA("SEGUNDA_VIA"),IMPRESSAO_FOTO("IMPRESSAO_FOTO"), XEROX("XEROX");

	private String valor;

	TipoServico(String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}

}
