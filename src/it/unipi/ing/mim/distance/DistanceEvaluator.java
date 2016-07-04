package it.unipi.ing.mim.distance;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bytedeco.javacpp.opencv_core.Mat;

import it.unipi.ing.mim.featuresextraction.DNNExtractor;
import it.unipi.ing.mim.featuresextraction.ImgDescriptor;
import it.unipi.ing.mim.featuresextraction.ExtractionParameters;
import it.unipi.ing.mim.featuresextraction.FeaturesStorage;

public class DistanceEvaluator {

	private DNNExtractor extractor;
	private List<ImgDescriptor> descriptors;
	
	public DistanceEvaluator(File storageFile) throws ClassNotFoundException, IOException {
		extractor = new DNNExtractor();
		descriptors = FeaturesStorage.load(storageFile);
	}
	
	/**
	 * Evaluates the distance between the two images specified.
	 * If the image is already in the DB, get the features from the features DB.
	 * If not, extract the features.
	 * @param img1 is the first image
	 * @param img2 is the second image
	 * @return the distance between the two images
	 * */
	public float evaluateDistance(File img1, File img2) {
		ImgDescriptor imgDescriptor1;
		ImgDescriptor imgDescriptor2;
		// check if images are in our DB
		// since indexOf(o) uses the equals method of the parameter object, we need to define
		// a ghost descriptor in order to make it work correctly
		int index1 = descriptors.indexOf(new ImgDescriptor(new float[] {0.0f}, img1.getName()));
		if(index1 == -1) {
			//img1 is not in the DB
			System.out.println("img1 no in DB: " + img1.getName());
			float[] imgFeatures1 = extractor.extract(img1, ExtractionParameters.DEEP_LAYER);
			imgDescriptor1 = new ImgDescriptor(imgFeatures1, img1.getName());
		}
		else {
			System.out.println("img1 in DB: " + img1.getName());
			imgDescriptor1 = descriptors.get(index1);
		}
		// since indexOf(o) uses the equals method of the parameter object, we need to define
		// a ghost descriptor in order to make it work correctly
		int index2 = descriptors.indexOf(new ImgDescriptor(new float[] {0.0f}, img2.getName()));
		if (index2 == -1) {
			//img2 is not in the DB
			System.out.println("img2 no in DB: " + img2.getName());
			float[] imgFeatures2 = extractor.extract(img2, ExtractionParameters.DEEP_LAYER);
			imgDescriptor2 = new ImgDescriptor(imgFeatures2, img2.getName());
		}
		else {
			System.out.println("img2 in DB: " + img2.getName());
			imgDescriptor2 = descriptors.get(index2);
		}

		return (float)imgDescriptor1.distance(imgDescriptor2);
	}
	
	/**
	 * Evaluates the distance between the two images specified.
	 * If the image is already in the DB, get the features from the features DB.
	 * If not, extract the features.
	 * @param imgMat1 is the first image
	 * @param imgMat2 is the second image
	 * @param img1 is the name of the first image
	 * @param img2 is the name of the second image
	 * @return the distance between the two images
	 * */
	public float evaluateDistance(Mat imgMat1, Mat imgMat2, File img1, File img2) {
		ImgDescriptor imgDescriptor1;
		ImgDescriptor imgDescriptor2;
		// check if images are in our DB
		// since indexOf(o) uses the equals method of the parameter object, we need to define
		// a ghost descriptor in order to make it work correctly
		int index1 = descriptors.indexOf(new ImgDescriptor(new float[] {0.0f}, img1.getName()));
		if(index1 == -1) {
			//img1 is not in the DB
			System.out.println("img1 no in DB: " + img1.getName());
			float[] imgFeatures1 = extractor.extract(imgMat1, ExtractionParameters.DEEP_LAYER);
			imgDescriptor1 = new ImgDescriptor(imgFeatures1, img1.getName());
		}
		else {
			System.out.println("img1 in DB: " + img1.getName());
			imgDescriptor1 = descriptors.get(index1);
		}
		// since indexOf(o) uses the equals method of the parameter object, we need to define
		// a ghost descriptor in order to make it work correctly
		int index2 = descriptors.indexOf(new ImgDescriptor(new float[] {0.0f}, img2.getName()));
		if (index2 == -1) {
			//img2 is not in the DB
			System.out.println("img2 no in DB: " + img2.getName());
			float[] imgFeatures2 = extractor.extract(imgMat2, ExtractionParameters.DEEP_LAYER);
			imgDescriptor2 = new ImgDescriptor(imgFeatures2, img2.getName());
		}
		else {
			System.out.println("img2 in DB: " + img2.getName());
			imgDescriptor2 = descriptors.get(index2);
		}

		return (float)imgDescriptor1.distance(imgDescriptor2);
	}
}
