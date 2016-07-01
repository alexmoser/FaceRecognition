package it.unipi.ing.mim.facerecognition;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import it.unipi.ing.mim.deep.DNNExtractor;
import it.unipi.ing.mim.deep.ImgDescriptor;
import it.unipi.ing.mim.deep.Parameters;
import it.unipi.ing.mim.utilities.CsvFileWriter;

public class DistancesEvaluator {

	public static void main(String [] args) throws Exception {
		String imgPath1, imgPath2;
		CsvFileWriter resultsFile = new CsvFileWriter("distance", Parameters.DISTANCES_FILE);
	
		FileReader source = new FileReader(Parameters.TEST_FILE);
	    BufferedReader b = new BufferedReader(source);
	 
		DNNExtractor extractor = new DNNExtractor();
		
	    int count = Integer.parseInt(b.readLine());
	    double [] distances = new double[count*2];
	    
	    for (int i = 0; i < (count*2); i++){
	    	
	    	String tmp = b.readLine();
	    	String [] split = tmp.split("\\t");
	    	
	    	if (i < count){
	    		imgPath1 = split[0] + "/" + split[0] + "_0" + ((Integer.parseInt(split[1]) < 10) ? "00" : (Integer.parseInt(split[1]) < 100) ? "0" : "") + split[1] + ".jpg";
	    		imgPath2 = split[0] + "/" + split[0] + "_0" + ((Integer.parseInt(split[2]) < 10) ? "00" : (Integer.parseInt(split[2]) < 100) ? "0" : "") + split[2] + ".jpg";
	    	}
	    	else{
	    		imgPath1 = split[0] + "/" + split[0] + "_0" + ((Integer.parseInt(split[1]) < 10) ? "00" : (Integer.parseInt(split[1]) < 100) ? "0" : "") + split[1] + ".jpg";
	    		imgPath2 = split[2] + "/" + split[2] + "_0" + ((Integer.parseInt(split[3]) < 10) ? "00" : (Integer.parseInt(split[3]) < 100) ? "0" : "") + split[3] + ".jpg";
	    	}   
			
			distances[i] = evaluateDistance(extractor, Parameters.SRC_FOLDER + imgPath1, Parameters.SRC_FOLDER + imgPath2);
					
			if(i == 0) 
				resultsFile.addLine("equals-pairs");
			if(i == count) 
				resultsFile.addLine("non-equals-pairs");
			
			// print distances to a file 
			resultsFile.addLine(Double.toString(distances[i]));
	    }
	
	    resultsFile.closeFile();
		b.close();
		source.close();
	}
	
	/**
	 * Evaluates the distance between the two images specified.
	 * @param extractor is the DNNExtractor to be used for the feature extraction
	 * @param imgPath1 is the path of the first image
	 * @param imgPath2 is the path of the second image
	 * @return the distance between the two images
	 * */
	private static float evaluateDistance(DNNExtractor extractor, String imgPath1, String imgPath2) {
		File img1 = new File(imgPath1);
		File img2 = new File(imgPath2);
		
		float[] imgFeatures1 = extractor.extract(img1, Parameters.DEEP_LAYER);
		float[] imgFeatures2 = extractor.extract(img2, Parameters.DEEP_LAYER);
		
		ImgDescriptor imgDescriptor1 = new ImgDescriptor(imgFeatures1, img1.getName());
		ImgDescriptor imgDescriptor2 = new ImgDescriptor(imgFeatures2, img2.getName());
		
		return (float)imgDescriptor1.distance(imgDescriptor2);
	}
	
	/**
	 * Evaluates whether the two images specified are the same person or not.
	 * @param imgPath1 is the path of the first image
	 * @param imgPath2 is the path of the second image
	 * @return true if they are the same person, false otherwise
	 * */
	public static boolean same(String imgPath1, String imgPath2) {
		DNNExtractor extractor = new DNNExtractor();
		
		float distance = evaluateDistance(extractor, imgPath1, imgPath2);
		System.out.println("distance: " + distance);
		return (distance<=Parameters.THRESHOLD);
		
	}
}
