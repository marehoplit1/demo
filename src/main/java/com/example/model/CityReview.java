package com.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author mgudelj
 */
@Data
@Entity
@NoArgsConstructor
@ToString(exclude = {"city","user"})
@Table(name = "city_review", schema = "public")
public class CityReview {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "city_review_seq")
    @SequenceGenerator(allocationSize = 1, name = "city_review_seq", sequenceName = "city_review_seq_id")
    @Column(name = "id")
    private Long id;

    @Column(name = "uuid", nullable = false, unique = true)
    private UUID uuid;

    @Column(name = "content", nullable = false, length = 255)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @PrePersist
    public void prePersist() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
    }
}
