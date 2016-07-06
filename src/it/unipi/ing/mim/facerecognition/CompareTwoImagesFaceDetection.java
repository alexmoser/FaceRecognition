package it.unipi.ing.mim.facerecognition;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.bytedeco.javacpp.opencv_core.Mat;

import it.unipi.ing.mim.distance.DistanceEvaluator;
import it.unipi.ing.mim.facedetection.DetectionParameters;
import it.unipi.ing.mim.facedetection.FaceDetection;
import it.unipi.ing.mim.featuresextraction.ExtractionParameters;

public class CompareTwoImagesFaceDetection {
	
	public static void main(String [] args) throws Exception{
		
		String 	imgPath1 = null, 
				imgPath2 = null;
		DistanceEvaluator distanceEvaluator = null;
		FaceDetection faceDetector = new FaceDetection(DetectionParameters.HAAR_CASCADE_FRONTALFACE);
		
		try {
			distanceEvaluator = new DistanceEvaluator(ExtractionParameters.STORAGE_FILE_FD);
		}
		catch(IOException e){
			System.err.println("Features file not found, please launch CreateSeqFeaturesFileFaceDetection first!");
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
		
		Mat imgMat1 = faceDetector.getFaces(img1.getPath());
    	Mat imgMat2 = faceDetector.getFaces(img2.getPath());
    	
		if(distanceEvaluator.evaluateDistance(imgMat1, imgMat2, img1, img2) <= RecognitionParameters.THRESHOLD_FD)
			System.out.println("SAME");
		else
			System.out.println("NOT SAME");
	}
}
