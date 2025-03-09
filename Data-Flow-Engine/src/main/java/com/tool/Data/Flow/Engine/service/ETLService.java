package com.tool.Data.Flow.Engine.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.tool.Data.Flow.Engine.model.ApplicationProperties;
//import com.tool.Data.Flow.Engine.model.DataChunk;
import com.tool.Data.Flow.Engine.model.DataRecord;
import com.tool.Data.Flow.Engine.model.TransformationMapping;
import com.tool.Data.Flow.Engine.repository.DataRecordRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.transaction.annotation.Transactional;
//import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ETLService {

    private final DataRecordRepository dataRecordRepository;
   // private final TransformationService transformationService; single ton changed to prototype
    private final ProgressReportingService progressReportingService;

    @Autowired
    //@Scope("prototype")
    private TransformationService transformationService;
private final ApplicationProperties applicationProperties; // use to get application Level arguements paased
    @Async("taskExecutor")
    @Transactional
    public void processChunk(List<DataRecord> records, List<TransformationMapping> mappings) {        
        // Transform data
        List<DataRecord> transformedRecords = transformationService.transform(records, mappings);

        // Save to database in batch
        dataRecordRepository.saveAll(transformedRecords);

        // Update progress
        progressReportingService.incrementProcessedChunks();

        log.info("Chunk processed and saved to database");
    }
// just for testing
     public void processFile() {
        String filePath = applicationProperties.getFilePath();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Process each line
                // For example, parse the line into DataRecord and process it
            }
        } catch (IOException e) {
            log.error("Error reading file: {}", filePath, e);
        }
    }
}
// public class ETLService {
// private final DataRecordRepository dataRecordRepository;
//     private final TransformationService transformationService;

//     @Async("taskExecutor")
//     public void processChunk(DataChunk chunk, List<TransformationMapping> mappings) {
//         // Parse chunk data
//         List<DataRecord> records = parseChunk(chunk);

//         // Transform data
//         List<DataRecord> transformedRecords = transformationService.transform(records, mappings);

//         // Save to database
//         dataRecordRepository.saveAll(transformedRecords);

//         log.info("Chunk processed and saved to database");
//     }

//     private List<DataRecord> parseChunk(DataChunk chunk) {
//         // Implement parsing logic
//         return List.of(); // Placeholder
//     }
// }
