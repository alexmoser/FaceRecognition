package it.unipi.ing.mim.facerecognition;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import it.unipi.ing.mim.distance.DistanceEvaluator;
import it.unipi.ing.mim.featuresextraction.ExtractionParameters;

public class CompareTwoImages {

	public static void main(String [] args) throws Exception{
	
		String 	imgPath1 = null, 
				imgPath2 = null;
		
		DistanceEvaluator distanceEvaluator = new DistanceEvaluator();
		
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
	      
		if(distanceEvaluator.evaluateDistance(imgPath1, imgPath2) <= RecognitionParameters.THRESHOLD)
			System.out.println("SAME");
		else
			System.out.println("NOT SAME");
	}
}
