package com.trevizan.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import com.trevizan.business.ClienteBusiness;
import com.trevizan.entities.Cliente;

@FacesConverter(value = "clienteConverter")
public class ClienteConverter implements Converter {

	@Inject
	ClienteBusiness clienteBusiness;

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value != null && !value.isEmpty()) {
			return clienteBusiness.buscarClientePorId(Long.parseLong(value));
		}
		return null;
	}

	@Override  
	    public String getAsString(FacesContext context, UIComponent component, Object value) {  
	        if (value instanceof Cliente) {  
	        	Cliente cliente = (Cliente) value;  
	            return String.valueOf(cliente.getIdCliente());  
	        }  
	        return "";  
	    }}