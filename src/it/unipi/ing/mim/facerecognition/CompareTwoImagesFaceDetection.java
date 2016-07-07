package it.unipi.ing.mim.facerecognition;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.bytedeco.javacpp.opencv_core.Mat;
import it.unipi.ing.mim.facedetection.DetectionParameters;
import it.unipi.ing.mim.facedetection.FaceDetection;
import it.unipi.ing.mim.featuresextraction.DNNExtractor;
import it.unipi.ing.mim.featuresextraction.ExtractionParameters;
import it.unipi.ing.mim.featuresextraction.ImgDescriptor;

public class CompareTwoImagesFaceDetection {
	
	public static void main(String [] args) throws Exception{
		
		String 	imgPath1 = null, 
				imgPath2 = null;

    	DNNExtractor extractor = new DNNExtractor();
    	
		FaceDetection faceDetector = new FaceDetection(DetectionParameters.HAAR_CASCADE_FRONTALFACE);
		
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
		
		Mat [] imgMat1 = faceDetector.getFaces(img1.getPath(), DetectionParameters.PADDING);
    	Mat [] imgMat2 = faceDetector.getFaces(img2.getPath(), DetectionParameters.PADDING);
    	
    	boolean face_found = false;
    	/*
    	 * For each face detected in the first image, check if it appears in the second image.
    	 * First extract the features and store the descriptors, compute distance afterwards
    	 * We don't use the evaluateDistance method because it would extract the features every time
    	 * */
    	List<ImgDescriptor> descImg1 = new ArrayList<ImgDescriptor>();
    	List<ImgDescriptor> descImg2 = new ArrayList<ImgDescriptor>();
  
    	for(int i = 0; i < imgMat1.length; i++){
    		String id = img1.getName() + "_" + i;
    		float[] features = extractor.extract(imgMat1[i], ExtractionParameters.DEEP_LAYER);
    		ImgDescriptor tmp = new ImgDescriptor(features, id);
    		descImg1.add(tmp);
    		System.out.println("Extracting features for " + id);
    	}
    	for(int i = 0; i < imgMat2.length; i++){
    		String id = img2.getName() + "_" + i;
    		float[] features = extractor.extract(imgMat2[i], ExtractionParameters.DEEP_LAYER);
    		ImgDescriptor tmp = new ImgDescriptor(features, id);
    		descImg2.add(tmp);
    		System.out.println("Extracting features for " + id);
		}
    	for(ImgDescriptor desc1 : descImg1) {
    		for(ImgDescriptor desc2 : descImg2) {
    			if(desc1.distance(desc2) <= RecognitionParameters.THRESHOLD_FD) {
    				System.out.println(desc1.id + " matches with " + desc2.id);
    				face_found = true;
    			}
    		}
    	}
		if(!face_found) {
			System.out.println("No matching faces!");
		}
	}
}
