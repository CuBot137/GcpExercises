# GcpExercise 1
Google Cloud Functions are blocks of code that are designed to serve a specific purpose.
They are lightweight, serverless and cost-effective.

In this example I created a function that gets the current time and logs it. I then used Cloud Scheduler to get the function to run every minute

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
    - --entry-point com.GcpExercies.GcpExercies.Ex1.HttpFunctions