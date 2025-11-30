# Exercices - TP4

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

### Principal avantage qu'offre une telle technologie
Le principal avantage de l'IA pour les développeurs est l'accélération significative du développement en automatisant les tâches répétitives et en fournissant une assistance instantanée.
Concrètement, l'IA permet de :
- Générer rapidement du code pour des Proof Of Concept ou des implémentations de base
- Comprendre et déboguer du code existant plus rapidement
- Explorer de nouvelles technologies sans passer des heures dans la documentation
- Se concentrer sur la résolution de problèmes complexes plutôt que sur la syntaxe
L'utilisation d'un tel outil permet de se concentrer sur l'aspect créatif et architectural d'un projet plutôt que sur des détais d'implémentation souvent répétitifs.

### Désavantages potentiels de l’utilisation d’une telle technologie
Il y a plusieurs dangers potentiels à l'utilisation de l'IA :
- Qualité et fiabilité du code : Le code généré peut contenir des bugs subtils, des failles de sécurité, des mauvaises pratiques et l'IA peut "halluciner".
- Dépendance et compétences : Risque de dépendance excessive qui peut freiner l'apprentissage des fondamentaux pour les développeurs débutants.
- Sécurité et confidentialité : Risque d'exposer du code propriétaire ou sensible selon l'outil utilisé.
L'utilisation d'un tel outil nécessite une revue critique et des tests rigoureux. Elle doit rester un assistant et non pas comme un remplaçant.

### Aura-t-on toujours besoin de spécialistes en développement logiciel malgré cette technologie
On aura toujours besoin de spécialistes en développement logiciel, mais le rôle évolue.
L'architecture logicielle complexe nécessite une vision d'ensemble et une expertise que l'IA n'a pas. L'IA manque toujours de compétences pour résoudre de nouveaux problèmes qui sortent des patterns habituels.
Quelqu'un devra toujours évaluer, réviser et valider le code généré par l'IA.
Les développeurs deviennent plus des "architectes" et "superviseurs" que des "codeurs" qui passent plus de temps sur la conception, l'innovation et la résolution de problèmes complexes.
Comme les calculatrices n'ont pas éliminé les mathématiciens, l'IA ne remplacera pas les développeurs.

**Nous certifions que le code soumis est le fruit de notre travail personnel, avec l'appui de l'outil mentionné**.

---

## Sécurité

**Question:**
Identifiez trois pratiques à intégrer dans un processus de développement logiciel afin de réduire les risques de vulnérabilités.

---

## Open sourcing

**Question:**
Nommez trois avantages de contribuer à des projets open source en tant qu'entreprise et expliquez en quoi cela peut être bénéfique pour tous.

**Réponse:**

1. **Développement des compétences et recrutement** : Contribuer à l'open source permet aux employés de développer leurs compétences techniques, de se tenir à jour avec les technologies émergentes, et de construire une réputation professionnelle. L'entreprise devient également plus visible auprès de talents potentiels qui peuvent observer la qualité du code et la culture de contribution.

2. **Réduction des coûts de développement et mutualisation** : En partageant un projet open source, l'entreprise peut bénéficier de contributions externes qui améliorent le logiciel sans coût interne. Les bugs sont identifiés plus rapidement, et des fonctionnalités sont développées par la communauté, réduisant ainsi le fardeau de maintenance.

3. **Innovation collaborative et standardisation** : L'open source permet de créer des standards industriels et de favoriser l'innovation collaborative. En rendant un projet public, l'entreprise peut influencer l'écosystème technologique et s'assurer que les solutions évoluent dans une direction alignée avec ses besoins stratégiques.

**Question:**
Décrivez trois défis liés à la mise en place d'un projet open source et justifiez votre réponse.

**Réponse:**

1. **Risque de burnout des mainteneurs** : Gérer un projet open source demande un investissement constant en temps et énergie. Répondre aux issues, réviser les pull requests, et maintenir la qualité du code peut rapidement devenir épuisant. Comme l'article "Best Practices for Maintainers" le mentionne, il faut "établir des frontières saines" et apprendre à dire non pour éviter l'accumulation et le surmenage.

2. **Gestion de la gouvernance et prise de décision** : Déterminer qui a le pouvoir de décision (BDFL, méritocratie, contribution libérale) et comment formaliser la structure de leadership peut être complexe. Il faut trouver un équilibre entre accueillir les contributions et maintenir une vision cohérente du projet.

