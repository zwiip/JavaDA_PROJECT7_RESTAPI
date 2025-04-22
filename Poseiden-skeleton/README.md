# Poseiden

## Contexte

*Ce projet est le 7ème de la formation Développement d'applications Java d'OpenClassrooms.*
Poseiden est une application Spring Boot qui, à terme, doit permettre aux utilisateurs de générer des transactions financières.

## Technologies utilisées

- Back-end : Java, Spring Boot (Spring Data JPA, Spring Security).
- Gestion des dépendances : Maven
- Base de données : Hibernate et MySQL pour l'environnement de production, H2 pour les tests.
- Front-end : Thymeleaf pour les vues HTML/CSS dynamiques.

## Configuration
Le projet est paramétré pour créer et peupler automatiquement la base de données grâce à hibernate.
Il vous suffit pour cela de spécifier votre nom d'utilisateur et mot de passe de votre base de données dans le fichier src/main/resources/application.properties :
```
spring.datasource.username=<votre_nom_d'utilisateur>
spring.datasource.password=<votre_mot_de_passe>
```

## Lancement de l'application
Prérequis : avoir spécifié le mot de passe de sa base MySql dans ``application.properties``.
(voir section configuration)

**1. Compilation et lancement**
```
mvn clean install
mvn spring-boot:run
```

**2. Accès à l'application**
Ouvrez un navigateur et accédez à ``http://localhost:8080``

## Tests et rapports

L'application est couverte par des tests afin de vérifier le bon fonctionnement des services.

- Pour lancer les tests : ``mvn test``

- Les logs des tests unitaires échoués ou réussis sont dans le dossier : ``target/surefire-reports`` 

- Les logs des tests d'intégration échoués ou réussis sont dans le dossier : ``/target/failsafe-reports``

- Afin de vérifier le taux de couverture des tests avec JaCoCo, vous pouvez lancer la commande : ``mvn verify``
Note : la couverture des tests comprend les tests unitaires et d'intégration.

- Les rapports JaCoCo seront générés dans le dossier : ``target/site/jacoco/index.html``