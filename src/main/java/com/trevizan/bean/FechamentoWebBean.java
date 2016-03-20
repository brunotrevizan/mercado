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

import com.trevizan.dto.RegistroFechamentoConsulta;
import com.trevizan.dto.RegistroGrafico;
import com.trevizan.entities.RegistroFechamento;
import com.trevizan.service.RegistroFechamentoService;
import com.trevizan.util.ValorFormatter;

@Named
@ViewScoped
public class FechamentoWebBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private RegistroFechamentoService registroFechamentoService;

	private RegistroFechamento registroFechamento;
	private RegistroFechamentoConsulta registroFechamentoConsulta;
	private List<RegistroFechamento> registrosCaixa;
	private List<RegistroFechamento> registrosGastos;
	
	private LineChartModel balancoAnual;

	@PostConstruct
	public void init() {
		inicializarRegistroFechamento();
		inicializarRegistroFechamentoConsulta();
		popularRegistros();
		popularRegistrosAnual();
	}

	private void inicializarRegistroFechamento() {
		setRegistroFechamento(new RegistroFechamento());
		getRegistroFechamento().setDataRegistro(new Date());
	}

	public void popularRegistros() {
		setRegistrosCaixa(registroFechamentoService.buscarRegistrosCaixa(registroFechamentoConsulta));
		setRegistrosGastos(registroFechamentoService.buscarRegistrosGastos(registroFechamentoConsulta));
	}
	
	public List<String> buscarAnosDisponiveis(){
		return registroFechamentoService.buscarAnosDisponiveis();
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
		BigDecimal valorMaximo = registroFechamentoService.buscarValorMaximoGraficoBalancoGeral(registroFechamentoConsulta.getAno());
		return valorMaximo != null ? valorMaximo.add(new BigDecimal(500)) : BigDecimal.ZERO;
	}

	private LineChartModel inicializarBalancoAnual() {
        LineChartModel model = new LineChartModel();
        ChartSeries caixa = popularCaixaBalancoAnual();
        ChartSeries gastos = popularGastosBalancoAnual();
        model.addSeries(caixa);
        model.addSeries(gastos);
         
        return model;
    }

	private ChartSeries popularGastosBalancoAnual() {
		ChartSeries registrosGastos = new ChartSeries();
		registrosGastos.setLabel("Gastos");
		List<RegistroGrafico> registrosGastosGrafico = registroFechamentoService.buscarRegistrosGraficoAnual(registroFechamentoConsulta.getAno(), "GASTOS");
		for (RegistroGrafico registroGrafico : registrosGastosGrafico) {
			registrosGastos.set(registroGrafico.getLabel(), registroGrafico.getValor());
		}
		return registrosGastos;
	}

	private ChartSeries popularCaixaBalancoAnual() {
		ChartSeries registrosCaixa = new ChartSeries();
		registrosCaixa.setLabel("Caixa");
		List<RegistroGrafico> registrosCaixaGrafico = registroFechamentoService.buscarRegistrosGraficoAnual(registroFechamentoConsulta.getAno(), "CAIXA");
		for (RegistroGrafico registroGrafico : registrosCaixaGrafico) {
			registrosCaixa.set(registroGrafico.getLabel(), registroGrafico.getValor());
		}
		return registrosCaixa;
	}
	
	public String totalCaixa() {
		try {
			return registroFechamentoService.getTotalRegistros(getRegistrosCaixa());
		} catch (Exception e) {
			return ValorFormatter.formatarValor(0d, true);
		}
	}
	
	public String totalGastos(){
		try {
			return registroFechamentoService.getTotalRegistros(getRegistrosGastos());
		} catch (Exception e) {
			return ValorFormatter.formatarValor(0d, true);
		}
	}
	
	public String lucro(){
		try {
			return registroFechamentoService.getLucro(getRegistrosCaixa());
		} catch (Exception e) {
			return ValorFormatter.formatarValor(0d, true);
		}
	}
	
	public String totalCaixaMenosGastos(){
		try {
			return registroFechamentoService.totalCaixaMenosGastos(getRegistrosCaixa(), getRegistrosGastos());
		} catch (Exception e) {
			return ValorFormatter.formatarValor(0d, true);
		}
	}
	
	public String totalCaixaAnual(){
		try {
			return registroFechamentoService.getTotalCaixaAnual(registroFechamentoConsulta.getAno());
		} catch (Exception e) {
			return ValorFormatter.formatarValor(0d, true);
		}
	}
	
	public String totalGastosAnual(){
		try {
			return registroFechamentoService.getTotalGastosAnual(registroFechamentoConsulta.getAno());
		} catch (Exception e) {
			return ValorFormatter.formatarValor(0d, true);
		}
	}
	
	public String lucroAnual(){
		try {
			return registroFechamentoService.getLucroAnual(registroFechamentoConsulta.getAno());
		} catch (Exception e) {
			return ValorFormatter.formatarValor(0d, true);
		}
	}
	
	public String totalCaixaMenosGastosAnual(){
		try {
			return registroFechamentoService.totalCaixaMenosGastosAnual(registroFechamentoConsulta.getAno());
		} catch (Exception e) {
			return ValorFormatter.formatarValor(0d, true);
		}
	}

	private void inicializarRegistroFechamentoConsulta() {
		setRegistroFechamentoConsulta(new RegistroFechamentoConsulta());
		getRegistroFechamentoConsulta().setAno(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
		getRegistroFechamentoConsulta().setMes(String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1));
	}
	
	public void salvarRegistro(){
		registroFechamentoService.salvarRegistro(registroFechamento);
		registroFechamento = new RegistroFechamento();
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro cadastrado com sucesso", ""));
		popularRegistros();
		popularRegistrosAnual();
		inicializarRegistroFechamento();
	}
	
	public void excluirRegistro(RegistroFechamento registroFechamento){
		registroFechamentoService.excluirRegistro(registroFechamento);
		popularRegistros();
	}

	public boolean desabilitarComboAno(){
		return registroFechamentoConsulta.getMes() == null || "".equals(registroFechamentoConsulta.getMes());
	}
	
	public RegistroFechamento getRegistroFechamento() {
		return registroFechamento;
	}

	public void setRegistroFechamento(RegistroFechamento registroFechamento) {
		this.registroFechamento = registroFechamento;
	}

	public RegistroFechamentoConsulta getRegistroFechamentoConsulta() {
		return registroFechamentoConsulta;
	}

	public void setRegistroFechamentoConsulta(RegistroFechamentoConsulta registroFechamentoConsulta) {
		this.registroFechamentoConsulta = registroFechamentoConsulta;
	}

	public List<RegistroFechamento> getRegistrosCaixa() {
		return registrosCaixa;
	}

	public void setRegistrosCaixa(List<RegistroFechamento> registrosCaixa) {
		this.registrosCaixa = registrosCaixa;
	}

	public List<RegistroFechamento> getRegistrosGastos() {
		return registrosGastos;
	}

	public void setRegistrosGastos(List<RegistroFechamento> registrosGastos) {
		this.registrosGastos = registrosGastos;
	}

	public LineChartModel getBalancoAnual() {
		return balancoAnual;
	}

	public void setBalancoAnual(LineChartModel balancoAnual) {
		this.balancoAnual = balancoAnual;
	}

}
