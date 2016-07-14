package it.unipi.ing.mim.facerecognition;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * This class only contains a main function that is used to test the image comparison
 * with face detection
 * */
public class CompareTwoImagesFaceDetection {
	
	public static void main(String [] args) throws Exception{
		// read the path of the two images from the console
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter first image path : ");
	    String imgPath1 = bufferRead.readLine();
	    System.out.print("Enter second image path : ");
	    String imgPath2 = bufferRead.readLine();
		int res = CompareTwoImages.compareWithFaceDetection(imgPath1, imgPath2);
		if(res > 0)
			System.out.println("There are " + res + " matching faces");
		else
			System.out.println("There are NOT matching faces");
	}
}
