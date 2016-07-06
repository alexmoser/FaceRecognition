package it.unipi.ing.mim.facedetection;

import static org.bytedeco.javacpp.opencv_imgcodecs.CV_LOAD_IMAGE_COLOR;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_objdetect.CV_HAAR_DO_CANNY_PRUNING;

import java.io.File;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.RectVector;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier;

/**
 * This class provides the methods used to detect the faces in a specified image.
 * */
public class FaceDetection {

	private CascadeClassifier face_cascade;

	public FaceDetection(String haarcascadePath) {
		//init detector
		face_cascade = new CascadeClassifier(haarcascadePath);
	}

	private RectVector detect(Mat img, Size minSize, Size maxSize) {
		//detect faces
		RectVector face = new RectVector();
		face_cascade.detectMultiScale(img, face, 1.2, 4, CV_HAAR_DO_CANNY_PRUNING, minSize, maxSize);
		return face;
	}
	
	/**
	 * Detects and crops the faces from the specified image.
	 * The crop is done in such a way that each resulting image is squared, according to 
	 * the longest side of the detected rectangle, increased of a certain specified percentage
	 * @param imgPath is the path of the image
	 * @param padding is the padding (in percentage) to add to the detected rectangle
	 * @return an array of Mat representing the detected and cropped faces
	 * */
	public Mat[] getFaces(String imgPath, float padding) {
		Mat img = null;
		Mat[] ret = null;
		try {
			File image = new File(imgPath);
			img = imread(image.getAbsolutePath(), CV_LOAD_IMAGE_COLOR);

			// detect the face
			RectVector faces = detect(img, DetectionParameters.FACE_MIN_SIZE, DetectionParameters.FACE_MAX_SIZE);
			ret = new Mat[(int)faces.size()];
			System.out.println("Number of detected faces: " + faces.size());
			for(int i = 0; i < faces.size(); i++){
				//Utility.highlight(img, faces.get(i));
				ret[i] = Utility.getImageROI(img, faces.get(i), padding);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}
}
