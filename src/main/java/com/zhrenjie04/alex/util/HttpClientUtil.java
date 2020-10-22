package com.zhrenjie04.alex.util;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.net.ssl.SSLException;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zhrenjie04.alex.core.exception.CrisisError;

/**
 * @author 张人杰
 */
public class HttpClientUtil {
	private static final String CHARSET_UTF8 = "utf-8";
	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	private static PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();

	private static Integer waitIdleConnectionTimeout = 1000;
	private static Integer connectTimeout = 1000;
	private static Integer soTimeout = 1000;
	private static Integer retryExecutionCount = 1;

	public static void init(Integer maxTotal, Integer maxPerRoute, Integer validateInactivityDuration,
			Integer waitIdleConnectionTimeoutConfig, Integer connectTimeoutConfig, Integer soTimeoutConfig,
			Integer retryExecutionCountConfig) {
		poolingHttpClientConnectionManager.setMaxTotal(maxTotal);
		poolingHttpClientConnectionManager.setDefaultMaxPerRoute(maxPerRoute);
		poolingHttpClientConnectionManager.setValidateAfterInactivity(validateInactivityDuration);
		SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(soTimeout).build();
		poolingHttpClientConnectionManager.setDefaultSocketConfig(socketConfig);
		waitIdleConnectionTimeout = waitIdleConnectionTimeoutConfig;// 等待池中空闲
		connectTimeout = connectTimeoutConfig;// 链接超时
		soTimeout = soTimeoutConfig;// 数据传输超时
		retryExecutionCount = retryExecutionCountConfig;// 重试次数
	}

	/**
	 * POST请求
	 * 
	 * @param url
	 * @param paramObject   比如:传入hashMap
	 * @param returnCharSet 对方服务器返回字符集，比如:传入GB2312
	 * @return
	 */
	public static String doPostJson(String url, Object paramObject) {
		return doPostJson(url, paramObject, CHARSET_UTF8);
	}

	/**
	 * POST请求
	 * 
	 * @param url
	 * @param paramObject   比如:传入hashMap
	 * @param returnCharSet 对方服务器返回字符集，比如:传入GB2312
	 * @return
	 */
	public static String doPostJson(String url, HashMap<String, String> headers, Object paramObject) {
		return doPostJson(url, headers, paramObject, CHARSET_UTF8);
	}

