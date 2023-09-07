package cl.atianza.remove.utils;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.atianza.remove.exceptions.RemoveApplicationException;

/**
 * Clase para encriptado y desencriptado de cadena de caracteres
 * 
 * @author Jose Gutierrez
 */
public class CipherUtils {
	private static final int IV_SIZE = 96;
	private static final int TAG_BIT_LENGTH = 128;
	private static final String ALGO_TRANSFORMATION_STRING = "AES/GCM/PKCS5Padding";
	private static final String SECRET_KEY_ALGORITHM = "AES";
	private static final String CHARSET_NAME = "UTF-8";
	private static final String SYM_SERVICE = "symService";

	private static final String DEFAULT_SECRET = "1cnER782jjRo28STZ3bztEKUVIly15v1sXWR+D4xivU=";

	private static byte[] iv = new byte[IV_SIZE];
	
	private static Logger log = LogManager.getLogger(CipherUtils.class);

	public static String encrypt(String strToEncrypt) throws RemoveApplicationException {
		String encryptedText = null;
		try {
			if (strToEncrypt == null)
				return null;
			
			GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(TAG_BIT_LENGTH, iv);
			byte[] aadData = SYM_SERVICE.getBytes();
			SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(DEFAULT_SECRET),
					SECRET_KEY_ALGORITHM);
			Cipher cipher;

			cipher = Cipher.getInstance(ALGO_TRANSFORMATION_STRING);

			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, gcmParameterSpec, new SecureRandom());
			cipher.updateAAD(aadData);
			encryptedText = Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(CHARSET_NAME)));
		} catch (Exception e) {
			log.error("Error encriptando ("+strToEncrypt+"): ", e);
		}
		
		return encryptedText;
	}

	public static String decrypt(String strToDecrypt) throws RemoveApplicationException {
		String decryptedText = null;
		try {
			if (strToDecrypt == null || strToDecrypt.isEmpty())
				return null;
			GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(TAG_BIT_LENGTH, iv);
			byte[] aadData = SYM_SERVICE.getBytes();
			SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(DEFAULT_SECRET),
					SECRET_KEY_ALGORITHM);
			Cipher cipher;

			cipher = Cipher.getInstance(ALGO_TRANSFORMATION_STRING);

			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, gcmParameterSpec, new SecureRandom());
			cipher.updateAAD(aadData);
			decryptedText = new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
		} catch (Exception e) {
			log.error("Error desencriptando ("+strToDecrypt+"): ", e);
		}
		
		return decryptedText;
	}

	/**
	 * Encripta el token de seguridad para un usuario Web
	 * 
	 * @param user
	 * @param agent
	 * @return
	 * @throws RemoveApplicationException
	 */
//	public static String encryptWebCompanyToken(Integer idUser, Integer idProfile, String agent, Integer idCompany) throws RemoveApplicationException {
//		return encrypt(RemoveTokenAccess.initForWebCompany(idUser, idProfile, agent, idCompany).concatKey());
//	}
//	public static String encryptMobileCompanyToken(Integer idUser, Integer idProfile, String agent, Integer idCompany) throws RemoveApplicationException {
//		return encrypt(RemoveTokenAccess.initForMobileCompany(idUser, idProfile, agent, idCompany).concatKey());
//	}
//	public static String encryptWebAdminToken(Integer idUser, Integer idProfile, String agent) throws RemoveApplicationException {
//		return encrypt(RemoveTokenAccess.initForWebAdmin(idUser, idProfile, agent).concatKey());
//	}

	/**
	 * Genera un password provisional random de 8 caracteres.
	 * 
	 * @return
	 */
	public static String generatePassword() {
		SecureRandom random = new SecureRandom();

		// Chose a Character random from this String
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
		int maxSize = 8;

		// Create StringBuffer size of AlphaNumericString
		StringBuilder sb = new StringBuilder(maxSize);

		for (int i = 0; i < maxSize; i++) {
			// generate a random number between
			// 0 to AlphaNumericString variable length
			int index = (int) (AlphaNumericString.length() * random.nextDouble());

			// add Character one by one in end of sb
			sb.append(AlphaNumericString.charAt(index));
		}

		return sb.toString();
	}
	
	
}
