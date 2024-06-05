package com.GcpExercies.GcpExercies.Ex5.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;

import com.google.cloud.bigquery.Field;
import com.google.cloud.bigquery.Field.Mode;
import com.google.cloud.bigquery.Schema;
import com.google.cloud.bigquery.StandardSQLTypeName;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NodeData  implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nodeName;
    private ZonedDateTime timeStamp;
    private double uplink;
    private double downlink;

    public static Schema getSchema() {
        return Schema.of(
                Field.newBuilder("nodeName", StandardSQLTypeName.STRING).setMode(Mode.REQUIRED).setDescription("Node Name").build(),
                Field.newBuilder("timeStamp", StandardSQLTypeName.TIMESTAMP).setMode(Mode.REQUIRED).setDescription("Time stamp").build(),
                Field.newBuilder("uplink", StandardSQLTypeName.FLOAT64).setMode(Mode.REQUIRED).setDescription("Uplink Value").build(),
                Field.newBuilder("downlink", StandardSQLTypeName.FLOAT64).setMode(Mode.REQUIRED).setDescription("Downlink Value").build()
        );
    }
}
