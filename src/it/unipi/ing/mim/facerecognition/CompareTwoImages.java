package it.unipi.ing.mim.facerecognition;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import it.unipi.ing.mim.distance.DistanceEvaluator;
import it.unipi.ing.mim.featuresextraction.ExtractionParameters;

public class CompareTwoImages {

	public static void main(String [] args) throws Exception{
		//Image Query File
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter first image path : ");
	    String imgPath1 = bufferRead.readLine();
	    System.out.print("Enter second image path : ");
	    String imgPath2 = bufferRead.readLine();
		boolean res = compare(imgPath1, imgPath2);
		if(res)
			System.out.println("SAME");
		else
			System.out.println("NOT SAME");
	}
	
	public static boolean compare(String imgPath1, String imgPath2) throws Exception {
		
		DistanceEvaluator distanceEvaluator = null;
		
		distanceEvaluator = new DistanceEvaluator(ExtractionParameters.STORAGE_FILE);
	    
		File img1 = new File(imgPath1);
		File img2 = new File(imgPath2);
		
		return (distanceEvaluator.evaluateDistance(img1, img2) <= RecognitionParameters.THRESHOLD);
	}
}
