package com.safetynet.appSafetynet.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "firestation")
public class Firestation {


        @javax.persistence.Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long Id;
        private String address;
        private String station;

}

