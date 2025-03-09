package com.tool.Data.Flow.Engine.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tool.Data.Flow.Engine.service.ProgressReportingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/progress")
@RequiredArgsConstructor
@Slf4j
public class ProgressController {
    private final ProgressReportingService progressReportingService;

    @GetMapping
    public String getProgress() {
        try {
            return progressReportingService.getProgress();
        } catch (Exception ex) {
            log.error("Error getting progress", ex);
            throw ex; // Let the global exception handler handle it
        }
    }
    // private final ProgressReportingService progressReportingService;

    // @GetMapping
    // public String getProgress() {
       
    //     try {
    //         return progressReportingService.getProgress();
    //     } catch (Exception ex) {
    //         log.error("Error getting progress", ex);
    //         throw ex; // Let the global exception handler handle it
    //     }
    // }
    // public void exampleMethod() {
    //     log.info("This is an info message");
    //     log.debug("This is a debug message");
    //     log.error("This is an error message");
    // } 
}
