package com.GcpExercies.GcpExercies.functions;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

public class HttPFunctions implements HttpFunction {

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        // See what type of request has been made
        String method = httpRequest.getMethod();

        String responseMessage;

        switch(method){
            case "GET":
                responseMessage = "This is a Get response";
                break;
            case "POST":
                String requestBody = httpRequest.getReader().readLine();
                if(requestBody != null){
                    responseMessage = "Post response: Received Data";
                }
                else {
                    responseMessage = "Post response: No Received Data";
                }
                break;
            case "DELETE":
                responseMessage = "Delete Response";
                break;
            default:
                responseMessage = "Unsupported HTTP method: "+method;
        }
        // Set response type
        httpResponse.setContentType("application/json");
        httpResponse.setStatusCode(method.equals("DELETE") ? 204 : 200);

        // Convert the response message to JSON
        httpResponse.getWriter().write(responseMessage);
    }
}
