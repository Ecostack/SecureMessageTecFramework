package de.bio.hazard.securemessage.tecframework.encryption.asymmetric;

public class AsymmetricKey {
	private byte[] privateKey;
	private byte[] publicKey;
	
	public AsymmetricKey(){
		setPublicKey(null);
		setPrivateKey(null);
	}
	
	public AsymmetricKey(byte[] publicKey){
		setPublicKey(publicKey);
		setPrivateKey(null);
	}
	
	public AsymmetricKey(byte[] publicKey, byte[] privateKey){
		setPublicKey(publicKey);
		setPrivateKey(privateKey);
	}

	public byte[] getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(byte[] privateKey) {
		this.privateKey = privateKey;
	}

	public byte[] getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(byte[] publicKey) {
		this.publicKey = publicKey;
	}
	
}
