package org.example.eventmanagementsystem.mapper;

import org.example.eventmanagementsystem.dto.payment.AddPaymentDto;
import org.example.eventmanagementsystem.dto.payment.PaymentDto;
import org.example.eventmanagementsystem.model.Payment;
import org.example.eventmanagementsystem.model.Ticket;
import org.example.eventmanagementsystem.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "ticket.id", target = "ticketId")
    PaymentDto paymentToPaymentDto(Payment payment);

    @Mapping(source = "userId", target = "user", qualifiedByName = "mapUserIdToUser")
    @Mapping(source = "ticketId", target = "ticket", qualifiedByName = "mapTicketIdToTicket")
    Payment addPaymentDtoToPayment(AddPaymentDto addPaymentDto);

    @Named("mapUserIdToUser")
    default User mapUserIdToUser(Long id) {
        if (id == null) return null;
        User user = new User();
        user.setId(id);
        return user;
    }

    @Named("mapTicketIdToTicket")
    default Ticket mapTicketIdToTicket(Long id) {
        if (id == null) return null;
        Ticket ticket = new Ticket();
        ticket.setId(id);
        return ticket;
    }
}
