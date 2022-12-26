package com.safetynet.appSafetynet.model;
//persons": [
//        { "firstName":"John", "lastName":"Boyd", "address":"1509 Culver St",
//        "city":"Culver", "zip":"97451", "phone":"841-874-6512", "email":"jaboyd@email.com" },

import lombok.Data;


@Data


public class PersonModel {

    private String firstName;


    private String lastName;

    private String address;
    private String city;

    private String zip;

    private String phone;
    private String email;
    private MedicalrecordsModel medicalrecords;

// utiliser jsoniter pour aller chercher dans le fichier json
    //ne pas toucher à la classe main.(@springboot annotation > il saura quel bean aller chercher)
    // qd dépendance importée, créer une fonction qui permette de lire ça. récup liste personne et mettre. Où mettre ces trois fonctions?
    //enlever crud extend
    // >>changer les repo en classe . au lieu d'avoir une interface repo, remplacer par une classe repo et mettre mes fonctions de récup de données
    // dedans
    //prendre premier url (n° station en paramètre) > dans personneservice , je fais le lien entre l'url et mes données récupéeées dans repo.
    // ensuite dans le contrôleur appeler le service
    //enlever dep H2 et extention CRUD
    //obj pour cette semaine : faire fonction recup données + premier endpoint
}
