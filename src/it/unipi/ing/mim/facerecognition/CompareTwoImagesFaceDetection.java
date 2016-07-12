package it.unipi.ing.mim.facerecognition;

import it.unipi.ing.mim.utilities.Output;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter;

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
    		// define a copy of the image in order to prevent extract method to modify the original properties
    		Mat tmpImg = new Mat(imgMat1[i]);
    		String id = i + "_" + img1.getName();
    		float[] features = extractor.extract(tmpImg, ExtractionParameters.DEEP_LAYER);
    		ImgDescriptor tmp = new ImgDescriptor(features, id);
    		descImg1.add(tmp);
    		System.out.println("Extracting features for " + id);
    	}
    	for(int i = 0; i < imgMat2.length; i++){
    		// define a copy of the image in order to prevent extract method to modify the original properties
    		Mat tmpImg = new Mat(imgMat2[i]);
    		String id = i + "_" + img2.getName();
    		float[] features = extractor.extract(tmpImg, ExtractionParameters.DEEP_LAYER);
    		ImgDescriptor tmp = new ImgDescriptor(features, id);
    		descImg2.add(tmp);
    		System.out.println("Extracting features for " + id);
    	}
    	
    	// Delete old temporary files
    	if(RecognitionParameters.TMP_COMPARE_FOLDER.exists()) {
    		Output.deleteAllFiles(RecognitionParameters.TMP_COMPARE_FOLDER);
    	}
    	else {	
    		// Create new temporary directory
    		RecognitionParameters.TMP_COMPARE_FOLDER.mkdirs();
    	}
		
    	int i = 0;
    	int j = 0;
    	for(ImgDescriptor desc1 : descImg1) {
    		for(ImgDescriptor desc2 : descImg2) {
    			if(desc1.distance(desc2) <= RecognitionParameters.THRESHOLD_FD) {
    				System.out.println(desc1.id + " matches with " + desc2.id);
    				counter++;
    				Utility.face2File(imgMat1[i], new File(RecognitionParameters.TMP_COMPARE_FOLDER + "/" + "match_" + counter + "_img1_" + i + ".jpg"));
    				Utility.face2File(imgMat2[j], new File(RecognitionParameters.TMP_COMPARE_FOLDER + "/" + "match_" + counter + "_img2_" + j + ".jpg"));
    			}
    			j++;
    		}
    		j = 0;
    		i++;
    	}
    	
		Output.printTmpToHTML(RecognitionParameters.COMPARE_HTML_FD);
    	
		return counter;
	}
}
