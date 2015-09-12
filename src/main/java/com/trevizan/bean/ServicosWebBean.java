package com.trevizan.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

import com.trevizan.dto.RegistroGrafico;
import com.trevizan.dto.ServicosConsulta;
import com.trevizan.entities.Servico;
import com.trevizan.service.ServicosService;
import com.trevizan.type.TipoServico;
import com.trevizan.util.ValorFormatter;

@Named
@ViewScoped
public class ServicosWebBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ServicosService servicosService;

	private Servico servico;
	private ServicosConsulta servicosConsulta;
	private List<Servico> servicosSegundaVia;
	private List<Servico> servicosXerox;
	
	private LineChartModel balancoAnual;

	@PostConstruct
	public void initialize() {
		inicializarServicoConsulta();
		inicializarServico();
		popularRegistros();
		criarGraficoBalancoAnual();
	}
	
	private void inicializarServicoConsulta() {
		servicosConsulta = new ServicosConsulta();
		getServicosConsulta().setAno(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
		getServicosConsulta().setMes(String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1));
	}
	
	public void popularRegistrosAnual(){
		criarGraficoBalancoAnual();
	}
	
	private void criarGraficoBalancoAnual() {
		balancoAnual = inicializarBalancoAnual();
		balancoAnual.setTitle("Balanço Anual");
		balancoAnual.setLegendPosition("e");
		balancoAnual.setAnimate(true);
		Axis xAxis = balancoAnual.getAxis(AxisType.X);
		Axis yAxis = balancoAnual.getAxis(AxisType.Y);
        xAxis.setMin(0);
        xAxis.setMax(12);
        xAxis.setLabel("Meses");
        yAxis = balancoAnual.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(getValorMaximoGrafico());
	}
	
	private Object getValorMaximoGrafico() {
		BigDecimal valorMaximo = servicosService.buscarValorMaximoGraficoBalancoGeral(servicosConsulta.getAno());
		return valorMaximo != null ? valorMaximo.add(new BigDecimal(500)) : BigDecimal.ZERO;
	}

	private LineChartModel inicializarBalancoAnual() {
        LineChartModel model = new LineChartModel();
        ChartSeries caixa = popularXeroxBalancoAnual();
        ChartSeries gastos = popularSegundaViaBalancoAnual();
        model.addSeries(caixa);
        model.addSeries(gastos);
         
        return model;
    }
	
	private ChartSeries popularSegundaViaBalancoAnual() {
		ChartSeries registrosGastos = new ChartSeries();
		registrosGastos.setLabel("Segunda Via");
		List<RegistroGrafico> registrosGastosGrafico = servicosService.buscarRegistrosGraficoAnual(servicosConsulta.getAno(), TipoServico.SEGUNDA_VIA.getValor());
		for (RegistroGrafico registroGrafico : registrosGastosGrafico) {
			registrosGastos.set(registroGrafico.getLabel(), registroGrafico.getQuantidade());
		}
		return registrosGastos;
	}
	
	private ChartSeries popularXeroxBalancoAnual() {
		ChartSeries registrosGastos = new ChartSeries();
		registrosGastos.setLabel("Xerox");
		List<RegistroGrafico> registrosGastosGrafico = servicosService.buscarRegistrosGraficoAnual(servicosConsulta.getAno(), TipoServico.XEROX.getValor());
		for (RegistroGrafico registroGrafico : registrosGastosGrafico) {
			registrosGastos.set(registroGrafico.getLabel(), registroGrafico.getQuantidade());
		}
		return registrosGastos;
	}
	
	public List<String> buscarAnosDisponiveis(){
		return servicosService.buscarAnosDisponiveis();
	}
	
	private void inicializarServico() {
		servico = new Servico();
		servico.setDataRegistro(new Date());
		servico.setQuantidade(1);
	}

	public String totalSegundaVia() {
		int count = 0;
		for (Servico servico : servicosSegundaVia) {
			count += servico.getQuantidade();
		}
		return String.valueOf(count);
	}
	
	public String totalSegundaViaAnual(){
		try {
			return servicosService.getTotalSegundaViaAnual(servicosConsulta.getAno());
		} catch (Exception e) {
			return ValorFormatter.formatarValor(0d, true);
		}
	}
	
	public String valorTotalSegundaViaAnual(){
		return servicosService.getValorTotalSegundaViaAnual(servicosConsulta.getAno());
	}
	
	public String valorTotalXeroxAnual(){
		return servicosService.getValorTotalXeroxAnual(servicosConsulta.getAno());
	}
	
	public String totalXeroxAnual(){
		try {
			return servicosService.getTotalXeroxAnual(servicosConsulta.getAno());
		} catch (Exception e) {
			return ValorFormatter.formatarValor(0d, true);
		}
	}
	
	public String totalXerox(){
		int count = 0;
		for (Servico servico : servicosXerox) {
			count += servico.getQuantidade();
		}
		return String.valueOf(count);
	}
	
	public String totalValorSegundaVia(){
		return servicosService.getTotalValorServico(servicosSegundaVia, TipoServico.SEGUNDA_VIA.getValor());
	}
	
	public String totalValorXerox(){
		return servicosService.getTotalValorServico(servicosXerox, TipoServico.XEROX.getValor());
	}
	
	public void salvarServico() {
		servicosService.salvarServico(servico);
		inicializarServico();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Serviço cadastrado com sucesso.", ""));
		popularRegistros();
		criarGraficoBalancoAnual();
	}

	public void excluirServico(Servico servico) {
		servicosService.excluirServico(servico);
		popularRegistros();
	}

	public void popularRegistros() {
		setServicosSegundaVia(servicosService.buscarRegistrosSegundaVia(servicosConsulta));
		setServicosXerox(servicosService.buscarRegistrosXerox(servicosConsulta));
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public List<Servico> getServicosSegundaVia() {
		return servicosSegundaVia;
	}

	public void setServicosSegundaVia(List<Servico> servicosSegundaVia) {
		this.servicosSegundaVia = servicosSegundaVia;
	}

	public List<Servico> getServicosXerox() {
		return servicosXerox;
	}

	public void setServicosXerox(List<Servico> servicosXerox) {
		this.servicosXerox = servicosXerox;
	}

	public ServicosConsulta getServicosConsulta() {
		return servicosConsulta;
	}

	public void setServicosConsulta(ServicosConsulta servicosConsulta) {
		this.servicosConsulta = servicosConsulta;
	}

	public LineChartModel getBalancoAnual() {
		return balancoAnual;
	}

	public void setBalancoAnual(LineChartModel balancoAnual) {
		this.balancoAnual = balancoAnual;
	}

}
