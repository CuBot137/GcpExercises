# GcpExercise 4

This Google Cloud Function does  the following:
1. Handle a PubSub message
2. Read a JSON message
3. Create a storage bucket
4. Store the json response in the bucket

## Before You Start
Create a service account on GCP with read and write permissions for Cloud Storage

<Command>
gcloud functions deploy ex4 --gen2 
--region=us-central1 --runtime=java17 
--source=target --entry-point=com.GcpExercies.GcpExercies.Ex4.PubSubStorageFunction 
--trigger-topic=my-topic --run-service-account=pub-sub-storage@function-project-423615.iam.gserviceaccount.com 
--allow-unauthenticated   
</Command>

<PubSub>
gcloud pubsub topics publish my-topic --message="{\"nodeName\":\"node1\",\"timeStamp\":\"2023-09-25T14:53:45Z\",\"uplink\":1.23,\"downlink\":3.45}"
</PubSub>

## Blocked
Currently, this project is blocked. When I deploy the function it fails.
The exact same code works in a different environment. This has been looked into and the answer currently evades us. 
For now, I will move onto more immediate work.