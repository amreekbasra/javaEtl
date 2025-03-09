#!/bin/bash

# Define the path to the Spring Boot application JAR file
JAR_PATH="/path/to/your/spring-boot-application.jar"

# Define the parameters for this job
FILE_PATH="/path/to/source/file1.csv"
JOB_ID="job1"
FILE_NAME="file1.csv"
JOB_NAME="ETLJob1"

# Define the log file path
LOG_FILE="/path/to/logs/etl_application_job1.log"

# Clear the log file before starting
> $LOG_FILE

# Run the Spring Boot application with the parameters as arguments
java -jar $JAR_PATH --job.filePath=$FILE_PATH --job.jobId=$JOB_ID --job.fileName=$FILE_NAME --job.jobName=$JOB_NAME >> $LOG_FILE 2>&1

# Capture the exit status of the application
EXIT_STATUS=$?

# Check the exit status and log success or failure
if [ $EXIT_STATUS -eq 0 ]; then
  echo "ETL application completed successfully" >> $LOG_FILE
else
  echo "ETL application failed with exit status $EXIT_STATUS" >> $LOG_FILE
fi

# Exit with the same status as the application
exit $EXIT_STATUS