package it.unipi.ing.mim.facerecognition;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.bytedeco.javacpp.opencv_core.Mat;

import it.unipi.ing.mim.distance.DistanceEvaluator;
import it.unipi.ing.mim.facedetection.DetectionParameters;
import it.unipi.ing.mim.facedetection.FaceDetection;
import it.unipi.ing.mim.facedetection.Utility;
import it.unipi.ing.mim.featuresextraction.DNNExtractor;
import it.unipi.ing.mim.featuresextraction.ExtractionParameters;
import it.unipi.ing.mim.featuresextraction.ImgDescriptor;
import it.unipi.ing.mim.utilities.Output;

/**
 * This class provides the methods used to compare two images, both with a preliminary
 * face detection or without.
 * It also contains a main to test the second option.
 * */
public class CompareTwoImages {

	private static DNNExtractor extractor;
	
	public static void main(String [] args) throws Exception{
		// read the path of the two images from the console
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
	
	/**
	 * Compares the two specified images and evaluates whether they are the same 
	 * person or not.
	 * @param imgPath1 is the path of the first image
	 * @param imgPath2 is the path of the second image
	 * @return true if the two images contain the same person, false otherwise
	 * @throws ClassNotFoundException 
	 * */
	public static boolean compare(String imgPath1, String imgPath2) throws ClassNotFoundException {
		
		DistanceEvaluator distanceEvaluator = new DistanceEvaluator(ExtractionParameters.STORAGE_FILE);
	    
		File img1 = new File(imgPath1);
		File img2 = new File(imgPath2);
		
		return (distanceEvaluator.evaluateDistance(img1, img2) <= RecognitionParameters.THRESHOLD);
	}
	
	/**
	 * Compares the two specified images performing a preliminary face detection.
	 * For each face detected in the first image evaluates whether it appears also in
	 * the second image or not.
	 * Outputs the results as pairs of matching faces in an Html file specified
	 * in {@link RecognitionParameters}. 
	 * @param imgPath1 is the path of the first image
	 * @param imgPath2 is the path of the second image
	 * @return the number of matching faces
	 * @throws ClassNotFoundException 
	 * */
	public static int compareWithFaceDetection(String imgPath1, String imgPath2) throws ClassNotFoundException {
    	
		/* prepare folder to store the output images */
    	// Delete old temporary files
    	if(RecognitionParameters.TMP_COMPARE_FOLDER.exists()) {
    		Output.deleteAllFiles(RecognitionParameters.TMP_COMPARE_FOLDER);
    	}
    	else {	
    		// Create new temporary directory
    		RecognitionParameters.TMP_COMPARE_FOLDER.mkdirs();
    	}
    	
		FaceDetection faceDetector = new FaceDetection(DetectionParameters.HAAR_CASCADE_FRONTALFACE);
	    
		// files containing the two images specified by the parameters paths
		File img1 = new File(imgPath1);
		File img2 = new File(imgPath2);
		
		// Mat vectors containing the detected faces
		Mat [] faces1 = faceDetector.getFaces(img1.getPath(), DetectionParameters.PADDING);
    	Mat [] faces2 = faceDetector.getFaces(img2.getPath(), DetectionParameters.PADDING);
    	
    	// Initialize extractor
    	extractor = new DNNExtractor();
    	
    	// List of descriptors relative to the faces detected in the two images
    	List<ImgDescriptor> descImg1 = getDescriptors(faces1, img1.getName());
    	List<ImgDescriptor> descImg2 = getDescriptors(faces2, img2.getName());
		
    	/*
    	 * For each face detected in the first image, check if it appears in the second image.
    	 * Use the descriptors lists to compute the distances.
    	 * Note: We don't use the evaluateDistance method because it would extract the features every time
    	 * */
    	int i = 0, j = 0, counter = 0;
    	for(ImgDescriptor desc1 : descImg1) {
    		for(ImgDescriptor desc2 : descImg2) {
    			if(desc1.distance(desc2) <= RecognitionParameters.THRESHOLD_FD) {
    				System.out.println(desc1.id + " matches with " + desc2.id);
    				counter++;
    				// Create image file for each matching face
    				Utility.face2File(faces1[i], new File(RecognitionParameters.TMP_COMPARE_FOLDER + "/" + "match_" + counter + "_img1_" + i + ".jpg"));
    				Utility.face2File(faces2[j], new File(RecognitionParameters.TMP_COMPARE_FOLDER + "/" + "match_" + counter + "_img2_" + j + ".jpg"));
    			}
    			j++;
    		}
    		j = 0;
    		i++;
    	}
    	
    	if(counter > 0)
    		// Create results file
    		Output.printTmpToHTML(RecognitionParameters.COMPARE_HTML_FD);
    	
		return counter;
	}
	
	/**
	 * Creates a list containing the descriptor of each face.
	 * @param faces is the array containing the detected faces
	 * @param name is the name of the original file, from which the id of each descriptor will be generated
	 * @return a list containing all the descriptors created for each face  
	 * */
	private static List<ImgDescriptor> getDescriptors(Mat[] faces, String name) {
    	List<ImgDescriptor> ret = new ArrayList<ImgDescriptor>();
		for(int i = 0; i < faces.length; i++){
    		// define a copy of the image in order to prevent extract method to modify the original properties
    		Mat tmpImg = new Mat(faces[i]);
    		String id = i + "_" + name;
    		float[] features = extractor.extract(tmpImg, ExtractionParameters.DEEP_LAYER);
    		ImgDescriptor tmp = new ImgDescriptor(features, id);
    		ret.add(tmp);
    		System.out.println("Extracting features for " + id);
    	}
		
		return ret;
	}
}
