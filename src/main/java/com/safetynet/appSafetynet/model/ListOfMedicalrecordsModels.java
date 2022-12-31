package com.safetynet.appSafetynet.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Data
@Component
public class ListOfMedicalrecordsModels {
    List<MedicalrecordsModel> listOfMedicalrecordsModels = new ArrayList<>();
}
