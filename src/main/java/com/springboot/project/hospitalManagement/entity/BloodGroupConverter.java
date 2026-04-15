package com.springboot.project.hospitalManagement.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BloodGroupConverter implements AttributeConverter<BloodGroup, String> {

    @Override
    public String convertToDatabaseColumn(BloodGroup attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public BloodGroup convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        for (BloodGroup bg : BloodGroup.values()) {
            if (bg.getValue().equals(dbData)) {
                return bg;
            }
        }
        throw new IllegalArgumentException("Unknown database value: " + dbData);
    }
}
