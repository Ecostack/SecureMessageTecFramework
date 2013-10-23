package de.bio.hazard.securemessage.encryption.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.bio.hazard.securemessage.tecframework.encryption.hashing.BCrypt;

public class TestBCrypt {

	@Test
	public void testBCrypt() {
		String lcText = "Text to hash!";
		String lcHashed = BCrypt.hashpw(lcText, BCrypt.gensalt(15));
		assertTrue(BCrypt.checkpw(lcText, lcHashed));
	}
}
