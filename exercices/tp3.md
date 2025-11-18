# Exercices - TP3

---

## Déclaration d'utilisation de l'IA

Pour ce projet, certains membres de l'équipe ont utilisé **ChatGPT** d'OpenAI comme outil d'intelligence artificielle.

### Outils utilisés
- ChatGPT pour aider à structurer les étapes d'implémentation.
- ChatGPT pour aider à tester les requêtes.

### Comment nous avons intégré l'IA dans notre travail
- Nous avons demandé à ChatGPT d'aider à subdiviser et structurer les étapes globales du travail.
- Nous avons utilisé ChatGPT pour générer des exemples de commandes `curl` pour tester l'API REST.

### Rôle de l'IA dans la réalisation du projet
- L'IA a servi d'assistant pour organiser le projet en sous-tâches et faciliter la compréhension des interactions avec l'API.
- Elle a facilité la mise en place des tests à l'aide du terminal.
- Elle a fourni des suggestions pour améliorer la clarté du code et la validation des données.

**Nous certifions que le code soumis est le fruit de notre travail personnel, avec l'appui de l'outil mentionné**.

---

## Rétrospective - Intégration continue et tests

### Pipeline CI

1. Avant l'implémentation du pipeline de tests automatisés, combien de temps passiez-vous à vérifier et tester manuellement le code lors des intégrations et des remises ?
***Réponse :*** ***Avant l'implémentation du pipeline, nous passions environ 20 à 30 minutes par fonctionnalité pour tester manuellement la nouvelle implémentation.***

2. Après l'implémentation du CI, combien de temps passez-vous à effectuer ces vérifications ?
***Réponse :*** ***Grâce à l'implémentation du CI, n'importe quel pull request permet de rouler les tests automatiquement permettant de gagner 20 à 30 minutes.***

3. Quels sont les points positifs que le CI a apportés à votre **processus**? Donnez-en au moins **trois**.
***Réponse :*** ***Une détection rapide des erreurs, une qualité de code améliorée et un gain de temps et de productivité grâce à nos workflows de tests, linting et build.***

4. Le pipeline CI introduit-il un élément qui pourrait devenir négatif ou risqué pour le processus, le produit et/ou l'équipe? Justifiez votre réponse.
***Réponse :*** ***Oui, par exemple, le pipeline CI peut introduire une dépendance excessive à l'automatisation: certains problèmes peuvent passer inaperçus car la "pipeline est verte" et peut favoriser le fait de moins tester manuellement.***

### Tests

1. Quelle proportion de temps passez-vous à implémenter le code fonctionnel par rapport aux tests? Cette proportion évolue-t-elle avec le temps? Pourquoi?
***Réponse :*** ***Plus de temps à implémenter les tests (apprentissage de comment coder des tests correctement de façon clean ainsi que les frameworks, etc). Elle diminue avec le temps plus que cela deviens naturel (courbe d'aprentissage)***

2. L'implémentation des tests augmente naturellement la charge de travail. Comment cela a-t-il affecté votre processus? (ex. : taille des issues/PRs, temps d'implémentation, planification, etc.)
***Réponse :*** ***Oui évidemment. La taille des issues n'a pas nécessairement grandit (division), mais la charge de travail par membre oui, puisque sa reste que celui qui dévloppe la feature fait les tests relier en général. Le temps d'implémentation combiner et donc plus long. Pas vraiment besoin de plus de planification.***

3. Avez-vous davantage confiance en votre code maintenant que vous avez des tests? Justifiez votre réponse.
***Réponse :*** ***Oui, cela permet d'assurer que le code fonctionne adéquatement (ce qui est tester et correctement). Ainsi, il est plus facile de pouvoir ce concentrer sur d'autres éléments sans craindre les sections de code bien tester.***

4. Que pouvez-vous faire pour améliorer la qualité de vos tests? Donnez au moins **trois** solutions.
***Réponse :*** ***Plus de fixture, une meilleur division des concepts/rôle et une meilleur clarter/nomenclature (nom des variables, méthodes (parfois), etc)***

---

