package de.bio.hazard.securemessage.encryption.test;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import junit.framework.TestCase;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.RSAKeyGenerationParameters;

public class TestEnryption extends TestCase {

	public void testRSA() {
		Random lcRandom = new Random();
		StringBuilder lcMeinTestString = new StringBuilder();
		for (int i = 0; i < 2000; i++) {
			lcMeinTestString.append(lcRandom.nextInt()+"");
		}
		System.err.println(lcMeinTestString.toString());
		
		interalTest(lcMeinTestString.toString());

	}

	private void interalTest(String pText) {
		AsymmetricCipherKeyPair lcKeyPair = generateKeys(4000);

		String lcHallo = pText;

		byte[] lcEncrypted = encrypt(lcHallo.getBytes(), lcKeyPair.getPrivate());
		byte[] lcDecrypted = decrypt(lcEncrypted, lcKeyPair.getPublic());
		
		String lcDecryptedPrivToPub = new String(lcDecrypted);
		
		assertTrue(lcHallo.equals(lcDecryptedPrivToPub));
		
		lcEncrypted = encrypt(lcHallo.getBytes(), lcKeyPair.getPublic());
		lcDecrypted = decrypt(lcEncrypted, lcKeyPair.getPrivate());
		
		String lcDecryptedPubToPriv = new String(lcDecrypted);
		
		assertTrue(lcHallo.equals(lcDecryptedPubToPriv));
	}
	

	private void printByteArray(byte[] pArray) {
		System.err.println("Print byte array: ");
		for (byte b : pArray) {
			System.out.printf("0x%02X", b);
		}
		System.err.println(" ");
	}

	public AsymmetricCipherKeyPair generateKeys(int keySizeInBits) {
		RSAKeyPairGenerator r = new RSAKeyPairGenerator();
		r.init(new RSAKeyGenerationParameters(new BigInteger("10001", 16),
				new SecureRandom(), keySizeInBits, 80));

		AsymmetricCipherKeyPair keys = r.generateKeyPair();
		return keys;
	}

	public byte[] encrypt(byte[] data, AsymmetricKeyParameter key) {
		RSAEngine e = new RSAEngine();
		e.init(true, key);

		int blockSize = e.getInputBlockSize();

		List<Byte> output = new ArrayList<Byte>();

		for (int chunkPosition = 0; chunkPosition < data.length; chunkPosition += blockSize) {
			int chunkSize = Math.max(Math.min(blockSize, data.length
					- (chunkPosition * blockSize)),0);

			addByteArrayToByteList(
					e.processBlock(data, chunkPosition, chunkSize), output);
		}
		return classByteListToPrimitiveByteArray(output);
	}

	private byte[] classByteListToPrimitiveByteArray(List<Byte> pList) {

		Byte[] lcArray = pList.toArray(new Byte[pList.size()]);

		return classByteArrayToPrimitiveByteArray(lcArray);

	}

	private byte[] classByteArrayToPrimitiveByteArray(Byte[] pByteArray) {
		byte[] lcResult = new byte[pByteArray.length];

		for (int i = 0; i < pByteArray.length; i++) {
			byte b = pByteArray[i];
			lcResult[i] = b;
		}

		return lcResult;

	}

	public byte[] decrypt(byte[] data, AsymmetricKeyParameter key) {
		RSAEngine e = new RSAEngine();
		e.init(false, key);

		int blockSize = e.getInputBlockSize();

		List<Byte> output = new ArrayList<Byte>();

		for (int chunkPosition = 0; chunkPosition < data.length; chunkPosition += blockSize) {
			int chunkSize = Math.max(Math.min(blockSize, data.length
					- (chunkPosition * blockSize)),0);

			addByteArrayToByteList(
					e.processBlock(data, chunkPosition, chunkSize), output);
		}
		return classByteListToPrimitiveByteArray(output);
	}

	private void addByteArrayToByteList(byte[] pArray, List<Byte> pList) {
		for (Byte lcByte : pArray) {
			pList.add(lcByte);
		}
	}

	// private void generate() {
	//
	// try {
	//
	// Security.addProvider(new
	// org.bouncycastle.jce.provider.BouncyCastleProvider());
	//
	// // Create the public and private keys
	// KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA",
	// "BC");
	// Base64 b64 = new Base64();
	//
	// SecureRandom random = createFixedRandom();
	// System.err.println("first int: " +random.nextInt());
	//
	// generator.initialize(4096, random);
	//
	// KeyPair pair = generator.generateKeyPair();
	// Key pubKey = pair.getPublic();
	// Key privKey = pair.getPrivate();
	//
	// String lcTestString = "Hallo";
	//
	//
	// RSAEngine lcRSAEngine = new RSAEngine();
	//
	// AsymmetricKeyParameter lcAsymmetricKeyParameter = new
	// AsymmetricKeyParameter(true);
	//
	//
	//
	// lcRSAEngine.init(true, lcAsymmetricKeyParameter);
	//
	// lcRSAEngine.processBlock(lcTestString, inOff, inLen)
	//
	// lcRSAEngine.
	//
	//
	//
	//
	//
	// } catch (Exception e) {
	// System.out.println(e);
	// }
	// }
	//
	// public static SecureRandom createFixedRandom() {
	// return new SecureRandom();
	// }
	//
	// public static void printByteArray(byte[] pArray) {
	// System.err.println("Byte Array:");
	// for (byte lcByte : pArray) {
	// System.err.print(lcByte + ";");
	// }
	// System.err.println("");
	// }
	//
	// private static class FixedRand extends SecureRandom {
	//
	// MessageDigest sha;
	// byte[] state;
	//
	// FixedRand() {
	// try {
	// this.sha = MessageDigest.getInstance("SHA-1");
	// this.state = sha.digest();
	// printByteArray(sha.digest());
	// printByteArray(sha.digest());
	// } catch (NoSuchAlgorithmException e) {
	// throw new RuntimeException("can't find SHA-1!");
	// }
	// }
	//
	// public void nextBytes(byte[] bytes) {
	//
	// int off = 0;
	//
	// sha.update(state);
	//
	// while (off < bytes.length) {
	// state = sha.digest();
	//
	// if (bytes.length - off > state.length) {
	// System.arraycopy(state, 0, bytes, off, state.length);
	// } else {
	// System.arraycopy(state, 0, bytes, off, bytes.length - off);
	// }
	//
	// off += state.length;
	//
	// sha.update(state);
	// }
	// }
	// }
}
