package com.GcpExercies.GcpExercies.Ex1;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedWriter;
import java.io.IOException;

@RestController
public class HttPFunctions implements HttpFunction {

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        String method = request.getMethod();
        BufferedWriter writer = response.getWriter();
        String responseMessage;

        switch (method) {
            case "GET":
                responseMessage = "This is a GET response";
                break;
            case "POST":
                String requestBody = request.getReader().readLine();
                if (requestBody != null) {
                    responseMessage = "POST response: Received Data";
                } else {
                    responseMessage = "POST response: No Received Data";
                }
                break;
            case "DELETE":
                responseMessage = "DELETE response";
                break;
            default:
                responseMessage = "Unsupported HTTP method: " + method;
        }

        writer.write(responseMessage);
    }
}
