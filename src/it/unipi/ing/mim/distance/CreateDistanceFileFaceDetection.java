package it.unipi.ing.mim.distance;

import java.io.BufferedReader;
import java.io.FileReader;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter;

import it.unipi.ing.mim.facedetection.DetectionParameters;
import it.unipi.ing.mim.facedetection.FaceDetection;
import it.unipi.ing.mim.facedetection.Utility;
import it.unipi.ing.mim.utilities.CsvFileWriter;

public class CreateDistanceFileFaceDetection {

	public static void main(String [] args) throws Exception {
		
		String imgPath1, imgPath2;
		CsvFileWriter resultsFile = new CsvFileWriter("distance", DistanceParameters.DISTANCES_FILE_FD);
	
		FileReader source = new FileReader(DistanceParameters.TEST_FILE);
	    BufferedReader b = new BufferedReader(source);
	 
		DistanceEvaluator distanceEvaluator = new DistanceEvaluator();
		
		FaceDetection faceDetector = new FaceDetection(DetectionParameters.HAAR_CASCADE_FRONTALFACE);
		
	    int count = Integer.parseInt(b.readLine());
	    double [] distances = new double[count*2];
	    
	    for (int i = 0; i < (count*2); i++){
	    	
	    	String tmp = b.readLine();
	    	String [] split = tmp.split("\\t");
	    	
	    	if (i < count){
	    		imgPath1 = split[0] + "/" + split[0] + "_0" + ((Integer.parseInt(split[1]) < 10) ? "00" : (Integer.parseInt(split[1]) < 100) ? "0" : "") + split[1] + ".jpg";
	    		imgPath2 = split[0] + "/" + split[0] + "_0" + ((Integer.parseInt(split[2]) < 10) ? "00" : (Integer.parseInt(split[2]) < 100) ? "0" : "") + split[2] + ".jpg";
	    	}
	    	else{
	    		imgPath1 = split[0] + "/" + split[0] + "_0" + ((Integer.parseInt(split[1]) < 10) ? "00" : (Integer.parseInt(split[1]) < 100) ? "0" : "") + split[1] + ".jpg";
	    		imgPath2 = split[2] + "/" + split[2] + "_0" + ((Integer.parseInt(split[3]) < 10) ? "00" : (Integer.parseInt(split[3]) < 100) ? "0" : "") + split[3] + ".jpg";
	    	}   
			
	    	Mat img1 = faceDetector.getFace(DistanceParameters.SRC_FOLDER_FD + imgPath1);
	    	Mat img2 = faceDetector.getFace(DistanceParameters.SRC_FOLDER_FD + imgPath2);
			
	    	distances[i] = distanceEvaluator.evaluateDistance(img1, img2, imgPath1, imgPath2);
					
			if(i == 0) 
				resultsFile.addLine("equals-pairs");
			if(i == count) 
				resultsFile.addLine("non-equals-pairs");
			
			// print distances to a file 
			resultsFile.addLine(Double.toString(distances[i]));
	    }
	
	    resultsFile.closeFile();
		b.close();
		source.close();
	}
}
