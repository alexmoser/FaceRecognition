package it.unipi.ing.mim.featuresextraction;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CreateSeqFeaturesFile {

	public static void main(String[] args) throws Exception {
				
		CreateSeqFeaturesFile indexing = new CreateSeqFeaturesFile();
				
		List<ImgDescriptor> descriptors = indexing.extractFeatures(ExtractionParameters.SRC_FOLDER);
		
		FeaturesStorage.store(descriptors, ExtractionParameters.STORAGE_FILE);
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
				float [] features = obj.extract(fileList[j], ExtractionParameters.DEEP_LAYER);
				ImgDescriptor temp = new ImgDescriptor(features, fileList[j].getName());
				descs.add(temp);
				System.out.println("Feature " + (j) + ", " + temp.id);
			}
		}
		
		return descs;	
	}		
}
