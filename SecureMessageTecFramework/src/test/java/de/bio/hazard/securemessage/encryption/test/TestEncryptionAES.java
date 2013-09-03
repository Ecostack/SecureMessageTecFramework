package de.bio.hazard.securemessage.encryption.test;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import junit.framework.TestCase;

import org.apache.commons.codec.DecoderException;

public class TestEncryptionAES extends TestCase {

	String originalText = "";

	public void setUp() throws NoSuchAlgorithmException,
			NoSuchProviderException, NoSuchPaddingException {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		Random lcRandom = new Random();
		StringBuilder lcMeinTestString = new StringBuilder();
		for (int i = 0; i < 1000000; i++) {
			lcMeinTestString.append("a");
		}

		originalText = lcMeinTestString.toString();
	}

	public void testOne() throws NoSuchAlgorithmException,
			NoSuchProviderException, DecoderException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		KeyGenerator kgen = KeyGenerator.getInstance("AES", "BC");
		kgen.init(128);
		SecretKey skey = kgen.generateKey();
		byte[] raw = skey.getEncoded();

		String key = new String(
				org.apache.commons.codec.binary.Hex.encodeHex(raw));
		// System.out.printf("My Secret Key: %s\n", key);

		byte[] keyByteArray = org.apache.commons.codec.binary.Hex.decodeHex(key
				.toCharArray());
		SecretKeySpec skeySpec = new SecretKeySpec(keyByteArray, "AES");

		Cipher cipher = Cipher.getInstance("AES", "BC");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(originalText.getBytes());

		String encryptedMessage = new String(
				org.apache.commons.codec.binary.Hex.encodeHex(encrypted));
		// System.out.printf("The encrypted message: %s\n", encryptedMessage);

		keyByteArray = org.apache.commons.codec.binary.Hex.decodeHex(key
				.toCharArray());
		skeySpec = new SecretKeySpec(keyByteArray, "AES");

		cipher.init(Cipher.DECRYPT_MODE, skeySpec);

		String originalMessage = new String(
				cipher.doFinal(org.apache.commons.codec.binary.Hex
						.decodeHex(encryptedMessage.toCharArray())));
		
		assertTrue(originalMessage.equals(originalText));
		// System.out.printf("The original message: %s\n", originalMessage);
	}

	// public void testTwo() throws Exception {
	//
	// SecureRandom lcSecureRandom = new SecureRandom();
	// byte[] lcKey = new byte[16];
	// lcSecureRandom.nextBytes(lcKey);
	//
	// /* Derive the key, given password and salt. */
	// // SecretKeyFactory factory =
	// // SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
	// // KeySpec spec = new PBEKeySpec(password, salt, 65536, 256);
	// // SecretKey tmp = factory.generateSecret(spec);
	// SecretKey secret = new SecretKeySpec(lcKey, "AES");
	// /* Encrypt the message. */
	// Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");
	// cipher.init(Cipher.ENCRYPT_MODE, secret);
	// AlgorithmParameters params = cipher.getParameters();
	// byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
	// byte[] ciphertext = cipher.doFinal("Hello, World!".getBytes("UTF-8"));
	//
	// // cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");
	// cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
	// // params = cipher.getParameters();
	// // iv = params.getParameterSpec(IvParameterSpec.class).getIV();
	// byte[] decryptText = cipher.doFinal(ciphertext);
	// System.err.println("Decrypted: "+ new String(decryptText, "UTF-8"));
	// }
}
