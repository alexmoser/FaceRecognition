package it.unipi.ing.mim.facedetection;

import static org.bytedeco.javacpp.opencv_imgcodecs.CV_LOAD_IMAGE_COLOR;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_objdetect.CV_HAAR_DO_CANNY_PRUNING;

import java.io.File;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.RectVector;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier;

public class FaceDetection {

	private CascadeClassifier face_cascade;

	public FaceDetection(String haarcascadePath) {
		//init detector
		face_cascade = new CascadeClassifier(haarcascadePath);
	}

	private RectVector detect(Mat img, Size minSize, Size maxSize) {
		//detect faces
		RectVector face = new RectVector();
		face_cascade.detectMultiScale(img, face, 1.2, 3, CV_HAAR_DO_CANNY_PRUNING, minSize, maxSize);
		return face;
	}
	
	/**
	 * Detects and crops the face from the specified image.
	 * @param imgPath is the path of the image
	 * @return a Mat representing the detected and cropped face
	 * */
	public Mat getFace(String imgPath) {
		Mat img = null;
		try {
			File image = new File(imgPath);
			img = imread(image.getAbsolutePath(), CV_LOAD_IMAGE_COLOR);

			// Face
			RectVector faces = detect(img, DetectionParameters.FACE_MIN_SIZE, DetectionParameters.FACE_MAX_SIZE);
			
			//TODO chiedere come gestire la detection di più facce
			if(faces.size() > 1){
				System.err.println("More than one face detected, only one selected!");
			}
			
			img = Utility.getImageROI(img, faces.get(0));
			
			/*//debug 
			for(int i = 0; i < faces.size(); i++){
				Utility.highlight(img, faces.get(i));
			}
			*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return img;
	}
}
