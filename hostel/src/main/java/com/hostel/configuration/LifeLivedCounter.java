package com.hostel.configuration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LifeLivedCounter {

	public static void main(String ... args) throws ParseException{
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		Date birthDate =new Date();
		Date currDate =new Date();
		
		birthDate = dateFormatter.parse("23/10/1988 00:00");
		

		
		
	}
}
