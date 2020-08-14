package com.zhrenjie04.alex.core;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class BaseEnumCodeTypeHandler<E extends Enum<E> & BaseEnum> extends BaseTypeHandler<E> {

	private final Class<E> type;

	public BaseEnumCodeTypeHandler(Class<E> type) {
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
		return rs.wasNull() ? null : EnumUtils.codeOf(this.type, code);
	}

	@Override
	public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
//		int code = rs.getInt(columnIndex);
//		return rs.wasNull() ? null : EnumUtils.codeOf(this.type, code);
		Object code = rs.getObject(columnIndex);
		return rs.wasNull() ? null : EnumUtils.codeOf(this.type, code);
	}

	@Override
	public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
//		int code = cs.getInt(columnIndex);
//		return cs.wasNull() ? null : EnumUtils.codeOf(this.type, code);
		Object code = cs.getObject(columnIndex);
		return cs.wasNull() ? null : EnumUtils.codeOf(this.type, code);
	}
}