	/**
	 * POST请求
	 * 
	 * @param url
	 * @param paramObject   比如:传入hashMap
	 * @param returnCharSet 对方服务器返回字符集，比如:传入GB2312
	 * @return
	 */
	public static String doPostJson(String url, HashMap<String, String> headers, Object paramObject,
			String returnCharSet) {
		CloseableHttpClient closeableHttpClient = getCloseableHttpClient();
		HttpPost method = new HttpPost(url);
		// 设置请求参数
		RequestConfig config = RequestConfig.custom().setConnectTimeout(connectTimeout) // 连接超时时间
				.setConnectionRequestTimeout(waitIdleConnectionTimeout) // 从等待线程池中空闲链接的超时时间
				.setSocketTimeout(soTimeout) // 设置数据超时时间
				.build();
		method.setConfig(config);
		logger.debug("request:doPostJson:{}(post data is following)",url);
		if (null != headers) {
			for (Entry<String, String> entry : headers.entrySet()) {
				method.addHeader(entry.getKey(), entry.getValue());
			}
		}
		if (null != paramObject) {
			String json = JsonUtil.stringify(paramObject);
			logger.debug("request:doPostJson:{}:post data:{}",url,json);
			// 解决中文乱码问题
			StringEntity entity = new StringEntity(json, CHARSET_UTF8);
			entity.setContentEncoding(CHARSET_UTF8);
			entity.setContentType("application/json");
			method.setEntity(entity);
		} else {
			logger.debug("request:doPostJson:{}:post data: null",url);
		}
		CloseableHttpResponse response = null;
		try {
			response = closeableHttpClient.execute(method);
			String result = EntityUtils.toString(response.getEntity(), returnCharSet);
			if (response.getStatusLine() != null && response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				logger.error("{}:::{}",response.getStatusLine().getStatusCode(),result);
				throw new CrisisError(response.getStatusLine().getStatusCode() + ":::" + result);
			}
			logger.debug("request:doPostJson:{}:response:{}",url,result);
			return result;
		} catch (IOException e) {
			logger.error("connect error:" + url, e);
			throw new CrisisError(e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error("connect close error:" + url, e);
				}
			}
		}
	}

	/**
	 * POST请求
	 * 
	 * @param url
	 * @param paramObject   比如:传入hashMap
	 * @param returnCharSet 对方服务器返回字符集，比如:传入GB2312
	 * @return
	 */
	public static String doPostJson(String url, Object paramObject, String returnCharSet) {
		CloseableHttpClient closeableHttpClient = getCloseableHttpClient();
		HttpPost method = new HttpPost(url);
		// 设置请求参数
		RequestConfig config = RequestConfig.custom().setConnectTimeout(connectTimeout) // 连接超时时间
				.setConnectionRequestTimeout(waitIdleConnectionTimeout) // 从等待线程池中空闲链接的超时时间
				.setSocketTimeout(soTimeout) // 设置数据超时时间
				.build();
		method.setConfig(config);
		logger.debug("request:doPostJson:{}(post data is following)",url);
		if (null != paramObject) {
			String json = JsonUtil.stringify(paramObject);
			logger.debug("request:doPostJson:{} :post data:{} ",url,json);
			// 解决中文乱码问题
			StringEntity entity = new StringEntity(json, CHARSET_UTF8);
			entity.setContentEncoding(CHARSET_UTF8);
			entity.setContentType("application/json");
			method.setEntity(entity);
		} else {
			logger.debug("request:doPostJson:{} post data: null",url);
		}
		CloseableHttpResponse response = null;
		try {
			response = closeableHttpClient.execute(method);
			String result = EntityUtils.toString(response.getEntity(), returnCharSet);
			logger.debug("request:doPostJson:{}:response:{}",url,result);
			if (response.getStatusLine() != null && response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				logger.error("{}:::{}",response.getStatusLine().getStatusCode(),result);
				throw new CrisisError(response.getStatusLine().getStatusCode() + ":::" + result);
			}
			return result;
		} catch (IOException e) {
			logger.error("connect error:" + url, e);
			throw new CrisisError(e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error("connect close error:" + url, e);
				}
			}
		}
	}

	/**
	 * POST请求,返回对象用法：Jsonresponse response
	 * =doPostJson(url,paramObject,Jsonresponse.class);
	 * 
	 * @param url
	 * @param paramObject   比如:传入hashMap
	 * @param clazz         反序列化的类，比如:传入User.class
	 * @param returnCharSet 对方服务器返回字符集，比如:传入GB2312
	 * @return
	 */
	public static <T> T doPostJson(String url, Object paramObject, Class<T> clazz) {
		return JsonUtil.parse(doPostJson(url, paramObject), clazz);
	}

	/**
	 * POST请求,返回对象用法：Jsonresponse response
	 * =doPostJson(url,paramObject,Jsonresponse.class);
	 * 
	 * @param url
	 * @param paramObject   比如:传入hashMap
	 * @param clazz         反序列化的类，比如:传入User.class
	 * @param returnCharSet 对方服务器返回字符集，比如:传入GB2312
	 * @return
	 */
	public static <T> T doPostJson(String url, HashMap<String, String> headers, Object paramObject, Class<T> clazz) {
		return JsonUtil.parse(doPostJson(url, headers, paramObject), clazz);
	}

	/**
	 * POST请求,返回对象用法：Jsonresponse response
	 * =doPostJson(url,paramObject,Jsonresponse.class);
	 * 
	 * @param url
	 * @param paramObject   比如:传入hashMap
	 * @param clazz         反序列化的类，比如:传入User.class
	 * @param returnCharSet 对方服务器返回字符集，比如:传入GB2312
	 * @return
	 */
	public static <T> T doPostJson(String url, Object paramObject, Class<T> clazz, String returnCharSet) {
		return JsonUtil.parse(doPostJson(url, paramObject, returnCharSet), clazz);
	}

	/**
	 * POST请求,返回对象用法：Jsonresponse response
	 * =doPostJson(url,paramObject,Jsonresponse.class);
	 * 
	 * @param url
	 * @param paramObject   比如:传入hashMap
	 * @param clazz         反序列化的类，比如:传入User.class
	 * @param returnCharSet 对方服务器返回字符集，比如:传入GB2312
	 * @return
	 */
	public static <T> T doPostJson(String url, HashMap<String, String> headers, Object paramObject, Class<T> clazz,
			String returnCharSet) {
		return JsonUtil.parse(doPostJson(url, headers, paramObject, returnCharSet), clazz);
	}

	/**
	 * Post请求，返回List用法：LinkedList<Integer> response = doPostJson(url, paramObject,
	 * new TypeReference<LinkedList<Integer>>(){});
	 * 
	 * @param url
	 * @param paramObject   比如:传入hashMap
	 * @param typeReference 反序列化的模板化参数类，比如:传入new
	 *                      TypeReference<LinkedList<Integer>>(){}
	 * @param returnCharSet 对方服务器返回字符集，比如:传入GB2312
	 * @return
	 */
	public static <T> T doPostJson(String url, Object paramObject, TypeReference<T> typeReference) {
		return JsonUtil.parse(doPostJson(url, paramObject), typeReference);
	}

	/**
	 * Post请求，返回List用法：LinkedList<Integer> response = doPostJson(url, paramObject,
	 * new TypeReference<LinkedList<Integer>>(){});
	 * 
	 * @param url
	 * @param paramObject   比如:传入hashMap
	 * @param typeReference 反序列化的模板化参数类，比如:传入new
	 *                      TypeReference<LinkedList<Integer>>(){}
	 * @param returnCharSet 对方服务器返回字符集，比如:传入GB2312
	 * @return
	 */
	public static <T> T doPostJson(String url, HashMap<String, String> headers, Object paramObject,
			TypeReference<T> typeReference) {
		return JsonUtil.parse(doPostJson(url, headers, paramObject), typeReference);
	}

	/**
	 * Post请求，返回List用法：LinkedList<Integer> response = doPostJson(url, paramObject,
	 * new TypeReference<LinkedList<Integer>>(){});
	 * 
	 * @param url
	 * @param paramObject   比如:传入hashMap
	 * @param typeReference 反序列化的模板化参数类，比如:传入new
	 *                      TypeReference<LinkedList<Integer>>(){}
	 * @param returnCharSet 对方服务器返回字符集，比如:传入GB2312
	 * @return
	 */
	public static <T> T doPostJson(String url, Object paramObject, TypeReference<T> typeReference,
			String returnCharSet) {
		return JsonUtil.parse(doPostJson(url, paramObject, returnCharSet), typeReference);
	}

	/**
	 * PUT请求
	 * 
	 * @param url
	 * @param paramObject
	 * @param returnCharSet 对方服务器返回字符集，比如:传入GB2312
	 * @return
	 */
	public static String doPutJson(String url, Object paramObject) {
		return doPutJson(url, paramObject, CHARSET_UTF8);
	}

	/**
	 * PUT请求
	 * 
	 * @param url
	 * @param paramObject
	 * @param returnCharSet 对方服务器返回字符集，比如:传入GB2312
	 * @return
	 */
	public static String doPutJson(String url, Object paramObject, String returnCharSet) {
		logger.debug("request:doPutJson:{}(put data is following)",url);
		CloseableHttpClient closeableHttpClient = getCloseableHttpClient();
		HttpPut method = new HttpPut(url);
		// 设置请求参数
		RequestConfig config = RequestConfig.custom().setConnectTimeout(connectTimeout) // 连接超时时间
				.setConnectionRequestTimeout(waitIdleConnectionTimeout) // 从等待线程池中空闲链接的超时时间
				.setSocketTimeout(soTimeout) // 设置数据超时时间
				.build();
		method.setConfig(config);
		if (null != paramObject) {
			String json = JsonUtil.stringify(paramObject);
			logger.debug("request:doPutJson:{}:put data:{}",url,json);
			// 解决中文乱码问题
			StringEntity entity = new StringEntity(json, CHARSET_UTF8);
			entity.setContentEncoding(CHARSET_UTF8);
			entity.setContentType("application/json");
			method.setEntity(entity);
		} else {
			logger.debug("request:doPutJson:{}:put data: null",url);
		}
		CloseableHttpResponse response = null;
		try {
			response = closeableHttpClient.execute(method);
			String result = EntityUtils.toString(response.getEntity(), returnCharSet);
			logger.debug("request:doPutJson:{}:response:{}",url,result);
			if (response.getStatusLine() != null && response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				logger.error("{}:::{}",response.getStatusLine().getStatusCode(),result);
				throw new CrisisError(response.getStatusLine().getStatusCode() + ":::" + result);
			}
			return result;
		} catch (IOException e) {
			logger.error("connect error:" + url, e);
			throw new CrisisError(e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error("connect close error:" + url, e);
				}
			}
		}
	}

	/**
	 * PUT请求,返回对象用法：Jsonresponse response
	 * =doPutJson(url,paramObject,Jsonresponse.class);
	 * 
	 * @param url
	 * @param paramObject   比如:传入hashMap
	 * @param clazz         反序列化的类，比如:传入User.class
	 * @param returnCharSet 对方服务器返回字符集，比如:传入GB2312
	 * @return
	 */
	public static <T> T doPutJson(String url, Object paramObject, Class<T> clazz) {
		return JsonUtil.parse(doPutJson(url, paramObject), clazz);
	}

	/**
	 * PUT请求,返回对象用法：Jsonresponse response
	 * =doPutJson(url,paramObject,Jsonresponse.class);
	 * 
	 * @param url
	 * @param paramObject   比如:传入hashMap
	 * @param clazz         反序列化的类，比如:传入User.class
	 * @param returnCharSet 对方服务器返回字符集，比如:传入GB2312
	 * @return
	 */
	public static <T> T doPutJson(String url, Object paramObject, Class<T> clazz, String returnCharSet) {
		return JsonUtil.parse(doPutJson(url, paramObject, returnCharSet), clazz);
	}

	/**
	 * PUT请求，返回List用法：LinkedList<Integer> response = doPutJson(url, paramObject, new
	 * TypeReference<LinkedList<Integer>>(){});
	 * 
	 * @param url
	 * @param paramObject   比如:传入hashMap
	 * @param typeReference 反序列化的模板化参数类，比如:传入new
	 *                      TypeReference<LinkedList<Integer>>(){}
	 * @param returnCharSet 对方服务器返回字符集，比如:传入GB2312
	 * @return
	 */
	public static <T> T doPutJson(String url, Object paramObject, TypeReference<T> typeReference) {
		return JsonUtil.parse(doPutJson(url, paramObject), typeReference);
	}

	/**
	 * PUT请求，返回List用法：LinkedList<Integer> response = doPutJson(url, paramObject, new
	 * TypeReference<LinkedList<Integer>>(){});
	 * 
	 * @param url
	 * @param paramObject   比如:传入hashMap
	 * @param typeReference 反序列化的模板化参数类，比如:传入new
	 *                      TypeReference<LinkedList<Integer>>(){}
	 * @param returnCharSet 对方服务器返回字符集，比如:传入GB2312
	 * @return
	 */
	public static <T> T doPutJson(String url, Object paramObject, TypeReference<T> typeReference,
			String returnCharSet) {
		return JsonUtil.parse(doPutJson(url, paramObject, returnCharSet), typeReference);
	}

	/**
	 * GET请求
	 * 
	 * @param url
	 * @param returnCharSet 对方服务器返回字符集，比如:传入GB2312
	 * @return
	 */
	public static String doGet(String url) {
		return doGet(url, CHARSET_UTF8);
	}

	/**
	 * GET请求
	 * 
	 * @param url
	 * @param returnCharSet 对方服务器返回字符集，比如:传入GB2312
	 * @return
	 */
	public static String doGet(String url, String returnCharSet) {
		logger.debug("request:doGet:" + url);
		CloseableHttpClient closeableHttpClient = getCloseableHttpClient();
		HttpGet method = new HttpGet(url);
		// 设置请求参数
		RequestConfig config = RequestConfig.custom().setConnectTimeout(connectTimeout) // 连接超时时间
				.setConnectionRequestTimeout(waitIdleConnectionTimeout) // 从等待线程池中空闲链接的超时时间
				.setSocketTimeout(soTimeout) // 设置数据超时时间
				.build();
		method.setConfig(config);
		CloseableHttpResponse response = null;
		try {
			response = closeableHttpClient.execute(method);
			String result = EntityUtils.toString(response.getEntity(), returnCharSet);
			logger.debug("request:doGet:{}:response:{}",url,result);
			if (response.getStatusLine() != null && response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				logger.error("{}:::{}",response.getStatusLine().getStatusCode(),result);
				throw new CrisisError(response.getStatusLine().getStatusCode() + ":::" + result);
			}
			return result;
		} catch (IOException e) {
			logger.error("connect error:" + url, e);
			throw new CrisisError(e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error("connect close error:" + url, e);
				}
			}
		}
	}

	/**
	 * GET请求,返回对象用法：Jsonresponse response =doGet(url,Jsonresponse.class);
	 * 
	 * @param url
	 * @param clazz反序列化类型
	 * @param returnCharSet 对方服务器返回字符集，比如:传入GB2312
	 * @return
	 */
	public static <T> T doGet(String url, Class<T> clazz, String returnCharSet) {
		return JsonUtil.parse(doGet(url, returnCharSet), clazz);
	}

	/**
	 * GET请求,返回对象用法：Jsonresponse response =doGet(url,Jsonresponse.class);
	 * 
	 * @param url
	 * @param clazz反序列化类型
	 * @param returnCharSet 对方服务器返回字符集，比如:传入GB2312
	 * @return
	 */
	public static <T> T doGet(String url, Class<T> clazz) {
		return JsonUtil.parse(doGet(url), clazz);
	}

	/**
	 * GET请求，返回List用法：LinkedList<Integer> response = doGet(url, new
	 * TypeReference<LinkedList<Integer>>(){});
	 * 
	 * @param url
	 * @param clazz反序列化类型
	 * @param returnCharSet 对方服务器返回字符集，比如:传入GB2312
	 * @return
	 */
	public static <T> T doGet(String url, TypeReference<T> typeReference, String returnCharSet) {
		return JsonUtil.parse(doGet(url, returnCharSet), typeReference);
	}

	/**
	 * GET请求，返回List用法：LinkedList<Integer> response = doGet(url, new
	 * TypeReference<LinkedList<Integer>>(){});
	 * 
	 * @param url
	 * @param clazz反序列化类型
	 * @param returnCharSet 对方服务器返回字符集，比如:传入GB2312
	 * @return
	 */
	public static <T> T doGet(String url, TypeReference<T> typeReference) {
		return JsonUtil.parse(doGet(url), typeReference);
	}

	/**
	 * DELETE请求
	 * 
	 * @param url
	 * @param returnCharSet 对方服务器返回字符集，比如:传入GB2312
	 * @return
	 */
	public static String doDelete(String url) {
		return doDelete(url, CHARSET_UTF8);
	}

	/**
	 * DELETE请求
	 * 
	 * @param url
	 * @param returnCharSet 对方服务器返回字符集，比如:传入GB2312
	 * @return
	 */
	public static String doDelete(String url, String returnCharSet) {
		logger.debug("request:doDelete:" + url);
		CloseableHttpClient closeableHttpClient = getCloseableHttpClient();
		HttpDelete method = new HttpDelete(url);
		// 设置请求参数
		RequestConfig config = RequestConfig.custom().setConnectTimeout(connectTimeout) // 连接超时时间
				.setConnectionRequestTimeout(waitIdleConnectionTimeout) // 从等待线程池中空闲链接的超时时间
				.setSocketTimeout(soTimeout) // 设置数据超时时间
				.build();
		method.setConfig(config);
		CloseableHttpResponse response = null;
		try {
			response = closeableHttpClient.execute(method);
			String result = EntityUtils.toString(response.getEntity(), returnCharSet);
			logger.debug("request:doDelete:{} :response:{}",url,result);
			if (response.getStatusLine() != null && response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				logger.error("{}:::{}",response.getStatusLine().getStatusCode(),result);
				throw new CrisisError(response.getStatusLine().getStatusCode() + ":::" + result);
			}
			return result;
		} catch (IOException e) {
			logger.error("connect error:" + url, e);
			throw new CrisisError(e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error("connect close error:" + url, e);
				}
			}
		}
	}

	/**
	 * DELETE请求,返回对象用法：Jsonresponse response =doGet(url,Jsonresponse.class);
	 * 
	 * @param url
	 * @param clazz反序列化类型
	 * @param returnCharSet 对方服务器返回字符集，比如:传入GB2312
	 * @return
	 */
	public static <T> T doDelete(String url, Class<T> clazz) {
		return JsonUtil.parse(doDelete(url), clazz);
	}

	/**
	 * DELETE请求,返回对象用法：Jsonresponse response =doGet(url,Jsonresponse.class);
	 * 
	 * @param url
	 * @param clazz反序列化类型
	 * @param returnCharSet 对方服务器返回字符集，比如:传入GB2312
	 * @return
	 */
	public static <T> T doDelete(String url, Class<T> clazz, String returnCharSet) {
		return JsonUtil.parse(doDelete(url, returnCharSet), clazz);
	}

	/**
	 * DELETE请求，返回List用法：LinkedList<Integer> response = doGet(url, new
	 * TypeReference<LinkedList<Integer>>(){});
	 * 
	 * @param url
	 * @param clazz反序列化类型
	 * @param returnCharSet 对方服务器返回字符集，比如:传入GB2312
	 * @return
	 */
	public static <T> T doDelete(String url, TypeReference<T> typeReference) {
		return JsonUtil.parse(doDelete(url), typeReference);
	}

	/**
	 * DELETE请求，返回List用法：LinkedList<Integer> response = doGet(url, new
	 * TypeReference<LinkedList<Integer>>(){});
	 * 
	 * @param url
	 * @param clazz反序列化类型
	 * @param returnCharSet 对方服务器返回字符集，比如:传入GB2312
	 * @return
	 */
	public static <T> T doDelete(String url, TypeReference<T> typeReference, String returnCharSet) {
		return JsonUtil.parse(doDelete(url, returnCharSet), typeReference);
	}

	/**
	 * 从连接池中获取连接
	 * 
	 * @return
	 */
	private static CloseableHttpClient getCloseableHttpClient() {
		return HttpClientBuilder.create().setConnectionManager(poolingHttpClientConnectionManager)
				.setRetryHandler(new HttpRequestRetryHandlerImpl(retryExecutionCount)).build();
	}

	/**
	 * retry配置类
	 */
	private static class HttpRequestRetryHandlerImpl implements HttpRequestRetryHandler {
		private int retryExecutionCount;

		public HttpRequestRetryHandlerImpl(int retryExecutionCount) {
			this.retryExecutionCount = retryExecutionCount;
		}

		@Override
		public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
			if (executionCount > retryExecutionCount) {
				return false;
			}
			if (exception instanceof InterruptedIOException) {
				return false;
			}
			if (exception instanceof UnknownHostException) {
				return false;
			}
			if (exception instanceof ConnectTimeoutException) {
				return true;
			}
			if (exception instanceof SSLException) {
				return false;
			}
			HttpClientContext clientContext = HttpClientContext.adapt(context);
			HttpRequest request = clientContext.getRequest();
			boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
			if (idempotent) {
				// Retry if the request is considered idempotent
				return true;
			}
			return false;
		}

	};
}