## Story 1 - Créer une commande
En tant qu'utilisateur, je peux effectuer une commande.
### Critères de succès
- L'id de la commande doit être unique
- Le nom d'un client ne peut pas être vide
- Les numéros de téléphone et addresses courriels doivent être valides
- Un même client peut avoir plusieurs commandes en même temps
- Le menu commander ne peut pas être vide
### Spécifications
#### Requête
##### Path
`POST /restaurants/<id>/commandes`
##### Body

```json
{
    date: string, //date without time
    menu: string,
    customer: {
        name: string,
        email: string, // x@y.z
        phoneNumber: string // 10 chiffres
    }
}
```

<details>
<summary>Exemple</summary>

```json
{
    "date": "2025-11-14",
    "menu": "salade césar",
    "customer": {
        "name": "John Deer",
        "email": "john.deer@gmail.com",
        "phoneNumber": "1234567890"
    }
}
```

</details>

#### Réponses
- `201 CREATED` : succès
    ##### Headers
    - `Location : string` : URI de la commande créée
        - format: `<host>/commandes/<id>`
        - Exemple: `http://localhost:8080/commandes/123`
- `400 BAD REQUEST` : un des paramètres obligatoires est manquant
    ##### Body
    ```
    {
        error: "MISSING_PARAMETER",
        description: string
    }
    ```
- `404 BAD REQUEST` : un des paramètres n'est pas valide
    ##### Body
    ```
    {
        error: "INVALID_PARAMETER",
        description: string
    }
    ```
- `404 NOT FOUND` : le restaurant n'existe pas

---

## Story 2 - Obtenir une commande
En tant qu'utilisateur, je peux voir une de mes commande.
### Critères de succès
Voir la story "Créer une commande".
### Spécifications
#### Requête
##### Path
`GET /commandes/<number>`
#### Réponses
- `200 OK` : succès
##### Body

```json
{
    number: string,
    date: string, //date without time
    menu: string,
    customer: {
        name: string,
        email: string, // x@y.z
        phoneNumber: string // 10 chiffres
    },
    restaurant: {
        id: string,
        name: string,
        hour: {
            open: string, // time without timezone
            close: string // time without timezone
        }
    }
}
```

<details>
<summary>Exemple</summary>

```json
{
    "number": "12345"
    "date": "2025-11-14",
    "menu": "salade césar",
    "customer": {
        "name": "John Deer",
        "email": "john.deer@gmail.com",
        "phoneNumber": "1234567890"
    },
    "restaurant": {
        "id": "54321"
        "name": "La Botega",
        "hours": {
            "open": "11:00:00",
            "close": "19:30:00"
        }
    }
}
```

</details>

- `404 NOT FOUND` : la commande n'existe pas

---

## Story 3 - Créer et consulter les offres spéciales des restaurants
En tant que restaurateur, je souhaite créer et mettre en avant des offres spéciales (par
exemple, menus saisonniers, repas combinés) pour mon restaurant, afin que les clients
puissent consulter facilement les promotions disponibles et être incités à réserver.

### Critères de succès
- Création des offres : Le restaurateur peut enregistrer une offre spéciale associée à son
restaurant.
- Détails de l'offre : Chaque offre inclut un titre, une description, et une période de
validité (dates de début et de fin).
- Propriété : Seul le propriétaire du restaurant (vérifié par le header Owner) peut créer
l'offre.
- Consultation dédiée : Les clients peuvent consulter les offres valides d'un restaurant
via un point d'accès dédié.

### Spécifications
Créer une Offre Spéciale (Nouvelle fonctionnalité)
Ceci est une nouvelle fonctionnalité qui ne modifie aucune route, requête ou réponse existante.

### Requête

#### Path
`POST /restaurants/<id>/special-offers`
##### Headers Owner:
`string: ID du restaurateur`
##### Body

```json
{
    "offer": {
    "title": "string",
    "description": "string",
    "validFrom": "string", // date sans time zone
    "validUntil": "string" // date sans time zone
    }
}
```

<details>
<summary>Exemple</summary>

```json
{
    "offer": {
    "title": "Menu d'hiver",
    "description": "Un menu spécial sur le thème de l'hiver, disponible les weekends.",
    "validFrom": "2024-12-01",
    "validUntil": "2024-12-31"
    }
}
```

</details>

