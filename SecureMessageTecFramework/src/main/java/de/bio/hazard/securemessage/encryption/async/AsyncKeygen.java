package de.bio.hazard.securemessage.encryption.async;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

public class AsyncKeygen {
	
	private KeyPairGenerator generator;
	
	public AsyncKeygen() throws NoSuchAlgorithmException, NoSuchProviderException{
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		generator = KeyPairGenerator.getInstance("RSA", "BC");
	}
	
	public AsyncKey getAsyncKey(int keySizeInBits){
		KeyPair kp = getKeyPair(keySizeInBits);
		return new AsyncKey(kp.getPublic().getEncoded(), kp.getPrivate().getEncoded());
	}
	
	public KeyPair getKeyPair(int keySizeInBits){
		generator.initialize(keySizeInBits);
		return generator.genKeyPair();
	}
	
}
