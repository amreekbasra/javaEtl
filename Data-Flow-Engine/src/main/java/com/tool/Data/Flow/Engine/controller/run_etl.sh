#!/bin/bash

# Define the path to the Spring Boot application JAR file
JAR_PATH="/path/to/your/spring-boot-application.jar"

# Define the log file path
LOG_FILE="/path/to/logs/etl_application.log"

# Run the Spring Boot application and redirect output to the log file
java -jar $JAR_PATH >> $LOG_FILE 2>&1

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

# update shell for loggin
#!/bin/bash

# Define the path to the Spring Boot application JAR file
JAR_PATH="/path/to/your/spring-boot-application.jar"

# Define the log file path
LOG_FILE="/path/to/logs/etl_application.log"

# Clear the log file before starting
> $LOG_FILE

# Run the Spring Boot application and redirect output to the log file
java -jar $JAR_PATH >> $LOG_FILE 2>&1 &

# Capture the PID of the Spring Boot application
APP_PID=$!

# Function to check the application status
check_status() {
  if ps -p $APP_PID > /dev/null; then
    echo "ETL application is running (PID: $APP_PID)"
  else
    echo "ETL application has stopped"
    exit 1
  fi
}

# Monitor the log file for progress updates
tail -f $LOG_FILE | while read LOGLINE
do
   [[ "${LOGLINE}" == *"Progress:"* ]] && echo "${LOGLINE}"
   [[ "${LOGLINE}" == *"ETL application completed successfully"* ]] && pkill -P $$ tail
   [[ "${LOGLINE}" == *"ETL application failed"* ]] && pkill -P $$ tail
done &

# Wait for the application to finish
wait $APP_PID

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