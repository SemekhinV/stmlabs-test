package ru.ticketapp.carrier.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Carriers")
@Getter @Setter @ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Carrier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    @Column(name = "phone_number", nullable = false)
    String phoneNumber;
}
