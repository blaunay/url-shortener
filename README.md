# url-shortener

Micro-service Spring Boot permettant de raccourcir une URL. L'application peut être démarrée avec la commande suivante:

```sh
mvn spring-boot:run
```

### REST

Deux endpoints REST sont exposés pour le processus de raccourcissement d'URL.

| Method | Path                        | Description                                                                          |
|--------|-----------------------------|--------------------------------------------------------------------------------------|
| POST   | /url/shorten                | Retourne l'URL raccourcie (au format domaine/shortUrlId), à partir d'une URL donnée  |
| GET    | /url/unshorten/{shortUrlId} | Retourne l'URL complète, à partir de l'identifiant d'une URL précédemment raccourcie |

Les deux points d'entrées peuvent être testés via Swagger-UI sur http://localhost:8080/swagger-ui/index.html, une fois
l'application démarrée.

### Configuration

Le nom de domaine à utiliser pour les URL raccourcies peut être configuré dans le fichier `application.properties`. Il
peut être modifié en tout temps, et n'impacte pas les identifiants utilisés dans les URL courtes.

```properties
shortener.url-domain-name=<nom de domaine à utiliser pour les URL raccourcies>
```

### Fonctionnement

À la réception d'une URL à raccourcir, celle-ci est hachée en utilisant la fonction MD5, puis convertie en [`Base64 (
URL-safe)`](https://datatracker.ietf.org/doc/html/rfc4648).
Ce qui assure que deux URL identiques donneront le même résultat, une fois encodé en base64.
Les 10 premiers chiffres du résultat sont utilisés comme identifiant de l'URL raccourcie.
Si le nom de domaine à utiliser est configuré en tant que "https://short.bl", l'URL raccourcie retournée aura alors le
format suivant:

```text
"https://short.bl/{identifiant}"
```

### Données

Le service utilise une base de données h2 (fichier sur disque) pour stocker et conserver les données même après
redémarrage du service.
