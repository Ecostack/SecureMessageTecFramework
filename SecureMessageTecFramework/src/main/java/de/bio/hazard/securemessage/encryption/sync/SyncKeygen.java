package de.bio.hazard.securemessage.encryption.sync;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class SyncKeygen {
	public SyncKeygen(){
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}
	
	public byte[] getSyncKey(int keySizeInBits) throws NoSuchAlgorithmException, NoSuchProviderException{
		KeyGenerator kgen = KeyGenerator.getInstance("AES", "BC");
		kgen.init(keySizeInBits);
		SecretKey skey = kgen.generateKey();
		return skey.getEncoded();
	}
}
