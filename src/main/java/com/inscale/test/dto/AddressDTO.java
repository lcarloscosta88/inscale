package com.inscale.test.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private Long id;
    private String address;
    private String number;
    private String city;
    private String country;
    private Boolean type;

}
