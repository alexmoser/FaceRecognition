package it.unipi.ing.mim.facerecognition;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.bytedeco.javacpp.opencv_core.Mat;

import it.unipi.ing.mim.facedetection.DetectionParameters;
import it.unipi.ing.mim.facedetection.FaceDetection;
import it.unipi.ing.mim.featuresextraction.ImgDescriptor;
import it.unipi.ing.mim.utilities.Output;

/**
 * This class only contains a main function used to test the SearchEngine when a face
 * detection has first occurred.
 * */
public class SearchEngineFaceDetection {
	
	public static void main(String[] args) throws Exception {
		//Image Query File
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter image path : ");
	    String imgPath = bufferRead.readLine();
	    System.out.print("Enter number of desired similar images : ");
	    int K = Integer.parseInt(bufferRead.readLine());

		FaceDetection faceDetector = new FaceDetection(DetectionParameters.HAAR_CASCADE_FRONTALFACE);
		
		Mat[] imgMat = faceDetector.getFaces(imgPath, DetectionParameters.PADDING);
	 
		List<ImgDescriptor> res = SearchEngine.searchEngine(imgMat[0], K, imgPath);
		Output.toHTML(res, RecognitionParameters.BASE_URI_SEARCH_ENGINE_FD, RecognitionParameters.SEARCH_ENGINE_HTML_FD);
	}
}
