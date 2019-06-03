hadoop fs -rm -r -f /user/cloudera/recommender/output* && 
javac -cp /usr/lib/hadoop/*:/usr/lib/hadoop-mapreduce/* -d ../bin/recommender/ ../src/recommender/*.java -Xlint &&
echo "Java class compilation success." &&
jar -cvf recommender.jar -C ../bin/recommender/ . &&
echo "Begin Hadoop MapReduce Job..." &&
time hadoop jar recommender.jar recommender.Tester \
/user/cloudera/recommender/input/job_100/ \
/user/cloudera/recommender/input/user_100/ \
/user/cloudera/recommender/output \
/user/cloudera/recommender/output2;