##### Réponses
`201 CREATED: succès Body`

```json
{
"id": "string",
"title": "string",
"description": "string",
"validFrom": "string",
"validUntil": "string"
}
```

<details>
<summary>Exemple</summary>

```json
{
"id": "12345",
"title": "Menu d'hiver",
"description": "Un menu spécial sur le thème de l'hiver, disponible les weekends.",
"validFrom": "2024-12-01",
"validUntil": "2024-12-31"
}
```
</details>

`400 BAD REQUEST: un des paramètres obligatoires est manquant`

##### Body

```json
{
"error": "MISSING_PARAMETER",
"description": "string"
}
```

`404 NOT FOUND: le restaurant n'existe pas ou n'appartient pas au restaurateur`

##### Body

```json
{
"error": "NOT_FOUND",
"description": "string"
}
```

---

## Story 4 - Obtenir les Offres Spéciales d'un Restaurant
Ceci est une nouvelle route qui permet aux clients de consulter les offres après avoir identifié le
restaurant. Elle ne modifie pas POST /search/restaurants.

### Requête

#### Path
`GET /restaurants/<id>/special-offers`
#### Headers
`Aucun`

#### Réponses

`200 OK: succès Body`

```json
[
    {
        "id": "string",
        "title": "string",
        "description": "string",
        "validFrom": "string",
        "validUntil": "string"
    }
]
```

<details>
<summary>Exemple</summary>

```json
[
    {
        "id": "12345",
        "title": "Menu d'hiver",
        "description": "Un menu spécial sur le thème de l'hiver, disponible les weekends.",
        "validFrom": "2024-12-01",
        "validUntil": "2024-12-31"
    },
    {
        "id": "67890",
        "title": "Combo pizza et bière",
        "description": "Pizza large et deux bières artisanales.",
        "validFrom": "2025-01-01",
        "validUntil": "2025-03-31"
    }
]
```

