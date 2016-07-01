package it.unipi.ing.mim.deep;

import java.io.File;

public class Parameters {
	
	//DEEP parameters
	public static final String DEEP_PROTO = "data/vgg_face_caffe/VGG_FACE_deploy.prototxt";
	public static final String DEEP_MODEL = "data/vgg_face_caffe/VGG_FACE.caffemodel";
	public static final String DEEP_MEAN_IMG = "data/vgg_face_caffe/mean.png";
	
	//Test source file
	public static final String TEST_FILE = "data/pairsDevTest.txt";
	
	public static final String DEEP_LAYER = "fc7";
	public static final int IMG_WIDTH = 224;
	public static final int IMG_HEIGHT = 224;
	
	//Image Source Folder
	public static final File SRC_FOLDER = new File("data/lfw_funneled");
	
	//Output File Path
	public static final File DISTANCES_FILE = new File("out/results.csv"); 
	
	//Threshold chosen statistically
	public static final float THRESHOLD = 1.2507f;
	
	//non usati per adesso
	
	//Features Storage File
	public static final File STORAGE_FILE = new File("out/deep.seq.dat");
	
	//k-Nearest Neighbors
	public static final int K = 30;
	
	//HTML Output Parameters
	public static final  String BASE_URI = "file:///" + Parameters.SRC_FOLDER.getAbsolutePath() + "/";
	public static final File RESULTS_HTML = new File("out/deep.seq.html");

}
