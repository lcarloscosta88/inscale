package com.inscale.test.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person")
public class Person implements Serializable {

    @Serial
    private static final long serialVersionUID = 2067508366270213585L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "person", cascade = CascadeType.MERGE, orphanRemoval = true)
    private Set<Address> address;

    @NotNull
    @Column(name = "number")
    private String number;

    @NotNull
    @Column(name = "identification_number")
    private String identificationNumber;

}
