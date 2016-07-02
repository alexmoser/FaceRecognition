package it.unipi.ing.mim.facedetection;

import org.bytedeco.javacpp.opencv_core.Size;

public class DetectionParameters {
	
	public static final Size FACE_MIN_SIZE = new Size(100, 100);

	public static final Size FACE_MAX_SIZE = new Size(640, 360);
	
	public static final String HAAR_CASCADE_FRONTALFACE = "data/haarcascades/haarcascade_frontalface_default.xml";
}