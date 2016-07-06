package it.unipi.ing.mim.featuresextraction;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bytedeco.javacpp.opencv_core.Mat;

import it.unipi.ing.mim.facedetection.DetectionParameters;
import it.unipi.ing.mim.facedetection.FaceDetection;

/**
 * This class is analogous to CreateSeqFeaturesFile, with the only difference that also
 * performs face detection.
 * */
public class CreateSeqFeaturesFileFaceDetection {

	public static void main(String[] args) throws Exception {
				
		CreateSeqFeaturesFileFaceDetection indexing = new CreateSeqFeaturesFileFaceDetection();
				
		List<ImgDescriptor> descriptors = indexing.extractFeatures(ExtractionParameters.SRC_FOLDER_FD);
		
		FeaturesStorage.store(descriptors, ExtractionParameters.STORAGE_FILE_FD);
	}
	
	private List<ImgDescriptor> extractFeatures(File imgFolder){
		
		List<ImgDescriptor>  descs = new ArrayList<ImgDescriptor>();
		DNNExtractor obj = new DNNExtractor ();
		
		// scan the folder to get all the sub-directories
		File [] dirList = imgFolder.listFiles();
		
		// scan each directory
		for (int i = 0; i < dirList.length; i++){
			if(!dirList[i].isDirectory())
				continue;
			// get all the files in the directory
			File [] fileList = dirList[i].listFiles();

			// for each file
			for (int j = 0; j < fileList.length; j++){
				// skip hidden files
				if(fileList[j].isHidden())
					continue;
				// face detection
				FaceDetection faceDetector = new FaceDetection(DetectionParameters.HAAR_CASCADE_FRONTALFACE);
				Mat img = faceDetector.getFaces(fileList[j].getAbsolutePath());
				// extract the deep features
				float [] features = obj.extract(fileList[j], ExtractionParameters.DEEP_LAYER);
				// put the features in an Imgdescriptor object (as ID use the file name)
				ImgDescriptor temp = new ImgDescriptor(features, fileList[j].getName());
				// add it to the descs list
				descs.add(temp);
				System.out.println("Feature " + (j) + ", " + temp.id);
			}
		}
		
		return descs;	
	}		
}
