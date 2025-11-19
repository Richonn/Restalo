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
***Réponse :*** ***Avant l'implémentation du pipeline CI, nous consacrions approximativement 20 à 30 minutes par fonctionnalité pour effectuer les tests manuels de chaque nouvelle implémentation. Ce temps incluait la vérification des fonctionnalités, la validation des cas limites et la détection des régressions potentielles.***

2. Après l'implémentation du CI, combien de temps passez-vous à effectuer ces vérifications ?
***Réponse :*** ***Grâce à l'implémentation du pipeline CI, les tests sont désormais exécutés automatiquement lors de chaque pull request, ce qui nous permet d'économiser les 20 à 30 minutes précédemment consacrées aux vérifications manuelles. Le temps d'attente pour les résultats des tests automatisés est généralement inférieur à 5 minutes, et nous pouvons travailler sur d'autres tâches pendant leur exécution.***

3. Quels sont les points positifs que le CI a apportés à votre **processus**? Donnez-en au moins **trois**.
***Réponse :*** ***Le pipeline CI a apporté trois améliorations majeures à notre processus de développement : (1) une détection rapide et systématique des erreurs dès l'intégration du code, (2) une amélioration de la qualité du code grâce aux workflows de tests unitaires, de linting et de build qui s'exécutent automatiquement, et (3) un gain significatif de temps et de productivité en libérant les développeurs des tâches répétitives de vérification manuelle.***

4. Le pipeline CI introduit-il un élément qui pourrait devenir négatif ou risqué pour le processus, le produit et/ou l'équipe? Justifiez votre réponse.
***Réponse :*** ***Oui, le pipeline CI peut introduire une dépendance excessive à l'automatisation qui présente certains risques. Lorsque tous les tests passent et que le pipeline affiche un statut "vert", l'équipe peut développer un faux sentiment de sécurité et négliger les tests manuels exploratoires. Certains problèmes, notamment ceux liés à l'expérience utilisateur ou aux cas d'usage complexes non couverts par les tests automatisés, peuvent ainsi passer inaperçus. Il est donc important de maintenir un équilibre entre automatisation et vérification manuelle ciblée.***

### Tests

1. Quelle proportion de temps passez-vous à implémenter le code fonctionnel par rapport aux tests? Cette proportion évolue-t-elle avec le temps? Pourquoi?
***Réponse :*** ***Initialement, nous consacrons davantage de temps à l'implémentation des tests qu'au code fonctionnel lui-même, principalement en raison de la courbe d'apprentissage associée aux bonnes pratiques de test, aux frameworks de test et à la rédaction de tests propres et maintenables. Cependant, cette proportion évolue favorablement avec le temps : à mesure que l'équipe acquiert de l'expérience et de la familiarité avec les outils et méthodologies, l'écriture des tests devient plus naturelle et efficace, réduisant ainsi le temps nécessaire à leur implémentation.***

2. L'implémentation des tests augmente naturellement la charge de travail. Comment cela a-t-il affecté votre processus? (ex. : taille des issues/PRs, temps d'implémentation, planification, etc.)
***Réponse :*** ***L'implémentation des tests a effectivement augmenté la charge de travail globale du projet. Bien que la taille des issues individuelles n'ait pas significativement augmenté grâce à une meilleure décomposition des tâches, la charge de travail par développeur s'est accrue, car chaque membre est responsable de l'implémentation des tests relatifs aux fonctionnalités qu'il développe. En conséquence, le temps d'implémentation combiné (code fonctionnel + tests) est plus long. Toutefois, cette charge supplémentaire n'a pas nécessité de modifications majeures dans notre processus de planification, car nous avons intégré l'estimation des tests dès le début de chaque sprint.***

