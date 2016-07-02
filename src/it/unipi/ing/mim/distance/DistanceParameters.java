package it.unipi.ing.mim.distance;

import java.io.File;

public class DistanceParameters {
	
	//Test source file
	public static final String TEST_FILE = "data/pairsDevTest.txt";
	
	//Image Source Folder
	public static final String SRC_FOLDER = new String("data/lfw_funneled/");
	public static final String SRC_FOLDER_FD = new String("data/lfw/");
		
	//Output File Path
	public static final File DISTANCES_FILE = new File("out/distances.csv"); 
	public static final File STATISTICS_FILE = new File("out/threshold_statistics.csv");
	public static final File DISTANCES_FILE_FD = new File("out/distances_fd.csv"); 
	public static final File STATISTICS_FILE_FD = new File("out/threshold_statistics_fd.csv");
}
