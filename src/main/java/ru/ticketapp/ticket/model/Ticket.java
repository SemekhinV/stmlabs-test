package ru.ticketapp.ticket.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.ticketapp.route.model.Route;
import ru.ticketapp.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Tickets")
@Getter @Setter @ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    User owner;

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    Route route;

    @Column(name = "date_time", nullable = false)
    LocalDateTime dateTime;

    @Column(name = "seat_number", nullable = false)
    Long seatNumber;

    @Column(nullable = false)
    Long price;

    @Column(nullable = false)
    Boolean status;
}