3. **Application du code de conduite et gestion de la communauté** : Créer une communauté saine nécessite d'appliquer rigoureusement un code de conduite. Comme le souligne l'article "Your Code of Conduct" : "Un code de conduite non appliqué est pire que pas de code du tout". Cela demande du temps pour enquêter sur les signalements et prendre des mesures appropriées.

**Question:**
Quelle information vous a le plus surpris à propos de l'open source?

**Réponse:**

L'information la plus surprenante concerne **l'importance des contributions non-code**. Nous sous pensions que l'open source se concentrait principalement sur le code, mais l'article "How to Contribute to Open Source" révèle que **28% des contributions occasionnelles concernent la documentation**. Cela démontre que l'open source valorise autant (sinon plus) la clarté, l'accessibilité et le support communautaire que les lignes de code elles-mêmes. Cette perspective change complètement la perception de qui peut contribuer et comment on peut avoir un impact significatif sans être un développeur expert.

### Choix de la licence : MIT License

**Trois raisons principales :**

1. **Simplicité et clarté** : La licence MIT est extrêmement courte et facile à comprendre, ce qui élimine l'ambiguïté juridique. Comme recommandé dans l'article "The Legal Side of Open Source", la MIT offre "simplicité et flexibilité".

2. **Flexibilité maximale pour les utilisateurs** : La MIT est une licence permissive qui permet aux utilisateurs d'utiliser le code dans des projets commerciaux, de modifier et redistribuer librement, et d'intégrer le code dans des projets propriétaires.

3. **Compatibilité et adoption dans l'écosystème** : La MIT est compatible avec pratiquement toutes les autres licences, facilitant l'intégration de dépendances externes et permettant à d'autres projets d'utiliser notre code sans conflits de licence. C'est particulièrement important pour un écosystème Node.js/TypeScript où de nombreuses bibliothèques utilisent des licences permissives. L'article "The Legal Side of Open Source" souligne que les "dependencies" et la "license compatibility" sont des facteurs cruciaux dans le choix d'une licence.

---

## Rétrospective - Processus

**Question:**
Décrivez deux problématiques liées à votre processus et proposez deux plans distincts pour les résoudre. Soyez constructifs dans vos critiques et évitez d'attribuer la faute à une personne ou un groupe en particulier.

**Question:**
Décrivez la démarche que vous avez entreprise pour intégrer de nouveaux outils technologiques. Quelles étaient les étapes du processus?

**Question:**
Comment avez-vous réagi aux différents bogues? Avez-vous exploré ces outils à l'aide de tests unitaires ou manuels? Qu'avez-vous appris grâce à cette démarche?
Quels sont les bons coups de votre équipe? De quelles réalisations êtes-vous fiers? Nommez-en trois.

**Question:**
Quel conseil donneriez-vous aux prochains étudiants qui devront réaliser ce projet?

**Question:**
Quels apprentissages, astuces ou techniques acquis lors de ce projet pensez-vous pouvoir réutiliser plus tard?

**Question:**
Décrivez-en au moins deux. Il peut s'agir d'apprentissages techniques, pratiques, liés au travail d'équipe ou encore au processus de développement.

---

## Captures d'écran

### Screenshot du GitHub Project (colonnes + issues)

![alt text](screen-shots/rendu_4/github-project.png)

### Screenshots du Milestone (titre, description, date, issues)

![alt text](screen-shots/rendu_4/milestone1.png)
![alt text](screen-shots/rendu_4/milestone2.png)
![alt text](screen-shots/rendu_4/milestone3.png)

### Screenshots de 3 issues

#### Issue 1

![alt text](screen-shots/rendu_4/issue1-1.png)
![alt text](screen-shots/rendu_4/issue1-2.png)

#### Issue 2

![alt text](screen-shots/rendu_4/issue2-1.png)
![alt text](screen-shots/rendu_4/issue2-2.png)

#### Issue 3

![alt text](screen-shots/rendu_4/issue3.png)

### Screenshot de 3 Pull Requests (avec reviews)

#### Pull request 1
![alt text](screen-shots/rendu_4/pullrequest1-1.png)
![alt text](screen-shots/rendu_4/pullrequest1-2.png)

#### Pull request 2
![alt text](screen-shots/rendu_4/pullrequest2-1.png)
![alt text](screen-shots/rendu_4/pullrequest2-2.png)
![alt text](screen-shots/rendu_4/pullrequest2-3.png)
![alt text](screen-shots/rendu_4/pullrequest2-4.png)

