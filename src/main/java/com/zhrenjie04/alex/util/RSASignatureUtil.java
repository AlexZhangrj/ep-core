package com.zhrenjie04.alex.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
/**
 * Rj-RSA双钥加解密工具
 * 本工具包经过加密前241个byte后的密文循环加密后续数据，具有密文长度与Base64密文长度接近，没有私钥无法解密、无法破解密文的特性。
 * @author 张人杰
 *
 */
public class RSASignatureUtil {
	public static final String KEY_ALGORITHM = "RSA";
	/** 貌似默认是RSA/NONE/PKCS1Padding，未验证 */
	public static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
	public static final String PUBLIC_KEY = "publicKey";
	public static final String PRIVATE_KEY = "privateKey";

	/** RSA密钥长度必须是64的倍数，在512~65536之间。默认是1024 */
	public static final int KEY_SIZE = 2048;//256字节

	private PublicKey publicKey;
	private PrivateKey privateKey;
	
	public static RSASignatureUtil getInstance() {
		return new RSASignatureUtil();
	}
	/**
	 * 通过base64的公钥数据设置公钥，加密数据必须设置公钥，可以不设置私钥
	 * @param publicKeyBase64
	 */
	public PublicKey restorePublicKey(String publicKeyBase64) {
		return restorePublicKey(Base64Util.decode(publicKeyBase64));
	}
	/**
	 * 通过base64的私钥数据设置私钥，解密数据必须设置私钥，可以不设置公钥
	 * @param privateKeyBase64
	 */
	public PrivateKey restorePrivateKey(String privateKeyBase64) {
		return restorePrivateKey(Base64Util.decode(privateKeyBase64));
	}
	/**
	 * 用公钥加密数据，加密数据必须设置公钥，可以不设置私钥，循环加密法加密
	 * @param s 数据内容
	 * @return
	 */
	public byte[] encode(byte[] in) {
		if(in == null || in.length>245) {
			throw new RuntimeException("原文不能超过245字节");
		}
		return RSAEncode(publicKey, in);
	}
	/**
	 * 用私钥解密数据，解密数据必须设置私钥，可以不设置公钥
	 * @param s 密文内容
	 * @return
	 */
	public byte[] decode(byte[] in) {
		return RSADecode(privateKey, in);
	}

	/**
	 * 生成密钥对。注意这里是生成密钥对KeyPair，再由密钥对获取公私钥
	 * 
	 * @return
	 */
	public Map<String, byte[]> generateKeyBytes() {
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
			keyPairGenerator.initialize(KEY_SIZE, SecureRandom.getInstanceStrong());
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			Map<String, byte[]> keyMap = new HashMap<String, byte[]>();
			keyMap.put(PUBLIC_KEY, publicKey.getEncoded());
			keyMap.put(PRIVATE_KEY, privateKey.getEncoded());
			this.privateKey=privateKey;
			this.publicKey=publicKey;
			System.out.println("public key: "+Base64Util.encode(publicKey.getEncoded()));
			System.out.println("private key: "+Base64Util.encode(privateKey.getEncoded()));
			return keyMap;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 还原公钥，X509EncodedKeySpec 用于构建公钥的规范
	 * 
	 * @param keyBytes
	 * @return
	 */
	private PublicKey restorePublicKey(byte[] keyBytes) {
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
		try {
			KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
			PublicKey publicKey = factory.generatePublic(x509EncodedKeySpec);
			this.publicKey=publicKey;
			return publicKey;
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 还原私钥，PKCS8EncodedKeySpec 用于构建私钥的规范
	 * 
	 * @param keyBytes
	 * @return
	 */
	private PrivateKey restorePrivateKey(byte[] keyBytes) {
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
		try {
			KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
			PrivateKey privateKey = factory.generatePrivate(pkcs8EncodedKeySpec);
			this.privateKey=privateKey;
			return privateKey;
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 加密，三步走。
	 * 
	 * @param key
	 * @param plainText
	 * @return
	 */
	private byte[] RSAEncode(PublicKey key, byte[] plainText) {
		try {
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return cipher.doFinal(plainText);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密，三步走。
	 * 
	 * @param key
	 * @param encodedText
	 * @return
	 */
	private byte[] RSADecode(PrivateKey key, byte[] encodedText) {
		try {
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			return cipher.doFinal(encodedText);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 生成公钥、私钥部分及测试加密、解密过程
	 * @param args
	 */
	public static void main(String[] args) {
		long t=System.currentTimeMillis();
		RSASignatureUtil rsaUtil=RSASignatureUtil.getInstance();
		Map<String, byte[]> keyMap = rsaUtil.generateKeyBytes();
		System.out.println("generateKeyBytes time:"+(System.currentTimeMillis()-t));
		t=System.currentTimeMillis();
		// 加密密钥
		PublicKey publicKey = rsaUtil.restorePublicKey(keyMap.get(PUBLIC_KEY));
		System.out.println("restorePublicKey time:"+(System.currentTimeMillis()-t));
		// 解密密钥
		t=System.currentTimeMillis();
		PrivateKey privateKey = rsaUtil.restorePrivateKey(keyMap.get(PRIVATE_KEY));
		System.out.println("Public key:"+Base64Util.encode(keyMap.get(PUBLIC_KEY)));
		System.out.println("Private key:"+Base64Util.encode(keyMap.get(PRIVATE_KEY)));
		String s="你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli啊啊啊哈哈哈哈哈哈";
		byte[] md5=Md5Util.encrypt(s.getBytes());
		System.out.println("md5::::"+Base64Util.encode(md5));
		System.out.println("md5 length::::"+md5.length);//16字节，128位
		byte[] signature=rsaUtil.encode(md5);
		System.out.println("signature::::"+Base64Util.encode(signature));
		System.out.println("signature length::::"+signature.length);//256字节
		byte[] decodedMd5=rsaUtil.decode(signature);
		System.out.println("decodedMd5::::"+Base64Util.encode(decodedMd5));
	}
}
