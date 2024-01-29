# Projet UwU

##  L'architecture à respecter (pour une homogénéité)

```scss
src/
├── model/
│   └── MonModel.java   // Définition du modèle de données
├── repository/         // Contient les classes de gestion des données
|   └── MonModelRepository.java 
├── service/           // Contient les classes de logique métier
|   └── MonModelService.java 
└── controller/
    ├── RequestBody/    // Contient les classes pour gérer les requêtes entrantes
    │   └── ...
    ├── ResponseBody/   // Contient les classes pour formater les réponses sortantes
    │   └── ...
    └── Endpoint.java   // Définition des endpoints de l'API et leur logique associée
```



## repo-post

### Description:

Le but de se services est de stocker les posts des utilisateurs.
Il utilisera MongoDB.

### Models:

```json
post :

{
  "id": "string",
  "text": "string",
  "media": "string",
  "replyPostId": "string",
  "createdAt": "string (format ISO 8601)",
  "userId": "string",
  "repostId": "string"
}
```

### Les Endpoints:

### Posts

- **GET /api/posts**
  - **Description** : Récupère la liste de tous les posts.
  - **Reponse code** :
    - HTTP 200 : La requête a réussi.
  - **Réponse attendue** : 
    ```json
    [
      {
        "id": "123456789",
        "text": "Ceci est le contenu du premier post.",
        "media": "http://example.com/media/image.jpg",
        "replyPostId": "987654321",
        "createdAt": "2024-01-25T12:00:00Z",
        "userId": "uuid",
        "repostId": null
      },
      {
        "id": "987654321",
        "text": "Ceci est le contenu du deuxième post.",
        "media": null,
        "replyPostId": null,
        "createdAt": "2024-01-26T09:30:00Z",
        "userId": "uuid2",
        "repostId": null
      }
    ]
    ```
    (FIXME: ajout d'un système de page car on peut pas get un millard de post d'un coup ^^)

- **GET /api/posts/{post_id}**
  - **Description** : Récupère les détails d'un post spécifique.
  - **Paramètres de requête** : `{post_id}` - L'identifiant unique du post.
  - **Reponse code** :
    - HTTP 200 : La requête a réussi.
    - HTTP 400 : Post_id est null ou invalide.
    - HTTP 404 : Post non trouvé.
  - **Réponse attendue** : 
    ```json
    {
      "id": "{post_id}",
      "text": "Ceci est le contenu du post.",
      "media": null,
      "replyPostId": "987654321",
      "createdAt": "2024-01-25T12:00:00Z",
      "userId": "uuid",
      "repostId": null
    }
    ```

