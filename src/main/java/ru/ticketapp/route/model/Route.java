package ru.ticketapp.route.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.ticketapp.carrier.model.Carrier;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Routes")
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "departure_point", nullable = false)
    String departurePoint;

    @Column(name = "destination_point", nullable = false)
    String destinationPoint;

    @ManyToOne
    @JoinColumn(name = "carrier_id", nullable = false)
    Carrier carrier;

    @Column(nullable = false)
    Long duration;
}
