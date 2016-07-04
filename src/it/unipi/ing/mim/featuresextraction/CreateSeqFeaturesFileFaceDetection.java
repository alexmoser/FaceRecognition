package it.unipi.ing.mim.featuresextraction;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bytedeco.javacpp.opencv_core.Mat;

import it.unipi.ing.mim.facedetection.DetectionParameters;
import it.unipi.ing.mim.facedetection.FaceDetection;

public class CreateSeqFeaturesFileFaceDetection {

	public static void main(String[] args) throws Exception {
				
		CreateSeqFeaturesFileFaceDetection indexing = new CreateSeqFeaturesFileFaceDetection();
				
		List<ImgDescriptor> descriptors = indexing.extractFeatures(ExtractionParameters.SRC_FOLDER_FD);
		
		FeaturesStorage.store(descriptors, ExtractionParameters.STORAGE_FILE_FD);
	}
	
	private List<ImgDescriptor> extractFeatures(File imgFolder){
		
		List<ImgDescriptor>  descs = new ArrayList<ImgDescriptor>();
		DNNExtractor obj = new DNNExtractor ();
		
		//Scan the folder to get all the sub-directories
		File [] dirList = imgFolder.listFiles();
		
		//Scan each directory
		for (int i = 0; i < dirList.length; i++){
			if(!dirList[i].isDirectory())
				continue;
			//Get all the files in the directory
			File [] fileList = dirList[i].listFiles();

			//Extract the deep features for each file
			//Put the features in an ImgDescriptor object and add it in the descs List
			//Descriptor ID is the file name
			for (int j = 0; j < fileList.length; j++){ 
				//skip the system files
				if(fileList[j].isHidden())
					continue;
				//Face Detection
				FaceDetection faceDetector = new FaceDetection(DetectionParameters.HAAR_CASCADE_FRONTALFACE);
				Mat img = faceDetector.getFace(fileList[j].getAbsolutePath());
				float [] features = obj.extract(img, ExtractionParameters.DEEP_LAYER);
				ImgDescriptor temp = new ImgDescriptor(features, fileList[j].getName());
				descs.add(temp);
				System.out.println("Feature " + (j) + ", " + temp.id);
			}
		}
		
		return descs;	
	}		
}
