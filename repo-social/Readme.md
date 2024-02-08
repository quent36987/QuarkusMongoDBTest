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
