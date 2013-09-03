package de.bio.hazard.securemessage.encryption.test;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import junit.framework.TestCase;

public class TestEncryption2 extends TestCase {

	PublicKey publicKey = null;
	PrivateKey privateKey = null;

	String originalText = "";
	byte[] encryptedByteArray = null;

	Cipher cipher = null;

	public void setUp() throws NoSuchAlgorithmException,
			NoSuchProviderException, NoSuchPaddingException {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		cipher = Cipher.getInstance("RSA", "BC");
		
		
		Random lcRandom = new Random();
		StringBuilder lcMeinTestString = new StringBuilder();
		for (int i = 0; i < 512; i++) {
			lcMeinTestString.append("a");
		}

		originalText = lcMeinTestString.toString();
		
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA", "BC");
		keyGen.initialize(4096);
		KeyPair kp = keyGen.genKeyPair();

		publicKey = kp.getPublic();
		privateKey = kp.getPrivate();
	}

//	public void test1RSAwithBC() throws NoSuchAlgorithmException,
//			NoSuchProviderException {
//
//		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA", "BC");
//		keyGen.initialize(4096);
//		KeyPair kp = keyGen.genKeyPair();
//
//		publicKey = kp.getPublic();
//		privateKey = kp.getPrivate();
//
//	}

//	public void test2GenerateText() {
//		Random lcRandom = new Random();
//		StringBuilder lcMeinTestString = new StringBuilder();
//		for (int i = 0; i < 512; i++) {
//			lcMeinTestString.append("a");
//		}
//
//		originalText = lcMeinTestString.toString();
//	}

	public void testbEncrypt() throws InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {
		
		Long lcTime = System.currentTimeMillis();

		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		System.err.println("cipher init encrypt: " + (System.currentTimeMillis() - lcTime));
		lcTime = System.currentTimeMillis();

		encryptedByteArray = cipher.doFinal(originalText.getBytes());
		System.err.println("encrypt and store: " + (System.currentTimeMillis() - lcTime));
		lcTime = System.currentTimeMillis();
		
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		System.err.println("cipher init decrypt: " + (System.currentTimeMillis() - lcTime));
		lcTime = System.currentTimeMillis();
		byte[] y = cipher.doFinal(encryptedByteArray);
		System.err.println("decrypt and store: " + (System.currentTimeMillis() - lcTime));
		lcTime = System.currentTimeMillis();

		assertTrue(new String(y).equals(originalText));

	}

//	public void testaDecrypt() throws InvalidKeyException,
//			IllegalBlockSizeException, BadPaddingException {
//		cipher.init(Cipher.DECRYPT_MODE, privateKey);
//		byte[] y = cipher.doFinal(encryptedByteArray);
//
//		assertTrue(new String(y).equals(originalText));
//
//	}
}
