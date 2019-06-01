package recommender;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class JobReducer extends Reducer<Text, Text, Text, Text> {
	
  @Override
  public void reduce(Text jobId, Iterable<Text> skillSet, Context context) throws IOException, InterruptedException {
    String skills = "";
  	for(Text skill : skillSet) {
      skills += (!skills.isEmpty() ? "+" : "") + skill.toString();
    }
    context.write(jobId, new Text(skills));
  }
}