package com.zhrenjie04.alex.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
import java.security.spec.*;
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
 * @author 张人杰
 *
 */
public class RSAEncryptDecryptUtil {
	public static final String KEY_ALGORITHM = "RSA";
	/** 貌似默认是RSA/NONE/PKCS1Padding，未验证 */
	public static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
	public static final String PUBLIC_KEY = "publicKey";
	public static final String PRIVATE_KEY = "privateKey";

	/** RSA密钥长度必须是64的倍数，在512~65536之间。默认是1024 */
	public static final int KEY_SIZE = 2048;

	public static final String PLAIN_TEXT = "Up Task is the greatest comminicating tool in the world";

	private PublicKey publicKey;
	private PrivateKey privateKey;
	
	public static RSAEncryptDecryptUtil getInstance() {
		return new RSAEncryptDecryptUtil();
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
	public String encrypt(String s,String charSetName) {
		byte[] bytes=null;
		try {
			bytes=s.getBytes(charSetName);
		} catch (UnsupportedEncodingException e1) {
			throw new RuntimeException(e1);
		}
		if(bytes.length<244) {
			return Base64Util.encode(RSAEncrypt(publicKey, bytes));
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
			byte[] keyBytes=RSAEncrypt(publicKey, key);
			System.out.println(keyBytes.length);
			//keyBytes作为循环加密密钥，前245个byte再做一次加密后存入
			byte[] keyBytes245=new byte[245];
			for(int i=0;i<245;i++) {
				keyBytes245[i]=keyBytes[i];
			}
			byte[] keyKeyBytes=RSAEncrypt(publicKey,keyBytes245);//前245个byte再做一次加密后存入
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			try {
				baos.write(keyKeyBytes);
				for(int i=245;i<keyBytes.length;i++) {//写入keyBytes加密后的剩余部分
					baos.write(keyBytes[i]);
				}
				int j=0;
				for(int i=241;i<bytes.length;i++) {//keyBytes作为循环加密密钥
					byte b=bytes[i];
					byte a=keyBytes245[j%keyBytes245.length];
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
	 * 用公钥加密数据，加密数据必须设置公钥，可以不设置私钥，循环加密法加密
	 * @param bytes 数据内容
	 * @return
	 */
	public byte[] encrypt(byte[] bytes) {
		if(bytes.length<244) {
			return RSAEncrypt(publicKey, bytes);
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
			byte[] keyBytes=RSAEncrypt(publicKey, key);
			System.out.println(keyBytes.length);
			//keyBytes作为循环加密密钥，前245个byte再做一次加密后存入
			byte[] keyBytes245=new byte[245];
			for(int i=0;i<245;i++) {
				keyBytes245[i]=keyBytes[i];
			}
			byte[] keyKeyBytes=RSAEncrypt(publicKey,keyBytes245);//前245个byte再做一次加密后存入
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			try {
				baos.write(keyKeyBytes);
				for(int i=245;i<keyBytes.length;i++) {//写入keyBytes加密后的剩余部分
					baos.write(keyBytes[i]);
				}
				int j=0;
				for(int i=241;i<bytes.length;i++) {//keyBytes作为循环加密密钥
					byte b=bytes[i];
					byte a=keyBytes245[j%keyBytes245.length];
					byte c=(byte)(a^b);
					baos.write(c);
					++j;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return baos.toByteArray();
		}
	}
	/**
	 * 用私钥解密数据，解密数据必须设置私钥，可以不设置公钥
	 * @param s 密文内容
	 * @return
	 */
	public String decrypt(String s,String charSetName) {
		byte[] encodedText=Base64Util.decode(s);
		if(encodedText.length<=256) {
			try {
				return new String(RSADecrypt(privateKey, encodedText),charSetName);
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}else {
			byte[] encodedKeyKey=new byte[256];
			for(int i=0;i<256;i++) {
				encodedKeyKey[i]=encodedText[i];
			}
			byte[] encodedKey=RSADecrypt(privateKey, encodedKeyKey);//245key的密文
			byte[] fullEncodedKey=new byte[256];
			byte[] key=new byte[245];
			for(int i=0;i<245;i++) {
				fullEncodedKey[i]=encodedKey[i];
				key[i]=encodedKey[i];
			}
			for(int i=0;i<256-245;i++) {//拼接key的密文
				fullEncodedKey[i+245]=encodedText[i+256];
			}
			byte[] prefix=RSADecrypt(privateKey, fullEncodedKey);//解密key
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			for(int i=4;i<prefix.length;i++) {
				baos.write(prefix[i]);
			}
			int j=0;
			for(int i=256+256-245;i<encodedText.length;i++) {//用key的密文解密剩余部分
				byte b=encodedText[i];
				byte a=key[j%key.length];
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
	 * 用私钥解密数据，解密数据必须设置私钥，可以不设置公钥
	 * @param bytes 密文内容
	 * @return
	 */
	public byte[] decrypt(byte[] bytes) {
		if(bytes.length<=256) {
			return RSADecrypt(privateKey, bytes);
		}else {
			byte[] encodedKeyKey=new byte[256];
			for(int i=0;i<256;i++) {
				encodedKeyKey[i]=bytes[i];
			}
			byte[] encodedKey=RSADecrypt(privateKey, encodedKeyKey);//245key的密文
			byte[] fullEncodedKey=new byte[256];
			byte[] key=new byte[245];
			for(int i=0;i<245;i++) {
				fullEncodedKey[i]=encodedKey[i];
				key[i]=encodedKey[i];
			}
			for(int i=0;i<256-245;i++) {//拼接key的密文
				fullEncodedKey[i+245]=bytes[i+256];
			}
			byte[] prefix=RSADecrypt(privateKey, fullEncodedKey);//解密key
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			for(int i=4;i<prefix.length;i++) {
				baos.write(prefix[i]);
			}
			int j=0;
			for(int i=256+256-245;i<bytes.length;i++) {//用key的密文解密剩余部分
				byte b=bytes[i];
				byte a=key[j%key.length];
				byte c=(byte)(a^b);
				baos.write(c);
				++j;
			}
			return baos.toByteArray();
		}
	}
	/**
	 * 生成公钥、私钥部分及测试加密、解密过程
	 * @param args
	 */
	public static void main(String[] args) {
		long t=System.currentTimeMillis();
		RSAEncryptDecryptUtil rsaUtil= RSAEncryptDecryptUtil.getInstance();
		Map<String, byte[]> keyMap = rsaUtil.generateKeyBytes();
		System.out.println("generateKeyBytes time:"+(System.currentTimeMillis()-t));
		t=System.currentTimeMillis();
		// 加密
		PublicKey publicKey = rsaUtil.restorePublicKey(keyMap.get(PUBLIC_KEY));
		byte[] encodedText = rsaUtil.RSAEncrypt(publicKey, PLAIN_TEXT.getBytes());
		System.out.println("restorePublicKey time:"+(System.currentTimeMillis()-t));
		System.out.println("RSA encoded: " + Base64Util.encode(encodedText));
		// 解密
		t=System.currentTimeMillis();
		PrivateKey privateKey = rsaUtil.restorePrivateKey(keyMap.get(PRIVATE_KEY));
		System.out.println("restorePrivateKey time:"+(System.currentTimeMillis()-t));
		System.out.println("RSA decoded: " + new String(rsaUtil.RSADecrypt(privateKey, encodedText)));
		String s="你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli啊啊啊哈哈哈哈哈哈";
		t=System.currentTimeMillis();
		RSAEncryptDecryptUtil rsaUtil2= RSAEncryptDecryptUtil.getInstance();
		rsaUtil2.generateKeyBytes();
		String encodedString=rsaUtil2.encrypt(s, "UTF-8");
		System.out.println("RSAUtil.encode time:::"+(System.currentTimeMillis()-t));
		System.out.println("encoded(密文):::::"+encodedString);
		System.out.println("base64（对比base64字符串长度）:::::"+Base64Util.encode(s.getBytes()));
		t=System.currentTimeMillis();
		System.out.println("decoded（解密后明文）:::::"+rsaUtil2.decrypt(encodedString, "UTF-8"));
		System.out.println("RSAUtil.decode time:::"+(System.currentTimeMillis()-t));

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
//		String privKey="MIIEogIBAAKCAQEArtdHcShs3Y6rZhvFafqJI9q6e0cWQkz9GhvBdQZBPXgomIdle61JvaSxtpJYlp2g8SU9unCL3SCcKLfMcG/20qBa+NS7FFbTsHv5yzvOD9hnO3FRs8SoXrbRpmnuwU06nNzvZ8AQSrdOi8eltylfYf1fTBlWpVz5EhzB4RJ+q+YJyq/87q5kZqO+qAirjbwAM5wn1QxLCy6aG1WMvRlp7nDbkFTVrRzdgv8vYfX4eY4oBPDfW8/hhXTm4KpY/6ulaCO8WGAZYbZ4+LucDssITWdIRZ4LcE1wl/Zr6SuV02DCYbjoUPjVKBreCR6RufX+DcxOPMtd5C5x/2xt/eCjNQIDAQABAoIBAG3+hbWM5yBjtzTf18yaj1h9LMCNslU3tiuMtqJ2suiHBZMf6xpppHCogh0H6K+ory3GbhUy5OrSryt2pik+ZxuPQmw0+RUMotTuyfGvyC1zyU4+NlZQLFSZ0z9MeaYmfe9dl0fALv+yXrnVek3Gu3kcO63WawpReWiJqvd7+TEO11jQaDJHinlU2flPemsk5LurMUVmQkn9cTIuor9lifulFva4p4eofqZXyNQ1cmGXCGsqatnPUDAntMHZZbUDGnFF7wCCr0ur+ukYUhKLy76NPS7GeiJhnrhFY5oIcGVZ0ditUOarjepCGf0ytDNKuzr0/ycc4kJ14PPoUwQUxsECgYEA2mr6b1veTldQHdzasnJSSdzvhB9Btug9AAJrKHIJ7/Lf6Ey7CBv6dcPxrMNAm/MTMy13hMNuR6mDYkvxvYpWOUWNdAtV3BmsmfiPWuJU02OizjzRNX1mhJ1hQXsJ5YnHfQn5dJDRDBzqDcT6Z99P7Nou/WXtGYgjPU9GO30QAZkCgYEAzOzLEUBO7sz7EPQNFD/QlbzKCjGA90GuKgBHb2UTHm3E6pjtn7ebC6xJSK1MTN88hJfGtNYiulxZAUlkR0Xw5qNC8Uvy+z1QU/kP4mFwOCbyELp7JL/A1Bl0GNZYKhLSKdIyKUEXFEx/Ya/dRaVbToY7IyPPkV5XG/CnTY9O5/0CgYAMaT/JzCKZuQRobgBPW4epgtBpZY1KY2/z7C9CoRhHdjma7aFDGLPmtTeqZX3qXqmcotzDwocls1Av6bjW2GGF55neGjbKxvqz1RfwRiQjzumVZodMzs9ggcDhA6jQTj/zrvlp0kDOu2XhWmwQMvRLtSouY/hRFxdJiJOpFbYvIQKBgEknIpqG7WZ70AhORrj6ytgZK7qVz2b0kFq3/Mg8OaX8I3ZnnzQL1BJHr2V9T6aFblIa6Dk30+/Y9YDrFRwIUKXUlBoVNakQSzOezlfO1sOEy5bDKyCEPy6342TWZ2SlkVaSjYuCi92YLUTPBdtN2xld+5BgwUEwf0IfF2othUp1AoGAR01kN8zbHQH9jix3r84OcTTX4ikiWNIzcn1xZII8HksY8QL/rW7KxmqKIEgTrKdzj/3pkRZ/htWSbdlTCi7ZtoyTSTrHyecTje5OMndIrs6qFmy/aLV1rmvB2w0ixiLdONgwAdflnMdS4W9i4OlUYSJNu7h1gFNfRQPFZeTjNG4=";
//		rsaUtil.restorePrivateKey(privKey);
//		encodedString="X6paKPHZbDiXfnyA32bh3/TMJQmXpB4jicYkllGMClTnTHyZJtAk8Hg7kXTTvhbwcwgrf75O64WfLxpsOhxw4vBReH6Vt4+hapJX0eOxPd18ofauwWK5KBg0Jip2Lnz7usjVdCpo+EfTesT1K4He5W7JA53YyEYVqCJ5VLr0/QDVgG4UmBUiZLrQJmYNA4LLiuq8eVTiMhnZ3fc7ZHdk9QmzzqmlwRa8+JHzE+XgcuLUn41yXvdy71PP2xskyQerZTQMnj78VA4csShFGRCWhrCqt4ZbmlwlwuVF6bMJR7BvgonYAMt+HF3N8hdJOCVHWd8xSUBHMtRag5VOkueGWQ==";
//		System.out.println("decodedString:::"+rsaUtil.decrypt(encodedString,"UTF-8"));
//		String pubKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArtdHcShs3Y6rZhvFafqJI9q6e0cWQkz9GhvBdQZBPXgomIdle61JvaSxtpJYlp2g8SU9unCL3SCcKLfMcG/20qBa+NS7FFbTsHv5yzvOD9hnO3FRs8SoXrbRpmnuwU06nNzvZ8AQSrdOi8eltylfYf1fTBlWpVz5EhzB4RJ+q+YJyq/87q5kZqO+qAirjbwAM5wn1QxLCy6aG1WMvRlp7nDbkFTVrRzdgv8vYfX4eY4oBPDfW8/hhXTm4KpY/6ulaCO8WGAZYbZ4+LucDssITWdIRZ4LcE1wl/Zr6SuV02DCYbjoUPjVKBreCR6RufX+DcxOPMtd5C5x/2xt/eCjNQIDAQAB";
//		rsaUtil.restorePublicKey(pubKey);
//		s="12345678";
//		encodedString=rsaUtil.encrypt(s,"UTF-8");
//		System.out.println(s);
//		System.out.println(rsaUtil.decrypt(encodedString,"UTF-8"));
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
	private byte[] RSAEncrypt(PublicKey key, byte[] plainText) {
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
	private byte[] RSADecrypt(PrivateKey key, byte[] encodedText) {
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
}
