package com.tool.Data.Flow.Engine.service;

import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.tool.Data.Flow.Engine.model.DataChunk;
import com.tool.Data.Flow.Engine.model.DataRecord;
import com.tool.Data.Flow.Engine.model.TransformationMapping;
import com.tool.Data.Flow.Engine.repository.DataRecordRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ETLService {
private final DataRecordRepository dataRecordRepository;
    private final TransformationService transformationService;

    @Async("taskExecutor")
    public void processChunk(DataChunk chunk, List<TransformationMapping> mappings) {
        // Parse chunk data
        List<DataRecord> records = parseChunk(chunk);

        // Transform data
        List<DataRecord> transformedRecords = transformationService.transform(records, mappings);

        // Save to database
        dataRecordRepository.saveAll(transformedRecords);

        log.info("Chunk processed and saved to database");
    }

    private List<DataRecord> parseChunk(DataChunk chunk) {
        // Implement parsing logic
        return List.of(); // Placeholder
    }
}
