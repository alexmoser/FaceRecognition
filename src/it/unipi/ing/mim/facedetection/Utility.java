package it.unipi.ing.mim.facedetection;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;

import java.io.File;
import java.io.FilenameFilter;

import org.bytedeco.javacpp.opencv_core.CvPoint;
import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.Scalar;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;

/**
 * This class contains utility function used for the face detection.
 * */
public class Utility {
	
	private static OpenCVFrameConverter.ToMat frame2Mat = new OpenCVFrameConverter.ToMat();
	
	public static CanvasFrame getCanvas(int width, int height) {
		CanvasFrame canvasFrame = new CanvasFrame("OpenCV Face Recognition");
		canvasFrame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		canvasFrame.setCanvasSize(width, height);
		
		return canvasFrame;
	}
	
	public static void highlight(Frame image, Rect rect) {
		highlight(frame2Mat.convertToIplImage(image), rect);
	}
	
	public static void highlight(IplImage image, Rect rect) {
		int xMin = rect.x();
		int yMin = rect.y();
		int xMax = rect.width() + rect.x();
		int yMax = rect.height() + rect.y();
		int thick = 1;

		CvPoint pt1 = cvPoint(xMin, yMin);
		CvPoint pt2 = cvPoint(xMax, yMax);
		CvScalar color = cvScalar(255, 0, 0, 0); // blue [green] [red]
		cvRectangle(image, pt1, pt2, color, thick, 4, 0);
	}

	public static void highlight(Mat image, Rect rect) {
		int xMin = rect.x();
		int yMin = rect.y();
		int xMax = rect.width() + rect.x();
		int yMax = rect.height() + rect.y();
		int thick = 1;

		Point pt1 = new Point(xMin, yMin);
		Point pt2 = new Point(xMax, yMax);
		Scalar color = new Scalar(255, 0, 0, 0); // blue [green] [red]
		rectangle(image, pt1, pt2, color, thick, 4, 0);
	}

	public static Mat getGrayImage(Mat img) {
		Mat grayImg = new Mat(img.cols(), img.rows(), CV_8UC1);
		cvtColor(img, grayImg, CV_BGR2GRAY);
		return grayImg;
	}

	/**
	 * Returns a squared Mat image containing the specified face, cropped from the specified
	 * image, accordingly to the padding.
	 * @param img is the source image from which the face needs to be taken
	 * @param face is the Rect representing the face inside the image
	 * @param padding is a number representing the percentage of padding w.r.t.
	 * the longest side of the face rectangle
	 * */
	public static Mat getImageROI(Mat img, Rect face, float padding) {
		Rect rect = null;
		Mat roi = null;
		try {
			// compute side dimension
			int longest_side = (face.width() > face.height()) ? face.width() : face.height();
			longest_side += longest_side*padding;
			/*
			 * x_fill[0] : left 
			 * x_fill[1] : right
			 * y_fill[0] : top
			 * y_fill[1] : bottom
			 * */
			int[] 	x_fill = {0, 0},
					y_fill = {0, 0};
			int width, height;
			// redefine coordinates in order to keep face centered
			// store filling pixels, if any
			int x_coord = face.x() - ((longest_side-face.width())/2); 
			if(x_coord + longest_side > img.cols()) {
				x_fill[1] = (x_coord + longest_side) - img.cols();
			}
			if(x_coord < 0) {
				x_fill[0] = -x_coord;
				x_coord = 0;
			}
			int y_coord = face.y() - ((longest_side-face.height())/2);
			if(y_coord + longest_side > img.rows()) {
				y_fill[1] = (y_coord + longest_side) - img.rows();
			}
			if(y_coord < 0) {
				y_fill[0] = -y_coord;
				y_coord = 0;
			}
			
			// compute effective rectangle dimensions (no filling)
			width = longest_side - x_fill[0] - x_fill[1];
			height = longest_side - y_fill[0] - y_fill[1];
			
			// define rectangle
			rect = new Rect(x_coord, y_coord, width, height);
			
			// crop image (no filling)
			roi = new Mat(img, rect);
			
			// add filling pixels
			copyMakeBorder(roi, roi, y_fill[0], y_fill[1], x_fill[0], x_fill[1], 0);
			
		} catch (Exception e) {
			System.err.println("x: " + face.x() + ", " +  face.y() + ", " + face.width() + ", " + face.height());
		}
		return roi;
	}

	public static File face2File(Mat imgROI, File outFile) {
		imwrite(outFile.getAbsolutePath(), imgROI);
		return outFile;
	}

	public static Mat getNormalizedImage(Mat img) {
		Mat resized = new Mat();
		resize(img, resized, new Size(300, 300));
		return resized;
	}

	public static int countNumFiles(File classesFolder) {
		int numFiles = 0;
		File[] listFiles = classesFolder.listFiles();
		for (File file : listFiles) {
			if (file.isDirectory()) {
				numFiles += file.listFiles(imgFilter).length;
			}
		}
		return numFiles;
	}
	
	 public static FilenameFilter imgFilter = new FilenameFilter() {
         public boolean accept(File dir, String name) {
             return name.toLowerCase().endsWith(".jpg");
         }
     };
}
