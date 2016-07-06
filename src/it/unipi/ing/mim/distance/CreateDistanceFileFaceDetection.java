package it.unipi.ing.mim.distance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.bytedeco.javacpp.opencv_core.Mat;
import it.unipi.ing.mim.facedetection.DetectionParameters;
import it.unipi.ing.mim.facedetection.FaceDetection;
import it.unipi.ing.mim.featuresextraction.ExtractionParameters;
import it.unipi.ing.mim.utilities.CsvFileWriter;

/**
 * This class only contains a main that is intended to be launched once.
 * The difference is that before evaluating the distance, the face needs to be detected.
 * Function is anologous to CreateDistanceFile, see such class for further details.
 * */
public class CreateDistanceFileFaceDetection {

	public static void main(String [] args) throws Exception {
		
		CsvFileWriter resultsFile = new CsvFileWriter("distance", DistanceParameters.DISTANCES_FILE_FD);
	
		FileReader source = new FileReader(DistanceParameters.TEST_FILE);
	    BufferedReader b = new BufferedReader(source);
	 
		DistanceEvaluator distanceEvaluator = new DistanceEvaluator(ExtractionParameters.STORAGE_FILE_FD);
		
		FaceDetection faceDetector = new FaceDetection(DetectionParameters.HAAR_CASCADE_FRONTALFACE);

		File img1, img2;
		
		int count = Integer.parseInt(b.readLine());
	    double [] distances = new double[count*2];
	    
	    for (int i = 0; i < (count*2); i++){
	    	
	    	String tmp = b.readLine();
	    	String [] split = tmp.split("\\t");
	    	
	    	if (i < count){
	    		img1 = new File(DistanceParameters.SRC_FOLDER_FD + split[0] + "/" + split[0] + "_0" + ((Integer.parseInt(split[1]) < 10) ? "00" : (Integer.parseInt(split[1]) < 100) ? "0" : "") + split[1] + ".jpg");
	    		img2 = new File(DistanceParameters.SRC_FOLDER_FD + split[0] + "/" + split[0] + "_0" + ((Integer.parseInt(split[2]) < 10) ? "00" : (Integer.parseInt(split[2]) < 100) ? "0" : "") + split[2] + ".jpg");
	    	}
	    	else{
	    		img1 = new File(DistanceParameters.SRC_FOLDER_FD + split[0] + "/" + split[0] + "_0" + ((Integer.parseInt(split[1]) < 10) ? "00" : (Integer.parseInt(split[1]) < 100) ? "0" : "") + split[1] + ".jpg");
	    		img2 = new File(DistanceParameters.SRC_FOLDER_FD + split[2] + "/" + split[2] + "_0" + ((Integer.parseInt(split[3]) < 10) ? "00" : (Integer.parseInt(split[3]) < 100) ? "0" : "") + split[3] + ".jpg");
	    	} 
			
	    	// detect the face(s) from the images specified
	    	Mat imgMat1 = faceDetector.getFaces(img1.getPath());
	    	Mat imgMat2 = faceDetector.getFaces(img2.getPath());
	    	
	    	distances[i] = distanceEvaluator.evaluateDistance(imgMat1, imgMat2, img1, img2);
					
			if(i == 0) 
				resultsFile.addLine("equals-pairs");
			if(i == count) 
				resultsFile.addLine("non-equals-pairs");
			
			resultsFile.addLine(Double.toString(distances[i]));
	    }
	
	    resultsFile.closeFile();
		b.close();
		source.close();
	}
}
