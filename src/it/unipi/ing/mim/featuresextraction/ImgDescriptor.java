package it.unipi.ing.mim.featuresextraction;

import java.io.Serializable;

public class ImgDescriptor implements Serializable, Comparable<ImgDescriptor> {

	private static final long serialVersionUID = 1L;
	
	private float[] normalizedVector; // image feature
	
	public String id; // unique id of the image (usually file name)
	
	public double dist; // used for sorting purposes
	
	public ImgDescriptor(float[] features, String id) {
		this.id = id;
		float norm2 = evaluateNorm2(features);
		this.normalizedVector = getNormalizedVector(features, norm2);
	}
	
	public float[] getFeatures() {
		return normalizedVector;
	}
	
    // compare with other friends using distances
	@Override
	public int compareTo(ImgDescriptor arg0) {
		return new Double(dist).compareTo(arg0.dist);
	}
	
	//TODO
	public double distance(ImgDescriptor desc) {
		
		double sum = 0;
		double dist;
		
		//Evaluate the Euclidian distance between 2 ImgDescriptor
		float [] normalizedVector2 = desc.getFeatures();
		for (int i = 0; i < this.normalizedVector.length; i++){
			
			sum += Math.pow(this.normalizedVector[i] - normalizedVector2[i], 2);	
		}
		
		dist = Math.sqrt(sum);
		
		return dist;
	}
	
	//TODO
	private float[] getNormalizedVector(float[] vector, float norm2) {
		
		float[] normalizedVector = new float [vector.length];
		
		//Normalize the vector values by means of its norm 2
		
		for(int i = 0; i < vector.length; i++){
			
			normalizedVector[i] = vector[i]/norm2;
		}
		
		return normalizedVector;
	}
	
	//TODO
	private float evaluateNorm2(float[] vector) {
		
		float norm2 = 0;
		double sum = 0;
		
		//Evaluate the norm 2 of the vector
		for (int i = 0; i < vector.length; i++){
			
			sum += Math.pow(vector[i], 2);
		}
		
		norm2 = (float)Math.sqrt(sum);
		
		return norm2;
	}
    
}
