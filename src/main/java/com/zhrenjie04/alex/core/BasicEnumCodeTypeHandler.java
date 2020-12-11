package com.zhrenjie04.alex.core;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.zhrenjie04.alex.util.EnumUtil;
/**
 * 张人杰改进版
 * @author zhangrenjie
 * @param <E>
 */
public class BasicEnumCodeTypeHandler<E extends Enum<E> & BasicEnum> extends BaseTypeHandler<E> {

	private final Class<E> type;

	public BasicEnumCodeTypeHandler(Class<E> type) {
		if (type == null) {
			throw new IllegalArgumentException("Type argument cannot be null");
		}
		this.type = type;
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
//		ps.setInt(i, parameter.getCode());
		ps.setObject(i, parameter.getDbCode());
	}

	@Override
	public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
//		int code = rs.getInt(columnName);
//		return rs.wasNull() ? null : EnumUtils.codeOf(this.type, code);
		Object code = rs.getObject(columnName);
		return rs.wasNull() ? null : EnumUtil.codeOf(this.type, code);
	}

	@Override
	public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
//		int code = rs.getInt(columnIndex);
//		return rs.wasNull() ? null : EnumUtils.codeOf(this.type, code);
		Object code = rs.getObject(columnIndex);
		return rs.wasNull() ? null : EnumUtil.codeOf(this.type, code);
	}

	@Override
	public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
//		int code = cs.getInt(columnIndex);
//		return cs.wasNull() ? null : EnumUtils.codeOf(this.type, code);
		Object code = cs.getObject(columnIndex);
		return cs.wasNull() ? null : EnumUtil.codeOf(this.type, code);
	}
}
