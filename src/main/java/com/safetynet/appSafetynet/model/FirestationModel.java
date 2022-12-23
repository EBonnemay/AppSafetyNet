package com.safetynet.appSafetynet.model;

import lombok.Data;
import org.springframework.stereotype.Component;


@Data
@Component
public class FirestationModel {


        //@javax.persistence.Id
        //@GeneratedValue(strategy = GenerationType.IDENTITY)
        //private long Id;
        private String address;
        private String station;

}

