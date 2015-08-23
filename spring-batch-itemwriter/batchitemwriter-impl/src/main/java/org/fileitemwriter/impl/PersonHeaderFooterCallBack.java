package org.fileitemwriter.impl;

import java.io.IOException;
import java.io.Writer;

import org.fileitemwriter.impl.utils.AppUtils;
import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.batch.item.file.FlatFileHeaderCallback;

public class PersonHeaderFooterCallBack implements FlatFileHeaderCallback, FlatFileFooterCallback{
	
	private static final String CSV_HEADER = "#Persons";
	private static final String CSV_FOOTER = "#eof";
	
	@Override
	public void writeHeader(Writer writer) throws IOException {
		writer.write(CSV_HEADER + AppUtils.LINE_SEPARATOR);		
	}

	@Override
	public void writeFooter(Writer writer) throws IOException {
		writer.write(CSV_FOOTER);
	}

	

}
