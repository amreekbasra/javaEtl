package com.tool.Data.Flow.Engine.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.tool.Data.Flow.Engine.model.DataChunk;
import com.tool.Data.Flow.Engine.model.DataRecord;
import com.tool.Data.Flow.Engine.model.TransformationMapping;
import com.tool.Data.Flow.Engine.service.ETLService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/etl")
@RequiredArgsConstructor
public class ETLController {

    private final ETLService etlService;

    // @PostMapping("/process")
    // public void processChunk(@RequestBody DataChunk chunk, @RequestBody List<TransformationMapping> mappings) {
    //     etlService.processChunk(chunk, mappings);
    // }
     @PostMapping("/process")
    public void processChunk(@RequestBody List<DataRecord> records, @RequestBody List<TransformationMapping> mappings) {
        etlService.processChunk(records, mappings);
    }
}

// singleton: A single instance per Spring container (default).
// prototype: A new instance every time it is requested.
// request: A single instance per HTTP request (web applications only).
// session: A single instance per HTTP session (web applications only).
// application: A single instance per ServletContext (web applications only).
// websocket: A single instance per WebSocket session (web applications only).
