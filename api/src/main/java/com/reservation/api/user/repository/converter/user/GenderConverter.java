package com.reservation.api.user.repository.converter.user;

import com.reservation.api.user.entity.type.GenderType;
import com.reservation.common.error.exception.BusinessException;
import com.reservation.common.error.type.BadRequestType;
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
            throw new BusinessException(BadRequestType.INVALID_GENDER_TYPE_VALUE);
        }
        return genderType.getCode();
    }
}
