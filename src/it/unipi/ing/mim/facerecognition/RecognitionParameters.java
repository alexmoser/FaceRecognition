package it.unipi.ing.mim.facerecognition;

import java.io.File;

/**
 * This class contains parameters specifically defined for the package classes
 * */
public class RecognitionParameters {

	//Threshold chosen statistically
	public static final float THRESHOLD = 1.2507f;
	//Threshold chosen statistically using face detection
	public static final float THRESHOLD_FD = 1.2507f;
	
	//k-Nearest Neighbors
	public static final int K = 5;
	
	public static final File SRC_FOLDER = new File("data/lfw_funneled");
	public static final File SRC_FOLDER_FD = new File("data/lfw");
	
	public static final File TMP_FOLDER = new File("out/tmp");
	
	//HTML Output Parameters
	public static final String BASE_URI = "file:///" + SRC_FOLDER.getAbsolutePath() + "/";
	public static final File RESULTS_HTML = new File("out/seq.html");
	public static final String BASE_URI_FD = "file:///" + SRC_FOLDER_FD.getAbsolutePath() + "/";
	public static final File RESULTS_HTML_FD = new File("out/seq_fd.html");
	
	
}
