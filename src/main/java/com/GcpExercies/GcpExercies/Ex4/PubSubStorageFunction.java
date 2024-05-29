package com.GcpExercies.GcpExercies.Ex4;

import com.GcpExercies.GcpExercies.Ex2.CronFunctions;
import com.google.cloud.functions.BackgroundFunction;
import com.google.cloud.functions.Context;
import com.google.cloud.storage.*;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.logging.Logger;

@Component
public class PubSubStorageFunction implements BackgroundFunction<PubSubMessage> {
    private final Storage storage = StorageOptions.getDefaultInstance().getService();
    private static final Logger logger = Logger.getLogger(CronFunctions.class.getName());
    @Override
    public void accept(PubSubMessage message, Context context) throws Exception {
        try {
            if (message.data != null) {
                Gson gson = new Gson();
                // Get data returned from the Pub Sub
                String json = new String(Base64.getDecoder().decode(message.data), StandardCharsets.UTF_8);
                // Map data to the JsonDto class I created
                JsonDTO jsonDTO = gson.fromJson(json, JsonDTO.class);

                String fileName = jsonDTO.getFileName();
                String fileContent = jsonDTO.getFileContent();
                String bucketName = "ex4-storage";

                // Write to Cloud Storage
                BlobId blobId = BlobId.of(bucketName, fileName);
                Blob blob = storage.create(BlobInfo.newBuilder(blobId).build(), fileContent.getBytes());

                logger.info("FILE CREATE WITH NAME: " + fileName + " AND BUCKET: " + bucketName);
            }
        }catch (Exception e){
            logger.warning("EXCEPTION THROWN: "+e);
        }
    }
}



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
