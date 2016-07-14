package it.unipi.ing.mim.facedetection;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;
import java.io.File;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacv.CanvasFrame;

/**
 * This class contains utility functions used for the face detection.
 * */
public class Utility {
	
	/**
	 * Generates a canvas frame of the specified dimensions and the specified title.
	 * @param width is the width of the canvas frame to generate
	 * @param height is the height of the canvas frame to generate
	 * @param title is the text to be set as title of the canvas
	 * @return a canvas frame of the specified dimensions
	 * */
	public static CanvasFrame getCanvas(int width, int height, String title) {
		CanvasFrame canvasFrame = new CanvasFrame(title);
		canvasFrame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		canvasFrame.setCanvasSize(width, height);
		
		return canvasFrame;
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

	/**
	 * Saves the specified image as the specified file.
	 * @param imgROI is the image to be saved
	 * @param outFile is the file that will contain the image
	 * @return the created file
	 * */
	public static File face2File(Mat imgROI, File outFile) {
		imwrite(outFile.getAbsolutePath(), imgROI);
		return outFile;
	}
	
}
