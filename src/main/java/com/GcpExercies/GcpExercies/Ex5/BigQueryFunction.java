package com.GcpExercies.GcpExercies.Ex5;

import com.GcpExercies.GcpExercies.Ex4.PubSubStorageFunction;
import com.GcpExercies.GcpExercies.Ex5.Model.Message;
import com.google.cloud.functions.CloudEventsFunction;
import com.google.gson.Gson;
import io.cloudevents.CloudEvent;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.logging.Logger;

public class BigQueryFunction implements CloudEventsFunction {

    private static final Logger logger = Logger.getLogger(PubSubStorageFunction.class.getName());
    private final Gson gson = new Gson();
    private final String BUCKET_NAME = "ex4-storage";

    @Override
    public void accept(CloudEvent cloudEvent) throws Exception {
        if(cloudEvent.getData() != null){
            final String cloudEventData = new String(Base64.getDecoder().decode(cloudEvent.getData().toBytes()), StandardCharsets.UTF_8);
            final Message message = gson.fromJson(cloudEventData, Message.class);


        }
    }
}
