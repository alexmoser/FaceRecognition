package it.unipi.ing.mim.utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * This class provides methods that allow to write a .csv file by only specifying the line 
 * that needs to be added.
 * */
public class CsvFileWriter {
	
	private static final String NEW_LINE_SEPARATOR = "\n";
	
	private File file;
	private FileOutputStream outputStream;
	private OutputStreamWriter sw;
	
	/**
	 * Note that if the file is already existent, it will be overwritten.
	 * @param header is the first line of the file, hence the header of the table
	 * @param file is the file that needs to be written
	 * */
	public CsvFileWriter(String header, File file) {
		try{
			this.file = file;
			if(!this.file.exists())
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
	
	/**
	 * Appends a new line to the previously opened file
	 * @param line is the line to be added
	 * */
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
	
	/**
	 * This method needs to be called at the end, when the file is no longer accessed.
	 * */
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
