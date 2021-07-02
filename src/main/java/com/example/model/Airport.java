package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

/**
 * @author mgudelj
 */
@Data
@Builder
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "airport", schema = "public")
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "airport_seq")
    @SequenceGenerator(allocationSize = 1, name = "airport_seq", sequenceName = "airport_seq_id")
    @Column(name = "id")
    private Long id;

    @Column(name = "uuid", nullable = false, unique = true)
    private UUID uuid;

    @Column(name = "airport_id", nullable = false, length = 255)
    private int airportID ;

    @Column(name = "name", nullable = false, length = 255)
    private String name ;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @Column(name = "country", nullable = false, length = 255)
    private String country;

  /*  @Column(name = "iata", nullable = false, length = 255)
    private String iata;

    @Column(name = "icao", nullable = false, length = 255)
    private String icao;

    @Column(name = "longitude", nullable = false, length = 255)
    private double longitude;

    @Column(name = "latitude", nullable = false, length = 255)
    private double latitude;

    @Column(name = "altitude", nullable = false, length = 255)
    private double altitude ;
*/
    /*@OneToMany(fetch = FetchType.LAZY, mappedBy = "sourceAirportID")
    private List<Route> routes;*/
  @PrePersist
  public void prePersist() {
      if (uuid == null) {
          uuid = UUID.randomUUID();
      }
  }

}
