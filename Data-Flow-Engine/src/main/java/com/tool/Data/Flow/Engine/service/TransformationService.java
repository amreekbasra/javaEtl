package com.tool.Data.Flow.Engine.service;

// import java.util.List;
// import java.util.stream.Collectors;

// import org.springframework.stereotype.Service;

// import com.tool.Data.Flow.Engine.model.DataRecord;
// import com.tool.Data.Flow.Engine.model.TransformationMapping;

// import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.tool.Data.Flow.Engine.model.DataRecord;
import com.tool.Data.Flow.Engine.model.TransformationMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.lang.reflect.Field;

@Service
@Scope("prototype") // this change scop from singleton to prototype new instance everytime
@Slf4j
public class TransformationService {

    // public List<DataRecord> transform(List<DataRecord> records, List<TransformationMapping> mappings) {
    //     // Implement transformation logic
    //     return records; // Placeholder
    // }
     public List<DataRecord> transform1(List<DataRecord> records, List<TransformationMapping> mappings) {
        Map<String, TransformationMapping> mappingMap = mappings.stream()
                .collect(Collectors.toMap(TransformationMapping::getSourceField, mapping -> mapping));

        return records.stream()
                .map(record -> transformRecord1(record, mappingMap))
                .collect(Collectors.toList());
    }

    private DataRecord transformRecord1(DataRecord record, Map<String, TransformationMapping> mappingMap) {
        DataRecord transformedRecord = new DataRecord();

        // Example transformation logic
        if (mappingMap.containsKey("field1")) {
            TransformationMapping mapping = mappingMap.get("field1");
            transformedRecord.setField1(transformField1(record.getField1(), mapping));
        }

        if (mappingMap.containsKey("field2")) {
            TransformationMapping mapping = mappingMap.get("field2");
            transformedRecord.setField2(transformField1(record.getField2(), mapping));
        }

        // Add more fields as necessary

        return transformedRecord;
    }

    private String transformField1(String value, TransformationMapping mapping) {
        // Implement transformation logic based on mapping
        // For example, convert data types, apply formatting, etc.
        // This is a placeholder implementation
        return value;
    }

    //beteer way
    //Transformation Mapping: The TransformationMapping class includes source and destination field names and their types.
//Reflection: The transformRecord method uses reflection to dynamically access and set fields based on the mappings.
//Type Conversion and Validation: The transformField method performs type conversion and validation before setting the destination field.
    public List<DataRecord> transform(List<DataRecord> records, List<TransformationMapping> mappings) {
        Map<String, TransformationMapping> mappingMap = mappings.stream()
                .collect(Collectors.toMap(TransformationMapping::getSourceField, mapping -> mapping));

        return records.stream()
                .map(record -> transformRecord(record, mappingMap))
                .collect(Collectors.toList());
    }

    private DataRecord transformRecord(DataRecord record, Map<String, TransformationMapping> mappingMap) {
        DataRecord transformedRecord = new DataRecord();

        for (Map.Entry<String, TransformationMapping> entry : mappingMap.entrySet()) {
            String sourceField = entry.getKey();
            TransformationMapping mapping = entry.getValue();

            try {
                Field sourceFieldObj = DataRecord.class.getDeclaredField(sourceField);
                sourceFieldObj.setAccessible(true);
                Object sourceValue = sourceFieldObj.get(record);

                Object transformedValue = transformField(sourceValue, mapping);

                Field destinationFieldObj = DataRecord.class.getDeclaredField(mapping.getDestinationField());
                destinationFieldObj.setAccessible(true);
                destinationFieldObj.set(transformedRecord, transformedValue);

            } catch (NoSuchFieldException | IllegalAccessException e) {
                log.error("Error transforming field: {}", sourceField, e);
            }
        }

        return transformedRecord;
    }

    private Object transformField(Object value, TransformationMapping mapping) {
        if (value == null) {
            return null;
        }

        try {
            switch (mapping.getDestinationType()) {
                case "String":
                    return value.toString();
                case "Integer":
                    return Integer.parseInt(value.toString());
                case "Double":
                    return Double.parseDouble(value.toString());
                case "Boolean":
                    return Boolean.parseBoolean(value.toString());
                // Add more type conversions as needed
                default:
                    throw new IllegalArgumentException("Unsupported destination type: " + mapping.getDestinationType());
            }
        } catch (Exception e) {
            log.error("Error converting value: {} to type: {}", value, mapping.getDestinationType(), e);
            throw new IllegalArgumentException("Error converting value: " + value + " to type: " + mapping.getDestinationType(), e);
        }
    }
}