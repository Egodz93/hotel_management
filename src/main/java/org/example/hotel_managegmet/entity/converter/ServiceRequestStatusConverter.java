package org.example.hotel_managegmet.entity.converter;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.example.hotel_managegmet.entity.enums.ServiceRequestStatus;
@Converter(autoApply = true)
public class ServiceRequestStatusConverter implements AttributeConverter<ServiceRequestStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ServiceRequestStatus s) {
        return s != null ? s.getCode() : null;
    }

    @Override
    public ServiceRequestStatus convertToEntityAttribute(Integer c) {
        return c != null ? ServiceRequestStatus.fromCode(c) : null;
    }
}
