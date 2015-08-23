# spring-batch-itemwriter

This project groups together two main examples showing how spring batch can be used to produce flat files.
The job configured here reads a csv file (sample-data.csv), transform each line as an uppercase text and then writes it back to a file. 
The idea is to show one limitation in Spring batch that bothers me : There is no native API in Spring batch that helps
producing a valid json file.

It has two main projects : 

* Batchitemwriter-base : Configured job using FlatFileItemWriter and producing a flat file (sample-output-data.csv)
* Batchitemwiter-gson : Configured job combining FlatFileItemWriter and Gson in an attempt to produce a json file (json-sample-output-data.json).
But that one is invalid.
