# GcpExercise 5

This Google Cloud Function does  the following:
1. Handle a PubSub message
2. Read a JSON message
3. Create a storage bucket
4. Store the json response in the bucket
5. Write the same message into Bigquery

## Before You Start
Create a service account on GCP with read and write permissions for Cloud Storage and Bigquery





<Command>
gcloud functions deploy ex5 --gen2 
--entry-point com.GcpExercies.GcpExercies.Ex5.BigQueryFunction 
--runtime java17 --region=europe-west2 --trigger-topic=my-topic 
--source=target --run-service-account=pub-sub-storage@function-project-423615.iam.gserviceaccount.com 
--update-env-vars=TABLE_NAME=ex5-table,MAIN_CLASS=com.GcpExercies.GcpExercies.Ex5.BigQueryFunction --memory 512MB
</Command>