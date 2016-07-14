package it.unipi.ing.mim.distance;

import java.io.BufferedReader;
import java.io.FileReader;

import it.unipi.ing.mim.utilities.CsvFileWriter;

/**
 * This class only contains a main that is intended to be launched once.
 * Its function is to evaluate the rate of correctly classified images, False Positives and
 * False Negatives for different thresholds and print the results to a file.
 * */
public class CreateThresholdStatistics {

	// min and max values are computed based on distances obtained with CreateDistanceFile class
	private static final float MIN_THRESHOLD = 0.46f;
	private static final float MAX_THRESHOLD = 1.42f;
	// step defines the increasing step of the threshold
	private static final float STEP = 0.0001f; 
	
	public static void main(String [] args) throws Exception{
		
		int falsePositive = 0;
		int falseNegative = 0;
		int correct = 0;
		String tmp;
		
		CsvFileWriter fileWriter = new CsvFileWriter("Threshold,Correct,FP,FN", DistanceParameters.STATISTICS_FILE_FD);
		
		// test the distances for all the thresholds in the specified range
		for(float threshold = MIN_THRESHOLD; threshold <= MAX_THRESHOLD; threshold += STEP){
			
			FileReader source = new FileReader(DistanceParameters.DISTANCES_FILE_FD);
			BufferedReader b = new BufferedReader(source);
			
		    /* read the first two lines from the distances file previously created 
		     * No need to store or elaborate read values since they are just the titles
		     * "distances" and "equal-pairs" 
		     * */
			tmp = b.readLine();
		    tmp = b.readLine();
		    
		    // read the distances relative to the images representing the same person
		    while(!(tmp = b.readLine()).equals("non-equals-pairs")){
		    	// classify as false negative if distance above threshold
		    	if(Float.parseFloat(tmp) > threshold){
		    		falseNegative++;
		    	}
		    	// correct otherwise
		    	else{
		    		correct++;
		    	}
		    }

		    // read the distances relative to the images representing two different people
		    while((tmp = b.readLine()) != null){
		    	// classify as false positive if distance below threshold
		    	if(Float.parseFloat(tmp) < threshold){
		    		falsePositive++;
		    	}
		    	// correct otherwise
		    	else{
		    		correct++;
		    	}
		    }
		    
		    fileWriter.addLine(threshold + "," + correct + "," + falsePositive + "," + falseNegative);
		    
		    correct = 0;
		    falsePositive = 0;
		    falseNegative = 0;
		    
		    b.close();
			source.close();	
		}
	   
		fileWriter.closeFile();
	}
}
