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
		
		Mat [] img = faceDetection.getFaces("data/lfw/Chok_Tong_Goh/Chok_Tong_Goh_0001.jpg", 0.6f);
		
		if(img.length == 0)
			System.out.println("Face not found");
		
		for(int i = 0; i < img.length; i++) {
			CanvasFrame canvasFrame = Utility.getCanvas(img[i].cols(), img[i].rows());
			canvasFrame.setTitle("face_" + i);
			canvasFrame.showImage(new OpenCVFrameConverter.ToMat().convert(img[i]));
		}
	}
}