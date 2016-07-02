package it.unipi.ing.mim.distance;

import java.io.File;

import org.bytedeco.javacpp.opencv_core.Mat;

import it.unipi.ing.mim.featuresextraction.DNNExtractor;
import it.unipi.ing.mim.featuresextraction.ImgDescriptor;
import it.unipi.ing.mim.featuresextraction.ExtractionParameters;

public class DistanceEvaluator {

	private DNNExtractor extractor;
	
	public DistanceEvaluator() {
		extractor = new DNNExtractor();
	}
	
	/**
	 * Evaluates the distance between the two images specified.
	 * @param extractor is the DNNExtractor to be used for the feature extraction
	 * @param imgPath1 is the path of the first image
	 * @param imgPath2 is the path of the second image
	 * @return the distance between the two images
	 * */
	public float evaluateDistance(String imgPath1, String imgPath2) {
		File img1 = new File(imgPath1);
		File img2 = new File(imgPath2);
		
		float[] imgFeatures1 = extractor.extract(img1, ExtractionParameters.DEEP_LAYER);
		float[] imgFeatures2 = extractor.extract(img2, ExtractionParameters.DEEP_LAYER);
		
		ImgDescriptor imgDescriptor1 = new ImgDescriptor(imgFeatures1, img1.getName());
		ImgDescriptor imgDescriptor2 = new ImgDescriptor(imgFeatures2, img2.getName());
		
		return (float)imgDescriptor1.distance(imgDescriptor2);
	}
	
	/**
	 * Evaluates the distance between the two images specified.
	 * @param extractor is the DNNExtractor to be used for the feature extraction
	 * @param img1 is the first image
	 * @param img2 is the second image
	 * @param id1 is the name of the first image
	 * @param id2 is the name of the second image
	 * @return the distance between the two images
	 * */
	public float evaluateDistance(Mat img1, Mat img2, String id1, String id2) {
		
		float[] imgFeatures1 = extractor.extract(img1, ExtractionParameters.DEEP_LAYER);
		float[] imgFeatures2 = extractor.extract(img2, ExtractionParameters.DEEP_LAYER);
		
		ImgDescriptor imgDescriptor1 = new ImgDescriptor(imgFeatures1, id1);
		ImgDescriptor imgDescriptor2 = new ImgDescriptor(imgFeatures2, id2);
		
		return (float)imgDescriptor1.distance(imgDescriptor2);
	}
}
