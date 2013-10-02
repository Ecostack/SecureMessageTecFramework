package de.bio.hazard.securemessage.tecframework.data.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	public static String DATE_LONG_FORMAT = "dd.MM.yyyy hh:mm:ss SSS";

	public static Date transformStringToDateMaxForm(String pDate)
			throws ParseException {
		SimpleDateFormat lcSDF = new SimpleDateFormat(DATE_LONG_FORMAT);
		return lcSDF.parse(pDate);
	}

	public static String transformDateMaxFormToString(Date pDate)
			throws ParseException {
		SimpleDateFormat lcSDF = new SimpleDateFormat(DATE_LONG_FORMAT);
		return lcSDF.format(pDate);
	}
}