#### Pull request 3
![alt text](screen-shots/rendu_4/pullrequest3-1.png)
![alt text](screen-shots/rendu_4/pullrequest3-2.png)
![alt text](screen-shots/rendu_4/pullrequest3-3.png)

### Screenshots de l'arbre de commits et branches (≥10 commits)

![alt text](screen-shots/rendu_4/committree.png)

---

## Diagramme d'architecture

![alt text](screen-shots/rendu_4/diagramme.png)

---

## Rôles des classes principales

### Couche API

#### `RestaurantResource` & `ReservationResource`
**Points d'entrée REST** qui gèrent les requêtes HTTP (`GET`, `POST`, `DELETE`) et délèguent la logique métier aux Services applicatifs correspondants.

#### `OrderResource`
**Point d'entrée REST** pour la gestion des commandes :
- Gère les requêtes HTTP pour créer et consulter des commandes
- Délègue à `OrderService` pour la logique métier

#### `SpecialOfferResource`
**Point d'entrée REST** pour la gestion des offres spéciales :
- Gère les requêtes HTTP pour créer des offres promotionnelles
- Délègue à `SpecialOfferService` pour la logique métier

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
- **Coordonne** : `ReservationRepository`, `RestaurantRepository`, `ReservationFactory` et `RestaurantDomainService`
- **Validation** : Valide la capacité disponible via `RestaurantDomainService` avant de créer une réservation
- **Dépendance cross-module** : Utilise `RestaurantRepository` pour vérifier l'existence et récupérer les informations du restaurant

#### `OrderService`
Orchestre les commandes de plats :
- **Création et consultation** de commandes
- **Coordonne** : `OrderRepository`, `RestaurantRepository`, et `OrderFactory`
- **Validation** : Vérifie l'existence du restaurant via `RestaurantRepository` avant de créer une commande
- **Dépendance cross-module** : Utilise `RestaurantRepository` pour valider que le restaurant existe

#### `SpecialOfferService`
Orchestre les offres spéciales :
- **Création** d'offres promotionnelles pour les restaurants
- **Coordonne** : `SpecialOfferRepository`, `RestaurantRepository`, `SpecialOfferFactory`, et `SpecialOfferDomainService`
- **Validation** :
  - Vérifie l'existence du restaurant via `RestaurantRepository`
  - Valide qu'il n'y a pas de chevauchement d'offres via `SpecialOfferDomainService`
- **Dépendance cross-module** : Utilise `RestaurantRepository` pour valider que le restaurant existe

---

### Couche Domain

#### Agrégats

##### `Restaurant`
**Agrégat** représentant un restaurant avec ses règles métier de base :
- Génère un **UUID unique**
- Compose un objet `Hours` pour les horaires d'ouverture/fermeture
- Fournit des méthodes de recherche et de vérification d'horaires (`matchesName`, `isFullyOpenDuring`)
- La validation des champs est déléguée à `RestaurantFactory`

##### `Reservation`
**Agrégat** représentant une réservation :
- Compose `ReservationTime` (horaires de début/fin) et `Customer` (informations client)
- Ajuste l'heure au prochain créneau de **15 minutes**
- Génère tous les créneaux occupés via `generateAllTimeSlotsInterval()`
- La validation des champs est déléguée à `ReservationFactory`

##### `Order`
**Agrégat** représentant une commande de plats :
- Génère un **UUID unique**
- Compose un objet `Customer` (réutilisé du module Reservation)
- Stocke : `restaurantId`, `date`, `menu` (description du plat)
- Simple agrégat sans logique métier complexe

##### `SpecialOffer`
**Agrégat** représentant une offre spéciale :
- Génère un **UUID unique**
- Stocke : `restaurantId`, `title`, `description`, `validFrom`, `validUntil`
- Fournit la méthode `overlapsWith()` pour détecter les chevauchements de dates
- La validation des champs est déléguée à `SpecialOfferFactory`

#### Value Objects

##### `Hours`
**Value Object** représentant les horaires d'un restaurant :
- Compose `LocalTime` pour `openingHour` et `closingHour`
- Utilise `TimeUtils` pour valider les créneaux de 15 minutes
- Validation : horaire d'ouverture doit être avant l'horaire de fermeture

##### `ReservationTime`
**Value Object** représentant les horaires d'une réservation :
- Stocke `startTime` et `endTime` (LocalTime)
- Simple conteneur de données sans logique complexe

##### `Customer`
**Value Object** réutilisé par `Reservation` et `Order` :
- Stocke : `name`, `email`, `phoneNumber`
- Simple conteneur de données client

