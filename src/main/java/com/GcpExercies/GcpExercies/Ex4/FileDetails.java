package com.GcpExercies.GcpExercies.Ex4;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileDetails {
    private String fileName;
    private String fileContent;
}