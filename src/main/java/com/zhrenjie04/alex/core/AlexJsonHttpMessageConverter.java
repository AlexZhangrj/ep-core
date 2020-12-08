package com.zhrenjie04.alex.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.zhrenjie04.alex.util.JsonUtil;

public class AlexJsonHttpMessageConverter implements HttpMessageConverter<Serializable> {

	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		if(MediaType.APPLICATION_JSON.equals(mediaType)) {
			return true;
		}else if(MediaType.APPLICATION_JSON_UTF8.equals(mediaType)) {
				return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		if(MediaType.APPLICATION_JSON.equals(mediaType)) {
			return true;
		}else if(MediaType.APPLICATION_JSON_UTF8.equals(mediaType)) {
				return true;
		}else {
			return false;
		}
	}

	@Override
	public List<MediaType> getSupportedMediaTypes() {
		List<MediaType>supportedMediaTypes=new LinkedList<>();
		supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		supportedMediaTypes.add(MediaType.APPLICATION_JSON);
		return supportedMediaTypes;
	}

	@Override
	public Serializable read(Class<? extends Serializable> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		InputStream is=inputMessage.getBody();
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		byte[] bytes=new byte[1024];
		int len=0;
		while((len=is.read(bytes))>0) {
			baos.write(bytes, 0, len);
		}
		String paramString=new String(baos.toByteArray(),"utf-8");
		return JsonUtil.parse(paramString,clazz);
	}

	@Override
	public void write(Serializable t, MediaType contentType, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		OutputStream os=outputMessage.getBody();
		String resultString=JsonUtil.stringify(t);
		os.write(resultString.getBytes("utf-8"));
	}
}
