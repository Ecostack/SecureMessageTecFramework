package de.bio.hazard.securemessage.encryption.test;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import junit.framework.TestCase;

import de.bio.hazard.securemessage.encryption.sync.SyncCrypt;
import de.bio.hazard.securemessage.encryption.sync.SyncKeygen;

public class TestSyncCrypt extends TestCase {
	public void testSyncCrypt() throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		SyncKeygen keygen=new SyncKeygen();
		SyncCrypt crypt=new SyncCrypt();
		byte[] key=keygen.getSyncKey(128);
		
		StringBuilder lcMeinTestString = new StringBuilder();
		for (int i = 0; i < 500; i++) {
			lcMeinTestString.append("a");
		}

		String originalText = lcMeinTestString.toString();
		
		byte[] encrypted=crypt.encrypt(originalText.getBytes(), key);
		assertTrue(!(new String(encrypted).equals(originalText)));
		byte[] decrypted=crypt.decrypt(encrypted, key);
		assertTrue(new String(decrypted).equals(originalText));
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
