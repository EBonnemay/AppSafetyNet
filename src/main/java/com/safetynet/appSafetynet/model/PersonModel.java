package com.safetynet.appSafetynet.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class PersonModel {
    private String firstName;


    private String lastName;

    private String address;
    private String city;

    private String zip;

    private String phone;
    private String email;

}
