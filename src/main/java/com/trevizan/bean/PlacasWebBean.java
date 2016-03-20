package com.trevizan.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.trevizan.dto.PlacaDTO;
import com.trevizan.util.ValorFormatter;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Named
@ViewScoped
public class PlacasWebBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nome;
	private BigDecimal valor;
	private boolean segundaPlaca = false;
	private PlacaDTO placaDTO;
	
	@PostConstruct
	public void initialize() {
		setPlacaDTO(new PlacaDTO());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void imprimirPlaca() {
		try {
			popularPlacaDTO();
			FacesContext facesContext = FacesContext.getCurrentInstance();
			String relativeWebPath = "/pages/private/relatorios/duas_placas.jasper";
			ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
			String absoluteDiskPath = servletContext.getRealPath(relativeWebPath);
			
			List<PlacaDTO> lista = new ArrayList<PlacaDTO>();
			lista.add(placaDTO);
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(lista);
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(absoluteDiskPath, new HashMap(), beanCollectionDataSource);

	        httpServletResponse.addHeader("Content-disposition", "filename=report.pdf");
	        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
	        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
	        FacesContext.getCurrentInstance().responseComplete();
			limparAtributos();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		}
	}

	private void limparAtributos() {
		setNome(null);
		setValor(null);
	}

	private void popularPlacaDTO() {
		placaDTO.setNome(getNome());
		placaDTO.setValor(ValorFormatter.formatarValor(getValor().doubleValue(), true));
		placaDTO.setSegundaPlaca(isSegundaPlaca());
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public PlacaDTO getPlacaDTO() {
		return placaDTO;
	}

	public void setPlacaDTO(PlacaDTO placaDTO) {
		this.placaDTO = placaDTO;
	}

	public boolean isSegundaPlaca() {
		return segundaPlaca;
	}

	public void setSegundaPlaca(boolean segundaPlaca) {
		this.segundaPlaca = segundaPlaca;
	}

}
