package com.trevizan.bean;

import java.io.Serializable;
import java.util.Date;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.trevizan.service.HomeService;
import com.trevizan.util.Util;

@Named
@ViewScoped
public class HomeWebBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private HomeService homeService;
	
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
	
	@SuppressWarnings("deprecation")
	public String buscarSaudacao() {
		int hora = new Date().getHours();
		if (hora < 12) {
			return "Bom dia";
		} else if (hora > 12 && hora < 19) {
			return "Boa tarde";
		} else {
			return "Boa noite";
		}
	}
}
