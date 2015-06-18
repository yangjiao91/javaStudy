package com.funshion.dobo.utils;

import org.apache.avro.Schema;

public class AvroUtils {
	
	public static Schema toSchema(String json) {
		// parser不能用单例, 会和schema有关联
		Schema.Parser parser = new Schema.Parser();
		Schema schema = parser.parse(json);
		return schema;
	}
}
