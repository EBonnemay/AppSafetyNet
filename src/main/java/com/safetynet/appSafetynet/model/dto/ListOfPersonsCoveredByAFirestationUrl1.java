package com.safetynet.appSafetynet.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/*http://localhost:8080/firestation?stationNumber=<station_number>
Cette url doit retourner une liste des personnes couvertes par la caserne de pompiers correspondante.
Donc, si le numéro de station = 1, elle doit renvoyer les habitants couverts par la station numéro 1. La liste
doit inclure les informations spécifiques suivantes : prénom, nom, adresse, numéro de téléphone. De plus,
elle doit fournir un décompte du nombre d'adultes et du nombre d'enfants (tout individu âgé de 18 ans ou
moins) dans la zone desservie.*/


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListOfPersonsCoveredByAFirestationUrl1 {
    private int numberOfAdults;
    private int numberOfChildren;
    private List <PersonCoveredByAFirestationForUrl1> listOfPersonsCoveredByFirestation = new ArrayList<>();
}
