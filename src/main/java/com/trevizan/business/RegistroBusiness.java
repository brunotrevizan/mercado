package com.trevizan.business;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.trevizan.entities.Cliente;
import com.trevizan.entities.Registro;
import com.trevizan.service.RegistroService;

@Named
@SessionScoped
@TransactionManagement(TransactionManagementType.CONTAINER)
public class RegistroBusiness implements RegistroService {

	private static final String CIFRA = "R$ ";

	private static final long serialVersionUID = 350473379576921699L;

	@PersistenceContext
	private EntityManager entityManager;
	
	private DecimalFormat df = new DecimalFormat("#.00");

	@Override
	@Transactional
	public void salvarRegistro(Registro registro) {
		entityManager.persist(registro);
		entityManager.flush();
	}

	@Override
	@Transactional
	public void excluirRegistro(Registro registro) {
		registro = entityManager.find(Registro.class, registro.getIdRegistro());
		entityManager.remove(registro);
		entityManager.flush();
	}

	@Override
	public String buscarTotalDevedorCliente(Cliente cliente) {
		BigDecimal somaRegistrosDebito = (BigDecimal) entityManager.createNamedQuery(Registro.SOMA_REGISTRO_CLIENTE_DEBITO)
				.setParameter("idConta", cliente.getConta().getIdConta())
				.getSingleResult();
		BigDecimal somaRegistrosPagamento = (BigDecimal) entityManager.createNamedQuery(Registro.SOMA_REGISTRO_CLIENTE_PAGAMENTO)
				.setParameter("idConta", cliente.getConta().getIdConta())
				.getSingleResult();
		if (somaRegistrosDebito != null) {
			return formatarValorRegistro(somaRegistrosDebito.subtract(somaRegistrosPagamento != null ? somaRegistrosPagamento : BigDecimal.ZERO));
		} else if (somaRegistrosPagamento != null){
			return formatarValorRegistro(somaRegistrosPagamento);
		}
		return formatarValorRegistro(BigDecimal.ZERO);
	}

	@Override
	public String getMaiorCompraCliente(Cliente cliente) {
		BigDecimal maiorCompra = (BigDecimal) entityManager.createNamedQuery(Registro.MAIOR_COMPRA_CLIENTE)
				.setParameter("idConta", cliente.getConta().getIdConta())
				.setMaxResults(1)
				.getSingleResult();
		return formatarValorRegistro(maiorCompra);
	}

	@Override
	public String getMenorCompraCliente(Cliente cliente) {
		BigDecimal menorCompra = (BigDecimal) entityManager.createNamedQuery(Registro.MENOR_COMPRA_CLIENTE)
				.setParameter("idConta", cliente.getConta().getIdConta())
				.setMaxResults(1)
				.getSingleResult();
		return formatarValorRegistro(menorCompra);
	}

	private String formatarValorRegistro(BigDecimal valorRegistro) {
		if (valorRegistro != null) {
			return CIFRA + df.format(valorRegistro.doubleValue());
		}
		return CIFRA + df.format(BigDecimal.ZERO.doubleValue());
	}
}
