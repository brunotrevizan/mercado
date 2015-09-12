package com.trevizan.util;

import java.text.SimpleDateFormat;

public class Util {
	public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
	public static SimpleDateFormat simpleHoraFormat = new SimpleDateFormat("HH:mm");
	
	public static SimpleDateFormat getDateFormatter(){
		return simpleDateFormat;
	}
	
	public static SimpleDateFormat getHoraFormatter(){
		return simpleHoraFormat;
	}
}
