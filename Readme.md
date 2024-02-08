# Projet UwU
Le projet TinyX Subject est une application de micro-blogging social qui permet aux utilisateurs de :
- créer un compte
- publier des entrées de blog
- liker les entrées d'autres utilisateurs
- afficher les entrées likées par eux-mêmes ou leur réseau social
- s'abonner aux chaînes d'autres utilisateurs
- rechercher du contenu et d'autres utilisateurs
 
Les fonctionnalités sont accessibles via des endpoints REST et le système interne de communication est librement organisé.

## Tester le projet avec docker compose
(en mode rapide UwU)

```bash
cd docker
docker compose -f docker-compose.all.yml up -d
```

##  L'architecture à respecter (pour une homogénéité)
Pour ce projet nous sommes tombé d'accord pour utiliser l'architecture trois tiers car nous avions deja tous developpé un projet selon cette architecture.

```
src/main/java/com/epita/
├── data/
│   ├── model/              // Définition des modèles de données
│   │   └── Model.java
│   ├── repository/         // Classes de gestion des données
│   │   └── Repository.java
├── domain/
│   ├── entity/             // Abstraction des modèles de données
│   │   └── Entity.java
│   ├── service/            // Classes de logique métier
│   │   └── Service.java 
├── presentation/
│   ├── request/            // Classes pour gérer les requêtes entrantes
│   │   └── Request.java
│   ├── response/           // Classes pour formater les réponses sortantes
│   │   └── Response.java
│   └── Controller.java     // Définition des endpoints de l'API
└── utils/
    ├── converters/         // Classes de convertion des différentes représentation des données
    │   ├── EntityToResponse.java
    │   └── ModelToEntity.java
    └── errors/             // Classes de gestion d'erreur
        ├── ErrorCode.java
        └── RestError.java 
```

## Swagger des services

Un Swagger contenant toutes les API est automatiquement généré à l'adresse suivante :

[Les swaggers des services sont disponibles ici](https://warg-group.gitlab.io/s9-tinyx).

## Le front-end

Nous avons fait le choix d'implementer un front-end, en vue js, afin de finaliser le projet et pouvoir tester facilement.

[Documentation détaillée](./front-end-vue/Readme.md)

---
## Les services

### repo-post

Ce service gère la création, la récupération, la mise à jour et la suppression de publications sur la plateforme. Il stocke et organise les publications dans la base de données.

[Documentation détaillée](./repo-post/Readme.md)


### repo-social

Ce service gère les relations sociales entre utilisateurs, y compris les fonctionnalités de suivi et d'amitié. Il stocke les informations sur les abonnements et les amis dans la base de données.

[Documentation détaillée](./repo-social/Readme.md)

### srvc-user-timeline

Ce service permet de générer la timeline d'un utilisateur en fonction de ses propres publications et de celles des utilisateurs qu'il suit.

[Documentation détaillée](./srvc-user-timeline/Readme.md)


### srvc-search

Service de recherche permettant aux utilisateurs de rechercher des publications, des utilisateurs et des hashtags spécifiques sur la plateforme.

[Documentation détaillée](./srvc-search/Readme.md)

---

