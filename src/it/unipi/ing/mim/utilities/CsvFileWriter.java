package it.unipi.ing.mim.utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class CsvFileWriter {
	
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	
	private File file;
	private FileOutputStream outputStream;
	private OutputStreamWriter sw;
	
	public CsvFileWriter(String header, File file) {
		try{
			this.file = file;
			this.file.getParentFile().mkdirs();
			this.file.createNewFile();
			outputStream = new FileOutputStream(this.file, false);
            sw = new OutputStreamWriter(outputStream);
            sw.write(header);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addLine(String line) {
		try {
			sw.append(NEW_LINE_SEPARATOR);
			sw.append(line);
            sw.flush();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void closeFile() {
		try{
            sw.close();
            outputStream.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