##### `OpenedHours`
**Value Object** utilisé pour la validation :
- Représente les heures d'ouverture sous forme de chaînes de caractères
- Utilisé par `RestaurantDomainService` pour la validation

#### Services de Domaine

##### `RestaurantDomainService`
**Service de domaine** contenant la logique métier transversale qui nécessite plusieurs agrégats :
- **Valide** les headers et dates (`convertDateStringToLocalDate`, `validateOpenedHoursHeader`)
- **Calcule les disponibilités** : Analyse les créneaux libres d'un restaurant en fonction des réservations existantes
- **Validation des horaires** : Utilise `OpenedHours` pour valider les heures d'ouverture
- **Vérifie la capacité** : S'assure qu'un restaurant peut accepter une nouvelle réservation
- **Interaction multi-agrégats** : Coordonne `Restaurant`, `Reservation`, et `OpenedHours`

##### `SpecialOfferDomainService`
**Service de domaine** pour la gestion des offres spéciales :
- **Validation** : `validateNoOverlappingOffers()` vérifie qu'une nouvelle offre ne chevauche pas les offres existantes du même restaurant
- **Logique métier** : Une offre ne peut pas avoir de dates qui se chevauchent avec d'autres offres du même restaurant
- Lance `InvalidParameterException` si chevauchement détecté

#### Factories

##### `RestaurantFactory`
**Factory** responsable de la création et validation des restaurants :
- Valide tous les champs avant création (nom, capacité, horaires, durée de réservation, ID propriétaire)
- Crée l'instance `Restaurant` avec validation métier
- Lance `InvalidParameterException` si validation échoue

##### `ReservationFactory`
**Factory** responsable de la création et validation des réservations :
- Utilise `TimeUtils.adjustTimeToSlotIntervalSliceUp()` pour ajuster l'heure au créneau de 15 minutes
- Valide tous les champs requis (date, heure, taille du groupe, informations client)
- Dépend de `Restaurant` (paramètre) pour la validation contextuelle
- Lance `InvalidParameterException` si validation échoue

##### `OrderFactory`
**Factory** responsable de la création des commandes :
- Création simple sans validation complexe
- Délègue au constructeur de `Order`
- Pas de dépendances externes

##### `SpecialOfferFactory`
**Factory** responsable de la création et validation des offres spéciales :
- Valide que `validFrom` est avant `validUntil`
- Valide tous les champs requis (restaurant ID, titre, description, dates)
- Lance `InvalidParameterException` si validation échoue

#### Repositories (Interfaces)

##### `RestaurantRepository`
**Interface** définissant le contrat de persistance des restaurants :
- **Méthodes** : `save()`, `getRestaurantListOfOwner()`, `getRestaurantByIdOfOwner()`, `getRestaurantList()`, `getRestaurantById()`, `delete()`
- **Implémentations** : `InMemoryRestaurantRepository`, `MongoRestaurantRepository`

##### `ReservationRepository`
**Interface** définissant le contrat de persistance des réservations :
- **Méthodes** : `save()`, `getReservationListOfRestaurant()`, `getReservationById()`, `delete()`, `deleteAllReservationOfRestaurant()`
- **Implémentations** : `InMemoryReservationRepository`, `MongoReservationRepository`

##### `OrderRepository`
**Interface** définissant le contrat de persistance des commandes :
- **Méthodes** : `save()`, `getOrderById()`
- **Implémentations** : `InMemoryOrderRepository`, `MongoOrderRepository`

##### `SpecialOfferRepository`
**Interface** définissant le contrat de persistance des offres spéciales :
- **Méthodes** : `save()`, `getSpecialOffersByRestaurantId()`
- **Implémentations** : `InMemorySpecialOfferRepository`, `MongoSpecialOfferRepository`

#### Utilitaires

##### `TimeUtils`
**Classe utilitaire** pour la gestion du temps :
- `adjustTimeToSlotIntervalSliceUp()` : Arrondit l'heure au prochain créneau de 15 minutes
- `getSlotInterval()` : Retourne l'intervalle de créneaux (15 minutes)
- Utilisé par `ReservationFactory`, `Hours`, et `Reservation`

---

### Couche Infrastructure

#### Repositories In-Memory

##### `InMemoryRestaurantRepository`, `InMemoryReservationRepository`, `InMemoryOrderRepository`, `InMemorySpecialOfferRepository`
**Implémentations en mémoire** des repositories :
- Utilisent `HashMap<String, T>` pour le stockage temporaire
- Mode **développement/test** uniquement
- Pas de persistance entre les redémarrages

