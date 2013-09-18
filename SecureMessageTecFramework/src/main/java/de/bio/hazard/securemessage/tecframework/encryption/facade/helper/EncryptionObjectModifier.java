package de.bio.hazard.securemessage.tecframework.encryption.facade.helper;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import de.bio.hazard.securemessage.tecframework.encryption.asymmetric.AsymmetricCrypt;
import de.bio.hazard.securemessage.tecframework.encryption.symmetric.SymmetricCrypt;
import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;

public class EncryptionObjectModifier {

    private BASE64Encoder b64Encoder;
    private BASE64Decoder b64Decoder;
    private AsymmetricCrypt asymmetricCrypt;
    private SymmetricCrypt symmetricCrypt;

    public EncryptionObjectModifier() throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException {
	b64Encoder = new BASE64Encoder();
	b64Decoder = new BASE64Decoder();
	asymmetricCrypt = new AsymmetricCrypt();
	symmetricCrypt = new SymmetricCrypt();
    }

    public String symmetricEncrypt(String data, byte[] key) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
	return b64Encoder.encode(symmetricCrypt.encrypt(data.getBytes(), key));
    }

    public String symmetricDecrypt(String data, byte[] key) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
	return new String(symmetricCrypt.decrypt(b64Decoder.decodeBuffer(data), key));
    }
    
    public String asymmetricEncrypt(String data, byte[] key, boolean isPrivateKey) throws IOException {
	return b64Encoder.encode(asymmetricCrypt.encrypt(data.getBytes(), key, isPrivateKey));
    }

    public String asymmetricDecrypt(String data, byte[] key, boolean isPrivateKey) throws IOException {
	return new String(asymmetricCrypt.decrypt(b64Decoder.decodeBuffer(data), key, isPrivateKey));
    }
    
    public String symmetricEncrypt(byte[] data, byte[] key) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
	return b64Encoder.encode(symmetricCrypt.encrypt(data, key));
    }

    public byte[] symmetricDecryptToByte(String data, byte[] key) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
	return symmetricCrypt.decrypt(b64Decoder.decodeBuffer(data), key);
    }
    
    public String asymmetricEncrypt(byte[] data, byte[] key, boolean isPrivateKey) throws IOException {
	return b64Encoder.encode(asymmetricCrypt.encrypt(data, key, isPrivateKey));
    }
    
    public byte[] asymmetricDecryptToByte(String data, byte[] key, boolean isPrivateKey) throws IOException {
	return asymmetricCrypt.decrypt(b64Decoder.decodeBuffer(data), key, isPrivateKey);
    }
}
