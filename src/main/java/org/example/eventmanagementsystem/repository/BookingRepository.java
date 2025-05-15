package org.example.eventmanagementsystem.repository;

import org.example.eventmanagementsystem.model.Booking;
import org.example.eventmanagementsystem.model.Event;
import org.example.eventmanagementsystem.model.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {

    Integer countByEventAndBookingStatus(Event event, BookingStatus bookingStatus);

    List<Booking> findTopByEventAndBookingStatusOrderByBookingDateAsc(Event event, BookingStatus bookingStatus);
}
