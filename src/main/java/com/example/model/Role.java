package com.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author mgudelj
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "role", schema = "public")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
    @SequenceGenerator(allocationSize = 1, name = "user_seq", sequenceName = "role_seq_id")
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 255)
    private String name ;

    @Column(name = "description", length = 255)
    private String description ;

}
