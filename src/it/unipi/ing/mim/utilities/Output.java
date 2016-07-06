package it.unipi.ing.mim.utilities;

import it.unipi.ing.mim.featuresextraction.ImgDescriptor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

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
			System.out.print("html generated");
        } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
	}

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
}