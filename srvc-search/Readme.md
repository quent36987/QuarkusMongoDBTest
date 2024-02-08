## srvc-search

### Description

Le service de recherche srvc-search que vous décrivez semble être responsable de l'indexation des publications (posts) créées dans votre système et de la recherche de ces publications en fonction des critères de recherche fournis par l'utilisateur.

Utilisation de elastic search ?

### Model ou document ?

post_id, text, media, uder_id


### Indexation des publications

- **Description** : Indexe une nouvelle publication dans Elasticsearch.
  channel : create-post
  Redis avec un document : post_id, text, media, uder_id

### Suppression d'une publication de l'index

- **Description** : Supprime une publication de l'index Elasticsearch.
  chanel: delete-post
  redis aussi avec un document qui possède un post_id

### Recherche de publications

- **Description** : Recherche des publications en fonction des critères spécifiés.
- **Méthode HTTP** : GET
- **Chemin** : `/api/posts/search`
- **Paramètres de requête** :
    - `keywords` : Mots-clés de recherche réguliers (séparés par des espaces).
    - `hashtags` : Hashtags de recherche (séparés par des virgules).
- **Réponse attendue** : Liste des publications correspondant aux critères de recherche.
- **Exemple de requête** :
  ```
  GET /api/posts/search?keywords=motcle1%20motcle2&hashtags=tag1,tag2
  ```
    - **Réponse attendue** :
```json
[
    {
        "post_id": "65b7caad0f17e55938b11e90"
        "text": "motcle2 tag1 peu motcle1 être tag2",
        "media": ""
    },
    {
        "post_id": "65b7caad0dfdf17e55938b11e90"
        "text": "motcle1 tag1 peu pour être tag2",
        "media": ""
    }
    
]
```