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
import it.unipi.ing.mim.facedetection.Utility;
import it.unipi.ing.mim.featuresextraction.DNNExtractor;
import it.unipi.ing.mim.featuresextraction.ExtractionParameters;
import it.unipi.ing.mim.featuresextraction.ImgDescriptor;

public class CompareTwoImagesFaceDetection {
	
	public static void main(String [] args) throws Exception{
		//Image Query File
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter first image path : ");
	    String imgPath1 = bufferRead.readLine();
	    System.out.print("Enter second image path : ");
	    String imgPath2 = bufferRead.readLine();
		int res = compare(imgPath1, imgPath2);
		if(res > 0)
			System.out.println("There are " + res + " matching faces");
		else
			System.out.println("There are NOT matching faces");
	}
	
	public static int compare(String imgPath1, String imgPath2) throws Exception {

    	DNNExtractor extractor = new DNNExtractor();
    	
		FaceDetection faceDetector = new FaceDetection(DetectionParameters.HAAR_CASCADE_FRONTALFACE);
	    
		File img1 = new File(imgPath1);
		File img2 = new File(imgPath2);
		
		Mat [] imgMat1 = faceDetector.getFaces(img1.getPath(), DetectionParameters.PADDING);
    	Mat [] imgMat2 = faceDetector.getFaces(img2.getPath(), DetectionParameters.PADDING);
    	
    	int counter = 0;
    	/*
    	 * For each face detected in the first image, check if it appears in the second image.
    	 * First extract the features and store the descriptors, compute distance afterwards
    	 * We don't use the evaluateDistance method because it would extract the features every time
    	 * */
    	List<ImgDescriptor> descImg1 = new ArrayList<ImgDescriptor>();
    	List<ImgDescriptor> descImg2 = new ArrayList<ImgDescriptor>();
  
    	for(int i = 0; i < imgMat1.length; i++){
    		String id = i + "_" + img1.getName();
    		float[] features = extractor.extract(imgMat1[i], ExtractionParameters.DEEP_LAYER);
    		ImgDescriptor tmp = new ImgDescriptor(features, id);
    		descImg1.add(tmp);
    		System.out.println("Extracting features for " + id);
    		// Create temporary file with the face
    		Utility.face2File(imgMat1[i], new File(RecognitionParameters.TMP_FOLDER + id));
    	}
    	for(int i = 0; i < imgMat2.length; i++){
    		String id = i + "_" + img2.getName();
    		float[] features = extractor.extract(imgMat2[i], ExtractionParameters.DEEP_LAYER);
    		ImgDescriptor tmp = new ImgDescriptor(features, id);
    		descImg2.add(tmp);
    		System.out.println("Extracting features for " + id);
    		// Create temporary file with the face
    		Utility.face2File(imgMat2[i], new File(RecognitionParameters.TMP_FOLDER + id));
		}
    	for(ImgDescriptor desc1 : descImg1) {
    		for(ImgDescriptor desc2 : descImg2) {
    			if(desc1.distance(desc2) <= RecognitionParameters.THRESHOLD_FD) {
    				System.out.println(desc1.id + " matches with " + desc2.id);
    				counter++;
    				// TODO creare file immagini con le facce dei match (e.g. una cartella per ogni coppia)
    			}
    		}
    	}
    	
    	// Create temporary files to print to HTML file
    	// TODO rifare una outToHtml che stampi tutte le immagini in tmp
		
		return counter;
	}
}
