package com.trevizan.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Named;

import com.trevizan.entities.Configuracao;

@Named
public interface ConfiguracaoService extends Serializable {
	
	public void salvarConfiguracao(Configuracao configuracao);

	public Configuracao buscarConfiguracaoAtiva();
	
	public Configuracao buscarConfiguracaoPorDataServico(Date dataServico);

}
