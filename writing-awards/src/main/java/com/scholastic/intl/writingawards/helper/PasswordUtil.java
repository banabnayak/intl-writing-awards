/**
 * 
 */
package com.scholastic.intl.writingawards.helper;

import java.util.Random;

/**
 * @author nataraj.srikantaiah
 *
 */
public class PasswordUtil {

	private final static String CHARACTER_SET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	final static int passLength = 8;

	public static String getPassword() {
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder( passLength );
		for( int i = 0; i < passLength; i++ ) { 
			sb.append(CHARACTER_SET.charAt(rnd.nextInt(CHARACTER_SET.length())));
		}
		return sb.toString();
	}	



	/**
	 * 
	 * @param secretKey Key used to encrypt data
	 * @param plainText Text input to be encrypted
	 * @return Returns encrypted text
	 * 
	 *//*
	public static String encrypt(String secretKey, String plainText) 
			throws NoSuchAlgorithmException, 
			InvalidKeySpecException, 
			NoSuchPaddingException, 
			InvalidKeyException,
			InvalidAlgorithmParameterException, 
			UnsupportedEncodingException, 
			IllegalBlockSizeException, 
			BadPaddingException{
		//Key generation for enc and desc
		KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), salt, iterationCount);
		SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);        
		// Prepare the parameter to the ciphers
		AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

		//Enc process
		ecipher = Cipher.getInstance(key.getAlgorithm());
		ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);      
		String charSet="UTF-8";       
		byte[] in = plainText.getBytes(charSet);
		byte[] out = ecipher.doFinal(in);
		String encStr=new sun.misc.BASE64Encoder().encode(out);
		return encStr;
	}
	*//**     
	 * @param secretKey Key used to decrypt data
	 * @param encryptedText encrypted text input to decrypt
	 * @return Returns plain text after decryption
	 *//*
	public static String decrypt(String secretKey, String encryptedText)
			throws NoSuchAlgorithmException, 
			InvalidKeySpecException, 
			NoSuchPaddingException, 
			InvalidKeyException,
			InvalidAlgorithmParameterException, 
			UnsupportedEncodingException, 
			IllegalBlockSizeException, 
			BadPaddingException, 
			IOException{
		//Key generation for enc and desc
		KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), salt, iterationCount);
		SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);        
		// Prepare the parameter to the ciphers
		AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);
		//Decryption process; same key will be used for decr
		dcipher=Cipher.getInstance(key.getAlgorithm());
		dcipher.init(Cipher.DECRYPT_MODE, key,paramSpec);
		byte[] enc = new sun.misc.BASE64Decoder().decodeBuffer(encryptedText);
		byte[] utf8 = dcipher.doFinal(enc);
		String charSet="UTF-8";     
		String plainStr = new String(utf8, charSet);
		return plainStr;
	}    
*/
}

