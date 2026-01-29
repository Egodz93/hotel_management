package org.example.hotel_managegmet.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.example.hotel_managegmet.entity.enums.RoomStatus;

@Converter(autoApply = true)
public class RoomStatusConverter implements AttributeConverter<RoomStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(RoomStatus status) {
        return (status != null) ? status.getCode() : null;
    }

    @Override
    public RoomStatus convertToEntityAttribute(Integer code) {
        return (code != null) ? RoomStatus.fromCode(code) : null;
    }
}
