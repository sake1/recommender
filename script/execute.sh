hadoop fs -rm -r -f /user/recommender/output* && 
javac -cp /usr/lib/hadoop/*:/usr/lib/hadoop-mapreduce/* -d ../bin/recommender/ ../src/recommender/*.java -Xlint &&
echo "Java class compilation success." &&
jar -cvf recommender.jar -C ../bin/recommender/ . &&
echo "Begin Hadoop MapReduce Job..." &&
time yarn jar recommender.jar recommender.Tester -libjars ./json-simple-1.1.1.jar \
/user/recommender/input/job_30000/ \
/user/recommender/input/user_3200000/ \
/user/recommender/output \
/user/recommender/output2;
