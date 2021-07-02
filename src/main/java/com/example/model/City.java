package com.example.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author mgudelj
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"airports", "id","uuid","description","cityReviews"})
@Table(name = "city", schema = "public")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "city_seq")
    @SequenceGenerator(allocationSize = 1, name = "city_seq", sequenceName = "city_seq_id")
    @Column(name = "id")
    private Long id;

    @Column(name = "uuid", nullable = false, unique = true)
    private UUID uuid;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "country", nullable = false, length = 255)
    private String country;

    @Column(name = "description", nullable = false, length = 255)
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "city")
    private List<CityReview> cityReviews;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "city")
    private List<Airport> airports;

    public City(String name, String country) {
        this.name = name;
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return name.equals(city.name) && country.equals(city.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, country);
    }

    @PrePersist
    public void prePersist() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
    }
}
