package org.example.hotel_managegmet.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.example.hotel_managegmet.entity.enums.ReservationStatus;

@Converter(autoApply = true)
public class ReservationStatusConverter implements AttributeConverter<ReservationStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ReservationStatus status) {
        return (status != null) ? status.getCode() : null;
    }

    @Override
    public ReservationStatus convertToEntityAttribute(Integer code) {
        return (code != null) ? ReservationStatus.fromCode(code) : null;
    }
}