3. Avez-vous davantage confiance en votre code maintenant que vous avez des tests? Justifiez votre réponse.
***Réponse :*** ***Oui, la présence de tests nous confère une confiance significativement accrue dans la qualité et la fiabilité de notre code. Les tests garantissent que les fonctionnalités développées fonctionnent conformément aux spécifications et que les modifications ultérieures n'introduisent pas de régressions. Cette assurance nous permet de nous concentrer sur le développement de nouvelles fonctionnalités sans craindre d'impacter négativement les sections de code bien testées, ce qui améliore notre productivité et réduit le stress lié à l'intégration de changements.***

4. Que pouvez-vous faire pour améliorer la qualité de vos tests? Donnez au moins **trois** solutions.
***Réponse :*** ***Pour améliorer la qualité de nos tests, nous pourrions mettre en œuvre trois stratégies principales : (1) augmenter l'utilisation de fixtures réutilisables pour standardiser les données de test et réduire la duplication de code, (2) améliorer la séparation des préoccupations en divisant mieux les concepts et les responsabilités au sein des tests, facilitant ainsi leur compréhension et leur maintenance, et (3) adopter des conventions de nomenclature plus claires et cohérentes pour les noms de variables, de méthodes de test et de classes, rendant le code de test plus lisible et auto-documenté.***

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
    date: string, //date without time (cannot be null)
    menu: string,
    customer: {
        name: string,
        email: string, // x@y.z (can be null)
        phoneNumber: string // 10 chiffres (can be null)
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
- Limitation d'offres : Un restaurant ne peut créer qu'une seule offre spéciale à la
fois.

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

`400 BAD REQUEST: une offre spéciale existe déjà`

```json
{
"error": "OFFER_ALREADY_EXISTS",
"description": "Un restaurant ne peut avoir qu'une seule offre spéciale à la fois."
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

#### `RestaurantResource` & `ReservationResource`
**Points d'entrée REST** qui gèrent les requêtes HTTP (`GET`, `POST`, `DELETE`) et délèguent la logique métier aux Services applicatifs correspondants.

---

### Couche Application

#### `RestaurantService`
Orchestre les cas d'utilisation liés aux restaurants :
- **Création, recherche, récupération, suppression**
- **Coordonne** : `RestaurantRepository`, `ReservationRepository` (pour suppression en cascade), `RestaurantDomainService`, et `RestaurantFactory`
- **Utilise** : Mappers/Assemblers pour les conversions `DTO ↔ Domain`
- **Suppression en cascade** : Lors de la suppression d'un restaurant, supprime d'abord toutes ses réservations (via `deleteAllReservationOfRestaurant`) avant de supprimer le restaurant lui-même, respectant ainsi l'intégrité référentielle

#### `ReservationService`
Orchestre les réservations :
- **Création, recherche, suppression, calcul des disponibilités**
- **Coordonne** : les repositories, `ReservationFactory` et `RestaurantDomainService`
- **Validation** : Valide la capacité disponible via `RestaurantDomainService` avant de créer une réservation

---

### Couche Domain

#### `Restaurant`
**Agrégat** représentant un restaurant avec ses règles métier de base :
- Génère un **UUID unique**
- Fournit des méthodes de recherche et de vérification d'horaires (`matchesName`, `isFullyOpenDuring`)
- La validation des champs est déléguée à `RestaurantFactory`

#### `Reservation`
**Agrégat** représentant une réservation :
- Valide ses champs (date future, `groupSize > 0`)
- Ajuste l'heure au prochain créneau de **15 minutes**
- Génère tous les créneaux occupés

#### `RestaurantDomainService`
**Service de domaine** contenant la logique métier transversale qui nécessite plusieurs agrégats :
- **Valide** les headers et dates (`convertDateStringToLocalDate`, `isTimeRangeValid`)
- **Calcule** les disponibilités d'un restaurant :
  - `getRestaurantAvailability` retourne un `Map<LocalTime, Integer>` des places disponibles par créneau de 15min
  - Peut retourner des valeurs **négatives** si sur-réservé
- **Note** : Ne valide plus les restaurants individuels - cette responsabilité a été déplacée vers `RestaurantFactory`

#### `RestaurantFactory`
**Factory** créant des restaurants valides :
- **Orchestre** la validation des champs avant la création :
  - `capacité > 0`
  - Fermeture après ouverture
  - Ouverture minimum **1h**
  - Durée réservation **5-180min**
- **Encapsule** la logique complexe de validation pour garantir qu'aucun restaurant invalide ne peut être créé
- **Centralise** les validations qui étaient auparavant dispersées

#### `ReservationFactory`
**Factory** créant des réservations valides :
- **Orchestre** : création, ajustement horaire 15min, validation des champs, et définition de l'heure de fin via le restaurant
- **Utilise** : `TimeUtils` pour les manipulations temporelles communes

---

### Couche Infrastructure

#### `InMemoryRestaurantRepository` & `InMemoryReservationRepository`
**Implémentations en mémoire** (`HashMap`) :
- Stockage **volatile** pour développement et tests
- Manipulent directement les objets du domaine

#### `MongoRestaurantRepository` & `MongoReservationRepository`
**Implémentations MongoDB** avec Morphia ODM :
- Stockage **persistant** pour production
- **Utilisent** :
  - `MongoConnection` pour accéder au Datastore
  - Documents (`RestaurantDocument`, `ReservationDocument`) - structures avec champs primitifs uniquement
  - DocumentMappers (`RestaurantDocumentMapper`, `ReservationDocumentMapper`) pour convertir `Domain ↔ Document`

#### `MongoConnection`
**Singleton** gérant la connexion unique à MongoDB :
- Crée le **Datastore Morphia**
- Fournit l'accès à la base de données pour tous les repositories MongoDB

#### `PersistenceConfig`
Configure le choix de l'implémentation de persistance :
- **Options** : InMemory ou MongoDB via propriété système
- Permet de changer facilement entre les deux modes sans modifier le code applicatif

#### `RestaurantDocument` & `ReservationDocument`
**Représentations Morphia** des entités persistées :
- Contiennent uniquement des **champs primitifs** compatibles avec MongoDB

#### `RestaurantDocumentMapper` & `ReservationDocumentMapper`
**Convertissent** les objets :
- `Domain` (avec leurs Value Objects et logique métier) **↔** `Documents` (structures plates pour MongoDB)

## Explication des Choix

L'architecture du TP3 suit un **modèle en couches horizontales** inspiré du **Domain-Driven Design (DDD)** pour séparer clairement les responsabilités.

Elle comprend **quatre couches principales** :
- **API** : exposition des ressources via des API REST
- **Application** : logique métier
- **Domaine** : entités métiers et interfaces des repositories
- **Infrastructure** : persistance des données

---

### Séparation des Couches

Nous avons suivi le **principe de séparation des préoccupations** en distinguant clairement les couches :
- **API** (Présentation)
- **Application** (Service)
- **Domaine**
- **Infrastructure** (Persistance)

#### Avantages
Cette séparation permet de :
- Maintenir un code **organisé, évolutif et facilement testable**
- Faire évoluer chaque couche **indépendamment** des autres
- **Améliorer la lisibilité** du code
- **Faciliter la maintenance**
- Changer une couche **sans affecter les autres**
  - *Exemple* : changer la persistance (MongoDB → autre base de données)
  - *Exemple* : remplacer la couche API sans toucher à la logique métier

---

### Utilisation des Factories

Les **factories** sont utilisées pour centraliser la création des objets métier comme `Reservation` et `Restaurant`, assurant leur **validation** avant leur entrée dans le domaine.

#### Avantages
- **Garantit** que seuls des objets cohérents et valides sont utilisés, réduisant ainsi les erreurs
- **Simplifie** la logique des services en déléguant la création à des classes spécialisées
- **Améliore** la maintenabilité et l'évolutivité du code
- Permet de **modifier facilement** les règles de validation sans impacter d'autres parties du système
- Assure une **gestion efficace des dépendances**

---

### Abstraction de la Persistance

Nous avons **séparé l'interface des repositories et leur implémentation**.

#### Structure
- Les **interfaces** (`ReservationRepository`, `RestaurantRepository`) :
  - Définissent les **contrats** nécessaires pour manipuler les entités
  - **N'exposent pas** la logique de persistance

- Les **implémentations** (`MongoReservationRepository`, `InMemoryReservationRepository`) :
  - Se trouvent dans la **couche d'infrastructure**
  - Sont utilisées par les services

#### Avantages
- Permet de **changer la technologie sous-jacente** sans affecter le reste de l'application
  - *Exemple* : MongoDB → solution en mémoire
- Offre une **grande flexibilité** dans le choix de la persistance
- Respecte le **principe d'inversion des dépendances**

## Relations Suspectes et Solutions Potentielles

### 1. Couplage entre les services et les repositories

#### Problématique
Bien que les services comme `ReservationService` et `RestaurantService` orchestrent la logique métier, ils **dépendent encore directement** des repositories (`ReservationRepository`, `RestaurantRepository`) pour la persistance des données.

**Conséquences** :
- Crée un **couplage étroit** entre la logique métier et la couche d'accès aux données
- Pourrait rendre l'application **plus difficile à maintenir** si des changements dans la stratégie de persistance sont nécessaires à l'avenir

#### Solution potentielle
Bien que `RestaurantDomainService` centralise déjà la logique métier pour les restaurants, il serait avantageux de **dégager davantage la logique de persistance** des services métier comme `RestaurantService` et `ReservationService`.

**Approches possibles** :
- Utilisation d'**intermédiaires** ou de **façades** dédiés à la gestion des accès aux données
- Mieux **encapsuler la persistance** tout en gardant la logique métier isolée

---

### 2. Dépendances au sein des services

#### Problématique
Les services comme `ReservationService` et `RestaurantService` dépendent de **nombreux composants** tels que :
- Domain Services
- Repositories
- Factories

**Conséquences** :
- Bien que cela permette une séparation des responsabilités, un **trop grand nombre de dépendances** peut rendre ces services :
  - Difficiles à **tester**
  - Difficiles à **maintenir**

#### Solution potentielle
**Approches recommandées** :

1. **Réduire le couplage direct** :
   - Introduire des **interfaces** pour les composants clés (repositories, factories)
   - Rendre les services plus **flexibles** et facilement **testables**

2. **Organiser les services** :
   - Diviser en fonctions plus spécifiques : `create`, `get`, `delete`, etc.
   - Mieux **distinguer les responsabilités** de chaque service
   - Améliorer leur **lisibilité** au niveau des routes et de l'API

**Bénéfices** :
- Réduction de la **complexité**
- Architecture plus **modulaire**
- Plus facile à **maintenir** à long terme

---

### 3. Risque de surcharge dans les services de domaine

#### Problématique
Le `RestaurantDomainService` gère **plusieurs règles métier** :
- Validation des horaires
- Conversion des dates
- Calcul de la disponibilité des restaurants

**Conséquences** :
- Bien que cela permette une certaine **centralisation** de la logique, cette approche pourrait conduire à :
  - Une **surcharge de responsabilités**
  - Un service **difficile à maintenir** à mesure que de nouvelles règles sont ajoutées

#### Solution potentielle
**Refactorisation recommandée** :

Refactorer le `RestaurantDomainService` en **plusieurs services spécialisés**, chacun se concentrant sur un aspect spécifique de la logique métier :
- Un service pour la **gestion des horaires**
- Un autre pour la **gestion des disponibilités**
- etc.

**Bénéfices** :
- Meilleur respect du **Single Responsibility Principle (SRP)**
- Amélioration de la **maintenabilité**
- Amélioration de la **testabilité** du code

---
