package com.zhrenjie04.alex.core;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

/**
 * @author Alex.Zhang
 */
public class AlexJsonSerializer {

	private static final Logger logger = LoggerFactory.getLogger(AlexJsonSerializer.class);

	static final String DYNC_INCLUDE_PREFIX = "DYNC_INCLUDE";
	static final String DYNC_FILTER_PREFIX = "DYNC_FILTER";
	static final String DYNC_INCLUDE1 = "DYNC_INCLUDE1";
	static final String DYNC_FILTER1 = "DYNC_FILTER1";
	static final String DYNC_INCLUDE2 = "DYNC_INCLUDE2";
	static final String DYNC_FILTER2 = "DYNC_FILTER2";
	static final String DYNC_INCLUDE3 = "DYNC_INCLUDE3";
	static final String DYNC_FILTER3 = "DYNC_FILTER3";
	static final String DYNC_INCLUDE4 = "DYNC_INCLUDE4";
	static final String DYNC_FILTER4 = "DYNC_FILTER4";
	static final String DYNC_INCLUDE5 = "DYNC_INCLUDE5";
	static final String DYNC_FILTER5 = "DYNC_FILTER5";
	static final String DYNC_INCLUDE6 = "DYNC_INCLUDE6";
	static final String DYNC_FILTER6 = "DYNC_FILTER6";
	static final String DYNC_INCLUDE7 = "DYNC_INCLUDE7";
	static final String DYNC_FILTER7 = "DYNC_FILTER7";
	static final String DYNC_INCLUDE8 = "DYNC_INCLUDE8";
	static final String DYNC_FILTER8 = "DYNC_FILTER8";
	static final String DYNC_INCLUDE9 = "DYNC_INCLUDE9";
	static final String DYNC_FILTER9 = "DYNC_FILTER9";
	static final String DYNC_INCLUDE10 = "DYNC_INCLUDE10";
	static final String DYNC_FILTER10 = "DYNC_FILTER10";
	static final String DYNC_INCLUDE11 = "DYNC_INCLUDE11";
	static final String DYNC_FILTER11 = "DYNC_FILTER11";
	static final String DYNC_INCLUDE12 = "DYNC_INCLUDE12";
	static final String DYNC_FILTER12 = "DYNC_FILTER12";
	static final String DYNC_INCLUDE13 = "DYNC_INCLUDE13";
	static final String DYNC_FILTER13 = "DYNC_FILTER13";
	static final String DYNC_INCLUDE14 = "DYNC_INCLUDE14";
	static final String DYNC_FILTER14 = "DYNC_FILTER14";
	static final String DYNC_INCLUDE15 = "DYNC_INCLUDE15";
	static final String DYNC_FILTER15 = "DYNC_FILTER15";
	static final String DYNC_INCLUDE16 = "DYNC_INCLUDE16";
	static final String DYNC_FILTER16 = "DYNC_FILTER16";
	static final String DYNC_INCLUDE17 = "DYNC_INCLUDE17";
	static final String DYNC_FILTER17 = "DYNC_FILTER17";
	static final String DYNC_INCLUDE18 = "DYNC_INCLUDE18";
	static final String DYNC_FILTER18 = "DYNC_FILTER18";
	static final String DYNC_INCLUDE19 = "DYNC_INCLUDE19";
	static final String DYNC_FILTER19 = "DYNC_FILTER19";
	static final String DYNC_INCLUDE20 = "DYNC_INCLUDE20";
	static final String DYNC_FILTER20 = "DYNC_FILTER20";
	static final String DYNC_INCLUDE21 = "DYNC_INCLUDE21";
	static final String DYNC_FILTER21 = "DYNC_FILTER21";
	static final String DYNC_INCLUDE22 = "DYNC_INCLUDE22";
	static final String DYNC_FILTER22 = "DYNC_FILTER22";
	static final String DYNC_INCLUDE23 = "DYNC_INCLUDE23";
	static final String DYNC_FILTER23 = "DYNC_FILTER23";
	static final String DYNC_INCLUDE24 = "DYNC_INCLUDE24";
	static final String DYNC_FILTER24 = "DYNC_FILTER24";
	static final String DYNC_INCLUDE25 = "DYNC_INCLUDE25";
	static final String DYNC_FILTER25 = "DYNC_FILTER25";
	static final String DYNC_INCLUDE26 = "DYNC_INCLUDE26";
	static final String DYNC_FILTER26 = "DYNC_FILTER26";
	static final String DYNC_INCLUDE27 = "DYNC_INCLUDE27";
	static final String DYNC_FILTER27 = "DYNC_FILTER27";
	static final String DYNC_INCLUDE28 = "DYNC_INCLUDE28";
	static final String DYNC_FILTER28 = "DYNC_FILTER28";
	static final String DYNC_INCLUDE29 = "DYNC_INCLUDE29";
	static final String DYNC_FILTER29 = "DYNC_FILTER29";
	static final String DYNC_INCLUDE30 = "DYNC_INCLUDE30";
	static final String DYNC_FILTER30 = "DYNC_FILTER30";
	static final int MAX_FILTER_COUNT = 30;
	
