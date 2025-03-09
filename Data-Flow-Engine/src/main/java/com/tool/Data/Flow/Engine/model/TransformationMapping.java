package com.tool.Data.Flow.Engine.model;

import lombok.Data;

@Data
public class TransformationMapping {
    private String sourceField;
    private String sourceType;
    private String destinationField;
    private String destinationType;
}
