package it.unipi.ing.mim.facerecognition;

import static org.bytedeco.javacpp.opencv_imgcodecs.CV_LOAD_IMAGE_UNCHANGED;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bytedeco.javacpp.opencv_core.Mat;

import it.unipi.ing.mim.featuresextraction.ExtractionParameters;
import it.unipi.ing.mim.featuresextraction.ImgDescriptor;
import it.unipi.ing.mim.featuresextraction.FeaturesStorage;
import it.unipi.ing.mim.featuresextraction.DNNExtractor;
import it.unipi.ing.mim.utilities.Output;

/**
 * This class provides the methods used to search similar images in the database.
 * It also contains a main used for testing.
 * */
public class SearchEngine {
	
	private List<ImgDescriptor> descriptors;
	
	public static void main(String[] args) throws Exception {

		//Image Query File
		System.out.print("Enter image path : ");
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
	    String imgPath = bufferRead.readLine();
		
		List<ImgDescriptor> res = searchEngine(imgPath, 10);
		
		Output.toHTML(res, RecognitionParameters.BASE_URI_SEARCH_ENGINE, RecognitionParameters.SEARCH_ENGINE_HTML);
	}
	
	/**
	 * Scan the database to find similar images to the one specified by the path.
	 * @param imgPath is the path of the query image
	 * @param K is the number of most similar images to search
	 * @return a list of descriptors of the K most similar images
	 * @throws ClassNotFoundException
	 * */
	public static List<ImgDescriptor> searchEngine(String imgPath, int K) throws ClassNotFoundException {
		
		File img = new File(imgPath);
		Mat imgMat = imread(img.getAbsolutePath(), CV_LOAD_IMAGE_UNCHANGED);
		
		return searchEngine(imgMat, K, imgPath);
	}
	
	/**
	 * Scans the database to find similar images to the face specified as Mat.
	 * @param imgMat is the query face
	 * @param K is the number of most similar images to search
	 * @param imgPath is the path of the image from which the face has been taken
	 * @return a list of descriptors of the K most similar images
	 * @throws ClassNotFoundException
	 * */
	public static List<ImgDescriptor> searchEngine(Mat imgMat, int K, String imgPath) throws ClassNotFoundException {

		SearchEngine searcher = new SearchEngine();
		DNNExtractor extractor = new DNNExtractor();
		
		//in this case for computational reason the database must exist
		try{
			searcher.open(ExtractionParameters.STORAGE_FILE);
		}
		catch(IOException e){
			System.err.println("Features database doesn't exist yet, please launch CreateSeqFeaturesFile");
			return null;
		}
			   
	    File img = new File(imgPath);
	    
		float[] features = extractor.extract(imgMat, ExtractionParameters.DEEP_LAYER);
		ImgDescriptor query = new ImgDescriptor(features, img.getName());
				
		return searcher.search(query, K);
	}
		
	/**
	 * Loads the descriptors stored in the specified file as a ImgDescriptor list.
	 * @param storageFile is the file containing the the descriptors 
	 * @throws ClassNotFoundException, IOException
	 * */
	public void open(File storageFile) throws ClassNotFoundException, IOException {
		//Load the dataset and assign it to the descriptors object
		descriptors = FeaturesStorage.load(storageFile);	
	}
	
	/**
	 * Performs a sequential scan search on the descriptor list that represents the database
	 * in order to find the K most similar images (in terms of distance) to the specified query.
	 * @param query is the descriptor of the query image
	 * @param k is the number of desired results
	 * @return a list containing the image descriptors of the k most similar images 
	 * */
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
