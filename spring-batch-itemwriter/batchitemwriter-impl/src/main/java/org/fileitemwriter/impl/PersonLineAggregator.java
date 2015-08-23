package org.fileitemwriter.impl;

import org.fileitemwriter.impl.utils.AppUtils;
import org.springframework.batch.item.file.transform.LineAggregator;

public class PersonLineAggregator<Person> implements LineAggregator<Person> {

	@Override
	public String aggregate(Person person) {
		return person.toString() + AppUtils.LINE_SEPARATOR;
	}

}
