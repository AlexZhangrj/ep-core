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
public class RjRSAUtil {
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
	
	public static RjRSAUtil getInstance() {
		return new RjRSAUtil();
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
	public String encode(String s,String charSetName) {
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
	public String decode(String s,String charSetName) {
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
		RjRSAUtil rsaUtil=RjRSAUtil.getInstance();
		// 加密
		PublicKey publicKey = rsaUtil.restorePublicKey(keyMap.get(PUBLIC_KEY));
		byte[] encodedText = rsaUtil.RSAEncode(publicKey, PLAIN_TEXT.getBytes());
		System.out.println("restorePublicKey time:"+(System.currentTimeMillis()-t));
		System.out.println("RSA encoded: " + Base64Util.encode(encodedText));
		// 解密
		t=System.currentTimeMillis();
		PrivateKey privateKey = rsaUtil.restorePrivateKey(keyMap.get(PRIVATE_KEY));
		System.out.println("restorePrivateKey time:"+(System.currentTimeMillis()-t));
		System.out.println("RSA decoded: " + new String(rsaUtil.RSADecode(privateKey, encodedText)));
		String s="{\"dataType\":\"storgeBusinessData\", \"Type\":\"I\", \"Body\":[{\"uuid\" : \"2020-04-14 10:47:30fa50f861ebf845cb95ee34e299522ba0\",  \"granaryNo\" : \"GN169544214521055232\", \"granaryName\" : \"郑州天香面业二库\",    \"busNo\" : \"YWBH-2019111111234\",    \"iOTypeCode\" : \"001\",    \"iOTypeName\" : \"入库\",    \"busDate\" : \"2020-04-09 08:55:31\",    \"customerName\" : \"恩尔美-企业方\",    \"carrier\" : \"王小国\",    \"cellPhone\" : \"13938182743\",    \"identityCard\" : \"410824197302086034\",    \"fullAddress\" : \"xiangxidizhi\",    \"vehicle\" : \"汽车\",    \"vehicleNo\" : \"豫AT085M\",    \"entryTime\" : \"2020-03-01 08:55:31\",    \"entryOperatorName\" : \"物业\",    \"qualityNo\" : \"ZJDH-20191115010\",     \"varietyCode\" : \"1110000\",    \"varietyName\" : \"小麦\",    \"gradeName\" : \"二等\",    \"grainYear\" : \"2019\",    \"storeHouseCode\" : \"WN169544465319462912\",    \"storeHouseName\" : \"郑州天香面业二库1仓\",    \"roomCode\" : \"AOJIANBM123456\",    \"roomName\" : \"廒间名称\",    \"locationCode\" : \"HWBM123456\",    \"locationName\" : \"货位名称\",    \"prodPlace\" : \"河南焦作\",    \"advise\" : \"1\",    \"qualityCutWeight\" : \"0\",    \"qYOperator\" : \"张利花\",    \"qYTime\" : \"2019-11-16 09:01:17\",    \"zJOperator\" : \"张利花\",    \"zJTime\" : \"2019-11-15 09:01:43\",    \"grossWeight\" : \"26080\",    \"grossTime\" : \"2019-11-15 09:06:09\",    \"grossOperatorName\" : \"朱静\",    \"storekeeperCutType\" : \"扣价\",    \"storekeeperCut\" : \"0\",    \"verifyOperatorName\" : \"\",    \"verifyTime\" : \"\",    \"tareWeight\" : \"7280\",    \"tareTime\" : \"2019-11-16 10:44:38\",    \"tareOperatorName\" : \"朱静\",    \"cutWeight\" : \"0\",    \"netWeight\" : \"18800\",    \"jSWeight\" : \"200\",    \"jSPrice\" : \"1.0\",    \"jSMoney\" : \"55.00\",    \"jSTime\" : \"2019-11-15 10:44:59\",    \"jSOperatorName\" : \"朱静\",    \"outTime\" : \"2019-11-15 10:47:33\",    \"outOperatorName\" : \"物业\",    \"orgCode\" : \"GN169544214521055232\",    \"orgName\" : \"郑州天香面业二库\",    \"memo\" : \"无\",    \"payeeName\" : \"李永联\",    \"payeeCardNum\" : \"6228482378587812478\",    \"payeeBankName\" : \"农业银行\",    \"payeeIdCardNum\" : \"410825196711143011\",    \"payeeAmount\" : \"46248\",    \"settlemen\" : \"01\",    \"payStatus\" : 1,    \"bankCode\" : \"103100000026\",    \"bankCityCode\" : \"410800\",    \"bankCity\" : \"焦作市\",    \"payType\" : \"网银转账\",       \"type\" : 0,           \"granaryCity\" : \"焦作市\",    \"sysPayNo\" : \"S00000110\"}]}";
//		String s="你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli你好啊afjladfowureowrajgldjfwifjiwaoeifjawli啊啊啊哈哈哈哈哈哈";
		t=System.currentTimeMillis();
		
		RjRSAUtil rsaUtil2=RjRSAUtil.getInstance();
//		rsaUtil2.restorePublicKey(keyMap.get(PUBLIC_KEY));
//		rsaUtil2.restorePrivateKey(keyMap.get(PRIVATE_KEY));
		rsaUtil2.restorePrivateKey("MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDcWCwZLPFT8OmfixlQNaI0fV0+QWUBZGMDk1qaJrjJlMLBiHzCOAhO7UWkLHqcHDkiHTlIaQwwaZaMj5s+idg616skPJ7pTpLpKvDnvodPdw9mkvjsNVnameWqlPjMq8V2urMzwpTidv07On8RJMZbt7dP+GbSZ+X59UN2A0mfEwhbcspTCbC3ZD05tcUd1Gub6z7oum+1knIpV8fD75qDg611R/XaUB+8KL/aEjNPqWNff4dYzhTre1ouM/nHmvjyCBFBYuXpb6Ga2rBq/okfp5oiSSFGokFM1Pkf8svp46Es7049XcSWd8akTi0VknRLRgATFWiFwJl6o6TF/kgDAgMBAAECggEBAKbpz/X+Oo14yLDH4RQGnOogMTsQtJ/aWLsDq7VsMbzaCLIsXz2MzpZjXMbhvrt1Eb6K3rLAiLA/vACcZGB6cScZ6pXXqhVYJSemFLmodb23lh4ApbnhLEDRJc4TbWk1ka6//THva3Ml6ewtAsaM0gn2YFGe/NzUQb0YktLZ6LIrg+cSScK98MPaVl2TDcS0ud7fpB00zcTd6aIbK6DG1CY9Io5EiLHggiOF66auCMy+1/0mb9Nah27SM4nyGguNe/62+seZSBltINz36aRKdsZeot5vpEkxn+zY0WJZpUgS7s1AdL74Fi302qbUUAo54q7EIPoiwF5UWPr3afL2OrECgYEA7+96PS8jq6uXq7UxGgQqAw7seeTRubsah0MfnTn2XcLY1W7+RYiuIx+RnxLJ331QhqSWYSqOdvTSkPHsTuBC98rvGNnDkSvA41IvPLbtYFqTCliLQ2hqX0zEA07WmFYTLtb/3VhSFU0Zc8WmHfVgEDr9rWq+zc9ikyAprBoywhkCgYEA6xjm7Uj8hULbEAKB8FGWKRsahxceKhdt+musbGD8ibWkHEE46MlsyoPZPsbKA4TVszQ2cmbQkLrW37+cQwksbveHJiwAVrj71ptrRlhDaPxp7u1bEpRsJ59VrmL2+6J/yZxChjsHIXLXyNl5ab15UL0I/vBI5T2PRNRN9cqv9nsCgYBczA9ICKpQJA6GH479E+03v5bgUgp3PhE1jVV11swdWVXJvqLjO8i11ujYUEj5ghsZ6CAtNmthQLEL1DVkEEJ2Wet9fgOwau6wPMH+RnWqlX1XPAWorYKulx8cdGp1Ap1quUa+UgF1MZuNdj0YSyW7QUWJw8ZGDVZ/tqnHYV6OSQKBgAVMYV/1piNGt3Mr60vEOvMgNj/XKIngT002ggNLSEW+Pz2XxrWQXJBP2mSMzF657qsEQTng0VCBtXgDdH0aRtMVZwlYMoXB04Mpq4hBUvuZvLVyyfuoNqvJ7n9ooZJYPjMH/4PQ7r81PGG9bPwSFhrYt8wvFCV+dq0hb8RbVg3jAoGAZBUvsHuwj8nt5UAzIcELW2prUHDVqva1CEjM6KelwfYM7tcY7g3zPLaNjRWASPIYKJKmiW9/96XJu6ECJfa1StQQIv707eG6hbIqqvfX2MUzPhfYF1lF2VQPxAUidWjNQdnbP+aepiLdYTQRAhkytny1gZHGV0c/fDRvWTfYBDw=");
		rsaUtil2.restorePublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA3FgsGSzxU/Dpn4sZUDWiNH1dPkFlAWRjA5Namia4yZTCwYh8wjgITu1FpCx6nBw5Ih05SGkMMGmWjI+bPonYOterJDye6U6S6Srw576HT3cPZpL47DVZ2pnlqpT4zKvFdrqzM8KU4nb9Ozp/ESTGW7e3T/hm0mfl+fVDdgNJnxMIW3LKUwmwt2Q9ObXFHdRrm+s+6LpvtZJyKVfHw++ag4OtdUf12lAfvCi/2hIzT6ljX3+HWM4U63taLjP5x5r48ggRQWLl6W+hmtqwav6JH6eaIkkhRqJBTNT5H/LL6eOhLO9OPV3ElnfGpE4tFZJ0S0YAExVohcCZeqOkxf5IAwIDAQAB");
		String encodedString=rsaUtil.encode(s, "UTF-8");
		System.out.println("RSAUtil.encode time:::"+(System.currentTimeMillis()-t));
		System.out.println("encoded(密文):::::"+encodedString);
		System.out.println("base64（对比base64字符串长度）:::::"+Base64Util.encode(s.getBytes()));
		t=System.currentTimeMillis();
		System.out.println("decoded（解密后明文）:::::"+rsaUtil.decode(encodedString, "UTF-8"));
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
}
