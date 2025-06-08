package com.reservation.api.user.repository.converter.user;

import com.reservation.api.user.entity.type.RegisterType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RegisterTypeConverter implements AttributeConverter<RegisterType, Integer> {

    @Override
    public RegisterType convertToEntityAttribute(Integer value) {
        return RegisterType.findByCode(value);
    }

    @Override
    public Integer convertToDatabaseColumn(RegisterType registerType) {
        if (registerType == null) {
            throw new RuntimeException("registerType cannot be null");
        }

        return registerType.getCode();
    }
}
