package com.GcpExercies.GcpExercies.Ex5.handlers;

import com.GcpExercies.GcpExercies.Ex5.configuration.BigQueryConfiguration;
import com.google.cloud.bigquery.FormatOptions;
import com.google.cloud.bigquery.Job;
import com.google.cloud.bigquery.Schema;
import com.google.cloud.spring.bigquery.core.BigQueryTemplate;
import com.google.cloud.spring.bigquery.core.WriteApiResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BigQueryHandler {
    private static final Logger logger = Logger.getLogger(BigQueryHandler.class.getName());
    @Autowired
    private BigQueryConfiguration.BigQueryFileGateway bigQueryFileGateway;
    @Autowired
    private BigQueryTemplate bigQueryTemplate;

    public void createTable(final String tableName, final Schema schema){
        logger.info("Creating table: "+ tableName);
        this.bigQueryTemplate.createTable(tableName, schema);
        logger.info("Created table: "+tableName);
    }

    public boolean writeNodeDataToBq(final String data, final String tableName) {
        logger.info("Writing Node Data to bq table - " + tableName);
        try {
            final InputStream is = this.createInputStream(data);
            final CompletableFuture<WriteApiResponse> writeApiFuture = this.bigQueryTemplate.writeJsonStream(tableName, is);
            final WriteApiResponse apiResponse = writeApiFuture.get();
            if(apiResponse.isSuccessful()) {
                logger.info("Data successfully written to bq");
                return true;
            }
            logger.log(Level.SEVERE, "Error in writing node data to table " + tableName);
            return false;
        } catch (final Exception e) {
            logger.log(Level.SEVERE, "Error in writing node data to table " + tableName + ". Reason - " + e);
        }
        return false;
    }

    public String writeCsvToBq(final String csv, final String tableName) {
        logger.info("Writing CSV data to bq table - " + tableName);
        final CompletableFuture<Job> loadJob = this.bigQueryFileGateway.writeToBigQueryTable(csv.getBytes(), tableName);
        try {
            final Job job = loadJob.get();
            return job.getStatus().toString();
        } catch (final Exception e) {
            logger.log(Level.SEVERE, "Error in writing CSV data to table " + tableName + ". Reason - " + e);
        }
        return "NA";
    }

    public String loadCsvFile(final String tableName, final FileInputStream fis) {
        final CompletableFuture<Job> loadJob = this.bigQueryTemplate.writeDataToTable(tableName, fis,
                FormatOptions.csv());
        try {
            final Job job = loadJob.get();
            return job.getStatus().toString();
        } catch (final Exception e) {
            logger.log(Level.SEVERE, "Error in loading CSV file to table " + tableName + ". Reason - " + e);
        }
        return "NA";
    }

    private InputStream createInputStream(final String data) throws IOException {
        return IOUtils.toInputStream(data, StandardCharsets.UTF_8);
    }
}
