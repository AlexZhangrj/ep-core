package com.zhrenjie04.alex.core;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OperationLog {
	String paramsJson;
	String resultJson;
	String exceptionJson;
}
