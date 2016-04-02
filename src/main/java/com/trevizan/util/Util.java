package com.trevizan.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

public class Util {
	public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
	public static SimpleDateFormat simpleHoraFormat = new SimpleDateFormat("HH:mm");

	public static SimpleDateFormat getDateFormatter() {
		return simpleDateFormat;
	}

	public static SimpleDateFormat getHoraFormatter() {
		return simpleHoraFormat;
	}

	public static BigDecimal getMaiorValor(BigDecimal valorUm, BigDecimal valorDois) {
		if (valorUm == null && valorDois == null) {
			return BigDecimal.ZERO;
		}
		if (valorUm == null) {
			return valorDois;
		}
		if (valorDois == null) {
			return valorUm;
		}
		if (valorUm.compareTo(valorDois) == 1) {
			return valorUm;
		}
		return valorDois;
	}
}
