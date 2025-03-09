#!/bin/bash

# Define the path to the Spring Boot application JAR file
JAR_PATH="/path/to/your/spring-boot-application.jar"

# Define the log file directory
LOG_DIR="/path/to/logs"

# Function to run a job
run_job() {
  local job_id=$1
  local log_file="${LOG_DIR}/etl_application_${job_id}.log"

  # Clear the log file before starting
  > $log_file

  # Run the Spring Boot application and redirect output to the log file
  java -jar $JAR_PATH >> $log_file 2>&1 &

  # Capture the PID of the Spring Boot application
  local app_pid=$!

  # Monitor the log file for progress updates
  tail -f $log_file | while read LOGLINE
  do
     [[ "${LOGLINE}" == *"Progress:"* ]] && echo "Job ${job_id}: ${LOGLINE}"
     [[ "${LOGLINE}" == *"ETL application completed successfully"* ]] && pkill -P $$ tail
     [[ "${LOGLINE}" == *"ETL application failed"* ]] && pkill -P $$ tail
  done &

  # Wait for the application to finish
  wait $app_pid

  # Capture the exit status of the application
  local exit_status=$?

  # Check the exit status and log success or failure
  if [ $exit_status -eq 0 ]; then
    echo "Job ${job_id}: ETL application completed successfully" >> $log_file
  else
    echo "Job ${job_id}: ETL application failed with exit status $exit_status" >> $log_file
  fi

  # Exit with the same status as the application
  return $exit_status
}

# Run multiple jobs in parallel
run_job 1 &
run_job 2 &
run_job 3 &

# Wait for all background jobs to complete
wait

echo "All jobs completed"