#### Repositories MongoDB

##### `MongoRestaurantRepository`
**Implémentation MongoDB** de `RestaurantRepository` :
- **Dépendances** : `MongoConnection` (accès Datastore), `RestaurantDocumentMapper` (conversion Domain ↔ Document)
- **Utilise** : `RestaurantDocument` pour la persistance
- **Opérations** : Conversion via mapper, requêtes Morphia avec `Filters`

##### `MongoReservationRepository`
**Implémentation MongoDB** de `ReservationRepository` :
- **Dépendances** : `MongoConnection`, `ReservationDocumentMapper`
- **Utilise** : `ReservationDocument` (avec `CustomerDocument` embedded)
- **Opérations** : Gestion des suppressions en cascade, conversions via mapper

##### `MongoOrderRepository`
**Implémentation MongoDB** de `OrderRepository` :
- **Dépendances** : `MongoConnection`, `OrderDocumentMapper`
- **Utilise** : `OrderDocument` (avec `CustomerDocument` embedded)
- **Opérations** : Stockage et récupération de commandes

##### `MongoSpecialOfferRepository`
**Implémentation MongoDB** de `SpecialOfferRepository` :
- **Dépendances** : `MongoConnection`, `SpecialOfferDocumentMapper`
- **Utilise** : `SpecialOfferDocument`
- **Initialisation** : Configure les index Morphia via `getMapper().map()` et `ensureIndexes()`

#### Document Mappers

##### `RestaurantDocumentMapper`
**Convertisseur bidirectionnel** Restaurant ↔ RestaurantDocument :
- `toDocument()` : Domain → Document (pour insertion MongoDB)
- `toDomain()` : Document → Domain (pour lecture MongoDB)
- **Conversion** : Parse `LocalTime` depuis/vers String pour les horaires

##### `ReservationDocumentMapper`
**Convertisseur bidirectionnel** Reservation ↔ ReservationDocument :
- `toDocument()` : Crée `ReservationDocument` avec `CustomerDocument` embedded
- `toDomain()` : Reconstruit `Reservation` avec `Customer` et `ReservationTime`
- **Conversion** : Parse `LocalDate` et `LocalTime` depuis/vers String

##### `OrderDocumentMapper`
**Convertisseur bidirectionnel** Order ↔ OrderDocument :
- `toDocument()` : Crée `OrderDocument` avec `CustomerDocument` embedded
- `toDomain()` : Reconstruit `Order` avec `Customer`
- **Conversion** : Parse `LocalDate` depuis/vers String

##### `SpecialOfferDocumentMapper`
**Convertisseur bidirectionnel** SpecialOffer ↔ SpecialOfferDocument :
- `toDocument()` : Convertit les dates en String pour MongoDB
- `toDomain()` : Parse les dates String vers `LocalDate`
- **Conversion** : Gestion des dates de validité de l'offre

#### Documents MongoDB

##### `RestaurantDocument`
**Entité Morphia** (@Entity("restaurants")) :
- Champs : `id`, `name`, `capacity`, `openingHour`, `closingHour`, `reservationDuration`, `ownerId`
- Stockage : Toutes les heures en format String

##### `ReservationDocument`
**Entité Morphia** (@Entity("reservations")) :
- Champs : `id`, `date`, `startTime`, `endTime`, `groupSize`, `restaurantId`
- **Embedded** : `customer` (CustomerDocument)
- Stockage : Dates et heures en format String

##### `OrderDocument`
**Entité Morphia** (@Entity("orders")) :
- Champs : `id`, `restaurantId`, `date`, `menu`
- **Embedded** : `customer` (CustomerDocument)
- Stockage : Date en format String

##### `SpecialOfferDocument`
**Entité Morphia** (@Entity("special_offers")) :
- Champs : `id`, `restaurantId`, `title`, `description`, `validFrom`, `validUntil`
- Stockage : Dates de validité en format String

##### `CustomerDocument`
**Document embedded** (@Embedded) :
- Champs : `name`, `email`, `phoneNumber`
- **Réutilisé** par `ReservationDocument` et `OrderDocument`

#### Configuration et Infrastructure

##### `MongoConnection`
**Singleton** gérant la connexion MongoDB :
- **Pattern** : Lazy initialization
- **Dépendances** : `MongoConfig` (configuration), Morphia (`MongoClient`, `Datastore`)
- **Utilisation** : Injecté dans tous les repositories MongoDB
- **Cycle de vie** : Fermé via shutdown hook dans `Main.java`

