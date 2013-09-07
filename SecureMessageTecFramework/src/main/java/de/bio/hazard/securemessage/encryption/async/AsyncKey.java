package de.bio.hazard.securemessage.encryption.async;

public class AsyncKey {
	private byte[] privateKey;
	private byte[] publicKey;
	
	public AsyncKey(){
		setPublicKey(null);
		setPrivateKey(null);
	}
	
	public AsyncKey(byte[] publicKey){
		setPublicKey(publicKey);
		setPrivateKey(null);
	}
	
	public AsyncKey(byte[] publicKey, byte[] privateKey){
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
