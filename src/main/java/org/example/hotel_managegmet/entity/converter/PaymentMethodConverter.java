package org.example.hotel_managegmet.entity.converter;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.example.hotel_managegmet.entity.enums.PaymentMethod;
@Converter(autoApply = true)
public class PaymentMethodConverter implements AttributeConverter<PaymentMethod, Integer> {
    @Override
    public Integer convertToDatabaseColumn(PaymentMethod m) {
        return m != null ? m.getCode() : null;
    }

    @Override
    public PaymentMethod convertToEntityAttribute(Integer c) {
        return c != null ? PaymentMethod.fromCode(c) : null;
    }
}
