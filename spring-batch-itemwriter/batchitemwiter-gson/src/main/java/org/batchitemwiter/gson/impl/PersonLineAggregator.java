package org.batchitemwiter.gson.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.batchitemwiter.gson.utils.JsonUtils;
import org.springframework.batch.item.file.transform.LineAggregator;

import com.fasterxml.jackson.core.JsonProcessingException;

public class PersonLineAggregator<Person> implements LineAggregator<Person> {
	
	private static final Log logger = LogFactory.getLog(PersonLineAggregator.class);


	@Override
	public String aggregate(Person person) {
		String result = null;
		try {
			result = JsonUtils.convertObjectToJsonString(person); 

		} catch (JsonProcessingException jpe) {
			logger.warn("An error has occured " + jpe.getMessage() );
		}
		
		return result;
	}

}
