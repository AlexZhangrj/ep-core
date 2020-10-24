package com.zhrenjie04.alex.user.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Document(indexName = "es_user_search_key",shards = 10, replicas =2)
@Data
@ToString
@EqualsAndHashCode(of = {"userId"})
public class EsUserSearchKey {
	@Id
	private String userId;
	@Field(type = FieldType.Keyword)
	private String username;
	@Field(type = FieldType.Keyword)
	private String cellphone;
}
