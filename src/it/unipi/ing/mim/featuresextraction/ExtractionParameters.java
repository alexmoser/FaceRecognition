package it.unipi.ing.mim.featuresextraction;

import java.io.File;

public class ExtractionParameters {
	
	//DEEP parameters
	public static final String DEEP_PROTO = "data/vgg_face_caffe/VGG_FACE_deploy.prototxt";
	public static final String DEEP_MODEL = "data/vgg_face_caffe/VGG_FACE.caffemodel";
	public static final String DEEP_MEAN_IMG = "data/vgg_face_caffe/mean.png";
	
	public static final String DEEP_LAYER = "fc7";
	public static final int IMG_WIDTH = 224;
	public static final int IMG_HEIGHT = 224;
	
	//Image Source Folder
	public static final File SRC_FOLDER = new File("data/lfw_funneled");
	
	//Features Storage File
	public static final File STORAGE_FILE = new File("out/seqFeatures.dat");
		
	//Image Source Folder using face detection
	public static final File SRC_FOLDER_FD = new File("data/lfw");
		
	//Features Storage File using face detection
	public static final File STORAGE_FILE_FD = new File("out/seqFeatures_fd.dat");
	
}
