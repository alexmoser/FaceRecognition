package it.unipi.ing.mim.utilities;

import it.unipi.ing.mim.facerecognition.RecognitionParameters;
import it.unipi.ing.mim.featuresextraction.ImgDescriptor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * This class provides static methods that are used to produce the Html output files.
 * */
public class Output {

	public static final int COLUMNS = 5;

	public static void toHTML(List<ImgDescriptor> ids, String baseURI, File outputFile) {
		String html = "<html>\n<body>\n<table align='center'>\n";
		String imgName;
		
		for (int i = 0; i < ids.size(); i++) {
			System.out.println(i + " - " + (float) ids.get(i).dist + "\t" + ids.get(i).id );
			imgName = ids.get(i).id;
			
			if (i % COLUMNS == 0) {
				if (i != 0)
					html += "</tr>\n";
				html += "<tr>\n";
			}
			html += "<td><img align='center' border='0' height='160' title='" + ids.get(i).id + ", dist: "
			        + ids.get(i).dist + "' src='" + baseURI + imgName.substring(0, imgName.length() - 9) + "/" + ids.get(i).id + "'></td>\n";
		}
		if (ids.size() != 0)
			html += "</tr>\n";

		html += "</table>\n</body>\n</html>";
		
		try {
	        string2File(html, outputFile);
			System.out.println("html generated");
        } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
	}

	/**
	 * Create a new file containing the specified text.
	 * @param text is the text that needs to be included in the file
	 * @param file is the file to create
	 * @throws IOException
	 * */
	private static void string2File(String text, File file) throws IOException {
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(file);
			fileWriter.write(text);
		} finally {
			if (fileWriter != null)
				fileWriter.close();
		}
	}
	
	/**
	 * This method is specific for the generation of the file .html representing the output
	 * of the CompareTwoImages functionality. 
	 * It prints to an html file all the images contained in the "tmp_compare" folder.
	 * The images are printed as pairs of matching faces.
	 * @param outputFile is the file to be created
	 * */
	public static void printTmpToHTML(File outputFile) {
		String html = "<html>\n<body>\n";
		File [] imgList = RecognitionParameters.TMP_COMPARE_FOLDER.listFiles();
		
		int i = 1;
		for (File image : imgList){
			// skip hidden files
			if(image.isHidden())
				continue;
			html += "<img src=\"" + RecognitionParameters.BASE_URI_COMPARE_TMP + image.getName() + "\" style=\"width:200px;height:200px;\">&nbsp;";
			if (i%2 == 0)
				html += "<br><br>";
			i++;			
		}

		html += "</body>\n</html>";
		
		try {
	        string2File(html, outputFile);
			System.out.println("html generated");
        } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
	}
	
	/**
	 * This method deletes all the files contained in the specified directory.
	 * @param directory is the directory containing the files to delete
	 * @return true if all the files have been deleted, false otherwise
	 * */
	public static boolean deleteAllFiles(File directory) {
		File[] fileList = directory.listFiles();
		for(int i=0; i<fileList.length; i++) 
			if(fileList[i].delete() == false)
				return false;
		return true;
	}
}
