package com.GcpExercies.GcpExercies.Ex5;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.GcpExercies.GcpExercies.Ex5.handlers.BigQueryHandler;
import com.GcpExercies.GcpExercies.Ex5.handlers.StorageHandler;
import com.GcpExercies.GcpExercies.Ex5.model.NodeData;
import com.GcpExercies.GcpExercies.Ex5.model.PubSubMessage;
import com.GcpExercies.GcpExercies.Ex5.utils.Utils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ConfigurationPropertiesScan
public class BigQueryFunction{

    private static final Logger logger = Logger.getLogger(BigQueryFunction.class.getName());
    private final Gson gson = Utils.getDefaultGsonInstance();
    private static final String BUCKET_NAME = "Ex5";
    private static final String TABLE_NAME_KEY = "TABLE_NAME";

    @Autowired
    private StorageHandler storageHandler;
    @Autowired
    private BigQueryHandler bqHandler;

    @Bean
    public Consumer<PubSubMessage> pubSubFunction(){
        return message -> {
            try{
                final String encodedData = message.getData();
                final String decodedData = new String(Base64.getDecoder().decode(encodedData), StandardCharsets.UTF_8);
                logger.info("Message received: "+decodedData);

                final NodeData nodeData = gson.fromJson(decodedData, NodeData.class);

                // create bucket
                storageHandler.createBucket(BUCKET_NAME);
                // store file
                final String path = storageHandler.uploadFile(BUCKET_NAME, this.getFileName(nodeData), decodedData);
                logger.info("File created at - " + path);

                final String tableName = this.getTableName();
                logger.info("Table Name - " + tableName);

                // create bq table
                bqHandler.createTable(tableName, NodeData.getSchema());

                // write data to bq
                bqHandler.writeNodeDataToBq(decodedData, tableName);
            } catch (final Exception e) {
                logger.log(Level.SEVERE, "Failed to process message. Reason - " + e);
            }
        };
    }

    private String getFileName(final NodeData nodeData) {
        return nodeData.getNodeName() + "/" + nodeData.getTimeStamp().toString() + ".json";
    }
    private String getTableName() {
        if(System.getenv().containsKey(TABLE_NAME_KEY)) {
            return System.getenv(TABLE_NAME_KEY);
        }
        if(System.getProperties().containsKey(TABLE_NAME_KEY)) {
            return System.getProperty(TABLE_NAME_KEY);
        }
        return null;
    }
    private File writeToFile(final String content) throws IOException {
        final Path tempFile = Files.createTempFile("data", ".json");
        Files.write(tempFile, content.getBytes());
        return tempFile.toFile();
    }
}
