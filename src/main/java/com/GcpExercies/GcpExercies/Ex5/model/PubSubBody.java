package com.GcpExercies.GcpExercies.Ex5.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PubSubBody {
    private PubSubMessage message;
}
