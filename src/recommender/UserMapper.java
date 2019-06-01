package recommender;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class UserMapper extends Mapper<LongWritable, Text, Text, Text> {
	
	private boolean isSkillData(JSONObject data) {
		return data.containsKey("rank");
	}
	
	@Override
  public void map(LongWritable offset, Text lineText, Context context) throws IOException, InterruptedException {
		try {
			String line = lineText.toString();
      JSONArray arrayOfData = (JSONArray) new JSONParser().parse(line);
      for(Object datumObject : arrayOfData) {
      	JSONObject datum = (JSONObject) datumObject;
      	if(isSkillData(datum)) {
	      	if(!((String) datum.get("skill_id")).equals("NaN") && !((String) datum.get("rank")).equals("NaN")) {
		       	context.write(
		      			new Text((String) datum.get("employee_id")), 
		      			new Text((String) datum.get("skill_id") + "-" + (String) datum.get("rank"))
		  			);
	      	}
      	} else {
      		if(!((String) datum.get("occupation_id")).equals("NaN") && !((String) datum.get("year")).equals("NaN")) {
		       	context.write(
		      			new Text((String) datum.get("employee_id")), 
		      			new Text((String) datum.get("occupation_id") + "=" + (String) datum.get("year"))
		  			);
	      	}
      	}
      }      
		} catch(ParseException e) {
      System.out.println("ParseException");
      e.printStackTrace();
    }
	}
}
