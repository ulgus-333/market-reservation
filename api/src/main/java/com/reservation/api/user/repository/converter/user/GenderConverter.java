package com.reservation.api.user.repository.converter.user;

import com.reservation.api.user.entity.type.GenderType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class GenderConverter implements AttributeConverter<GenderType, Integer> {
    @Override
    public GenderType convertToEntityAttribute(Integer value) {
        return GenderType.findByCode(value);
    }

    @Override
    public Integer convertToDatabaseColumn(GenderType genderType) {
        if (genderType == null) {
            throw new RuntimeException("Gender type cannot be null");
        }
        return genderType.getCode();
    }
}
