package it.unipi.ing.mim.featuresextraction;

import java.io.Serializable;
/**
 * This class represents an image descriptor, which collects an image features
 * along with its id and other informations.
 * */
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
	
	/**
	 * Evaluates the Euclidean distance with the specified object.
	 * @param is the descriptor of the image
	 * @return the distance with the image 
	 * */
	public double distance(ImgDescriptor desc) {
		
		double sum = 0;
		double dist;
		
		// Evaluate the Euclidean distance between 2 ImgDescriptor
		float [] normalizedVector2 = desc.getFeatures();
		
		for (int i = 0; i < this.normalizedVector.length; i++){
			sum += Math.pow(this.normalizedVector[i] - normalizedVector2[i], 2);	
		}
		
		dist = Math.sqrt(sum);
		
		return dist;
	}
	
	/**
	 * It normalizes the features vector according to the specified parameter.
	 * @param vector is the features vector
	 * @param norm2 is the norm of the features vector
	 * @return the normalized vector
	 * */
	private float[] getNormalizedVector(float[] vector, float norm2) {
		
		float[] normalizedVector = new float [vector.length];
		
		// Normalize the vector values by means of its norm 2
		for(int i = 0; i < vector.length; i++){	
			normalizedVector[i] = vector[i]/norm2;
		}
		
		return normalizedVector;
	}

	/**
	 * It evaluates the norm2 of the specified vector.
	 * @param vector is the feature vector
	 * @return the norm2 value
	 * */
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
	
	/* Equals method has been overridden in order to make it possible to use
	 * the method indexOf in DistanceEvaluator only having the id of the descriptor
	 * and not the object itself.
	 * */  
	@Override
	public boolean equals(Object o) {
		ImgDescriptor obj = (ImgDescriptor) o;
		/* Two descriptors are to be intended equals if their id is equals, not if 
		 * the instance of the object is the same*/
		return (obj.id.equals(this.id));
	}
    
}
