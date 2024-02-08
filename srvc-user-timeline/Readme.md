
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
