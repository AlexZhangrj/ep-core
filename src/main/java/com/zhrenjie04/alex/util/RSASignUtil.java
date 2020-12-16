package com.zhrenjie04.alex.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA 签名验签工具类
 * @author 张人杰
 */
public class RSASignUtil {
	public static final String KEY_ALGORITHM = "RSA";
	/** 签名算法 */
	private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

	public static final String PUBLIC_KEY = "publicKey";
	public static final String PRIVATE_KEY = "privateKey";

	/** RSA密钥长度必须是64的倍数，在512~65536之间。默认是1024 */
	public static final int KEY_SIZE = 2048;// 256字节

	private PublicKey publicKey;
	private PrivateKey privateKey;

	public static RSASignUtil getInstance() {
		return new RSASignUtil();
	}

	/**
	 * 通过base64的公钥数据设置公钥（验签数据必须设置公钥，可以不设置私钥）
	 * 
	 * @param publicKeyBase64
	 */
	public RSASignUtil restorePublicKey(String publicKeyBase64) {
		return restorePublicKey(Base64.getDecoder().decode(publicKeyBase64));
	}

	/**
	 * 通过base64的私钥数据设置私钥（签名数据必须设置私钥，可以不设置公钥）
	 * 
	 * @param privateKeyBase64
	 */
	public RSASignUtil restorePrivateKey(String privateKeyBase64) {
		return restorePrivateKey(Base64.getDecoder().decode(privateKeyBase64));
	}

