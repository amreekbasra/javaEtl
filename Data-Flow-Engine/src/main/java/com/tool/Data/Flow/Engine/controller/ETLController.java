package com.tool.Data.Flow.Engine.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tool.Data.Flow.Engine.model.DataChunk;
import com.tool.Data.Flow.Engine.model.TransformationMapping;
import com.tool.Data.Flow.Engine.service.ETLService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/etl")
@RequiredArgsConstructor
public class ETLController {

    private final ETLService etlService;

    @PostMapping("/process")
    public void processChunk(@RequestBody DataChunk chunk, @RequestBody List<TransformationMapping> mappings) {
        etlService.processChunk(chunk, mappings);
    }
}
