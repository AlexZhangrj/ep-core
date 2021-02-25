
package com.zhrenjie04.alex.util;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author 张人杰
 */
public class JsonUtil {

	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	private final static ObjectMapper STATIC_OBJECT_MAPPER = new ObjectMapper();

	static {
		STATIC_OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
		STATIC_OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		STATIC_OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		STATIC_OBJECT_MAPPER.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
		STATIC_OBJECT_MAPPER.configure(JsonReadFeature.ALLOW_UNQUOTED_FIELD_NAMES.mappedFeature(), true);
		STATIC_OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		STATIC_OBJECT_MAPPER.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
	}

	static final String DYNC_FILTER_PK_AND_OTHERS = "DYNC_FILTER_PK_AND_OTHERS";

	@JsonFilter(DYNC_FILTER_PK_AND_OTHERS)
	interface DynamicFilterPkAndOthers {
	}
	private ObjectMapper thisObjectMapper;
	private int filterCount = 0;
	private SimpleFilterProvider filterProvider;

	private JsonUtil(boolean onlyNonNullProperties) {
		if (thisObjectMapper == null) {
			thisObjectMapper = new ObjectMapper();
			thisObjectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
			thisObjectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
			thisObjectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
			thisObjectMapper.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
			thisObjectMapper.configure(JsonReadFeature.ALLOW_UNQUOTED_FIELD_NAMES.mappedFeature(), true);
			thisObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			thisObjectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		}
		if (filterProvider == null) {
			filterProvider = new SimpleFilterProvider();
		}
		if(onlyNonNullProperties){
			thisObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		}
	}

	public static JsonUtil newSerializer() {
		return new JsonUtil(false);
	}

	public static JsonUtil newSerializer(boolean onlyNonNullProperties) {
		return new JsonUtil(onlyNonNullProperties);
	}

