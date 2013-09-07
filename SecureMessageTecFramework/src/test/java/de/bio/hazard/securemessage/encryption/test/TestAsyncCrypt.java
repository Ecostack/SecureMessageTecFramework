package de.bio.hazard.securemessage.encryption.test;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.crypto.NoSuchPaddingException;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import org.bouncycastle.crypto.params.RSAKeyGenerationParameters;

import de.bio.hazard.securemessage.encryption.async.AsyncCrypt;
import de.bio.hazard.securemessage.encryption.async.AsyncKey;
import de.bio.hazard.securemessage.encryption.async.AsyncKeygen;
import junit.framework.TestCase;

public class TestAsyncCrypt extends TestCase {
	public void testAsyncCrypt() throws NoSuchAlgorithmException,
			NoSuchProviderException, NoSuchPaddingException, IOException {
		boolean assertBoolean=false;
		AsyncKeygen keygen = new AsyncKeygen();
		AsyncKey keys = keygen.getAsyncKey(2048);
		AsyncCrypt crypt = new AsyncCrypt();

		byte[] data = new byte[4000];

		for (int i = 0; i < data.length; i++) {
			data[i] = 'x';
		}
		
		//printByteArray("data",data);
		//System.err.println("1.PRIVATE 2.PUBLIC");
		byte[] encrypted = crypt.encrypt(data, keys.getPrivateKey(),true);
		assertBoolean=false;
		for (int i = 0; i < data.length; i++) {
			assertBoolean |= (data[i] != encrypted[i]);
		}
		assertTrue(assertBoolean);
		//printByteArray(encrypted);
		byte[] decrypted = crypt.decrypt(encrypted, keys.getPublicKey(),false);
		assertTrue(data.length==decrypted.length);
		for (int i = 0; i < data.length; i++) {
			assertTrue(data[i] == decrypted[i]);
		}
		
		//printByteArray("decrypt",decrypt);
		//System.err.println("1.PUBLIC 2.PRIVATE");
		//printByteArray("data",data);
		encrypted = crypt.encrypt(data, keys.getPublicKey(),false);
		assertBoolean=false;
		for (int i = 0; i < data.length; i++) {
			assertBoolean |= (data[i] != encrypted[i]);
		}
		assertTrue(assertBoolean);
		//printByteArray(encrypted);
		decrypted = crypt.decrypt(encrypted, keys.getPrivateKey(),true);
		//printByteArray("decrypt",decrypt);
		assertTrue(data.length==decrypted.length);
		for (int i = 0; i < data.length; i++) {
			assertTrue(data[i] == decrypted[i]);
		}
	}

	private void printByteArray(String name,byte[] pArray) {
		System.err.println("Print byte array ("+name+"): ");
		for (byte b : pArray) {
			// System.out.printf("0x%02X", b);
			System.out.print((char) b);
		}
		System.err.println(" ");
	}
}