	/**
	 * 私钥签名（数据）: 用私钥对指定字节数组数据进行签名, 返回签名信息
	 */
	public byte[] sign(byte[] data){
		try{
			// 根据指定算法获取签名工具
			Signature sign = Signature.getInstance(SIGNATURE_ALGORITHM);
			// 用私钥初始化签名工具
			sign.initSign(privateKey);
			// 添加要签名的数据
			sign.update(data);
			// 计算签名结果（签名信息）
			byte[] signInfo = sign.sign();
			return signInfo;
		}catch (Exception e){
			throw new RuntimeException(e);
		}
	}
	/**
	 * 公钥验签（数据）: 用公钥校验指定数据的签名是否来自对应的私钥
	 */
	public boolean verify(byte[] data, byte[] signInfo) {
		try{
			// 根据指定算法获取签名工具
			Signature sign = Signature.getInstance(SIGNATURE_ALGORITHM);
			// 用公钥初始化签名工具
			sign.initVerify(publicKey);
			// 添加要校验的数据
			sign.update(data);
			// 校验数据的签名信息是否正确,
			// 如果返回 true, 说明该数据的签名信息来自该公钥对应的私钥,
			// 同一个私钥的签名, 数据和签名信息一一对应, 只要其中有一点修改, 则用公钥无法校验通过,
			// 因此可以用私钥签名, 然后用公钥来校验数据的完整性与签名者（所有者）
			boolean verify = sign.verify(signInfo);
			return verify;
		}catch (Exception e){
			throw new RuntimeException(e);
		}
	}
	/**
	 * 私钥签名（文件）: 用私钥对文件进行签名, 返回签名信息
	 */
	public byte[] signFile(File file){
		try{
			// 根据指定算法获取签名工具
			Signature sign = Signature.getInstance(SIGNATURE_ALGORITHM);
			// 用私钥初始化签名工具
			sign.initSign(privateKey);
			InputStream in = null;
			try {
				in = new FileInputStream(file);
				byte[] buf = new byte[1024];
				int len = -1;
				while ((len = in.read(buf)) != -1) {
					// 添加要签名的数据
					sign.update(buf, 0, len);
				}
			} finally {
				try{
					in.close();
				}catch(Exception e){}
			}
			// 计算并返回签名结果（签名信息）
			return sign.sign();
		}catch (Exception e){
			throw new RuntimeException(e);
		}
	}
	/**
	 * 公钥验签（文件）: 用公钥校验指定文件的签名是否来自对应的私钥
	 */
	public boolean verifyFile(File file, byte[] signInfo){
		try{
			// 根据指定算法获取签名工具
			Signature sign = Signature.getInstance(SIGNATURE_ALGORITHM);
			// 用公钥初始化签名工具
			sign.initVerify(publicKey);
			InputStream in = null;
			try {
				in = new FileInputStream(file);
				byte[] buf = new byte[1024];
				int len = -1;
				while ((len = in.read(buf)) != -1) {
					// 添加要校验的数据
					sign.update(buf, 0, len);
				}
			} finally {
				try{
					in.close();
				}catch(Exception e){}
			}
			// 校验签名
			return sign.verify(signInfo);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
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
	private RSASignUtil restorePublicKey(byte[] keyBytes) {
		java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
		try {
			KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
			PublicKey publicKey = factory.generatePublic(x509EncodedKeySpec);
			this.publicKey = publicKey;
			return this;
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 还原私钥，PKCS8EncodedKeySpec 用于构建私钥的规范
	 * 
	 * @param keyBytes
	 * @return
	 */
	private RSASignUtil restorePrivateKey(byte[] keyBytes) {
		java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
		try {
			KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
			PrivateKey privateKey = factory.generatePrivate(pkcs8EncodedKeySpec);
			this.privateKey = privateKey;
			return this;
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		RSASignUtil signUtil = RSASignUtil.getInstance();
//		signUtil.generateKeyBytes();//此行代码仅用于生成公私钥密钥对
		//调入已有私钥
		signUtil.restorePrivateKey("MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQChh0SYmVQwKv4o4nwyagz9INAhDzmPDyl0vtLs/sF87/p61RItjnjdL5ESfkAyjyC5YY+41rtEbMYCiwnUUssPta5CvI1eti6bPUfz+iovIPaDWJhNhR+wAhqcVozLnCsVD7wE9CCREIyIViwOZU+j1IlIvK4cJ8mG2i+c93Vz7IKAAtu8dXxmadsH0ROwCzvzTqPV4gs2IWWy4lPBcppjzSvMDfaUVhCd7lT4azUNZV/GeeiYEGpz4aYnV5MoMN3p+rAJWk1Jls18o/7lMcPrR63zwXM0OcgLIqi1t5nAT059lL5JHS2evxiu5nt5X6Nk0Lss8mFRYvmpodkNVv//AgMBAAECggEBAIeggKRw+hOHxeimYwohqc5Q4vEfS4EnhfOU1p5QOj6hcXv5tE20Nx/hdLpGb7kEHMILfRYClzMY8hnojNROwFnsfuliUIGaDwAiMGxXPVxdHR+kIB3XDYhH+/gRZB8CfdotX/34ze+AKb/AOzXLM1eoea0tYDQb5EbqpNfzoBc4weLGdj3u4Hxi/cVeGBZulo6Si1o9hAFZcZhtsGJak39s4vdKXvql6v8oltpqN0wUJEEKC+evkw7Gp1R2tCft1NqFHt4DBXZ8hue8qaPG/ZFUctDOgNODncHB6IN9noIdgklel/CkRxjKzjDftefkEqhl1EiPrn2cpk15enXm6cECgYEA7JBXWbM3jXm6plNc++dyOp6D//h2NN328m/MJnOFG0SJ0UbcsLrE3l2bi20cwmx22Dj9UOEBEE9GI6agCh9I8qHeCxnjqgpkDPkE7krXnSx5SXAf/qDKnx91wTBlHvC2VRAkHXhdYZCxjG80z8rbRJJAbW7K7ow2f8T5QOBXaYkCgYEArsyz28i4Z5F3iYWPZ1oa9rzKXuD4sdul8UpF3gf2vIpHraUB1CVbNGxoysIkLe7npIogXQ1oI957XNyKnGmuOnqNCM/3/UALhLRcDGn3ccryxaCPo6WFEzKmjFmn/v1kvOFGauQVKA0mnlaEn+d82/bBXyZAWecoQJsP6QiTI0cCgYEAtCNh8NNQ/TFx2U/noYbn7A1jNNsNoEpZztklGy6icFYroCoWyZkfarjbRjurR73ybcpP03PTLIlBPsqWWb7i/KgX/loTjBtnfp1tjMt/DpecMDjqlCn6wVrif18L0Zi5L2MgLTTH3xCgsvDHxWeSus+nse1JKIeqxHbI2wc6aQkCgYBMuBOIt0QYUMRCW5hhKC8FdD7t/ZepmFnw2URo+t3USmzDTdTLo8mv7JThyOvQLTcGBInHzYaTl6ZVT4aWt9t8VyVnZL/XKzDjAbYUzL2J+CUtkBb7ID6T+nsWxpLxW43HSKh7abfPaFY20IpNFHC8qkSc2lEMJGSKVoRZJyZNKQKBgQCCuXKpC/XktVX7G3gJr7/hzL7eSQpy2qcRE9Tcahm4oei/PSIowKtAPFKOD2osYSO3P7uEqup+HtNG8PD3WmJ20ZsUuo/uQJRf0/PM9P9EQWTdOTffqTTmqtQStKFVPYSst6XJ+a/R8WvxjbFR07y45ayW8u3zH3IeAO66mLcZhA==");
		//用私钥签名
		byte[] signature=signUtil.sign("adfadfadsfasdfadfadfadsfasdfadfadfadsfasdfadfadfadfadsfasdfadfadfadsfasdfadfadfadsfasdfadfadfadfadsfasdfadfadfadsfasdfadfadfadsfasdfadfadfadfadsfasdfadfadfadsfasdfadfadfadsfasdfadfadfadfadsfasdfadfadfadsfasdfadfadfadsfasdfadfadfadsfasdfadfadfadsfasdfadfadfadsfasdfadfadfadsfasdfadfadfadsfasdfadfadfadsfasdfadfadfadsfasdfadfadfadsfasdfadfadfadsfasdfadfadfadsfasdfadfadfadsfasdfadfadfadsfasdfadfadfadsfasdf".getBytes("UTF-8"));
		System.out.println("对原文的数字签名："+Base64.getEncoder().encodeToString(signature));
		//调入已有公钥
		signUtil.restorePublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoYdEmJlUMCr+KOJ8MmoM/SDQIQ85jw8pdL7S7P7BfO/6etUSLY543S+REn5AMo8guWGPuNa7RGzGAosJ1FLLD7WuQryNXrYumz1H8/oqLyD2g1iYTYUfsAIanFaMy5wrFQ+8BPQgkRCMiFYsDmVPo9SJSLyuHCfJhtovnPd1c+yCgALbvHV8ZmnbB9ETsAs7806j1eILNiFlsuJTwXKaY80rzA32lFYQne5U+Gs1DWVfxnnomBBqc+GmJ1eTKDDd6fqwCVpNSZbNfKP+5THD60et88FzNDnICyKotbeZwE9OfZS+SR0tnr8YruZ7eV+jZNC7LPJhUWL5qaHZDVb//wIDAQAB");
		//用公钥验签
		System.out.println("（传入原文和数字签名）用公钥验签结果："+signUtil.verify("adfadfadsfasdfadfadfadsfasdfadfadfadsfasdfadfadfadfadsfasdfadfadfadsfasdfadfadfadsfasdfadfadfadfadsfasdfadfadfadsfasdfadfadfadsfasdfadfadfadfadsfasdfadfadfadsfasdfadfadfadsfasdfadfadfadfadsfasdfadfadfadsfasdfadfadfadsfasdfadfadfadsfasdfadfadfadsfasdfadfadfadsfasdfadfadfadsfasdfadfadfadsfasdfadfadfadsfasdfadfadfadsfasdfadfadfadsfasdfadfadfadsfasdfadfadfadsfasdfadfadfadsfasdfadfadfadsfasdfadfadfadsfasdf".getBytes("UTF-8"),signature));
	}
}
