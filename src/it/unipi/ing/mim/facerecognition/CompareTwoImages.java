package it.unipi.ing.mim.facerecognition;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import it.unipi.ing.mim.distance.DistanceEvaluator;
import it.unipi.ing.mim.featuresextraction.ExtractionParameters;

public class CompareTwoImages {

	public static void main(String [] args) throws Exception{
	
		String 	imgPath1 = null, 
				imgPath2 = null;
		DistanceEvaluator distanceEvaluator = null;
		
		try {
			distanceEvaluator = new DistanceEvaluator(ExtractionParameters.STORAGE_FILE);
		}
		catch(IOException e){
			System.err.println("Features file not found, please launch CreateSeqFeaturesFile first!");
			return;
		}
		
		System.out.println("Enter images paths : ");   
		try{
		    BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		    imgPath1 = bufferRead.readLine();
		    imgPath2 = bufferRead.readLine();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	    
		File img1 = new File(imgPath1);
		File img2 = new File(imgPath2);
		
		if(distanceEvaluator.evaluateDistance(img1, img2) <= RecognitionParameters.THRESHOLD)
			System.out.println("SAME");
		else
			System.out.println("NOT SAME");
	}
}
