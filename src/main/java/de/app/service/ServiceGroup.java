package de.app.service;

import java.util.HashMap;
import java.util.Map;

import de.cryptone.crypto.AESCrypto;
import de.cryptone.crypto.CryptFactor;
import de.cryptone.crypto.RSAPBECrypto;

public class ServiceGroup {

	public ServiceGroup() {
	}

	public Map<String, String> newgroup(String groupname, String leadPubKey ){
		AESCrypto aes = (AESCrypto) CryptFactor.getInstance(CryptFactor.CRYPT_SYM_AES);
		RSAPBECrypto rsa = (RSAPBECrypto) CryptFactor.getInstance(CryptFactor.CRYPT_ASYM_RSA_PBE);
		String groupkey = aes.generateKey();
		String enc_groupkey = rsa.encrypt(leadPubKey, groupkey);
		Map< String, String > result = new HashMap<>();
		result.put("groupkey", enc_groupkey);
		return result;
	}

	public Map<String, String> createGroup( String groupname, String leadpubkey, String leaderId ){
		AESCrypto aes = (AESCrypto) CryptFactor.getInstance(CryptFactor.CRYPT_SYM_AES);
		RSAPBECrypto rsa = (RSAPBECrypto) CryptFactor.getInstance(CryptFactor.CRYPT_ASYM_RSA_PBE);
		String group_symkey = aes.generateKey();
		String enc_group_symkey = rsa.encrypt(leadpubkey, group_symkey);

		Map< String, String > result = new HashMap<String, String>();

		result.put("symkey", enc_group_symkey);
		result.put("groupname", groupname);
		result.put("leaderId", leaderId);
		return result;
		
	}

	
}