	private ObjectMapper mapper = new ObjectMapper();

	public AlexJsonSerializer() {
		SimpleModule module = new SimpleModule();
		module.addSerializer(BigDecimal.class, new AlexBigDecimalSerializer());//核心代码：加入默认BigDecimal处理
		module.addSerializer(Date.class, new AlexTimestampSerializer());//核心代码：加入默认java.util.Date的处理，处理为整数
		mapper.registerModule(module);
		mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		mapper.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
		mapper.configure(JsonReadFeature.ALLOW_UNQUOTED_FIELD_NAMES.mappedFeature(), true);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	private SimpleFilterProvider filterProvider = new SimpleFilterProvider();
	private int filterCount = 0;

	@JsonFilter(DYNC_FILTER1)
	interface DynamicFilter1 {
	}

	@JsonFilter(DYNC_INCLUDE1)
	interface DynamicInclude1 {
	}

	@JsonFilter(DYNC_FILTER2)
	interface DynamicFilter2 {
	}

	@JsonFilter(DYNC_INCLUDE2)
	interface DynamicInclude2 {
	}

	@JsonFilter(DYNC_FILTER3)
	interface DynamicFilter3 {
	}

	@JsonFilter(DYNC_INCLUDE3)
	interface DynamicInclude3 {
	}

	@JsonFilter(DYNC_FILTER4)
	interface DynamicFilter4 {
	}

	@JsonFilter(DYNC_INCLUDE4)
	interface DynamicInclude4 {
	}

	@JsonFilter(DYNC_FILTER5)
	interface DynamicFilter5 {
	}

	@JsonFilter(DYNC_INCLUDE5)
	interface DynamicInclude5 {
	}

	@JsonFilter(DYNC_FILTER6)
	interface DynamicFilter6 {
	}

	@JsonFilter(DYNC_INCLUDE6)
	interface DynamicInclude6 {
	}

	@JsonFilter(DYNC_FILTER7)
	interface DynamicFilter7 {
	}

	@JsonFilter(DYNC_INCLUDE7)
	interface DynamicInclude7 {
	}

	@JsonFilter(DYNC_FILTER8)
	interface DynamicFilter8 {
	}

	@JsonFilter(DYNC_INCLUDE8)
	interface DynamicInclude8 {
	}

	@JsonFilter(DYNC_FILTER9)
	interface DynamicFilter9 {
	}

	@JsonFilter(DYNC_INCLUDE9)
	interface DynamicInclude9 {
	}

	@JsonFilter(DYNC_FILTER10)
	interface DynamicFilter10 {
	}

	@JsonFilter(DYNC_INCLUDE10)
	interface DynamicInclude10 {
	}

	@JsonFilter(DYNC_FILTER11)
	interface DynamicFilter11 {
	}

	@JsonFilter(DYNC_INCLUDE11)
	interface DynamicInclude11 {
	}

	@JsonFilter(DYNC_FILTER12)
	interface DynamicFilter12 {
	}

	@JsonFilter(DYNC_INCLUDE12)
	interface DynamicInclude12 {
	}

	@JsonFilter(DYNC_FILTER13)
	interface DynamicFilter13 {
	}

	@JsonFilter(DYNC_INCLUDE13)
	interface DynamicInclude13 {
	}

	@JsonFilter(DYNC_FILTER14)
	interface DynamicFilter14 {
	}

	@JsonFilter(DYNC_INCLUDE14)
	interface DynamicInclude14 {
	}

	@JsonFilter(DYNC_FILTER15)
	interface DynamicFilter15 {
	}

	@JsonFilter(DYNC_INCLUDE15)
	interface DynamicInclude15 {
	}

	@JsonFilter(DYNC_FILTER16)
	interface DynamicFilter16 {
	}

	@JsonFilter(DYNC_INCLUDE16)
	interface DynamicInclude16 {
	}

	@JsonFilter(DYNC_FILTER17)
	interface DynamicFilter17 {
	}

	@JsonFilter(DYNC_INCLUDE17)
	interface DynamicInclude17 {
	}

	@JsonFilter(DYNC_FILTER18)
	interface DynamicFilter18 {
	}

	@JsonFilter(DYNC_INCLUDE18)
	interface DynamicInclude18 {
	}

	@JsonFilter(DYNC_FILTER19)
	interface DynamicFilter19 {
	}

	@JsonFilter(DYNC_INCLUDE19)
	interface DynamicInclude19 {
	}

	@JsonFilter(DYNC_FILTER20)
	interface DynamicFilter20 {
	}

	@JsonFilter(DYNC_INCLUDE20)
	interface DynamicInclude20 {
	}

	@JsonFilter(DYNC_FILTER21)
	interface DynamicFilter21 {
	}

	@JsonFilter(DYNC_INCLUDE21)
	interface DynamicInclude21 {
	}

	@JsonFilter(DYNC_FILTER22)
	interface DynamicFilter22 {
	}

	@JsonFilter(DYNC_INCLUDE22)
	interface DynamicInclude22 {
	}

	@JsonFilter(DYNC_FILTER23)
	interface DynamicFilter23 {
	}

	@JsonFilter(DYNC_INCLUDE23)
	interface DynamicInclude23 {
	}

	@JsonFilter(DYNC_FILTER24)
	interface DynamicFilter24 {
	}

	@JsonFilter(DYNC_INCLUDE24)
	interface DynamicInclude24 {
	}

	@JsonFilter(DYNC_FILTER25)
	interface DynamicFilter25 {
	}

	@JsonFilter(DYNC_INCLUDE25)
	interface DynamicInclude25 {
	}

	@JsonFilter(DYNC_FILTER26)
	interface DynamicFilter26 {
	}

	@JsonFilter(DYNC_INCLUDE26)
	interface DynamicInclude26 {
	}

	@JsonFilter(DYNC_FILTER27)
	interface DynamicFilter27 {
	}

	@JsonFilter(DYNC_INCLUDE27)
	interface DynamicInclude27 {
	}

	@JsonFilter(DYNC_FILTER28)
	interface DynamicFilter28 {
	}

	@JsonFilter(DYNC_INCLUDE28)
	interface DynamicInclude28 {
	}

	@JsonFilter(DYNC_FILTER29)
	interface DynamicFilter29 {
	}

	@JsonFilter(DYNC_INCLUDE29)
	interface DynamicInclude29 {
	}

	@JsonFilter(DYNC_FILTER30)
	interface DynamicFilter30 {
	}

	@JsonFilter(DYNC_INCLUDE30)
	interface DynamicInclude30 {
	}

	/**
	 * @param clazz   target type
	 * @param include include fields
	 * @param filter  filter fields
	 */
	public void filter(Class<?> clazz, String include, String filter) {
		++filterCount;
		if (clazz == null) {
			TClazzLocal.remove();
			return;
		}
		if (clazz == FilterWithNoneFiltered.class) {
			TClazzLocal.remove();
			return;
		}
		if (clazz == FilterWithNormalFiltered.class) {
			// 默认普通filter不显示otherParams和otherResults
			Class<?> tClazz = TClazzLocal.get();
			TClazzLocal.remove();
			if (tClazz == null) {
				return;
			}
			filter = "otherParams,otherResults";
			filterProvider.addFilter(DYNC_FILTER_PREFIX + filterCount,
					SimpleBeanPropertyFilter.serializeAllExcept(filter.replaceAll("\\s", "").split(",")));
			mapper.addMixIn(tClazz, getFilterClass(filterCount));
			return;
		}
		TClazzLocal.remove();
		if (filterCount > MAX_FILTER_COUNT) {
			logger.error(
					String.format("@ResponseJsonBodyWithFilters filter count >%d, the filters after %d will be ignored",
							MAX_FILTER_COUNT, MAX_FILTER_COUNT));
			return;
		}
		if (include != null && include.length() > 0) {
			// 优先考虑include
			filterProvider.addFilter(DYNC_INCLUDE_PREFIX + filterCount,
					SimpleBeanPropertyFilter.filterOutAllExcept(include.replaceAll("\\s", "").split(",")));
			mapper.addMixIn(clazz, getIncludeClass(filterCount));
		} else if (filter != null && filter.length() > 0) {
			filterProvider.addFilter(DYNC_FILTER_PREFIX + filterCount,
					SimpleBeanPropertyFilter.serializeAllExcept(filter.replaceAll("\\s", "").split(",")));
			mapper.addMixIn(clazz, getFilterClass(filterCount));
		}
	}

	@SuppressWarnings("rawtypes")
	private Class getIncludeClass(int count) {
		switch (count) {
		case 1:
			return DynamicInclude1.class;
		case 2:
			return DynamicInclude2.class;
		case 3:
			return DynamicInclude3.class;
		case 4:
			return DynamicInclude4.class;
		case 5:
			return DynamicInclude5.class;
		case 6:
			return DynamicInclude6.class;
		case 7:
			return DynamicInclude7.class;
		case 8:
			return DynamicInclude8.class;
		case 9:
			return DynamicInclude9.class;
		case 10:
			return DynamicInclude10.class;
		case 11:
			return DynamicInclude11.class;
		case 12:
			return DynamicInclude12.class;
		case 13:
			return DynamicInclude13.class;
		case 14:
			return DynamicInclude14.class;
		case 15:
			return DynamicInclude15.class;
		case 16:
			return DynamicInclude16.class;
		case 17:
			return DynamicInclude17.class;
		case 18:
			return DynamicInclude18.class;
		case 19:
			return DynamicInclude19.class;
		case 20:
			return DynamicInclude20.class;
		case 21:
			return DynamicInclude21.class;
		case 22:
			return DynamicInclude22.class;
		case 23:
			return DynamicInclude23.class;
		case 24:
			return DynamicInclude24.class;
		case 25:
			return DynamicInclude25.class;
		case 26:
			return DynamicInclude26.class;
		case 27:
			return DynamicInclude27.class;
		case 28:
			return DynamicInclude28.class;
		case 29:
			return DynamicInclude29.class;
		case 30:
			return DynamicInclude30.class;
		default:
			return null;
		}
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

	public void beginAddFilter() {
	}

	public void endAddFilter() {
		mapper.setFilterProvider(filterProvider);
	}

	public String toJson(Object object) throws JsonProcessingException {
		return mapper.writeValueAsString(object);
	}
}
