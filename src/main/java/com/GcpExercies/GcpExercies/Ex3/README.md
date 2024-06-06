# GcpExercise 3
Google Cloud Functions are blocks of code that are designed to serve a specific purpose.
They are lightweight, serverless and cost-effective.

In this example I created a function that is triggered by pub/sub. 
When a message is sent to the topic, function is triggered and logs the message. 

## cloudbuild.yaml
steps:
- name: 'gcr.io/google.com/cloudsdktool/cloud-sdk'
  args:
  - gcloud
  - functions
  - deploy
  - pubsubFunky
  - --region=us-central1
  - --source=.
  - --trigger-topic
  - --runtime=java17
  - --entry-point com.GcpExercies.GcpExercies.Ex1.PubSubFunction