package it.unipi.ing.mim.distance;

import java.io.BufferedReader;
import java.io.FileReader;

import it.unipi.ing.mim.utilities.CsvFileWriter;

public class CreateThresholdStatistics {

	//min and max are computed based on the previous distances calculated
	private static final float MIN_THRESHOLD = 0.46f;
	private static final float MAX_THRESHOLD = 1.42f;
	private static final float STEP = 0.0001f; 
	
	public static void main(String [] args) throws Exception{
		
		int falsePositive = 0;
		int falseNegative = 0;
		int correct = 0;
		String tmp;
		
		CsvFileWriter fileWriter = new CsvFileWriter("Threshold,Correct,FP,FN", DistanceParameters.STATISTICS_FILE);
		
		for(float threshold = MIN_THRESHOLD; threshold <= MAX_THRESHOLD; threshold += STEP){
			
			FileReader source = new FileReader(DistanceParameters.DISTANCES_FILE);
			BufferedReader b = new BufferedReader(source);
			
		    //read the first line (The first line is simply the string Distances)
			tmp = b.readLine();
			//read the second line (The second line is the string equal pairs)
		    tmp = b.readLine();
		    while(!(tmp = b.readLine()).equals("non-equals-pairs")){
		    	if(Float.parseFloat(tmp) > threshold){
		    		falseNegative++;
		    	}
		    	else{
		    		correct++;
		    	}
		    }
		    while((tmp = b.readLine()) != null){
		    	if(Float.parseFloat(tmp) < threshold){
		    		falsePositive++;
		    	}
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
