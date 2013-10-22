package de.bio.hazard.securemessage.tecframework.data;

import java.math.BigInteger;
import java.security.SecureRandom;

public class IdGenerator {
	private SecureRandom random = new SecureRandom();

	public synchronized BigInteger getNextRandomNumber() {
		return new BigInteger(512, random);
	}

	public synchronized String nextId() {
		return getNextRandomNumber().toString(32);
	}
	
}
