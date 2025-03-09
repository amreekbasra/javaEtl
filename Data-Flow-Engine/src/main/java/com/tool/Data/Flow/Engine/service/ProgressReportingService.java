package com.tool.Data.Flow.Engine.service;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProgressReportingService {

    public String getProgress1() {
        log.info("This is an info message");
        log.debug("This is a debug message");
        log.error("This is an error message");
        // Implement progress reporting logic
        return "Progress: 50%"; // Placeholder
    }

     private final AtomicInteger totalChunks = new AtomicInteger(0);
    private final AtomicInteger processedChunks = new AtomicInteger(0);

    public void setTotalChunks(int total) {
        totalChunks.set(total);
    }

    public void incrementProcessedChunks() {
        processedChunks.incrementAndGet();
    }

    public String getProgress() {
        int total = totalChunks.get();
        int processed = processedChunks.get();
        if (total == 0) {
            return "No chunks to process.";
        }
        int progress = (processed * 100) / total;
        return "Progress: " + progress + "% (" + processed + " out of " + total + " chunks processed)";
    }
}
