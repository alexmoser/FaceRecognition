package it.unipi.ing.mim.facedetection;

import java.io.File;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter;

import it.unipi.ing.mim.facerecognition.RecognitionParameters;

/**
 * This class provides a single main function whose function is to test the FaceDetection class
 * */
public class FaceDetectionMain {

	public static void main(String[] args) {
	
		FaceDetection faceDetection = new FaceDetection(DetectionParameters.HAAR_CASCADE_FRONTALFACE);
		
		Mat [] img = faceDetection.getFaces("data/friends.jpg", 0.6f);
		
		if(img.length == 0)
			System.out.println("Face not found");
		
		for(int i = 0; i < img.length; i++) {
			CanvasFrame canvasFrame = Utility.getCanvas(img[i].cols(), img[i].rows());
			canvasFrame.setTitle("face_" + i);
			canvasFrame.showImage(new OpenCVFrameConverter.ToMat().convert(img[i]));
			Utility.face2File(img[i], new File(RecognitionParameters.TMP_COMPARE_FOLDER + "/" + "match_"+i + "_img1_" + i +".jpg"));
			
		}
	}
}