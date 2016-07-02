package it.unipi.ing.mim.facedetection;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter;

public class FaceDetectionMain {

	public static void main(String[] args) {
	
		FaceDetection faceDetection = new FaceDetection(DetectionParameters.HAAR_CASCADE_FRONTALFACE);
		
		Mat img = faceDetection.getFace("data/lfw/Abdullah_Gul/Abdullah_Gul_0013.jpg");
		
		CanvasFrame canvasFrame = Utility.getCanvas(img.cols(), img.rows());
		canvasFrame.showImage(new OpenCVFrameConverter.ToMat().convert(img));
		
	}
}