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
 * RSA双钥加解密工具
 * 本工具包经过加密前241个byte后的密文循环加密后续数据，具有密文长度与Base64密文长度接近，没有私钥无法解密、无法破解密文的特性。
 * @author 张人杰
 *
 */
public class RSAUtil {
	public static final String KEY_ALGORITHM = "RSA";
	/** 貌似默认是RSA/NONE/PKCS1Padding，未验证 */
	public static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
	public static final String PUBLIC_KEY = "publicKey";
	public static final String PRIVATE_KEY = "privateKey";

	/** RSA密钥长度必须是64的倍数，在512~65536之间。默认是1024 */
	public static final int KEY_SIZE = 2048;

	public static final String PLAIN_TEXT = "Up Task is the greatest comminicating tool in the world";

	private static PublicKey publicKey;
	private static PrivateKey privateKey;
	/**
	 * 通过base64的公钥数据设置公钥，加密数据必须设置公钥，可以不设置私钥
	 * @param publicKeyBase64
	 */
	public static void restorePublicKey(String publicKeyBase64) {
		restorePublicKey(Base64Util.decode(publicKeyBase64));
	}
	/**
	 * 通过base64的私钥数据设置私钥，解密数据必须设置私钥，可以不设置公钥
	 * @param privateKeyBase64
	 */
	public static void restorePrivateKey(String privateKeyBase64) {
		restorePrivateKey(Base64Util.decode(privateKeyBase64));
	}
	/**
	 * 用公钥加密数据，加密数据必须设置公钥，可以不设置私钥，循环加密法加密
	 * @param s 数据内容
	 * @return
	 */
	public static String encode(String s,String charSetName) {
		byte[] bytes=null;
		try {
			bytes=s.getBytes(charSetName);
		} catch (UnsupportedEncodingException e1) {
			throw new RuntimeException(e1);
		}
		if(bytes.length<244) {
			return Base64Util.encode(RSAEncode(publicKey, bytes));
		}else {
			byte r0=(byte)(Math.random()*256);//4个随机数，防止知道密文和明文来模拟数据（作者：张人杰）
			byte r1=(byte)(Math.random()*256);
			byte r2=(byte)(Math.random()*256);
			byte r3=(byte)(Math.random()*256);
			byte[] key=new byte[245];
			key[0]=r0;
			key[1]=r1;
			key[2]=r2;
			key[3]=r3;
			for(int i=0;i<241;i++) {
				key[i+4]=bytes[i];
			}
			byte[] keyBytes=RSAEncode(publicKey, key);
			System.out.println(keyBytes.length);
			//keyBytes作为循环加密密钥，前245个byte再做一次加密后存入
			byte[] keyBytes245=new byte[245];
			for(int i=0;i<245;i++) {
				keyBytes245[i]=keyBytes[i];
			}
			byte[] keyKeyBytes=RSAEncode(publicKey,keyBytes245);//前245个byte再做一次加密后存入
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			try {
				baos.write(keyKeyBytes);
				for(int i=245;i<keyBytes.length;i++) {//写入keyBytes加密后的剩余部分
					baos.write(keyBytes[i]);
				}
				int j=0;
				for(int i=241;i<bytes.length;i++) {//keyBytes作为循环加密密钥
					byte b=bytes[i];
					byte a=keyBytes[j%keyBytes.length];
					byte c=(byte)(a^b);
					baos.write(c);
					++j;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return Base64Util.encode(baos.toByteArray());
		}
	}
	/**
	 * 用私钥解密数据，解密数据必须设置私钥，可以不设置公钥
	 * @param s 密文内容
	 * @return
	 */
	public static String decode(String s,String charSetName) {
		byte[] encodedText=Base64Util.decode(s);
		if(encodedText.length<=256) {
			try {
				return new String(RSADecode(privateKey, encodedText),charSetName);
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}else {
			byte[] encodedKeyKey=new byte[256];
			for(int i=0;i<256;i++) {
				encodedKeyKey[i]=encodedText[i];
			}
			byte[] encodedKey=RSADecode(privateKey, encodedKeyKey);//245key的密文
			byte[] fullEncodedKey=new byte[256];
			for(int i=0;i<245;i++) {
				fullEncodedKey[i]=encodedKey[i];
			}
			for(int i=0;i<256-245;i++) {//拼接key的密文
				fullEncodedKey[i+245]=encodedText[i+256];
			}
			byte[] key=RSADecode(privateKey, fullEncodedKey);//解密key
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			for(int i=4;i<key.length;i++) {
				baos.write(key[i]);
			}
			int j=0;
			for(int i=256+256-245;i<encodedText.length;i++) {//用key的密文解密剩余部分
				byte b=encodedText[i];
				byte a=fullEncodedKey[j%fullEncodedKey.length];
				byte c=(byte)(a^b);
				baos.write(c);
				++j;
			}
			try {
				return new String(baos.toByteArray(),charSetName);
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
	}
	/**
	 * 生成公钥、私钥部分及测试加密、解密过程
	 * @param args
	 */
	public static void main(String[] args) {
		long t=System.currentTimeMillis();
		Map<String, byte[]> keyMap = generateKeyBytes();
		System.out.println("generateKeyBytes time:"+(System.currentTimeMillis()-t));
		t=System.currentTimeMillis();
		// 加密
		PublicKey publicKey = restorePublicKey(keyMap.get(PUBLIC_KEY));
		byte[] encodedText = RSAEncode(publicKey, PLAIN_TEXT.getBytes());
		System.out.println("restorePublicKey time:"+(System.currentTimeMillis()-t));
		System.out.println("RSA encoded: " + Base64Util.encode(encodedText));
		// 解密
		t=System.currentTimeMillis();
		PrivateKey privateKey = restorePrivateKey(keyMap.get(PRIVATE_KEY));
		System.out.println("restorePrivateKey time:"+(System.currentTimeMillis()-t));
		System.out.println("RSA decoded: " + new String(RSADecode(privateKey, encodedText)));
		String s="你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli啊啊啊哈哈哈哈哈哈";
		t=System.currentTimeMillis();
		String encodedString=RSAUtil.encode(s, "UTF-8");
		System.out.println("RSAUtil.encode time:::"+(System.currentTimeMillis()-t));
		System.out.println("encoded:::::"+encodedString);
		System.out.println("base64:::::"+Base64Util.encode(s.getBytes()));
		t=System.currentTimeMillis();
		System.out.println("decoded:::::"+RSAUtil.decode(encodedString, "UTF-8"));
		System.out.println("RSAUtil.decode time:::"+(System.currentTimeMillis()-t));
	}

	/**
	 * 生成密钥对。注意这里是生成密钥对KeyPair，再由密钥对获取公私钥
	 * 
	 * @return
	 */
	public static Map<String, byte[]> generateKeyBytes() {
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
			keyPairGenerator.initialize(KEY_SIZE, SecureRandom.getInstanceStrong());
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			Map<String, byte[]> keyMap = new HashMap<String, byte[]>();
			keyMap.put(PUBLIC_KEY, publicKey.getEncoded());
			keyMap.put(PRIVATE_KEY, privateKey.getEncoded());
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
	private static PublicKey restorePublicKey(byte[] keyBytes) {
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
		try {
			KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
			PublicKey publicKey = factory.generatePublic(x509EncodedKeySpec);
			RSAUtil.publicKey=publicKey;
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
	private static PrivateKey restorePrivateKey(byte[] keyBytes) {
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
		try {
			KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
			PrivateKey privateKey = factory.generatePrivate(pkcs8EncodedKeySpec);
			RSAUtil.privateKey=privateKey;
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
	private static byte[] RSAEncode(PublicKey key, byte[] plainText) {
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
	private static byte[] RSADecode(PrivateKey key, byte[] encodedText) {
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
}
