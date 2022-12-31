package com.safetynet.appSafetynet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication (exclude = {DataSourceAutoConfiguration.class })

public class AppSafetynetApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppSafetynetApplication.class, args);

	}
}
//questions pour Georges
//1 où placer le set up ? (making models, + le début des fichiers repository qui remplissent les arraylists. dans URL unique


//4 un peu perdue dans les beans
//4 le run : problème de persistance des données, pour les url 1 et 2.
// 5 aide moi pour tinyLog ou log4J //log4j, plus tard
//6 les tests : avec mockito ou pas??// pas besoin
//7 tous les endpoints url sont fonctionnels ainsi que les Actuators health, info, trace et metrics ;//log
//8tous les endpoints url enregistrent leurs requêtes et leurs réponses. Les
//réponses réussies sont enregistrées au niveau Info, les erreurs ou exceptions
//sont enregistrées au niveau Erreur, les étapes ou calculs informatifs sont
//enregistrés au niveau Débogage ;
//9 Maven est fonctionnel, exécute des tests unitaires et une couverture de code ;(maven?) une, deux, trois couvertures de code?
// > maven, jacoco, surefire?
//10 ce n'est pas exactement le modèle MVC demandé dans les instructions du projet.Est-ce gênant?