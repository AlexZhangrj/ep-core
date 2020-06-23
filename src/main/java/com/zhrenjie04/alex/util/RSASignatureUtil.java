package com.zhrenjie04.alex.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
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
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Rj-RSA双钥加解密工具
 * 本工具包经过加密前241个byte后的密文循环加密后续数据，具有密文长度与Base64密文长度接近，没有私钥无法解密、无法破解密文的特性。
 * 
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
	public static final int KEY_SIZE = 2048;// 256字节

	private PublicKey publicKey;
	private PrivateKey privateKey;

	public static RSASignatureUtil getInstance() {
		return new RSASignatureUtil();
	}

	/**
	 * 通过base64的公钥数据设置公钥，加密数据必须设置公钥，可以不设置私钥
	 * 
	 * @param publicKeyBase64
	 */
	public PublicKey restorePublicKey(String publicKeyBase64) {
		return restorePublicKey(Base64.getDecoder().decode(publicKeyBase64));
	}

	/**
	 * 通过base64的私钥数据设置私钥，解密数据必须设置私钥，可以不设置公钥
	 * 
	 * @param privateKeyBase64
	 */
	public PrivateKey restorePrivateKey(String privateKeyBase64) {
		return restorePrivateKey(Base64.getDecoder().decode(privateKeyBase64));
	}

	/**
	 * 用公钥加密数据，加密数据必须设置公钥，可以不设置私钥，循环加密法加密
	 * 
	 * @param in 数据内容
	 * @return
	 */
	public byte[] encode(byte[] in) {
		if (in == null || in.length > 245) {
			throw new RuntimeException("原文不能超过245字节");
		}
		return RSAEncode(publicKey, in);
	}

	/**
	 * 用公钥加密数据，加密数据必须设置公钥，可以不设置私钥，循环加密法加密
	 * 
	 * @param in 数据内容
	 * @return
	 */
	public String encodeToBase64String(String in) {
		try {
			return Base64.getEncoder().encodeToString(RSAEncode(publicKey, in.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 用私钥解密数据，解密数据必须设置私钥，可以不设置公钥
	 * 
	 * @param in 密文内容
	 * @return
	 */
	public byte[] decode(byte[] in) {
		return RSADecode(privateKey, in);
	}

	/**
	 * 用私钥解密数据，解密数据必须设置私钥，可以不设置公钥
	 * 
	 * @param in 密文内容
	 * @return
	 */
	public byte[] decodeFromBase64String(String in) {
		return RSADecode(privateKey, Base64.getDecoder().decode(in));
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
			this.privateKey = privateKey;
			this.publicKey = publicKey;
			System.out.println("public key: " + Base64.getEncoder().encode(publicKey.getEncoded()));
			System.out.println("modules（模）: " + Base64.getEncoder().encodeToString(publicKey.getModulus().toByteArray()));
			System.out.println("PublicExponent（质数）: " + Base64.getEncoder().encodeToString(publicKey.getPublicExponent().toByteArray()));
			System.out.println("private key: " + Base64.getEncoder().encode(privateKey.getEncoded()));
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
			this.publicKey = publicKey;
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
			this.privateKey = privateKey;
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
			Cipher cipher = Cipher.getInstance("RSA");
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
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, key);
			return cipher.doFinal(encodedText);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * C#私钥转换成java私钥
	 */
	public String privateKeyFromXml(String privateKeyXml) {
		privateKeyXml = privateKeyXml.replaceAll("\r", "").replaceAll("\n", "");
		KeyFactory keyFactory;
		try {
			String modulusXml = privateKeyXml.substring(privateKeyXml.indexOf("<Modulus>") + 9,
					privateKeyXml.indexOf("</Modulus>"));
			BigInteger modulus = new BigInteger(1, Base64.getDecoder().decode(modulusXml));
			String publicExponentXml = privateKeyXml.substring(privateKeyXml.indexOf("<Exponent>") + 10,
					privateKeyXml.indexOf("</Exponent>"));
			BigInteger publicExponent = new BigInteger(1, Base64.getDecoder().decode(publicExponentXml));
			String privateExponentXml = privateKeyXml.substring(privateKeyXml.indexOf("<D>") + 3,
					privateKeyXml.indexOf("</D>"));
			BigInteger privateExponent = new BigInteger(1, Base64.getDecoder().decode(privateExponentXml));
			String primePXml = privateKeyXml.substring(privateKeyXml.indexOf("<P>") + 3, privateKeyXml.indexOf("</P>"));
			BigInteger primeP = new BigInteger(1, Base64.getDecoder().decode(primePXml));
			String primeQXml = privateKeyXml.substring(privateKeyXml.indexOf("<Q>") + 3, privateKeyXml.indexOf("</Q>"));
			BigInteger primeQ = new BigInteger(1, Base64.getDecoder().decode(primeQXml));
			String primeExponentPXml = privateKeyXml.substring(privateKeyXml.indexOf("<DP>") + 4,
					privateKeyXml.indexOf("</DP>"));
			BigInteger primeExponentP = new BigInteger(1, Base64.getDecoder().decode(primeExponentPXml));
			String primeExponentQXml = privateKeyXml.substring(privateKeyXml.indexOf("<DQ>") + 4,
					privateKeyXml.indexOf("</DQ>"));
			BigInteger primeExponentQ = new BigInteger(1, Base64.getDecoder().decode(primeExponentQXml));
			String crtCoefficientXml = privateKeyXml.substring(privateKeyXml.indexOf("<InverseQ>") + 10,
					privateKeyXml.indexOf("</InverseQ>"));
			BigInteger crtCoefficient = new BigInteger(1, Base64.getDecoder().decode(crtCoefficientXml));
			RSAPrivateCrtKeySpec rsaPriKey = new RSAPrivateCrtKeySpec(modulus, publicExponent, privateExponent, primeP,
					primeQ, primeExponentP, primeExponentQ, crtCoefficient);
			keyFactory = KeyFactory.getInstance("RSA");
			this.privateKey = keyFactory.generatePrivate(rsaPriKey);
			byte[] bytes = Base64.getEncoder().encode(privateKey.getEncoded());
			return new String(bytes, Charset.forName("utf-8"));
		} catch (Exception e) {
			System.err.println(e.toString());
		}
		return null;
	}

	/**
	 * C#公钥转换成java公钥
	 */
	public String publicKeyFromXml(String publicKeyXml) {
		KeyFactory keyFactory;
		publicKeyXml = publicKeyXml.replaceAll("\r", "").replaceAll("\n", "");
		try {
			String modulusXml = publicKeyXml.substring(publicKeyXml.indexOf("<Modulus>") + 9,
					publicKeyXml.indexOf("</Modulus>"));
			BigInteger modulus = new BigInteger(1, Base64.getDecoder().decode(modulusXml));

			String exponentXml = publicKeyXml.substring(publicKeyXml.indexOf("<Exponent>") + 10,
					publicKeyXml.indexOf("</Exponent>"));
			BigInteger publicExponent = new BigInteger(1, Base64.getDecoder().decode(exponentXml));
			RSAPublicKeySpec rsaPubKey = new RSAPublicKeySpec(modulus, publicExponent);
			keyFactory = KeyFactory.getInstance("RSA");
			this.publicKey = keyFactory.generatePublic(rsaPubKey);
			byte[] bytes = Base64.getEncoder().encode(publicKey.getEncoded());
			return new String(bytes, Charset.forName("utf-8"));
		} catch (Exception e) {
			System.err.println(e.toString());
			return null;
		}
	}

	/**
	 * 生成公钥、私钥部分及测试加密、解密过程
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		long t = System.currentTimeMillis();
		RSASignatureUtil rsaUtil = RSASignatureUtil.getInstance();
//		Map<String, byte[]> keyMap = rsaUtil.generateKeyBytes();
//		System.out.println("generateKeyBytes time:" + (System.currentTimeMillis() - t));
//		t = System.currentTimeMillis();
//		// 加密密钥
//		PublicKey publicKey = rsaUtil.restorePublicKey(keyMap.get(PUBLIC_KEY));
//		System.out.println("restorePublicKey time:" + (System.currentTimeMillis() - t));
//		// 解密密钥
//		t = System.currentTimeMillis();
//		PrivateKey privateKey = rsaUtil.restorePrivateKey(keyMap.get(PRIVATE_KEY));
//		System.out.println("Public key:" + Base64Util.encode(keyMap.get(PUBLIC_KEY)));
//		System.out.println("Private key:" + Base64Util.encode(keyMap.get(PRIVATE_KEY)));
//		String s = "你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli啊啊啊哈哈哈哈哈哈";
//		byte[] md5 = Md5Util.encrypt(s.getBytes());
//		System.out.println("md5::::" + Base64Util.encode(md5));
//		System.out.println("md5 length::::" + md5.length);// 16字节，128位
//		byte[] signature = rsaUtil.encode(md5);
//		System.out.println("signature::::" + Base64Util.encode(signature));
//		System.out.println("signature length::::" + signature.length);// 256字节
//		byte[] decodedMd5 = rsaUtil.decode(signature);
//		System.out.println("decodedMd5::::" + Base64Util.encode(decodedMd5));
//		
		//C#加密解密
		String privateKeyXml="<RSAKeyValue><Modulus>5n+9l/hssVjCyhKAhXBkAjuPlvWTE4GMlBxfKmBtTRqezBq2jepV/2byAJ5U56v5OAeY8Kari0QdgO1M1cPBzQTaGj2W80qLg8FhhSbBZBh8CxhrcABtl2o12ekctDmFBjXpXSoKJBDrq9d4gDy20OjZWfzTHbQcWhKdbJtnw7Zedvk/CvI+3R3ndXkDZbNLva47Aydirh6ZpgIf8Ia391t7BNoxgRtPcv6jjUGc+jlfSUYvOMag4KsTBacIu5YmrChmWJSNWOZjLETnimnA6O9s8navyQT6LPs6quaC0RPL6oQ6hMohkUQy/bzS9c0yi7+n6kM5aHnklAZIoM6TSw==</Modulus><Exponent>AQAB</Exponent><P>/RM0LpyZ4JNofXDAqq+pn3OqHYUK5Yt1tU/3QLm4Bnp3FHkPSgFq55Kc0imemk5hfU1iYidwpY9Fo68UaMSldjshRUsBMXfCMWZsSeP6+oyzM3CIHmpt+l46Y0c5F4CMWwniYx19yscWL3y/fiQtWWGjZ3ge5ornA+YJu6ci5nE=</P><Q>6Sm9L43/6e7fjVCjTWFb/Y4RU6NXZU+7rnqK/06oI/or8qyK7W0RNEeH2v1pvEViLWa8AyFbfJShDrs1EYHt0/qKU0G59Fre0FU++qbGI+N7v9Z1P0jcIG8meFYxOB9nSqUEsLkddhR7CkOUN+lhCZcJACasERcQfLPvlSTTC3s=</Q><DP>YuAO4isy/4f4KhJUZBuPlQm2rCPftwE+suEURAiLepR8U9Zcf9h/8QvaE05JdhYrhq4mQx0PqM+KMMJloe06jC/b9tuwEqisiWpv4oqc8yjnlrBfrip833XTMBiSmLkTnToGZh0OmZwg8dq2Yk02HpbEJdfLkt0wFtjYcqWBQDE=</DP><DQ>AeHNXiHmS+war9hTyR8dD/nil+s8nFu9ZxYkIxVudAPj8OyFno7/0Y02QoVDIjrpJPasU9YkF+hXOHg4YHDNC6X9Edj4+Ej3fXUCsiFAfi+q4wyHG8CpzjU3eUcw1IQkYEzesdJ8s2RM7fNHsf/XQa2PbAnj36tw/aCyMWD/txs=</DQ><InverseQ>S2yGwHP8u0fHiYPmL//6DkdV24P6+Pv1yoHmGLzEHHABpJ3s9ViAn7YWj2nmpM7FtQZwkJRRLb/Lm1hVO4prLgxkIQSUhSc0Sdj9u8Hf4PqDHjlF9aZD4/YqVohjdTzoEauZYtw87qhaYlVvtQ596qwcf6/rQSqYxy3qdhw2xLw=</InverseQ><D>SkJ5WI6mpfRMvUF8uoDdF4MM3bu8aJpiKg2B82shi6e55VPt4IbQsfn+mYhpHkJfhlecRFvOBI4rWzAYgv4QX/biNXYGPdeUXxxcSMOVpqPkwsZZRro8lH2ZX90kbEPjwX548pTqs3foFXLT1ay50VBTRhSYB4fRYouAwE7I/VoVKc0D/ANDaUhhVHs7LUrPcqnjsmGxLTlTEqZ+ApL5IFZArno0HO/BdcszAPgO4E+noC4zducjEbnKzgM0NCp50Iqg2r3uBmov3FEDHI692V1ZVY8+jNWpxWk8mmGbNIaBCs+0eIWNy7wfZFOLBp/FzEJ1glr6BfRYNWUcKyynwQ==</D></RSAKeyValue>";
		rsaUtil.privateKeyFromXml(privateKeyXml);
		byte[] bytes=rsaUtil.decodeFromBase64String("VCG9com6gImf5EjTDfKDJtUJXOeuuhX3tKPzpPkmOReZ//CGHhUKX0e5i46a0SBAJHP/8JI6szHKA4/IOIvjF7l0s09rtXpopMVYQNBi3g3etA4ckuF6VYqIT3x13FwbqXWY5HQqwTkSUfDSy2vHfQi9+Et7w1PjdM1uHfoUS0m1UN+yDZx+O835qkO4qfnh1roZzsTDWG69WAMVvNNnq4UlbQij3yQi9hstZoXlW5drcTCzWq4p1svjkDV7HSu+REALWD2alXwoVBnjD/LFiRnK3dNTfCBbS7YpvEnfWpyCASdQJr5wM4JhrFrgwhIq8PqezVh5MGy70VE79UsQkQ==");
		try {
			System.out.println(new String(bytes,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
