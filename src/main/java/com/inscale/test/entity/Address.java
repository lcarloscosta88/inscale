package com.inscale.test.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address")
public class Address implements Serializable {

    @Serial
    private static final long serialVersionUID = 7164916926026112502L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @Column(name = "address")
    private String address;

    @NotNull
    @Column(name = "number")
    private String number;

    @NotNull
    @Column(name = "city")
    private String city;

    @NotNull
    @Column(name = "country")
    private String country;

    @NotNull
    @Column(name = "type")
    private Boolean type;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "personId", referencedColumnName = "id")
    private Person person;

}
