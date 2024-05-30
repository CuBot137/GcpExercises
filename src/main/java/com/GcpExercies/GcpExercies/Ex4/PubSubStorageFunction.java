package com.GcpExercies.GcpExercies.Ex4;

import com.google.cloud.functions.CloudEventsFunction;
import com.google.gson.Gson;
import io.cloudevents.CloudEvent;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.logging.Logger;

@Component
public class PubSubStorageFunction implements CloudEventsFunction {
    private static final Logger logger = Logger.getLogger(PubSubStorageFunction.class.getName());
    private final Gson gson = new Gson();
    private final String BUCKET_NAME = "ex4-storage";
    private final StorageHandler storageHandler = new StorageHandler();


    @Override
    public void accept(final CloudEvent event) throws Exception {
        // Check if there is a message. it will be in json form
        if (event.getData() != null){
            // Turn the json message into bytes
            final String cloudEventData = new String(event.getData().toBytes(),StandardCharsets.UTF_8);
            // Pour the bytes into the PubSubBody mold
            final PubSubBody body = gson.fromJson(cloudEventData, PubSubBody.class);
            // Message contains all the data. Data contains the specific part of the data that we want.
            final String encodedData = body.getMessage().getData();
            // Convert the encodedData into a String value
            final String decodedData = new String(Base64.getDecoder().decode(encodedData), StandardCharsets.UTF_8);
            logger.info("Message Received: "+ decodedData);

            final FileDetails fileDetails = gson.fromJson(decodedData, FileDetails.class);
            logger.info("File Name - " + fileDetails.getFileName());

            // Create bucket if it does not already exist
            storageHandler.createBucket(BUCKET_NAME);

            // Store file
            final String path = storageHandler.uploadFile(BUCKET_NAME, fileDetails.getFileName(), fileDetails.getFileContent());
            logger.info("File created at: "+ path);
        }
    }

//    @Override
//    public void accept(PubSubBody pubSubBody, Context context) throws Exception {
//        if (pubSubBody.getMessage() != null) {
//            // Get the base64 encoded data
//            final String encodedData = pubSubBody.getMessage().getData();
//
//            // Decode the base64 encoded data to get the actual JSON string
//            final String decodedData = new String(Base64.getDecoder().decode(encodedData), StandardCharsets.UTF_8);
//            logger.info("Message Received: " + decodedData);
//
//            // Deserialize the decoded data to FileDetails
//            final FileDetails fileDetails = gson.fromJson(decodedData, FileDetails.class);
//            logger.info("File Name - " + fileDetails.getFileName());
//
//            // Create bucket if it does not already exist
//            storageHandler.createBucket(BUCKET_NAME);
//
//            // Store file
//            final String path = storageHandler.uploadFile(BUCKET_NAME, fileDetails.getFileName(), fileDetails.getFileContent());
//            logger.info("File created at: " + path);
//        }
//    }
}



//@Override
//    public void accept(PubSubMessage message, Context context) throws Exception {
//        try {
//            if (message.data != null) {
//                Gson gson = new Gson();
//                // Get data returned from the Pub Sub
//                String json = new String(Base64.getDecoder().decode(message.data), StandardCharsets.UTF_8);
//                // Map data to the JsonDto class I created
//                JsonDTO jsonDTO = gson.fromJson(json, JsonDTO.class);
//
//                String fileName = jsonDTO.getFileName();
//                String fileContent = jsonDTO.getFileContent();
//                String bucketName = "ex4-storage";
//
//                // Write to Cloud Storage
//                BlobId blobId = BlobId.of(bucketName, fileName);
//                Blob blob = storage.create(BlobInfo.newBuilder(blobId).build(), fileContent.getBytes());
//
//                logger.info("FILE CREATE WITH NAME: " + fileName + " AND BUCKET: " + bucketName);
//            }
//        }catch (Exception e){
//            logger.warning("EXCEPTION THROWN: "+e);
//        }
//    }





//if(message.data != null){
//        // Get data returned from the Pub Sub
//        String json = new String(Base64.getDecoder().decode(message.data), StandardCharsets.UTF_8);
//        // Des
//        JsonDTO jsonDTO = new Gson().fromJson(json, JsonDTO.class);
//
//
//        String content = jsonDTO.getFileName()+ ", "+ jsonDTO.getFileContent();
//        // Convert to byte array
//        byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
//
//        // Upload to Google Cloud Storage
//        Storage storage = StorageOptions.getDefaultInstance().getService();
//        BlobId blobId = BlobId.of(bucketName, objectName);
//        Blob blob = storage.create(BlobInfo.newBuilder(blobId).build(), contentBytes);
//
//        logger.info("FILE UPLOADED TO: "+ blob.getBlobId());
//        }
