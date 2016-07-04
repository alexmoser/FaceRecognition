package it.unipi.ing.mim.distance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import it.unipi.ing.mim.featuresextraction.ExtractionParameters;
import it.unipi.ing.mim.utilities.CsvFileWriter;

public class CreateDistanceFile {
	
	public static void main(String [] args) throws Exception {
		
		File img1, img2;
		CsvFileWriter resultsFile = new CsvFileWriter("distance", DistanceParameters.DISTANCES_FILE);
	
		FileReader source = new FileReader(DistanceParameters.TEST_FILE);
	    BufferedReader b = new BufferedReader(source);
	 
		DistanceEvaluator distanceEvaluator = new DistanceEvaluator(ExtractionParameters.STORAGE_FILE);
		
	    int count = Integer.parseInt(b.readLine());
	    double [] distances = new double[count*2];
	    
	    for (int i = 0; i < (count*2); i++){
	    	
	    	String tmp = b.readLine();
	    	String [] split = tmp.split("\\t");
	    	
	    	if (i < count){
	    		img1 = new File(DistanceParameters.SRC_FOLDER + split[0] + "/" + split[0] + "_0" + ((Integer.parseInt(split[1]) < 10) ? "00" : (Integer.parseInt(split[1]) < 100) ? "0" : "") + split[1] + ".jpg");
	    		img2 = new File(DistanceParameters.SRC_FOLDER + split[0] + "/" + split[0] + "_0" + ((Integer.parseInt(split[2]) < 10) ? "00" : (Integer.parseInt(split[2]) < 100) ? "0" : "") + split[2] + ".jpg");
	    	}
	    	else{
	    		img1 = new File(DistanceParameters.SRC_FOLDER + split[0] + "/" + split[0] + "_0" + ((Integer.parseInt(split[1]) < 10) ? "00" : (Integer.parseInt(split[1]) < 100) ? "0" : "") + split[1] + ".jpg");
	    		img2 = new File(DistanceParameters.SRC_FOLDER + split[2] + "/" + split[2] + "_0" + ((Integer.parseInt(split[3]) < 10) ? "00" : (Integer.parseInt(split[3]) < 100) ? "0" : "") + split[3] + ".jpg");
	    	}   
			
			distances[i] = distanceEvaluator.evaluateDistance(img1, img2);
					
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
}
