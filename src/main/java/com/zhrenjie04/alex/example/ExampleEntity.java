package com.zhrenjie04.alex.example;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of= {"id"})
@ToString
public class ExampleEntity {
	private String id;
	private ExampleEnum status;
}
