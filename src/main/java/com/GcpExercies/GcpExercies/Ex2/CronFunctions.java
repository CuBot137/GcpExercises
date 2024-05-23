package com.GcpExercies.GcpExercies.Ex2;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class CronFunctions implements HttpFunction {
    private static final Logger logger = Logger.getLogger(CronFunctions.class.getName());
    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);

        logger.info("Current Time: " + formattedTime);
    }
}
