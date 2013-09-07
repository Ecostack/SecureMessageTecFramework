package de.bio.hazard.securemessage.encryption.async;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.NoSuchPaddingException;

import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.bouncycastle.crypto.util.PublicKeyFactory;

public class AsyncCrypt {
	RSAEngine cipher = null;

	public AsyncCrypt() throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		cipher = new RSAEngine();
	}
	
	private void addByteArrayToByteList(byte[] pArray, List<Byte> pList) {
		for (Byte lcByte : pArray) {
			pList.add(lcByte);
		}
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
	
	private byte[] innerCrypt(byte[] data, byte[] key,boolean encrypt, boolean privateCrypt) throws IOException{		
		if(privateCrypt){
			cipher.init(encrypt, PrivateKeyFactory.createKey(key));
		}
		else{
			cipher.init(encrypt, PublicKeyFactory.createKey(key));
		}
		int blockSize = cipher.getInputBlockSize();
		List<Byte> output = new ArrayList<Byte>();
		//for (int chunkPosition = 0; chunkPosition < data.length; chunkPosition += blockSize) {
		//	int chunkSize = Math.min(blockSize, data.length - (chunkPosition * blockSize));
		//	addByteArrayToByteList(cipher.processBlock(data, chunkPosition, chunkSize), output);
		//}
		int chunkSize=0;
		for(int chunkCounter = 0; chunkCounter*blockSize < data.length; chunkCounter++) {
			chunkSize=Math.min(blockSize, data.length - (chunkCounter * blockSize));
			addByteArrayToByteList(cipher.processBlock(data, chunkCounter*blockSize, chunkSize), output);
		}
		return classByteListToPrimitiveByteArray(output);
	}
	
	public byte[] encrypt(byte[] data, byte[] key,boolean privateCrypt) throws IOException{
		return innerCrypt(data,key,true,privateCrypt);
	}
	
	public byte[] decrypt(byte[] data, byte[] key,boolean privateCrypt) throws IOException{
		return innerCrypt(data,key,false,privateCrypt);
	}
}
