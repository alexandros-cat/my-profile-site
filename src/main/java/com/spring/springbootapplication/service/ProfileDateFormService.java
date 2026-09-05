package com.spring.springbootapplication.service;

import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class ProfileDateFormService {
    // Date → String
	public String dateToString(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = dateFormat.format(date);
		return strDate;
	}
    
}
