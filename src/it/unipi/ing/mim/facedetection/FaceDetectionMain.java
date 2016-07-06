package it.unipi.ing.mim.facedetection;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter;

/**
 * This class provides a single main function whose function is to test the FaceDetection class
 * */
public class FaceDetectionMain {

	public static void main(String[] args) {
	
		FaceDetection faceDetection = new FaceDetection(DetectionParameters.HAAR_CASCADE_FRONTALFACE);
		
		Mat [] img = faceDetection.getFaces("data/prova3.jpg", 1f);
		
		for(int i=0; i<img.length; i++) {
			CanvasFrame canvasFrame = Utility.getCanvas(img[i].cols(), img[i].rows());
			canvasFrame.showImage(new OpenCVFrameConverter.ToMat().convert(img[i]));
		}
	}
}