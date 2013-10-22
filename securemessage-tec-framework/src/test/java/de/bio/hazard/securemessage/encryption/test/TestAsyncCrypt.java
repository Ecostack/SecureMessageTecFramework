package de.bio.hazard.securemessage.encryption.test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.NoSuchPaddingException;

import junit.framework.TestCase;
import de.bio.hazard.securemessage.tecframework.encryption.asymmetric.AsymmetricCrypt;
import de.bio.hazard.securemessage.tecframework.encryption.asymmetric.AsymmetricKey;
import de.bio.hazard.securemessage.tecframework.encryption.asymmetric.AsymmetricKeygen;

public class TestAsyncCrypt extends TestCase {

	public void testEmpty() {

	}

	public void _testAsyncCrypt() throws NoSuchAlgorithmException,
			NoSuchProviderException, NoSuchPaddingException, IOException {
		boolean assertBoolean = false;
		AsymmetricKeygen keygen = new AsymmetricKeygen();
		AsymmetricKey keys = keygen.getKey(2048);
		AsymmetricCrypt crypt = new AsymmetricCrypt();

		byte[] data = new byte[4000];

		for (int i = 0; i < data.length; i++) {
			data[i] = 'x';
		}

		// printByteArray("data",data);
		// System.err.println("1.PRIVATE 2.PUBLIC");
		byte[] encrypted = crypt.encrypt(data, keys.getPrivateKey(), true);
		assertBoolean = false;
		for (int i = 0; i < data.length; i++) {
			assertBoolean |= (data[i] != encrypted[i]);
		}
		assertTrue(assertBoolean);
		// printByteArray(encrypted);
		byte[] decrypted = crypt.decrypt(encrypted, keys.getPublicKey(), false);
		assertTrue(data.length == decrypted.length);
		for (int i = 0; i < data.length; i++) {
			assertTrue(data[i] == decrypted[i]);
		}

		// printByteArray("decrypt",decrypt);
		// System.err.println("1.PUBLIC 2.PRIVATE");
		// printByteArray("data",data);
		encrypted = crypt.encrypt(data, keys.getPublicKey(), false);
		assertBoolean = false;
		for (int i = 0; i < data.length; i++) {
			assertBoolean |= (data[i] != encrypted[i]);
		}
		assertTrue(assertBoolean);
		// printByteArray(encrypted);
		decrypted = crypt.decrypt(encrypted, keys.getPrivateKey(), true);
		// printByteArray("decrypt",decrypt);
		assertTrue(data.length == decrypted.length);
		for (int i = 0; i < data.length; i++) {
			assertTrue(data[i] == decrypted[i]);
		}
	}

	private void printByteArray(String name, byte[] pArray) {
		System.err.println("Print byte array (" + name + "): ");
		for (byte b : pArray) {
			// System.out.printf("0x%02X", b);
			System.out.print((char) b);
		}
		System.err.println(" ");
	}
}
