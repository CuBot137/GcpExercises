# GcpExercise 1
Google Cloud Functions are blocks of code that are designed to serve a specific purpose.
They are lightweight, serverless and cost-effective.

In this example I created a function that takes in a http request. It will give a response depending on the request type.
For example, a get request will return "This is a GET response".

## cloudbuild.yaml
steps:
- name: 'gcr.io/google.com/cloudsdktool/cloud-sdk'
  args:
  - gcloud
  - functions
  - deploy
  - function-http
  - --region=us-central1
  - --source=.
  - --trigger-http
  - --runtime=java17
  - --entry-point com.GcpExercies.GcpExercies.Ex1.CronFunctions


### Important 
Remember to update your function target in your pom
<plugin>
    <groupId>com.google.cloud.functions</groupId>
    <artifactId>function-maven-plugin</artifactId>
    <version>0.11.0</version>
    <configuration>
        <functionTarget>functions.CronFunctions</functionTarget>
    </configuration>
</plugin>