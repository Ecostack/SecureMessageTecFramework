package de.bio.hazard.securemessage.encryption.test;

import de.bio.hazard.securemessage.encryption.hashing.BCrypt;
import junit.framework.TestCase;

public class TestBCrypt extends TestCase {

	public void testBCrypt() {
		String lcText = "Text to hash!";
		
		String lcHashed = BCrypt.hashpw(lcText, BCrypt.gensalt(15));
		System.err.println(lcText);
		System.err.println(lcHashed);
		
		
		
		System.err.println(BCrypt.checkpw(lcText, lcHashed));
		lcHashed = BCrypt.hashpw(lcText, BCrypt.gensalt(16));
		
	}
}
