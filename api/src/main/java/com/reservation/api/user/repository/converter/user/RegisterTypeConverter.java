package com.reservation.api.user.repository.converter.user;

import com.reservation.api.user.entity.type.RegisterType;
import com.reservation.common.error.exception.BusinessException;
import com.reservation.common.error.type.BadRequestType;
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
            throw new BusinessException(BadRequestType.INVALID_USER_REGISTER_TYPE_VALUE);
        }

        return registerType.getCode();
    }
}
