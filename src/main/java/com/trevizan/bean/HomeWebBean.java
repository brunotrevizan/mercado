package com.trevizan.bean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.trevizan.service.HomeService;

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
}
