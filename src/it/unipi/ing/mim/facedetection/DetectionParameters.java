package it.unipi.ing.mim.facedetection;

import org.bytedeco.javacpp.opencv_core.Size;

/**
 * This class contains parameters specifically defined for the package classes
 * */
public class DetectionParameters {
	
	// Parameters for detectMultiScale method
	public static final Size FACE_MIN_SIZE = new Size(100, 100);
	public static final Size FACE_MAX_SIZE = new Size(640, 360);
	
	// OpenCV model used
	public static final String HAAR_CASCADE_FRONTALFACE = "data/haarcascades/haarcascade_frontalface_default.xml";
	
	// Padding for bounded box dimensions
	public static final float PADDING = 0.6f;
}