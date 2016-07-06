package it.unipi.ing.mim.distance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import it.unipi.ing.mim.featuresextraction.ExtractionParameters;
import it.unipi.ing.mim.utilities.CsvFileWriter;

/**
 * This class only contains a main that is intended to be launched once.
 * Its function is to create a file containing all the distances computed for the images
 * specified in the test file, which are a subset (test) of the lfw_funneled database.
 * */
public class CreateDistanceFile {
	
	public static void main(String [] args) throws Exception {
		
		CsvFileWriter resultsFile = new CsvFileWriter("distance", DistanceParameters.DISTANCES_FILE);
	
		FileReader source = new FileReader(DistanceParameters.TEST_FILE);
	    BufferedReader b = new BufferedReader(source);
	 
		DistanceEvaluator distanceEvaluator = new DistanceEvaluator(ExtractionParameters.STORAGE_FILE);
		
		File img1, img2;
		
		/* the first line of the test file represents the number (count) of images taken into account
		 * according to how such file is defined, it needs to be multiplied by 2 since 
		 * the first count images represent pairs of the same person,
		 * while the second count images represent pairs of two different people
		*/
	    int count = Integer.parseInt(b.readLine());
	    double [] distances = new double[count*2];
	    
	    for (int i = 0; i < (count*2); i++){
	    	
	    	String tmp = b.readLine();
	    	String [] split = tmp.split("\\t");
	    	
	    	// get the path of the image, according to the name read from the file
	    	if (i < count){
	    		img1 = new File(DistanceParameters.SRC_FOLDER + split[0] + "/" + split[0] + "_0" + ((Integer.parseInt(split[1]) < 10) ? "00" : (Integer.parseInt(split[1]) < 100) ? "0" : "") + split[1] + ".jpg");
	    		img2 = new File(DistanceParameters.SRC_FOLDER + split[0] + "/" + split[0] + "_0" + ((Integer.parseInt(split[2]) < 10) ? "00" : (Integer.parseInt(split[2]) < 100) ? "0" : "") + split[2] + ".jpg");
	    	}
	    	else{
	    		img1 = new File(DistanceParameters.SRC_FOLDER + split[0] + "/" + split[0] + "_0" + ((Integer.parseInt(split[1]) < 10) ? "00" : (Integer.parseInt(split[1]) < 100) ? "0" : "") + split[1] + ".jpg");
	    		img2 = new File(DistanceParameters.SRC_FOLDER + split[2] + "/" + split[2] + "_0" + ((Integer.parseInt(split[3]) < 10) ? "00" : (Integer.parseInt(split[3]) < 100) ? "0" : "") + split[3] + ".jpg");
	    	}   
			
	    	// evaluate distance between the 2 images
			distances[i] = distanceEvaluator.evaluateDistance(img1, img2);
					 
			if(i == 0) 
				resultsFile.addLine("equals-pairs");
			if(i == count) 
				resultsFile.addLine("non-equals-pairs");
			
			// print distances to a .csv file
			resultsFile.addLine(Double.toString(distances[i]));
	    }
	
	    resultsFile.closeFile();
		b.close();
		source.close();
	}
}
