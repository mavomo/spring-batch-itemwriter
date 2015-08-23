package org.fileitemwriter.impl;

import java.io.File;
import java.nio.charset.Charset;

import javax.sql.DataSource;

import org.fileitemwriter.impl.model.Person;
import org.fileitemwriter.impl.processor.PersonItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration extends DefaultBatchConfigurer {
	
	
	private static final String LINE_SEPARATOR = ",";
	private static final String INPUT_FILE = "sample-data.csv";
    public static final Charset UTF_8 = Charset.forName("UTF-8");
    

 // tag::readerwriterprocessor[]
    @Bean
    public ItemReader<Person> reader() {    	
        FlatFileItemReader<Person> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource(INPUT_FILE));
        
        reader.setLineMapper(new DefaultLineMapper<Person>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "firstName", "lastName" });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
                setTargetType(Person.class);
            }});
        }});
		return reader;    	
    }
    
    @Bean
    public ItemProcessor<Person, Person> processor() {
        return new PersonItemProcessor();
    }
    
    @Bean
    public ItemWriter<Person> writer(DataSource dataSource) {
        FlatFileItemWriter<Person> writer = new FlatFileItemWriter<Person>();        

        writer.setLineSeparator(LINE_SEPARATOR);
        writer.setLineAggregator(new PersonLineAggregator<Person>());       
        
        //Setting header and footer.
        PersonHeaderFooterCallBack headerFooterCallback = new PersonHeaderFooterCallBack();
        writer.setHeaderCallback(headerFooterCallback);
        writer.setFooterCallback(headerFooterCallback);

       writer.setResource(new FileSystemResource(System.getProperty("user.dir") + File.separator  + "src/main/resources/sample-output-data.csv"));
        writer.setEncoding(UTF_8.name());
        writer.setShouldDeleteIfExists(true);

        return writer;
    }
    
    // end::readerwriterprocessor[]
    
    // tag::jobstep[]
    @Bean
    public Job writeStepJob(JobBuilderFactory jobs, Step stepOne, JobExecutionListener listener) {
        return jobs.get("writeStepJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(stepOne)
                .end()
                .build();
    }

    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<Person> reader,
            ItemWriter<Person> writer, ItemProcessor<Person, Person> processor) {
    	
        return stepBuilderFactory.get("step1")
                .<Person, Person> chunk(2) //commit-interval = 2
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }


}
