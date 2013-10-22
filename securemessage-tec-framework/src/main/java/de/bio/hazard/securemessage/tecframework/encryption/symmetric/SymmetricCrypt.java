package de.bio.hazard.securemessage.tecframework.encryption.symmetric;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class SymmetricCrypt {
	private Cipher cipher;
	
	public SymmetricCrypt() throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException{
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		cipher = Cipher.getInstance("AES", "BC");
	}
	
	public byte[] encrypt(byte[] data, byte[] key) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException{
		return innerCrypt(data, key, Cipher.ENCRYPT_MODE);
	}
	
	public byte[] decrypt(byte[] data, byte[] key) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		return innerCrypt(data, key, Cipher.DECRYPT_MODE);
	}
	
	private byte[] innerCrypt(byte[] data, byte[] key,int mode) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException{
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
		cipher.init(mode, skeySpec);
		return cipher.doFinal(data);
	}
}
