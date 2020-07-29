package com.zhrenjie04.alex.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
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

import org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPublicKey;

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
			System.out.println("public key: " + Base64.getEncoder().encodeToString(publicKey.getEncoded()));
			System.out.println("private key: " + Base64.getEncoder().encodeToString(privateKey.getEncoded()));
			System.out
					.println("modules（模）: " + Base64.getEncoder().encodeToString(publicKey.getModulus().toByteArray()));
			System.out.println("PublicExponent（质数）: "
					+ Base64.getEncoder().encodeToString(publicKey.getPublicExponent().toByteArray()));
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
		java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
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
		java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
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
	 * C#私钥转换成java私钥
	 */
	public String privateKeyFromDotNetXml(String privateKeyXml) {
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
			return Base64.getEncoder().encodeToString(privateKey.getEncoded());
		} catch (Exception e) {
			System.err.println(e.toString());
		}
		return null;
	}
	/**
	 * C#公钥转换成java公钥
	 */
	public String publicKeyFromDotNetXml(String publicKeyXml) {
		KeyFactory keyFactory;
		publicKeyXml = publicKeyXml.replaceAll("\r", "").replaceAll("\n", "");
		try {
			String modulusXml = publicKeyXml.substring(publicKeyXml.indexOf("<Modulus>") + 9,
					publicKeyXml.indexOf("</Modulus>"));
			BigInteger modulus = new BigInteger(1, Base64.getDecoder().decode(modulusXml));

			String exponentXml = publicKeyXml.substring(publicKeyXml.indexOf("<Exponent>") + 10,
					publicKeyXml.indexOf("</Exponent>"));
			BigInteger publicExponent = new BigInteger(1, Base64.getDecoder().decode(exponentXml));
			RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulus, publicExponent);
			keyFactory = KeyFactory.getInstance("RSA");
			this.publicKey = keyFactory.generatePublic(keySpec);
			return Base64.getEncoder().encodeToString(publicKey.getEncoded());
		} catch (Exception e) {
			System.err.println(e.toString());
			return null;
		}
	}

	/**
	 * 生成公钥、私钥部分及测试加密、解密过程
	 * 
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		RSASignatureUtil rsaUtil = RSASignatureUtil.getInstance();
//		Map<String, byte[]> keyMap = rsaUtil.generateKeyBytes();
//		// 加密密钥
//		// 解密密钥
//		System.out.println("Public key:" + Base64.getEncoder().encodeToString(keyMap.get(PUBLIC_KEY)));
//		System.out.println("Private key:" + Base64.getEncoder().encodeToString(keyMap.get(PRIVATE_KEY)));
//		String md5="12312312312";
//		byte[] signature = rsaUtil.encode(md5.getBytes("UTF-8"));
//		System.out.println("signature::::" + Base64.getEncoder().encodeToString(signature));
//		System.out.println("signature length::::" + signature.length);// 256字节
//		byte[] decodedMd5 = rsaUtil.decode(signature);
//		System.out.println("decodedMd5::::" + new String(decodedMd5));

		// C#产生key,java使用key解密解密（成功）
//		String privateKeyXml = "<RSAKeyValue><Modulus>oYSmF5/tJMMXGYyZd9BY7T0ogxKqiJWl+v92M39k9v+apoC0qWVoSoIHAkjONyO7TD9njMdZhQ88/GHka5fHZwvi0gjVNdDakf4qrHlVbpUWRLoC4M/6mFQXkmrLXtWht9zInnwtcJMkWpW/r8BUNvZYMQFtfsRFUVPdWXNK5BEedtZAP3p+zB+kect2fso4QLfvhyaOoCEvS+2WA+th5XPrroaI+9cEJTkh7bnYYCACLEYqz0PVjE5LTkNiPQP9zWXTZtw90lbpWufYGU00u2E6IbGsNQjkGwKYW9k02p9bBNGNIZ7dJrw3cQAzKP6j2LDZTZRBUf5nqqUeI9CZ4w==</Modulus><Exponent>AQAB</Exponent><P>1ywgzShtlfV1daTxtwRHEEWIfrSBJJe7hm1T1fzNR00FJuSVPXllrwR8Q/cetBh8jMIeCSFqkY36BCLW1gMwQJ5upLAeq/rSmD4ySQwhQ1d3RquiFzPByyX71bbK41sblxWwaRnY+U3Fv8w14qWivn8ZCEgKL/sp/7H7+HHBy18=</P><Q>wCpMcVoZfNHD7aQ6yiEXNH2jnAnlXCwpMhTseDrQJ8RmYwfVh+e6jyicT8CuzCAbziabW+OFBHR49NK+/innG6AdcJIoIf4X0eLV31khCF6HL9HgM8KUttYVJcDI60iqrW9fG6Cw3Es/enw/apco8dppvxPm7YHaLoMznOdng/0=</Q><DP>B63wgcH2n0qCZa98XP7PFnqU04jXrxmnPLX8Lv/1x5ytGWVBGk+R3Hw5fyd7tgvFrjRjZAoIRqedi7Y+mqbEexnscv5CKfsqtvpJSygeq8ucWy5W+6MCQKDbaxY6CYE/69V/VVDx2Hv5Gc5NAgjNVSUuHIiNrIa3SgPtLUHKG9s=</DP><DQ>vNlU4pjfL3GekS6yr+4xNd67KR/4jcrAeX1inw19f3dd/7i1OqibCRGp/B6qKeTsLn52c6l3Ede8/y0VkgZwHxANnpGkazOhCR1ZffPDqMvtIyuooHV7XOurQLzjN4bkZSlsWGRlKzmwGpl8YS5IiNZS0funGIZiew9pa6tmaak=</DQ><InverseQ>x5rC7BCk4qL6OUgBuquSEk2nWIeU6KmWqUT2M98vuHx2Qfe1amS16rLEsdwnXqn7U3Uf2Cw4JAXL18v8ncAmdcTC1IlrcmlUG6+C4YKZrnGsmuvmwZLEEJhpznAX7wyBvP5H9guc4NvR1xVDx3Yhc3QdfTjse0oxqgppqL3u4Co=</InverseQ><D>OvlND8kz9D1LXOfKTUYEgjLXbOxO2eoSHZBTH0B/o/P2Cp9p14QQTUwmtP3SVa21QITSEUPkesHymZBAr8xOYWCko93Xw3uLosyz1h/DQsoqxFNKo70smZvys2JM+UZ1csWpdryOdtEy7+RHhitqVFskHliQGz4ctTIy2utOx/CnfPz6Rnx2z18nJFKV8N2KnSeGPPpq1SQ6ly6uzXkUlbitwQsUI5RiulNguW2fTmeoefCXc2XJgVNUQM611TioUJlOZNsvaKrF1WLIK6vkIzRJjNRD5HmLfBLqHyr7xEEFhwZYvRKfnG3ZB4mXo+OCpsgygxD3D1eBzVJp7NyiSQ==</D></RSAKeyValue>";
//		rsaUtil.privateKeyFromDotNetXml(privateKeyXml);
//		byte[] bytes = rsaUtil.decodeFromBase64String(
//				"XhjXbtY5123LtNrR61AMnuYYOOqnowy8xQ+qF564lGXavMMYqjZPQzVmdHuOwmBEGXQlD4lqIXgSjTiWm66wAdgZRYqi3AwXzv7oLLD0ZrQ1IYb61mC/m/zwbTTJxuH5A9Q37siPeTmNrXbIrTFYCVGg3wGGEd4YKafVQi+UCqFZz30wlgYPc578MLckkz20lPJXZzZtZG0Yodt1DN+WKeVuIBexCTYt/SuVl/Y3Yoc1G5lzsGwSYOkcpgJXAlHEtqB1eVC+IFoYDM2y+gMIE3wwbHUqDJL9snV4MieQWDZ4aKOmQsbaQmH5E8e1qDoX/RhMztD0h97B/DcLUVm30A==");
//		try {
//			System.out.println(new String(bytes, "UTF-8"));
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		String publicKeyXml = "<RSAKeyValue><Modulus>oYSmF5/tJMMXGYyZd9BY7T0ogxKqiJWl+v92M39k9v+apoC0qWVoSoIHAkjONyO7TD9njMdZhQ88/GHka5fHZwvi0gjVNdDakf4qrHlVbpUWRLoC4M/6mFQXkmrLXtWht9zInnwtcJMkWpW/r8BUNvZYMQFtfsRFUVPdWXNK5BEedtZAP3p+zB+kect2fso4QLfvhyaOoCEvS+2WA+th5XPrroaI+9cEJTkh7bnYYCACLEYqz0PVjE5LTkNiPQP9zWXTZtw90lbpWufYGU00u2E6IbGsNQjkGwKYW9k02p9bBNGNIZ7dJrw3cQAzKP6j2LDZTZRBUf5nqqUeI9CZ4w==</Modulus><Exponent>AQAB</Exponent></RSAKeyValue>";
//		rsaUtil.publicKeyFromDotNetXml(publicKeyXml);
//		String encoded=rsaUtil.encodeToBase64String("1234");
//		System.out.println(encoded);
//		String decoded=new String(rsaUtil.decodeFromBase64String(encoded),"UTF-8");
//		System.out.print(decoded);
		
		// swift生成的key，java使用key解密加密，使用私钥解密成功
		String privKey="MIIEogIBAAKCAQEArtdHcShs3Y6rZhvFafqJI9q6e0cWQkz9GhvBdQZBPXgomIdle61JvaSxtpJYlp2g8SU9unCL3SCcKLfMcG/20qBa+NS7FFbTsHv5yzvOD9hnO3FRs8SoXrbRpmnuwU06nNzvZ8AQSrdOi8eltylfYf1fTBlWpVz5EhzB4RJ+q+YJyq/87q5kZqO+qAirjbwAM5wn1QxLCy6aG1WMvRlp7nDbkFTVrRzdgv8vYfX4eY4oBPDfW8/hhXTm4KpY/6ulaCO8WGAZYbZ4+LucDssITWdIRZ4LcE1wl/Zr6SuV02DCYbjoUPjVKBreCR6RufX+DcxOPMtd5C5x/2xt/eCjNQIDAQABAoIBAG3+hbWM5yBjtzTf18yaj1h9LMCNslU3tiuMtqJ2suiHBZMf6xpppHCogh0H6K+ory3GbhUy5OrSryt2pik+ZxuPQmw0+RUMotTuyfGvyC1zyU4+NlZQLFSZ0z9MeaYmfe9dl0fALv+yXrnVek3Gu3kcO63WawpReWiJqvd7+TEO11jQaDJHinlU2flPemsk5LurMUVmQkn9cTIuor9lifulFva4p4eofqZXyNQ1cmGXCGsqatnPUDAntMHZZbUDGnFF7wCCr0ur+ukYUhKLy76NPS7GeiJhnrhFY5oIcGVZ0ditUOarjepCGf0ytDNKuzr0/ycc4kJ14PPoUwQUxsECgYEA2mr6b1veTldQHdzasnJSSdzvhB9Btug9AAJrKHIJ7/Lf6Ey7CBv6dcPxrMNAm/MTMy13hMNuR6mDYkvxvYpWOUWNdAtV3BmsmfiPWuJU02OizjzRNX1mhJ1hQXsJ5YnHfQn5dJDRDBzqDcT6Z99P7Nou/WXtGYgjPU9GO30QAZkCgYEAzOzLEUBO7sz7EPQNFD/QlbzKCjGA90GuKgBHb2UTHm3E6pjtn7ebC6xJSK1MTN88hJfGtNYiulxZAUlkR0Xw5qNC8Uvy+z1QU/kP4mFwOCbyELp7JL/A1Bl0GNZYKhLSKdIyKUEXFEx/Ya/dRaVbToY7IyPPkV5XG/CnTY9O5/0CgYAMaT/JzCKZuQRobgBPW4epgtBpZY1KY2/z7C9CoRhHdjma7aFDGLPmtTeqZX3qXqmcotzDwocls1Av6bjW2GGF55neGjbKxvqz1RfwRiQjzumVZodMzs9ggcDhA6jQTj/zrvlp0kDOu2XhWmwQMvRLtSouY/hRFxdJiJOpFbYvIQKBgEknIpqG7WZ70AhORrj6ytgZK7qVz2b0kFq3/Mg8OaX8I3ZnnzQL1BJHr2V9T6aFblIa6Dk30+/Y9YDrFRwIUKXUlBoVNakQSzOezlfO1sOEy5bDKyCEPy6342TWZ2SlkVaSjYuCi92YLUTPBdtN2xld+5BgwUEwf0IfF2othUp1AoGAR01kN8zbHQH9jix3r84OcTTX4ikiWNIzcn1xZII8HksY8QL/rW7KxmqKIEgTrKdzj/3pkRZ/htWSbdlTCi7ZtoyTSTrHyecTje5OMndIrs6qFmy/aLV1rmvB2w0ixiLdONgwAdflnMdS4W9i4OlUYSJNu7h1gFNfRQPFZeTjNG4=";
		rsaUtil.restorePrivateKey(privKey);
		String encodedString="X6paKPHZbDiXfnyA32bh3/TMJQmXpB4jicYkllGMClTnTHyZJtAk8Hg7kXTTvhbwcwgrf75O64WfLxpsOhxw4vBReH6Vt4+hapJX0eOxPd18ofauwWK5KBg0Jip2Lnz7usjVdCpo+EfTesT1K4He5W7JA53YyEYVqCJ5VLr0/QDVgG4UmBUiZLrQJmYNA4LLiuq8eVTiMhnZ3fc7ZHdk9QmzzqmlwRa8+JHzE+XgcuLUn41yXvdy71PP2xskyQerZTQMnj78VA4csShFGRCWhrCqt4ZbmlwlwuVF6bMJR7BvgonYAMt+HF3N8hdJOCVHWd8xSUBHMtRag5VOkueGWQ==";
		System.out.println("decodedString:::"+new String(rsaUtil.decodeFromBase64String(encodedString),"UTF-8"));
		String pubKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArtdHcShs3Y6rZhvFafqJI9q6e0cWQkz9GhvBdQZBPXgomIdle61JvaSxtpJYlp2g8SU9unCL3SCcKLfMcG/20qBa+NS7FFbTsHv5yzvOD9hnO3FRs8SoXrbRpmnuwU06nNzvZ8AQSrdOi8eltylfYf1fTBlWpVz5EhzB4RJ+q+YJyq/87q5kZqO+qAirjbwAM5wn1QxLCy6aG1WMvRlp7nDbkFTVrRzdgv8vYfX4eY4oBPDfW8/hhXTm4KpY/6ulaCO8WGAZYbZ4+LucDssITWdIRZ4LcE1wl/Zr6SuV02DCYbjoUPjVKBreCR6RufX+DcxOPMtd5C5x/2xt/eCjNQIDAQAB";
		rsaUtil.restorePublicKey(pubKey);
		String s="12345678";
		encodedString=rsaUtil.encodeToBase64String(s);
		System.out.println(s);
		byte[]decodedBytes=rsaUtil.decodeFromBase64String(encodedString);
		System.out.println(new String(decodedBytes,"UTF-8"));
		
	}
}
