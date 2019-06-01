package recommender;

import java.io.IOException;
import java.util.HashMap;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class UserReducer extends Reducer<Text, Text, Text, Text> {

	@Override
  public void reduce(Text userId, Iterable<Text> skillSet, Context context) throws IOException, InterruptedException {
    String[] jobSkillsData = context.getConfiguration().getStrings("jobSkills");
    for(String jobSkillData : jobSkillsData) {
    	String[] jobData = jobSkillData.split("~");
    	HashMap<Integer, Integer> thisJobSkills = new HashMap<>();
    	HashMap<Integer, Integer> thisJobExperiences = new HashMap<>();
    	for(String skillData : jobData[1].split("\\+")) {
    		if(skillData.contains("-")) {
    			thisJobSkills.put(Integer.parseInt(skillData.split("-")[0]), Integer.parseInt(skillData.split("-")[1]));
    		} else {
    			thisJobExperiences.put(Integer.parseInt(skillData.split("=")[0]), Integer.parseInt(skillData.split("=")[1]));
    		}
    	}
    	
    	HashMap<Integer, Integer> thisUserSkills = new HashMap<>();
    	HashMap<Integer, Integer> thisUserExperiences = new HashMap<>();
    	for(Text skillDataText : skillSet) {
    		String skillData = skillDataText.toString();
    		if(skillData.contains("-")) {
  				thisUserSkills.put(Integer.parseInt(skillData.split("-")[0]), Integer.parseInt(skillData.split("-")[1]));
    		} else {
    			thisUserExperiences.put(Integer.parseInt(skillData.split("=")[0]), Integer.parseInt(skillData.split("=")[1]));
    		}
      }
    	
    	double skillSimilarity = CosineSimilarity.similarity(thisUserSkills, thisJobSkills);
    	double experienceSimilarity = CosineSimilarity.similarity(thisUserExperiences, thisJobExperiences);
    	double totalSimilarity = (skillSimilarity + experienceSimilarity) / 2;
    	if(totalSimilarity > 0.3) {
    		context.write(new Text(userId.toString() + "-" + jobData[0]), new Text(totalSimilarity + ""));
    	}
    }
  }
}
