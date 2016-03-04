This is a RESTful service implemented using Dropwizard.

All configurations are mentioned in the file 'fileupload.yml'.

Few things to note:
1) Make sure the port that are mentioned in the YAML file are free when trying to run the service
2) Make sure the log file location is present when running the application

To run the application:

1) Run gradle:installApp task. NOTE: This uses a gradle wrapper to refer to the distribution we need to use.
Ensure the distribution URL(mentioned in gradle-wrapper.properties) is not blocked or update it to a appropriate one.
2) Once the task completes, from the 'build/install/mediamath/bin' folder run the appropriate start script as follows:

'./mediamath server <full path to the YAML config file in code>'

Ideally the YAML file would be wrapped up with the distribution.

3) Once the server starts successfully as indicated from the logs, open a browser and hit: http://localhost:8888/file/uploadFile

Service should be good to go.

4) To get details of all files uploaded, hit: http://localhost:8888/file/all

Also, please make sure the mavenCentral() location is set for the gradle plugin in use.