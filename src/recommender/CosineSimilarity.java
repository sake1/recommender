package recommender;

import java.util.HashMap;
import java.util.Map;

public class CosineSimilarity {
	
	public static double similarity(HashMap<Integer, Integer> user, HashMap<Integer, Integer> job) {
		double magnitude = magnitude(user) * magnitude(job);
		return magnitude == 0 ? 0 : dotProduct(user, job) / magnitude;
	}
	
	private static double magnitude(HashMap<Integer, Integer> vector) {
		double output = 0;
		for(Map.Entry<Integer, Integer> entry : vector.entrySet()) {
	    output += Math.pow(entry.getValue(), 2);
		}
		return Math.sqrt(output);
	}
	
	private static double dotProduct(HashMap<Integer, Integer> user, HashMap<Integer, Integer> job) {
		double output = 0;
		for(Map.Entry<Integer, Integer> entry : user.entrySet()) {
	    if(job.containsKey(entry.getKey())) {
	    	output += entry.getValue() * job.get(entry.getKey());
	    }
		}
		return output;
	}
}