```json
[]
```
(si aucune offre valide n'est disponible)

</details>

`404 NOT FOUND: le restaurant n'existe pas`

---

## Captures d'écran

### Screenshot du GitHub Project (colonnes + issues)

![alt text](screen-shots/rendu_3/github-project.png)

### Screenshots du Milestone (titre, description, date, issues)

![alt text](screen-shots/rendu_3/milestone1.png)
![alt text](screen-shots/rendu_3/milestone2.png)
![alt text](screen-shots/rendu_3/milestone3.png)

### Screenshots de 3 issues

#### Issue 1

![alt text](screen-shots/rendu_3/issue1.png)

#### Issue 2

![alt text](screen-shots/rendu_3/issue2.png)

#### Issue 3 (Parent-issue)

![alt text](screen-shots/rendu_3/parent-issue1.png)

### Screenshot de 3 Pull Requests (avec reviews)

#### Pull request 1
![alt text](screen-shots/rendu_3/pullrequest1-1.png)
![alt text](screen-shots/rendu_3/pullrequest1-2.png)

#### Pull request 2
![alt text](screen-shots/rendu_3/pullrequest2-1.png)
![alt text](screen-shots/rendu_3/pullrequest2-2.png)

#### Pull request 3
![alt text](screen-shots/rendu_3/pullrequest3-1.png)
![alt text](screen-shots/rendu_3/pullrequest3-2.png)
![alt text](screen-shots/rendu_3/pullrequest3-3.png)

### Screenshots de 'arbre de commits et branches (≥10 commits)

![alt text](screen-shots/rendu_3/treecommitbranches2.png)
![alt text](screen-shots/rendu_3/treecommitbranches1.png)

---

## Diagramme d'architecture

![alt text](screen-shots/rendu_3/diagramme-arch.png)

## Rôles des classes principales

### Couche API (Présentation)

#### RestaurantResource & ReservationResource
Points d'entrée REST qui gèrent les requêtes HTTP (GET, POST, DELETE) et délèguent la logique métier aux Services applicatifs correspondants.

### Couche Application

#### RestaurantService
Orchestre les cas d'utilisation liés aux restaurants (création, recherche, récupération, suppression). Coordonne RestaurantRepository, ReservationRepository (pour suppression en cascade), RestaurantDomainService, et RestaurantFactory. Utilise Mappers/Assemblers pour les conversions DTO ↔ Domain. Lors de la suppression d'un restaurant, supprime d'abord toutes ses réservations (via deleteAllReservationOfRestaurant) avant de supprimer le restaurant lui-même, respectant ainsi l'intégrité référentielle.

#### ReservationService
Orchestre les réservations (création, recherche, suppression, calcul des disponibilités). Coordonne les repositories, ReservationFactory et RestaurantDomainService. Valide la capacité disponible via RestaurantDomainService avant de créer une réservation.

### Couche Domain

#### Restaurant
Agrégat représentant un restaurant avec ses règles métier de base. Génère un UUID unique et fournit des méthodes de recherche et de vérification d'horaires (matchesName, isFullyOpenDuring). La validation des champs est déléguée à RestaurantFactory.

#### Reservation
Agrégat représentant une réservation. Valide ses champs (date future, groupSize > 0), ajuste l'heure au prochain créneau de 15 minutes, et génère tous les créneaux occupés.

#### RestaurantDomainService
Service de domaine contenant la logique métier transversale qui nécessite plusieurs agrégats. Valide les headers et dates (convertDateStringToLocalDate, isTimeRangeValid), et calcule les disponibilités d'un restaurant (getRestaurantAvailability retourne un Map<LocalTime, Integer> des places disponibles par créneau de 15min, peut retourner des valeurs négatives si sur-réservé). Ne valide plus les restaurants individuels - cette responsabilité a été déplacée vers RestaurantFactory.

#### RestaurantFactory
Factory créant des restaurants valides. Orchestre la validation des champs (capacité > 0, fermeture après ouverture, ouverture minimum 1h, durée réservation 5-180min) avant la création. Encapsule la logique complexe de validation pour garantir qu'aucun restaurant invalide ne peut être créé. Cette factory centralise les validations qui étaient auparavant dispersées.

#### ReservationFactory
Factory créant des réservations valides. Orchestre: création, ajustement horaire 15min, validation des champs, et définition de l'heure de fin via le restaurant. Utilise TimeUtils pour les manipulations temporelles communes.

### Couche Infrastructure

#### InMemoryRestaurantRepository & InMemoryReservationRepository
Implémentations en mémoire (HashMap). Stockage volatile pour développement et tests. Manipulent directement les objets du domaine.

#### MongoRestaurantRepository & MongoReservationRepository
Implémentations MongoDB avec Morphia ODM. Stockage persistant pour production. Utilisent MongoConnection pour accéder au Datastore, des Documents (RestaurantDocument, ReservationDocument - structures avec champs primitifs uniquement) et des DocumentMappers (RestaurantDocumentMapper, ReservationDocumentMapper) pour convertir Domain ↔ Document.

#### MongoConnection
Singleton gérant la connexion unique à MongoDB et la création du Datastore Morphia. Fournit l'accès à la base de données pour tous les repositories MongoDB.

#### PersistenceConfig
Configure le choix de l'implémentation de persistance (InMemory ou MongoDB) via propriété système. Permet de changer facilement entre les deux modes sans modifier le code applicatif.

#### RestaurantDocument & ReservationDocument:
Représentations Morphia des entités persistées. Contiennent uniquement des champs primitifs compatibles avec MongoDB.

#### RestaurantDocumentMapper & ReservationDocumentMapper:
Convertissent les objets Domain (avec leurs Value Objects et logique métier) vers/depuis les Documents (structures plates pour MongoDB).

## Explication des Choix

L'architecture du TP3 suit un modèle en couches horizontales inspiré du Domain-Driven Design (DDD) pour séparer clairement les responsabilités. Elle comprend quatre couches principales : l'API pour l'exposition des ressources via des API REST, l'Application pour la logique métier, le Domaine avec les entités métiers et les interfaces des repositories, et l'Infrastructure pour la persistance des données.

### Séparation des Couches
Nous avons suivi le principe de séparation des préoccupations en distinguant clairement les couches API (Présentation), Application (Service), Domaine et Infrastructure (Persistance) lors de notre implémentation. Cette séparation permet de maintenir un code organisé, évolutif et facilement testable. Chaque couche est responsable de son propre domaine et peut évoluer indépendamment des autres couches. Cela améliore la lisibilité du code, facilite la maintenance et permet de changer une couche sans affecter les autres (par exemple, changer la persistance en passant de MongoDB à une autre base de données, ou remplacer la couche API sans toucher à la logique métier).

### Utilisation des Factories
Les factories sont utilisées pour centraliser la création des objets métier comme Reservation et Restaurant, assurant leur validation avant leur entrée dans le domaine. Cela garantit que seuls des objets cohérents et valides sont utilisés, réduisant ainsi les erreurs. Ce modèle centralisé simplifie la logique des services en déléguant la création à des classes spécialisées, ce qui améliore la maintenabilité et l’évolutivité du code. Les règles de validation peuvent ainsi être modifiées facilement sans impacter d’autres parties du système, assurant une gestion efficace des dépendances.

### Abstraction de la Persistance
Nous avons séparé l'interface des repositories et leur implémentation. Les interfaces, comme ReservationRepository et RestaurantRepository, définissent les contrats nécessaires pour manipuler les entités sans exposer la logique de persistance. Cela permet de changer la technologie sous-jacente (par exemple, passer de MongoDB à une solution en mémoire) sans affecter le reste de l'application. L'implémentation spécifique de chaque repository (comme MongoReservationRepository ou InMemoryReservationRepository) se trouve dans la couche d'infrastructure et est utilisée par les services. Ainsi, cela offre une grande flexibilité dans le choix de la persistance tout en respectant le principe d'inversion des dépendances.

## Relations Suspectes et Solutions Potentielles

### 1. Couplage entre les services et les repositories
Bien que les services comme ReservationService et RestaurantService orchestrent la logique métier, ils dépendent encore directement des repositories (ReservationRepository, RestaurantRepository) pour la persistance des données. Bien que cette approche fonctionne, elle peut créer un couplage étroit entre la logique métier et la couche d'accès aux données. Cela pourrait rendre l'application plus difficile à maintenir si des changements dans la stratégie de persistance sont nécessaires à l'avenir.

#### Solution potentielle
Bien que RestaurantDomainService centralise déjà la logique métier pour les restaurants, il serait avantageux de dégager davantage la logique de persistance des services métier comme RestaurantService et ReservationService. Cela pourrait inclure l'utilisation d'intermédiaires ou de façades dédiés à la gestion des accès aux données, afin de mieux encapsuler la persistance tout en gardant la logique métier isolée.


### 2. Dépendances au sein des services
Les services comme ReservationService et RestaurantService dépendent de nombreux composants tels que des Domain Services, des Repositories et des Factories. Bien que cela permette une séparation des responsabilités, un trop grand nombre de dépendances peut rendre ces services difficiles à tester et à maintenir.

#### Solution potentielle
Réduire le couplage direct entre les services et leurs dépendances en introduisant des interfaces pour les composants clés (comme les repositories et les factories). Cela permettrait de rendre les services plus flexibles et facilement testables.
Organiser les services en fonctions plus spécifiques comme create, get, delete, etc., pour mieux distinguer les responsabilités de chaque service et améliorer leur lisibilité au niveau des routes et de l'API.
Cette approche permettrait non seulement de réduire la complexité, mais aussi de rendre l’architecture plus modulaire et plus facile à maintenir à long terme.

### 3. Risque de surcharge dans les services de domaine
Le RestaurantDomainService gère plusieurs règles métier, notamment la validation des horaires, la conversion des dates et le calcul de la disponibilité des restaurants. Bien que cela permette une certaine centralisation de la logique, cette approche pourrait conduire à une surcharge de responsabilités et rendre le service difficile à maintenir à mesure que de nouvelles règles sont ajoutées.

#### Solution potentielle
Pour éviter cette surcharge, il serait pertinent de refactorer le RestaurantDomainService en plusieurs services spécialisés, chacun se concentrant sur un aspect spécifique de la logique métier (par exemple, un service pour la gestion des horaires, un autre pour la gestion des disponibilités). Cela permettrait de mieux respecter le Single Responsibility Principle (SRP), tout en améliorant la maintenabilité et la testabilité du code."

---
