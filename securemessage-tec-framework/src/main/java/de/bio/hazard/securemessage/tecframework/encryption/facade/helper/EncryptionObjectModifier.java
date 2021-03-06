package de.bio.hazard.securemessage.tecframework.encryption.facade.helper;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import de.bio.hazard.securemessage.tecframework.encryption.asymmetric.AsymmetricCrypt;
import de.bio.hazard.securemessage.tecframework.encryption.symmetric.SymmetricCrypt;

public class EncryptionObjectModifier {

	private BASE64Encoder b64Encoder;
	private BASE64Decoder b64Decoder;
	private AsymmetricCrypt asymmetricCrypt;
	private SymmetricCrypt symmetricCrypt;

	public EncryptionObjectModifier() throws NoSuchAlgorithmException,
			NoSuchProviderException, NoSuchPaddingException {
		b64Encoder = new BASE64Encoder();
		b64Decoder = new BASE64Decoder();
		asymmetricCrypt = new AsymmetricCrypt();
		symmetricCrypt = new SymmetricCrypt();
	}

	public String encodeBase64(byte[] data) {
		return b64Encoder.encode(data);
	}

	public String encodeBase64(String data) {
		return encodeBase64(data.getBytes());
	}

	public byte[] decodeBase64ToByte(String data) throws IOException {
		return b64Decoder.decodeBuffer(data);
	}

	public String decodeBase64(String data) throws IOException {
		return new String(decodeBase64ToByte(data));
	}

	public String asymmetricEncrypt(String data, byte[] key,
			boolean isPrivateKey) throws IOException {
		return b64Encoder.encode(asymmetricCrypt.encrypt(data.getBytes(), key,
				isPrivateKey));
	}

	public String asymmetricEncrypt(byte[] data, byte[] key,
			boolean isPrivateKey) throws IOException {
		return b64Encoder.encode(asymmetricCrypt.encrypt(data, key,
				isPrivateKey));
	}

	public String symmetricEncrypt(String data, byte[] key)
			throws InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException {
		return b64Encoder.encode(symmetricCrypt.encrypt(data.getBytes(), key));
	}

	public String symmetricEncrypt(byte[] data, byte[] key)
			throws InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException {
		return b64Encoder.encode(symmetricCrypt.encrypt(data, key));
	}

	public String symmetricEncrypt(long data, byte[] key)
			throws InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException {
		return symmetricEncrypt("" + data, key);
	}

	public String asymmetricDecrypt(String data, byte[] key,
			boolean isPrivateKey) throws IOException {
		return new String(asymmetricDecryptToByte(data, key, isPrivateKey));
	}

	public byte[] asymmetricDecryptToByte(String data, byte[] key,
			boolean isPrivateKey) throws IOException {
		return asymmetricCrypt.decrypt(b64Decoder.decodeBuffer(data), key,
				isPrivateKey);
	}

	public String symmetricDecrypt(String data, byte[] key)
			throws InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, IOException {
		return new String(symmetricDecryptToByte(data, key));
	}

	public byte[] symmetricDecryptToByte(String data, byte[] key)
			throws InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, IOException {
		return symmetricCrypt.decrypt(b64Decoder.decodeBuffer(data), key);
	}

	public long symmetricDecryptToLong(String data, byte[] key)
			throws InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, IOException {
		return new Long(symmetricDecrypt(data, key));
	}

	// TODO Stream ver- und entschlüsseln
}
