package com.trevizan.business;

import java.util.Calendar;

import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.trevizan.service.ClienteService;
import com.trevizan.service.HomeService;
import com.trevizan.service.RegistroService;
import com.trevizan.service.ServicosService;

@Named
@SessionScoped
@TransactionManagement(TransactionManagementType.CONTAINER)
public class HomeBusiness implements HomeService {

	private static final long serialVersionUID = -6496663983273461331L;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Inject
	private ClienteService clienteService;
	
	@Inject
	private RegistroService registroService;
	
	@Inject
	ServicosService servicosService;

	@Override
	public String totalClientesCadastrados() {
		return clienteService.totalClientesCadastrados();
	}

	@Override
	public String totalAReceber() {
		return registroService.totalAReceber();
	}

	@Override
	public String getTotalXeroxMes() {
		return servicosService.getTotalXeroxMes(Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar.getInstance().get(Calendar.YEAR));
	}
	
	@Override
	public String getTotalSegundaViaMes() {
		return servicosService.getTotalSegundaViaMes(Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar.getInstance().get(Calendar.YEAR));
	}

}
