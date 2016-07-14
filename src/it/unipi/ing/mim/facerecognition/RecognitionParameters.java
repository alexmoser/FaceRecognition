package it.unipi.ing.mim.facerecognition;

import java.io.File;

/**
 * This class contains parameters specifically defined for the package classes
 * */
public class RecognitionParameters {

	// Threshold chosen statistically
	public static final float THRESHOLD = 1.2507f;
	// Threshold chosen statistically using face detection
	public static final float THRESHOLD_FD = 1.2405f;
	
	// Images Source Folder
	public static final File SRC_FOLDER = new File("data/lfw_funneled");
	public static final File SRC_FOLDER_FD = new File("data/lfw");

	// Temporary folders used to store images of the detected faces
	public static final File TMP_COMPARE_FOLDER = new File("out/tmp_compare");
	public static final File TMP_SEARCH_ENGINE_FOLDER = new File("out/tmp_search_engine");
	
	//HTML Output Parameters

	/* Note: in BASE_URI_SEARCH_ENGINE_FD we should be using SRC_FOLDER_FD, but due to time constraints
	 * we didn't create the features database for the lfw images. 
	 * Hence we base our search on the lfw_funneled database, for which the features have been stored.
	 * */

	// HTML File containing the results of the Search Engine
	public static final String BASE_URI_SEARCH_ENGINE = "file:///" + SRC_FOLDER.getAbsolutePath() + "/";
	public static final File SEARCH_ENGINE_HTML = new File("out/search_engine_matches.html");

	// HTML File containing the results of the Search Engine with Face Detection
	public static final String BASE_URI_SEARCH_ENGINE_FD = "file:///" + SRC_FOLDER.getAbsolutePath() + "/";
	public static final File SEARCH_ENGINE_HTML_FD = new File("out/search_engine_fd_matches.html");
	
	// Temporary folders to store the images of the detected faces in
	public static final String BASE_URI_SEARCH_ENGINE_TMP = "file:///" + TMP_COMPARE_FOLDER.getAbsolutePath() + "/";
	public static final String BASE_URI_COMPARE_TMP = "file:///" + TMP_COMPARE_FOLDER.getAbsolutePath() + "/";
	
	// HTML File containing the results of the Compare Two Images with Face Detection
	public static final File COMPARE_HTML_FD = new File("out/compare_matches.html");	
}
