package recommender;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;

public class Tester extends Configured implements Tool {

  public static void main(String[] args) throws Exception {
    int res = ToolRunner.run(new Configuration(), new Tester(), args);
    System.exit(res);
  }

	@Override
	public int run(String[] args) throws Exception {
		Job job = Job.getInstance(getConf(), "jobExec");
    job.setJarByClass(this.getClass());
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[2]));
    job.setMapperClass(JobMapper.class);
    job.setReducerClass(JobReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);
    job.setOutputFormatClass(SequenceFileOutputFormat.class);
//    int jobMapReduceResult = job.waitForCompletion(true) ? 0 : 1;
    if(!job.waitForCompletion(true)) {
    	return 1;
    }

    FileSystem fs = FileSystem.get(new Configuration());
    FileStatus[] fss = fs.listStatus(new Path(args[2]));
    String cachedJobSkillData = "";
    for(FileStatus status : fss) {
	    Path path = status.getPath();
	    if(path.toString().contains("_SUCCESS")) {
	    	continue;
	    }
	    SequenceFile.Reader reader = new SequenceFile.Reader(new Configuration(), SequenceFile.Reader.file(path));
	    Text key = new Text();
	    Text value = new Text();
	    while (reader.next(key, value)) {
	    	cachedJobSkillData += (cachedJobSkillData.isEmpty() ? "" : ",") + key + "~" + value;
	    }
//	    System.out.println(cachedJobSkillData);
	    reader.close();
    }
    fs.close();

    Job user = Job.getInstance(getConf(), "userExec");
    user.setJarByClass(this.getClass());
    FileInputFormat.addInputPath(user, new Path(args[1]));
    FileOutputFormat.setOutputPath(user, new Path(args[3]));
    user.setMapperClass(UserMapper.class);
    user.setReducerClass(UserReducer.class);
    user.setOutputKeyClass(Text.class);
    user.setOutputValueClass(Text.class);
    user.setOutputFormatClass(SequenceFileOutputFormat.class);
    user.getConfiguration().setStrings("jobSkills", cachedJobSkillData);
    return user.waitForCompletion(true) ? 0 : 1;
	}
}
