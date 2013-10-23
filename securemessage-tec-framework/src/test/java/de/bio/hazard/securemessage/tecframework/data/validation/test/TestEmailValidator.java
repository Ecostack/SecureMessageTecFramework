package de.bio.hazard.securemessage.tecframework.data.validation.test;

import static org.junit.Assert.*;
import junit.framework.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import de.bio.hazard.securemessage.tecframework.data.validation.EmailValidator;

public class TestEmailValidator {

	private static EmailValidator emailValidator;

	@BeforeClass
	public static void setUp() {
		emailValidator = new EmailValidator();
	}

	public static String[] validMails = { "testme@yahoo.com",
			"testme-100@yahoo.com", "testme.100@yahoo.com",
			"testme111@testme.com", "testme-100@testme.net",
			"testme.100@testme.com.au", "testme@1.com", "testme@gmail.com.com",
			"testme+100@gmail.com", "testme-100@yahoo-test.com" };

	public static String[] invalidMails = { "testme", "testme@.com.my",
			"testme123@gmail.a", "testme123@.com", "testme123@.com.com",
			".testme@testme.com", "testme()*@gmail.com", "testme@%*.com",
			"testme..2002@gmail.com", "testme.@gmail.com",
			"testme@testme@gmail.com", "testme@gmail.com.1a" };

	@Test
	public void testValidMailsByFormat() {
		checkMailsByFormat(validMails, true);
	}

	@Test
	public void testInvalidMailsByFormat() {
		checkMailsByFormat(invalidMails, false);
	}

	private void checkMailsByFormat(String[] values, boolean expectedValue) {
		for (String check : values) {
			boolean valid = emailValidator.validateFormat(check);
			System.out.println(String.format("%1$s==%2$s\t %3$s", valid,
					expectedValue, check));
			assertEquals(valid, expectedValue);
		}
	}
}