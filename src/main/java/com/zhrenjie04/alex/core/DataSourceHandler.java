package com.zhrenjie04.alex.core;
/**
 * 注意：需要在ExceptionAdvice里remove移除ThreadLocal
 * @author zhangrenjie
 *
 */
public class DataSourceHandler {
	
	private static ThreadLocal<String> handlerThredLocal = new ThreadLocal<String>();

	/**
	 * @desction: 提供给AOP去设置当前的线程的数据源的信息
	 * @author: wangji
	 * @date: 2017/8/21
	 * @param: [datasource]
	 * @return: void
	 */
	public static void setDataSource(String datasource) {
		handlerThredLocal.set(datasource);
	}

	/**
	 * @desction: 提供给AbstractRoutingDataSource的实现类，通过key选择数据源
	 * @author: wangji
	 * @date: 2017/8/21
	 * @param: []
	 * @return: java.lang.String
	 */
	public static String getDataSource() {
		return handlerThredLocal.get();
	}

	/**
	 * @desction: 使用默认的数据源
	 */
	public static void remove() {
		handlerThredLocal.remove();
	}
}
