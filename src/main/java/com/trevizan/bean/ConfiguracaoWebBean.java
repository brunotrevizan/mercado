package com.trevizan.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.trevizan.entities.Configuracao;
import com.trevizan.service.ConfiguracaoService;

@Named
@ViewScoped
public class ConfiguracaoWebBean implements Serializable {

	private static final long serialVersionUID = -1120239980034439511L;

	@Inject
	private ConfiguracaoService configuracaoService;

	private Configuracao configuracao;

	@PostConstruct
	public void initialize() {
		popularConfiguracao();
	}

	public void salvarConfiguracao(){
		popularConfiguracaoNova();
		configuracaoService.salvarConfiguracao(configuracao);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Configuração salva com sucesso.", ""));
		popularConfiguracao();
	}
	
	private void popularConfiguracaoNova() {
		Configuracao novaConfiguracao = new Configuracao();
		novaConfiguracao.setValorSegundaVia(configuracao.getValorSegundaViaBigDecimal());
		novaConfiguracao.setValorXerox(configuracao.getValorXeroxBigDecimal());
		configuracao = novaConfiguracao;
	}

	private void popularConfiguracao() {
		configuracao = configuracaoService.buscarConfiguracaoAtiva();
	}

	public Configuracao getConfiguracao() {
		return configuracao;
	}

	public void setConfiguracao(Configuracao configuracao) {
		this.configuracao = configuracao;
	}

}
