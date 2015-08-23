package org.batchitemwiter.gson.impl.processor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.batchitemwiter.gson.impl.model.Person;
import org.springframework.batch.item.ItemProcessor;

public class PersonItemProcessor implements ItemProcessor<Person, Person>{
	  private static final Log logger = LogFactory.getLog(PersonItemProcessor.class);


		@Override
		public Person process(Person person) throws Exception {
			 final String firstName = person.getFirstname().toUpperCase();
		     final String lastName = person.getLastname().toUpperCase();
		     
		     final Person transformedPerson = new Person(firstName, lastName);

		     logger.info("Converting (" + person + ") into (" + transformedPerson + ")");

		        return transformedPerson;
		}

}
