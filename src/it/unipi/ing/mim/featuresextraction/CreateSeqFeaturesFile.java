package it.unipi.ing.mim.featuresextraction;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This class defines a main function whose target is to create a .mat file containing all the 
 * extracted features of the images in the database.
 * */
public class CreateSeqFeaturesFile {

	public static void main(String[] args) throws Exception {
				
		CreateSeqFeaturesFile indexing = new CreateSeqFeaturesFile();
				
		List<ImgDescriptor> descriptors = indexing.extractFeatures(ExtractionParameters.SRC_FOLDER);
		
		FeaturesStorage.store(descriptors, ExtractionParameters.STORAGE_FILE);
	}
	
	/**
	 * Creates a list containing one image descriptor for each file in the specified directory.
	 * @param imgFolder is the directory that contains all the files
	 * @return a list of the descriptor of each file
	 * */
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
