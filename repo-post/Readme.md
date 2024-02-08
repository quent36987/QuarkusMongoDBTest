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
    