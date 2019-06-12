hadoop fs -mkdir /user/recommender &&
hadoop fs -mkdir /user/recommender/input &&
hadoop fs -mkdir /user/recommender/input/user_100 &&
hadoop fs -mkdir /user/recommender/input/user_1000 &&
hadoop fs -mkdir /user/recommender/input/user_10000 &&
hadoop fs -mkdir /user/recommender/input/user_100000 &&
hadoop fs -mkdir /user/recommender/input/user_200000 &&
hadoop fs -mkdir /user/recommender/input/user_400000 &&
hadoop fs -mkdir /user/recommender/input/user_800000 &&
hadoop fs -mkdir /user/recommender/input/user_1600000 &&
hadoop fs -mkdir /user/recommender/input/job_100 &&
hadoop fs -mkdir /user/recommender/input/job_1000 &&
hadoop fs -mkdir /user/recommender/input/job_10000 &&
hadoop fs -mkdir /user/recommender/input/job_100000 &&
hadoop fs -mkdir /user/recommender/input/job_30000 &&
hadoop fs -put ./employee_*_100.json /user/recommender/input/user_100 &&
hadoop fs -put ./employee_*_1000.json /user/recommender/input/user_1000 &&
hadoop fs -put ./employee_*_10000.json /user/recommender/input/user_10000 &&
hadoop fs -put ./employee_*_100000.json /user/recommender/input/user_100000 &&
hadoop fs -put ./employee_skill_ranks_part_{0..3}.json /user/recommender/input/user_200000 &&
hadoop fs -put ./employee_skill_ranks_part_{0..7}.json /user/recommender/input/user_400000 &&
hadoop fs -put ./employee_skill_ranks_part_{0..15}.json /user/recommender/input/user_800000 &&
hadoop fs -put ./employee_skill_ranks_part_{0..31}.json /user/recommender/input/user_1600000 &&
hadoop fs -put ./employee_year_experiences_part_{0..1}.json /user/recommender/input/user_200000 &&
hadoop fs -put ./employee_year_experiences_part_{0..3}.json /user/recommender/input/user_400000 &&
hadoop fs -put ./employee_year_experiences_part_{0..7}.json /user/recommender/input/user_800000 &&
hadoop fs -put ./employee_year_experiences_part_{0..15}.json /user/recommender/input/user_1600000 &&
hadoop fs -put ./job_*_100.json /user/recommender/input/job_100 &&
hadoop fs -put ./job_*_1000.json /user/recommender/input/job_1000 &&
hadoop fs -put ./job_*_10000.json /user/recommender/input/job_10000 &&
hadoop fs -put ./job_*_100000.json /user/recommender/input/job_100000 &&
hadoop fs -put ./job_*_30000.json /user/recommender/input/job_30000;
