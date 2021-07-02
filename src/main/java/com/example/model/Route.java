package com.example.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author mgudelj
 */

@Data
@EqualsAndHashCode(exclude = {"price", "id","uuid"})
@Entity
@NoArgsConstructor
@Table(name = "route", schema = "public")
public class Route
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "route_seq")
    @SequenceGenerator(allocationSize = 1, name = "route_seq", sequenceName = "route_seq_id")
    @Column(name = "id")
    private Long id;

    @Column(name = "uuid", nullable = false, unique = true)
    private UUID uuid;

   /* @Column(name = "airline", nullable = false, length = 255)
    private String airline;

    @Column(name = "airline_id", nullable = false, length = 255)
    private String airlineId ;*/

   /* @Column(name = "source_airport_id", nullable = false, length = 255insert="false" update="false")
    private String sourceAirportId;*/

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinColumn(name = "source_airport_id")
    private Airport sourceAirport ;

   /* @Column(name = "target_airport_id", nullable = false, length = 255)
    private String targetAirportId;*/

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinColumn(name = "target_airport_id")
    private Airport targetAirport;

  /*  @Column(name = "codeshare", nullable = false, length = 255)
    private String codeShare ;

    @Column(name = "stops", nullable = false, length = 255)
    private int stops ;

    @Column(name = "equipment", nullable = false, length = 255)
    private String equipment ;*/

    @Column(name = "price", nullable = false, length = 255)
    private double price ;

    @PrePersist
    public void prePersist() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
    }

}
