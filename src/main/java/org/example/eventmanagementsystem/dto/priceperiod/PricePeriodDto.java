package org.example.eventmanagementsystem.dto.priceperiod;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PricePeriodDto(LocalDateTime startDate, LocalDateTime endDate,
                             BigDecimal price) {
}
