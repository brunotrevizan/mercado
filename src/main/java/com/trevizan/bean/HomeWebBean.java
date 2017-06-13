package com.trevizan.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.trevizan.entities.Usuario;
import com.trevizan.service.HomeService;
import com.trevizan.util.Util;

@Named
@ViewScoped
public class HomeWebBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private HomeService homeService;
	
	private Usuario usuarioLogado;
	
	@PostConstruct
	public void inicializar(){
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		usuarioLogado = (Usuario) sessionMap.get("usuarioLogado");
	}
	
	public String totalClientesCadastrados(){
		return homeService.totalClientesCadastrados();
	}
	
	public String totalAReceber(){
		return homeService.totalAReceber();
	}
	
	public String totalXeroxMes(){
		return homeService.getTotalXeroxMes();
	}
	
	public String totalSegundaViaMes(){
		return homeService.getTotalSegundaViaMes();
	}
	
	public String buscarDiaAtual(){
		return Util.getDateFormatter().format(new Date());
	}
	
	public String buscarHoraAtual(){
		return Util.getHoraFormatter().format(new Date())+ "h";
	}
	
	public String buscarSaudacao() {
		if(getUsuarioLogado() == null){
			return "Olá usuário.";
		}
		return "Olá " + getUsuarioLogado().getNome();
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
}