	public String doStringify(Object obj) {
		if (obj == null) {
			return null;
		}
		if (obj instanceof String) {
			return (String) obj;
		}
		try {
			return thisObjectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			logger.error("doStringify(obj)", e);
			throw new RuntimeException(e);
		}
	}
	/**
	 * 不输出指定属性
	 * @param tClazz
	 * @param filteredProps 以英文,号隔开的所需多个或一个字段的字符串
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public JsonUtil without(Class tClazz, String filteredProps) {
		++filterCount;
		if (filterCount > MAX_FILTER_COUNT) {
			throw new RuntimeException("只能使用" + MAX_FILTER_COUNT + "次JsonUtil的without或withOnly调用");
		}
		if (filterProvider == null) {
			filterProvider = new SimpleFilterProvider();
		}
		if (filteredProps != null && !"".equals(filteredProps)) {
			filterProvider.addFilter(DYNC_FILTER_PREFIX + filterCount,
					SimpleBeanPropertyFilter.serializeAllExcept(filteredProps.replaceAll("\\s", "").split(",")));
			thisObjectMapper.addMixIn(tClazz, getFilterClass(filterCount));
		}
		thisObjectMapper.setFilterProvider(filterProvider);
		return this;
	}
	/**
	 * 只输出指定属性
	 * @param tClazz
	 * @param filteredProps 以英文,号隔开的所需多个或一个字段的字符串
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public JsonUtil withOnly(Class tClazz, String filteredProps) {
		++filterCount;
		if (filterCount > MAX_FILTER_COUNT) {
			throw new RuntimeException("只能使用" + MAX_FILTER_COUNT + "次JsonUtil的without或withOnly调用");
		}
		if (filterProvider == null) {
			filterProvider = new SimpleFilterProvider();
		}
		if (filteredProps != null && !"".equals(filteredProps)) {
			filterProvider.addFilter(DYNC_FILTER_PREFIX + filterCount,
					SimpleBeanPropertyFilter.filterOutAllExcept(filteredProps.replaceAll("\\s", "").split(",")));
			thisObjectMapper.addMixIn(tClazz, getFilterClass(filterCount));
		}
		thisObjectMapper.setFilterProvider(filterProvider);
		return this;
	}

	public static String stringify(Object obj) {
		if (obj == null) {
			return null;
		}
		if (obj instanceof String) {
			return (String) obj;
		}
		try {
			return STATIC_OBJECT_MAPPER.writeValueAsString(obj);
		} catch (Exception e) {
			logger.error("stringify(Object)", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将json string反序列化成对象
	 */
	public static <T> T parse(String json, Class<T> valueType) {
		if (json == null || "".equals(json)) {
			return null;
		}
		try {
			return STATIC_OBJECT_MAPPER.readValue(json, valueType);
		} catch (Exception e) {
			logger.error("parse(Object)", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将json array反序列化为数组
	 */
	public static <T> T parse(String json, TypeReference<T> typeReference) {
		if (json == null || "".equals(json)) {
			return null;
		}
		try {
			return (T) STATIC_OBJECT_MAPPER.readValue(json, typeReference);
		} catch (Exception e) {
			logger.error("parse(String,TypeReference)", e);
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		LinkedList<Integer> result = JsonUtil.parse("[1,2,3,4]", new TypeReference<LinkedList<Integer>>() {
		});
		HashMap<String, Object> map = JsonUtil.parse(
				"{\"userId\":\"11111111\n1111111111\",\"count\":1,\"userRank\":1111111111111111111,\"userScore\":111111111111111111111.1,\"userName\":\"aaa\"}",
				new TypeReference<HashMap<String, Object>>() {
				});
		System.out.println(result.size());
		System.out.println(map.get("userRank").toString());
		System.out.println(map.get("userId").toString());
		System.out.println(new BigInteger(map.get("userRank").toString()).longValue());
		System.out.println(map.get("count"));
		System.out.println(map.get("count"));
	}

	static final String DYNC_FILTER_PREFIX = "DYNC_FILTER";
	static final String DYNC_FILTER1 = "DYNC_FILTER1";
	static final String DYNC_FILTER2 = "DYNC_FILTER2";
	static final String DYNC_FILTER3 = "DYNC_FILTER3";
	static final String DYNC_FILTER4 = "DYNC_FILTER4";
	static final String DYNC_FILTER5 = "DYNC_FILTER5";
	static final String DYNC_FILTER6 = "DYNC_FILTER6";
	static final String DYNC_FILTER7 = "DYNC_FILTER7";
	static final String DYNC_FILTER8 = "DYNC_FILTER8";
	static final String DYNC_FILTER9 = "DYNC_FILTER9";
	static final String DYNC_FILTER10 = "DYNC_FILTER10";
	static final String DYNC_FILTER11 = "DYNC_FILTER11";
	static final String DYNC_FILTER12 = "DYNC_FILTER12";
	static final String DYNC_FILTER13 = "DYNC_FILTER13";
	static final String DYNC_FILTER14 = "DYNC_FILTER14";
	static final String DYNC_FILTER15 = "DYNC_FILTER15";
	static final String DYNC_FILTER16 = "DYNC_FILTER16";
	static final String DYNC_FILTER17 = "DYNC_FILTER17";
	static final String DYNC_FILTER18 = "DYNC_FILTER18";
	static final String DYNC_FILTER19 = "DYNC_FILTER19";
	static final String DYNC_FILTER20 = "DYNC_FILTER20";
	static final String DYNC_FILTER21 = "DYNC_FILTER21";
	static final String DYNC_FILTER22 = "DYNC_FILTER22";
	static final String DYNC_FILTER23 = "DYNC_FILTER23";
	static final String DYNC_FILTER24 = "DYNC_FILTER24";
	static final String DYNC_FILTER25 = "DYNC_FILTER25";
	static final String DYNC_FILTER26 = "DYNC_FILTER26";
	static final String DYNC_FILTER27 = "DYNC_FILTER27";
	static final String DYNC_FILTER28 = "DYNC_FILTER28";
	static final String DYNC_FILTER29 = "DYNC_FILTER29";
	static final String DYNC_FILTER30 = "DYNC_FILTER30";
	static final int MAX_FILTER_COUNT = 30;

	@JsonFilter(DYNC_FILTER1)
	interface DynamicFilter1 {
	}

	@JsonFilter(DYNC_FILTER2)
	interface DynamicFilter2 {
	}

	@JsonFilter(DYNC_FILTER3)
	interface DynamicFilter3 {
	}

	@JsonFilter(DYNC_FILTER4)
	interface DynamicFilter4 {
	}

	@JsonFilter(DYNC_FILTER5)
	interface DynamicFilter5 {
	}

	@JsonFilter(DYNC_FILTER6)
	interface DynamicFilter6 {
	}

	@JsonFilter(DYNC_FILTER7)
	interface DynamicFilter7 {
	}

	@JsonFilter(DYNC_FILTER8)
	interface DynamicFilter8 {
	}

	@JsonFilter(DYNC_FILTER9)
	interface DynamicFilter9 {
	}

	@JsonFilter(DYNC_FILTER10)
	interface DynamicFilter10 {
	}

	@JsonFilter(DYNC_FILTER11)
	interface DynamicFilter11 {
	}

	@JsonFilter(DYNC_FILTER12)
	interface DynamicFilter12 {
	}

	@JsonFilter(DYNC_FILTER13)
	interface DynamicFilter13 {
	}

	@JsonFilter(DYNC_FILTER14)
	interface DynamicFilter14 {
	}

	@JsonFilter(DYNC_FILTER15)
	interface DynamicFilter15 {
	}

	@JsonFilter(DYNC_FILTER16)
	interface DynamicFilter16 {
	}

	@JsonFilter(DYNC_FILTER17)
	interface DynamicFilter17 {
	}

	@JsonFilter(DYNC_FILTER18)
	interface DynamicFilter18 {
	}

	@JsonFilter(DYNC_FILTER19)
	interface DynamicFilter19 {
	}

	@JsonFilter(DYNC_FILTER20)
	interface DynamicFilter20 {
	}

	@JsonFilter(DYNC_FILTER21)
	interface DynamicFilter21 {
	}

	@JsonFilter(DYNC_FILTER22)
	interface DynamicFilter22 {
	}

	@JsonFilter(DYNC_FILTER23)
	interface DynamicFilter23 {
	}

	@JsonFilter(DYNC_FILTER24)
	interface DynamicFilter24 {
	}

	@JsonFilter(DYNC_FILTER25)
	interface DynamicFilter25 {
	}

	@JsonFilter(DYNC_FILTER26)
	interface DynamicFilter26 {
	}

	@JsonFilter(DYNC_FILTER27)
	interface DynamicFilter27 {
	}

	@JsonFilter(DYNC_FILTER28)
	interface DynamicFilter28 {
	}

	@JsonFilter(DYNC_FILTER29)
	interface DynamicFilter29 {
	}

	@JsonFilter(DYNC_FILTER30)
	interface DynamicFilter30 {
	}

	@SuppressWarnings("rawtypes")
	private Class getFilterClass(int count) {
		switch (count) {
			case 1:
				return DynamicFilter1.class;
			case 2:
				return DynamicFilter2.class;
			case 3:
				return DynamicFilter3.class;
			case 4:
				return DynamicFilter4.class;
			case 5:
				return DynamicFilter5.class;
			case 6:
				return DynamicFilter6.class;
			case 7:
				return DynamicFilter7.class;
			case 8:
				return DynamicFilter8.class;
			case 9:
				return DynamicFilter9.class;
			case 10:
				return DynamicFilter10.class;
			case 11:
				return DynamicFilter11.class;
			case 12:
				return DynamicFilter12.class;
			case 13:
				return DynamicFilter13.class;
			case 14:
				return DynamicFilter14.class;
			case 15:
				return DynamicFilter15.class;
			case 16:
				return DynamicFilter16.class;
			case 17:
				return DynamicFilter17.class;
			case 18:
				return DynamicFilter18.class;
			case 19:
				return DynamicFilter19.class;
			case 20:
				return DynamicFilter20.class;
			case 21:
				return DynamicFilter21.class;
			case 22:
				return DynamicFilter22.class;
			case 23:
				return DynamicFilter23.class;
			case 24:
				return DynamicFilter24.class;
			case 25:
				return DynamicFilter25.class;
			case 26:
				return DynamicFilter26.class;
			case 27:
				return DynamicFilter27.class;
			case 28:
				return DynamicFilter28.class;
			case 29:
				return DynamicFilter29.class;
			case 30:
				return DynamicFilter30.class;
			default:
				return null;
		}
	}
}