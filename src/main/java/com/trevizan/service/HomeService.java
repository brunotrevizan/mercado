package com.trevizan.service;

import java.io.Serializable;

import javax.inject.Named;

@Named
public interface HomeService extends Serializable {

	String totalClientesCadastrados();

	String totalAReceber();

	String getTotalXeroxMes();

	String getTotalSegundaViaMes();
	
}
