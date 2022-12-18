package com.safetynet.appSafetynet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.appSafetynet.model.Medicalrecords;
import com.safetynet.appSafetynet.service.MedicalrecordsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class AppSafetynetApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppSafetynetApplication.class, args);

	}

	@Bean
	CommandLineRunner runner(MedicalrecordsService medicalrecordsService) {
		return args -> {
			// read json and write to db
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<List<Medicalrecords>> typeReference = new TypeReference<List<Medicalrecords>>() {
			};
			InputStream inputStream = TypeReference.class.getResourceAsStream("/static/data.json");
			try {
				List<Medicalrecords> medicalRecords = mapper.readValue(inputStream, typeReference);
				for (Medicalrecords medicalRecord: medicalRecords
					 ) {
					medicalrecordsService.saveMedicalrecords(medicalRecord);
				}
				System.out.println("Users Saved!");
			} catch (IOException e) {
				System.out.println("Unable to save users: " + e.getMessage());
			}
		};
	}
}