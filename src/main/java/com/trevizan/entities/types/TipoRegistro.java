package com.trevizan.entities.types;

public enum TipoRegistro {
	DEBITO("Débito"), PAGAMENTO("Pagamento");
	public String tipoRegistro;

	TipoRegistro(String tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}

	public String getTipoRegistro() {
		return tipoRegistro;
	}
}
