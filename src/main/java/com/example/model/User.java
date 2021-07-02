package com.example.model;

import com.example.security.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author mgudelj
 */

@Data
@Entity
@NoArgsConstructor
@Table(name = "user", schema = "public")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(allocationSize = 1, name = "user_seq", sequenceName = "user_seq_id")
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name", length = 255)
    private String firstName ;

    @Column(name = "last_name", length = 255)
    private String lastName ;

    @Column(name = "username", length = 255)
    private String username;

    @Column(name = "password", length = 255)
    private String password ;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<CityReview> cityReviews;

    @NotNull
    @Column(name = "role", nullable = false, length = 255)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column(name = "uuid", nullable = false, unique = true)
    private UUID uuid;

    @PrePersist
    public void prePersist() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
    }



}



/*@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet();*/