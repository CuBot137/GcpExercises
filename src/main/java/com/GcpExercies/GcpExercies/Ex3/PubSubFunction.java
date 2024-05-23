package com.GcpExercies.GcpExercies.Ex3;


import com.GcpExercies.GcpExercies.Ex2.CronFunctions;
import com.google.cloud.functions.BackgroundFunction;
import com.google.cloud.functions.Context;
import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage;
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.messaging.Message;

import java.util.Base64;
import java.util.logging.Logger;

@Component
public class PubSubFunction implements BackgroundFunction<PubSub> {
    // Handle Logging
    private static final Logger logger = Logger.getLogger(CronFunctions.class.getName());

    @Override
    public void accept(PubSub pubSub, Context context) throws Exception {

        if (pubSub != null && pubSub.getData() != null) {
            // Receives the Base64 encoded String
            String payload = pubSub.getData();
            // Convert Base64 into a String
            String decodedMessage = new String(Base64.getDecoder().decode(payload));
            logger.info("Your Message: " + decodedMessage);
        } else {
            logger.warning("Received message with no data");
        }
    }
}

//    public void handleMessage(Message<String> message){
//        // Get message
//        String payload = message.getPayload();
//        MessageHeaders headers = message.getHeaders();
//
//        logger.info("Your Message: "+payload);
//
//        BasicAcknowledgeablePubsubMessage pubsubMessage = headers.get(GcpPubSubHeaders.ORIGINAL_MESSAGE, BasicAcknowledgeablePubsubMessage.class);
//        pubsubMessage.ack();
//    }