##### `PersistenceConfig`
**Configuration statique** du mode de persistance :
- **Enum** : `PersistenceMode` (IN_MEMORY ou MONGO)
- **Méthodes** : `isInMemoryMode()`, `isMongoMode()`, `getPersistenceMode()`
- **Utilisation** :
  - `ApplicationBinder` : Choix de l'implémentation repository (InMemory vs Mongo)
  - `SentryConfig` : Détermination de l'environnement (development vs production)

##### `SentryConfig`
**Configuration du monitoring** d'erreurs avec Sentry :
- **Méthode** : `initialize()` - Configure le SDK Sentry
- **Configuration** :
  - DSN depuis `System.getProperty()` ou `System.getenv()`
  - `setTracesSampleRate(1.0)` : Capture 100% des transactions
  - `setEnvironment()` : "production" si Mongo, "development" si InMemory (via `PersistenceConfig`)
- **Dépendance** : `PersistenceConfig.isMongoMode()` (uniquement pour tagging environnement)
- **Pas de lien avec MongoDB** : Envoie les erreurs vers les serveurs Sentry cloud via HTTP

#### Exception Mappers (avec Sentry)

Tous les exception mappers suivent le **pattern Sentry** :
```java
@Override
public Response toResponse(Exception exception) {
    Sentry.captureException(exception);  // Capture pour monitoring
    Sentry.flush(2000);                   // Force l'envoi
    // ... créer ErrorResponse et retourner Response
}
```

##### Exception Mappers disponibles :
- `ConstraintViolationExceptionMapper` : Gère les violations de contraintes
- `IllegalArgumentExceptionMapper` : Gère les arguments invalides
- `NullPointerExceptionMapper` : Gère les pointeurs nuls
- `ProcessingExceptionMapper` : Gère les erreurs de traitement

#### Dependency Injection

##### `ApplicationBinder`
**Configuration HK2** (Jersey) pour l'injection de dépendances :
- **Services** : Bind en singleton (`RestaurantService`, `ReservationService`, `OrderService`, `SpecialOfferService`)
- **Factories** : Bind en singleton (`RestaurantFactory`, `ReservationFactory`, `OrderFactory`, `SpecialOfferFactory`)
- **Domain Services** : Bind en singleton (`RestaurantDomainService`, `SpecialOfferDomainService`)
- **Repositories** : Choix conditionnel basé sur `PersistenceConfig.isInMemoryMode()` :
  - Si InMemory → `InMemory*Repository`
  - Si Mongo → `Mongo*Repository`

---

## Points d'architecture importants

### Séparation des responsabilités
- **API** : Gère HTTP uniquement, pas de logique métier
- **Application** : Orchestre les cas d'utilisation, coordonne Domain et Infrastructure
- **Domain** : Contient la logique métier pure, indépendante de l'infrastructure
- **Infrastructure** : Détails d'implémentation (persistance, monitoring)

### Dépendances cross-module
- `OrderService` → `RestaurantRepository` : Valide l'existence du restaurant
- `SpecialOfferService` → `RestaurantRepository` : Valide l'existence du restaurant
- `ReservationService` → `RestaurantRepository` : Récupère les infos restaurant + capacité
- **Pattern** : Les services peuvent dépendre des repositories d'autres modules pour la validation

### Inversion de dépendances
- Domain définit les **interfaces** de repositories
- Infrastructure **implémente** ces interfaces
- Application dépend des **abstractions**, pas des implémentations
- **Pattern Repository** : Abstraction de la persistance

### Factory Pattern
- Centralise la **création et validation** des agrégats
- Évite la duplication de logique de validation
- Encapsule les règles de construction complexes

### Value Objects
- Immutables et réutilisables (`Customer`, `Hours`, `ReservationTime`)
- Encapsulent la logique de validation simple
- Réduisent la duplication de code

### Monitoring et Observabilité
- **Sentry** : Capture automatique de toutes les exceptions via mappers
- **Tagging environnement** : Différencie dev (InMemory) vs prod (MongoDB)
- **Pattern** : Centralisation du monitoring dans les exception mappers

### Persistance polymorphique
- **2 implémentations** par repository : InMemory (dev/test) + MongoDB (production)
- **Switch runtime** : Basé sur variable d'environnement via `PersistenceConfig`
- **Pattern Strategy** : Choix de l'implémentation à l'exécution

