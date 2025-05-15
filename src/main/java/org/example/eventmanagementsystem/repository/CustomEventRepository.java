package org.example.eventmanagementsystem.repository;

import org.example.eventmanagementsystem.model.Event;

import java.math.BigDecimal;
import java.util.List;

public interface CustomEventRepository {
    List<Event> search(String name, String date, String category, String location,
                       BigDecimal minPrice, BigDecimal maxPrice);
}
