package it.unipi.ing.mim.facerecognition;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bytedeco.javacpp.opencv_core.Mat;

import it.unipi.ing.mim.facedetection.DetectionParameters;
import it.unipi.ing.mim.facedetection.FaceDetection;
import it.unipi.ing.mim.featuresextraction.DNNExtractor;
import it.unipi.ing.mim.featuresextraction.ExtractionParameters;
import it.unipi.ing.mim.featuresextraction.FeaturesStorage;
import it.unipi.ing.mim.featuresextraction.ImgDescriptor;
import it.unipi.ing.mim.utilities.Output;

public class SearchEngineFaceDetection {
	
	private List<ImgDescriptor> descriptors;
	
	public static void main(String[] args) throws Exception {
		//Image Query File
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter image path : ");
	    String imgPath = bufferRead.readLine();
	    System.out.print("Enter number of desired similar images : ");
	    int K = Integer.parseInt(bufferRead.readLine());

		FaceDetection faceDetector = new FaceDetection(DetectionParameters.HAAR_CASCADE_FRONTALFACE);
		
		Mat[] imgMat = faceDetector.getFaces(imgPath, DetectionParameters.PADDING);
	 
		searchEngine(imgPath, K, imgMat[0]);
	}
	
	public static void searchEngine(String imgPath, int K, Mat imgMat) throws ClassNotFoundException {

		SearchEngine searcher = new SearchEngine();
		DNNExtractor extractor = new DNNExtractor();
		
		//in this case for computational reason the database must exist
		try{
			searcher.open(ExtractionParameters.STORAGE_FILE);
		}
		catch(IOException e){
			System.err.println("Features database doesn't exist yet, please launch CreateSeqFeaturesFile");
			return;
		}
			   
	    File img = new File(imgPath);
	    
		float[] features = extractor.extract(imgMat, ExtractionParameters.DEEP_LAYER);
		ImgDescriptor query = new ImgDescriptor(features, img.getName());
				
		List<ImgDescriptor> res = searcher.search(query, K);
		
		Output.toHTML(res, RecognitionParameters.BASE_URI_SEARCH_ENGINE_FD, RecognitionParameters.SEARCH_ENGINE_HTML_FD);
	}
		
	public void open(File storageFile) throws ClassNotFoundException, IOException {
		//Load the dataset and assign it to the descriptors object
		descriptors = FeaturesStorage.load(storageFile);	
	}
	
	public List<ImgDescriptor> search(ImgDescriptor query, int k) {	
		
		List<ImgDescriptor> res = new ArrayList<ImgDescriptor>();
		
		//LOOP descriptors to perform a sequential scan search
		for(int i = 0; i < descriptors.size(); i++){
			descriptors.get(i).dist = descriptors.get(i).distance(query);
		}
		
		//sort the results
		Collections.sort(descriptors);
		
		//return the sorted k best results
		for(int i = 0; i < k; i++){
			res.add(descriptors.get(i));
		}
		
		return res;
	}
}
