package com.trevizan.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class ValorFormatter {
	
	private static final String CIFRA = "R$ ";
	
	private static DecimalFormat df = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
	
	public static String formatarValor(double valor){
		return CIFRA + df.format(valor);
	}

}
