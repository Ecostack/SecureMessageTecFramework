package de.bio.hazard.securemessage.tecframework.encryption.asymmetric;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

public class AsymmetricKeygen {

	private KeyPairGenerator generator;

	public AsymmetricKeygen() throws NoSuchAlgorithmException,
			NoSuchProviderException {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		generator = KeyPairGenerator.getInstance("RSA", "BC");
	}

	public AsymmetricKey getKey(int keySizeInBits) {
		KeyPair kp = getKeyPair(keySizeInBits);
		return new AsymmetricKey(kp.getPublic().getEncoded(), kp.getPrivate()
				.getEncoded());
	}

	private KeyPair getKeyPair(int keySizeInBits) {
		generator.initialize(keySizeInBits);
		return generator.genKeyPair();
	}

}
