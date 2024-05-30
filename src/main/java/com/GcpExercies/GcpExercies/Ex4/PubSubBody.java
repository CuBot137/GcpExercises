package com.GcpExercies.GcpExercies.Ex4;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PubSubBody implements Serializable {

    private PubSubMessage message;

    public PubSubMessage getMessage() {
        return message;
    }
}