- **POST /api/posts**
  - **Description** : Crée un nouveau post.
  - **Corps JSON attendu** :
    ```json
    {
      "text": "Contenu du nouveau post.",
      "media": "http://example.com/media/image.jpg",
    }
    ```
  - **Reponse code** :
    - HTTP 201 : Post créé avec succès.
    - HTTP 400 : Champ invalide (text > 160 caratères.
    - HTTP 404 : Header X-user-id vide ou introuvable dans les users.
  - **Request headers** :
    - X-user-id : UUID, l'identifiant d'un utilisateur (le client).
  - **Réponse attendue** : 
    ```json
    {
      "id": "new_post_id",
      "text": "Contenu du nouveau post.",
      "media": "http://example.com/media/image.jpg",
      "replyPostId": null,
      "createdAt": "2024-01-27T15:45:00Z",
      "userId": "uuid",
      "repostId": null
    }
    ```


- **DELETE /api/posts/{post_id}**
  - **Description** : Supprime un post existant.
  - **Paramètres de requête** : `{post_id}` - L'identifiant unique du post à supprimer.
  - **Request headers** :
    - X-user-id : UUID, l'identifiant d'un utilisateur (le client).
  - **Reponse code** :
    - HTTP 204 : La suppression a réussi.
    - HTTP 400 : Post n'appartient pas a user
    - HTTP 404 : Post non trouvé.

### Réponses

- **GET /api/posts/{post_id}/replies**
  - **Description** : Récupère les réponses à un post spécifique.
  - **Paramètres de requête** : `{post_id}` - L'identifiant unique du post pour lequel les réponses doivent être récupérées.
  - **Reponse code** :
    - HTTP 200 : La requête a réussi.
    - HTTP 404 : Post non trouvé.
    - HTTP 400 : post_id null
  - **Réponse attendue** : 
    ```json
    [
      {
        "id": "reply_id_1",
        "text": "Ceci est une réponse au post.",
        "media": null,
        "replyPostId": "{post_id}",
        "createdAt": "2024-01-28T10:00:00Z",
        "userId": "uuid_reply_user",
        "repostId": null
      },
      {
        "id": "reply_id_2",
        "text": "Une autre réponse au post.",
        "media": "http://example.com/media/image.jpg",
        "replyPostId": "{post_id}",
        "createdAt": "2024-01-28T11:00:00Z",
        "userId": "uuid_reply_user2",
        "repostId": null
      }
    ]
    ```

- **POST /api/posts/{post_id}/replies**
  - **Description** : Répond à un post existant.
  - **Paramètres de requête** : `{post_id}` - L'identifiant unique du post auquel répondre.
  - **Request headers** :
    - X-user-id : UUID, l'identifiant d'un utilisateur (le client).
  - **Corps JSON attendu** :
    ```json
    {
      "text": "Contenu de la nouvelle réponse.",
      "media": "http://example.com/media/new_reply_image.jpg",
    }
    ```
  - **Reponse code** :
    - HTTP 201 : Réponse ajoutée avec succès.
    - HTTP 404 : Header X-user-id vide ou introuvable dans les users.
    - HTTP 400 : Champ invalide.
  - **Réponse attendue** : 
    ```json
    {
      "id": "new_reply_id",
      "text": "Nouvelle réponse ajoutée avec succès.",
      "media":"http://example.com/media/new_reply_image.jpg",
      "replyPostId": "{post_id}",
      "createdAt": "2024-01-28T12:00:00Z",
      "userId": "uuid_reply_user",
      "repostId": null
    }
    ```

### Reposts

- **POST /api/posts/{post_id}/reposts**
  - **Description** : Reposte un post existant.
  - **Paramètres de requête** : `{post_id}` - L'identifiant unique du post à reposter.
  - **Corps JSON attendu** : Aucun (pas de corps nécessaire).
  - **Request headers** :
    - X-user-id : UUID, l'identifiant d'un utilisateur (le client).
  - **Reponse code** :
    - HTTP 201 : Repost créé avec succès.
    - HTTP 404 : Header X-user-id vide ou introuvable dans les users.
    - HTTP 404 : Post non trouvé.
  - **Réponse attendue** : 
    ```json
       {
          "id": "new_repost_id",
          "text": "copy blabla anciien post.",
          "media": null,
          "replyPostId": null,
          "createdAt": "2024-01-28T13:00:00Z",
          "userId": "uuid_repost_user",
          "repostId": "{post_id}"
        }
    ```
    
## repo-social

### Description:

Le but de se services est de gérer les lien entre les utilisateur et de le stocker dans mongoDB (il envera sur des files redis de facon asyncrone pour les autres services plus tard j'imagine)

Il utilisera MongoDB.

### Models:

```json
follow :
{
  "userId": "string", // userId follow followId(user2Id)
  "followId": "string", 
  "followAt" : "string (format ISO 8601)",
}

block:
{
    "userId" : "string",
    "blockId": "string",
    "blockAt": "string (format ISO 8601)",
}

like:
{
    "userId" :"string"
    "postId": "string",
    "like": "boolean" // like = true, unlike = false
    "likeAt": "string (format ISO 8601)",
}

```


### Les Endpoints:

### Aimer/ne plus aimer un post d'un utilisateur qui n'a pas de relation de blocage avec eux

- **POST /api/posts/{post_id}/like**
  - **Description** : Aimer un post.
  - **Paramètres de requête** : `{post_id}` - L'identifiant unique du post à aimer.
  - **En-têtes de la requête** :
    - X-user-id : UUID, l'identifiant de l'utilisateur aimant (le client).
  - **Codes de réponse** :
    - HTTP 200 : Aimer réussi.
    - HTTP 404 : En-tête X-user-id vide ou non trouvé chez les utilisateurs.
    - HTTP 404 : Post non trouvé.

- **POST /api/posts/{post_id}/unlike**
  - **Description** : ne pas aimer un post.
  - **Paramètres de requête** : `{post_id}` - L'identifiant unique du post à aimer.
  - **En-têtes de la requête** :
    - X-user-id : UUID, l'identifiant de l'utilisateur non aimant (le client).
  - **Codes de réponse** :
    - HTTP 200 :  non Aimer réussi.
    - HTTP 404 : En-tête X-user-id vide ou non trouvé chez les utilisateurs.
    - HTTP 404 : Post non trouvé.

- **DELETE /api/posts/{post_id}/like**
  - **Description** : Ne plus aimer un post.
  - **Paramètres de requête** : `{post_id}` - L'identifiant unique du post à ne plus aimer.
  - **En-têtes de la requête** :
    - X-user-id : UUID, l'identifiant de l'utilisateur n'aimant plus (le client).
  - **Codes de réponse** :
    - HTTP 200 : Ne plus aimer réussi.
    - HTTP 404 : En-tête X-user-id vide ou non trouvé chez les utilisateurs.
    - HTTP 404 : Post non trouvé.

- **DELETE /api/posts/{post_id}/unlike**
  - **Description** : Ne plus plus aimer un post 
  - **Paramètres de requête** : `{post_id}` - L'identifiant unique du post à ne plus plus aimer.
  - **En-têtes de la requête** :
    - X-user-id : UUID, l'identifiant de l'utilisateur n'aimant plus (le client).
  - **Codes de réponse** :
    - HTTP 200 : Ne plus plus aimer réussi.
    - HTTP 404 : En-tête X-user-id vide ou non trouvé chez les utilisateurs.
    - HTTP 404 : Post non trouvé.
    - 
### Suivre/Ne plus suivre un utilisateur avec qui ils n'ont pas de relation de blocage

- **POST /api/users/{user_id}/follow**
  - **Description**: Suivre un utilisateur.
  - **Paramètres de chemin** : `{user_id}` - L'identifiant unique de l'utilisateur à suivre.
  - **En-têtes de la requête**:
    - X-user-id : UUID, l'identifiant de l'utilisateur qui suit (le client).
  - **Codes de réponse**:
    - HTTP 200 : Suivi réussi.
    - HTTP 403 : L'utilisateur nous a bloqué
    - HTTP 404 : user_id inconnu ou X-user-id inconnu user_id vide ou pas de X-user-id  

- **DELETE /api/users/{user_id}/follow**
  - **Description**: Ne plus suivre un utilisateur.
  - **Paramètres de chemin** : `{user_id}` - L'identifiant unique de l'utilisateur à ne plus suivre.
  - **En-têtes de la requête**:
    - X-user-id : UUID, l'identifiant de l'utilisateur qui ne suit plus (le client).
  - **Codes de réponse**:
    - HTTP 200 : Ne plus suivre réussi.
    - HTTP 404 : user_id inconnu ou X-user-id inconnu 
    - HTTP 400 : user_id vide ou pas de X-user-id 

### Bloquer/Débloquer un utilisateur

- **POST /api/users/{user_id}/block**
  - **Description**: Bloquer un utilisateur.
  - **Paramètres de chemin** : `{user_id}` - L'identifiant unique de l'utilisateur à bloquer.
  - **En-têtes de la requête**:
    - X-user-id : UUID, l'identifiant de l'utilisateur bloquant (le client).
  - **Codes de réponse**:
    - HTTP 200 : Blocage réussi.
    - HTTP 400 : user_id vide ou pas de X-user-id 
    - HTTP 403: deja bloquer

- **DELETE /api/users/{user_id}/block**
  - **Description**: Débloquer un utilisateur.
  - **Paramètres de chemin** : `{user_id}` - L'identifiant unique de l'utilisateur à débloquer.
  - **En-têtes de la requête**:
    - X-user-id : UUID, l'identifiant de l'utilisateur débloquant (le client).
  - **Codes de réponse**:
    - HTTP 200 : Déblocage réussi.
    - HTTP 404 : user_id inconnu ou X-user-id inconnu 
    - HTTP 400 : user_id vide ou pas de X-user-id 


### Requérir les posts aimés par un utilisateur

- **GET /api/posts/{post_id}/likes**
  - **Description** : Obtenir les utilisateurs ayant aimé un post.
  - **Paramètres de chemin** : `{post_id}` - L'identifiant unique du post.
  - **Codes de réponse**:
    - HTTP 200 : Réponse réussie.
    - HTTP 404 : post_id inconnu ou X-user-id inconnu 
    - HTTP 400 : post_id vide ou pas de X-user-id
  - **Réponse attendue** : 
    ```json
    [
      {
        "userId": "fdsgdfgqdfgdqfh",
        "likeAt": "2024-01-28T10:00:00Z"
      },
      {
        "userId": "asdef",
        "likeAt": "2024-01-28T11:00:00Z"
      }
    ]
    ```
    
### Requérir les posts non aimés par un utilisateur

- **GET /api/posts/{post_id}/unlikes**
  - **Description** : Obtenir les utilisateurs n'ayant pas aimé un post.
  - **Paramètres de chemin** : `{post_id}` - L'identifiant unique du post.
  - **Codes de réponse**:
    - HTTP 200 : Réponse réussie.
    - HTTP 404 : post_id inconnu ou X-user-id inconnu 
    - HTTP 400 : post_id vide ou pas de X-user-id
  - **Réponse attendue** : 
    ```json
    [
      {
        "userId": "fsdfsdgfdfgdqfh",
        "unlikeAt": "2024-01-28T10:00:00Z"
      },
      {
        "userId": "tyertery",
        "unlikeAt": "2024-01-28T11:00:00Z"
      }
    ]
    ```

### Requérir les utilisateurs suivis par un utilisateur

- **GET /api/users/{user_id}/follows**
  - **Description**: Obtenir les utilisateurs suivis par un utilisateur.
  - **Paramètres de chemin** : `{user_id}` - L'identifiant unique de l'utilisateur.
  - **Codes de réponse**:
    - HTTP 200 : Réponse réussie.
    - HTTP 400 : user_id vide ou pas de X-user-id
  - **Réponse attendue** : Un tableau JSON contenant les utilisateurs suivis par l'utilisateur spécifié avec la date de suivi.
    ```json
    [
      {
        "id":
        "userId": "{user_id}",
        "followId": "fgdsfg"
        "followedAt": "2024-01-30T08:00:00Z"
      },
      {
        "id":
        "userId": "{user_id}",
        "followId": "sfgdfgdfg"
        "followedAt": "2024-01-30T09:00:00Z"
      }
    ]
    ```

### Requérir les followers d'un utilisateur

- **GET /api/users/{user_id}/followers**
  - **Description**: Obtenir les utilisateurs suivant un utilisateur.
  - **Paramètres de chemin** : `{user_id}` - L'identifiant unique de l'utilisateur.
  - **Codes de réponse**:
    - HTTP 200 : Réponse réussie.
    - HTTP 400 : user_id vide ou pas de X-user-id
  - **Réponse attendue** : Un tableau JSON contenant les utilisateurs qui suivent l'utilisateur spécifié avec la date de suivi.
    ```json
    [
      {
        "id":
        "followId": "{user_id}",
        "userId": "fgdsfg"
        "followedAt": "2024-01-30T08:00:00Z"
      },
      {
        "id":
        "followId": "{user_id}",
        "userId": "sfgdfgdfg"
        "followedAt": "2024-01-30T09:00:00Z"
      }
    ]
    ```

### Requérir la liste des utilisateurs bloqués par un utilisateur

- **GET /api/users/{user_id}/block-list**
  - **Description**: Obtenir les utilisateurs bloqués par un utilisateur.
  - **Paramètres de chemin** : `{user_id}` - L'identifiant unique de l'utilisateur.
  - **Codes de réponse**:
    - HTTP 200 : Réponse réussie.
    - HTTP 400 : user_id vide ou pas de X-user-id
  - **Réponse attendue** : Un tableau JSON contenant les utilisateurs bloqués par l'utilisateur spécifié avec la date de blocage.
    ```json
    [
      {
           "id":
        "userId": "{user_id}",
          "blockId" : "dfdfdf"
        "blockAt": "2024-01-30T12:00:00Z"
      },
      {
           "id":
        "userId": "{user_id}",
          "blockId" : "dfdfdfff"
          "blockAt": "2024-01-30T13:00:00Z"
      }
    ]
    ```
#### Requérir la liste des utilisateurs qui ont bloqué un utilisateur

- **GET /api/users/{user_id}/blocked-by**
  - **Description**: Obtenir les utilisateurs qui ont bloqué un utilisateur.
  - **Paramètres de chemin** : `{user_id}` - L'identifiant unique de l'utilisateur.
  - **Codes de réponse**:
    - HTTP 200 : Réponse réussie.
    - HTTP 400 : user_id vide ou pas de X-user-id 
  - **Réponse attendue** : Un tableau JSON contenant les utilisateurs bloqués par l'utilisateur spécifié avec la date de blocage.
    ```json
    [
      {
           "id":
        "userId": "111",
          "blockId": "{user_id}"
        "blockAt": "2024-01-30T12:00:00Z"
      },
      {
           "id":
        "userId": "222",
          "blockId": "{user_id}"
        "blockAt": "2024-01-30T13:00:00Z"
      }
    ]
    ```


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

## srvc-user-timeline

### FIXME ?

- besoin du postModel, pk pas postId ?
- système de page car ca fait bc de truc  a get ^^

### Description

"srvc-user-timeline", est conçu pour fournir une chronologie des activités liées à un utilisateur spécifique.

### Model

document.id = user_id
document = 
```json
{
  "actions": [
      {
        "post": {
            "id": "987654321",
            "text": "Ceci est le contenu du deuxième post.",
            "media": null,
            "replyPostId": null,
            "createdAt": "2024-01-26T09:30:00Z",
            "userId": "uuid2",
            "repostId": null
        },
        "action": "create|delete|reply|repost|like|unlike|deleteLike|deleteUnlike|" ,
        "actionAt": "string (format ISO 8601)"
      }
  ]
}
```

### Ajout d'un document
On ajoute un post si l'utilisateur a écrit, reply, repost, like un post.


Redis channel : add-action 
model message : 
```json
{
    "userId": "",
    "post": {PostModel},
    "action": "create|delete|reply|repost|like|unlike|deleteLike|deleteUnlike|" ,
    "actionAt" : "date"
}
```

apres reception, actions.add(...) au document. "actionAt" : "date"id=user_id 

### Endpoint


#### GET /api/users/{user_id}/timeline
- **Description**: Obtenir la timeline d'un utilisateur spécifique, contenant les posts qu'il a créés et ceux qu'il a aimés.
- **Paramètres de chemin** : `{user_id}` - L'identifiant unique de l'utilisateur.
- **En-têtes de la requête** :
  - X-user-id : UUID, l'identifiant de l'utilisateur dont on veut obtenir la timeline.
- **Codes de réponse**:
  - HTTP 200 : Réponse réussie.
  - HTTP 400 : user_id vide ou non spécifié dans l'en-tête X-user-id.
- **Réponse attendue** : Un tableau JSON contenant les posts de la timeline de l'utilisateur spécifié.
  ```json
  [
      {
        "post": {
            "id": "987654321",
            "text": "Ceci est le contenu du deuxième post.",
            "media": null,
            "replyPostId": null,
            "createdAt": "2024-01-26T09:30:00Z",
            "userId": "uuid2",
            "repostId": null
        },
        "action": "create" ,
        "actionAt" : "date"
      },
      {
        "post": {
            "id": "987cc654321",
            "text": "Ceci est le contenu du dccd post.",
            "media": null,
            "replyPostId": null,
            "createdAt": "2024-01-26T09:30:00Z",
            "userId": "uuid2",
            "repostId": null
        },
        "action": "like" ,
        "actionAt" : "date"
      }
  ]
  ```

#### GET /api/users/{user_id}/posts
- **Description**: Obtenir les posts créés par un utilisateur spécifique.
- **Paramètres de chemin** : `{user_id}` - L'identifiant unique de l'utilisateur.
- **En-têtes de la requête** :
  - X-user-id : UUID, l'identifiant de l'utilisateur dont on veut obtenir les posts.
- **Codes de réponse**:
  - HTTP 200 : Réponse réussie.
  - HTTP 400 : user_id vide ou non spécifié dans l'en-tête X-user-id.
- **Réponse attendue** : Un tableau JSON contenant les posts créés par l'utilisateur spécifié.
  ```json
  [
    {
        "id": "987654321",
        "text": "Ceci est le contenu du deuxième post.",
        "media": null,
        "replyPostId": null,
        "createdAt": "2024-01-26T09:30:00Z",
        "userId": "uuid2",
        "repostId": null
    },
    {
        "id": "987654321",
        "text": "Ceci est le contenu du deuxième post.",
        "media": null,
        "replyPostId": null,
        "createdAt": "2024-01-26T09:30:00Z",
        "userId": "uuid2",
        "repostId": null
    }
  ]
  ```

#### GET /api/users/{user_id}/liked-posts
- **Description**: Obtenir les posts aimés par un utilisateur spécifique.
- **Paramètres de chemin** : `{user_id}` - L'identifiant unique de l'utilisateur.
- **En-têtes de la requête** :
  - X-user-id : UUID, l'identifiant de l'utilisateur dont on veut obtenir les posts aimés.
- **Codes de réponse**:
  - HTTP 200 : Réponse réussie.
  - HTTP 400 : user_id vide ou non spécifié dans l'en-tête X-user-id.
- **Réponse attendue** : Un tableau JSON contenant les posts aimés par l'utilisateur spécifié.
  ```json
  [
    {
        "id": "987654321",
        "text": "Ceci est le contenu du deuxième post.",
        "media": null,
        "replyPostId": null,
        "createdAt": "2024-01-26T09:30:00Z",
        "userId": "uuid2",
        "repostId": null
    },
    {
        "id": "987654321",
        "text": "Ceci est le contenu du deuxième post.",
        "media": null,
        "replyPostId": null,
        "createdAt": "2024-01-26T09:30:00Z",
        "userId": "uuid2",
        "repostId": null
    }
  ]
  ```

