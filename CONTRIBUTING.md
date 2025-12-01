# Guide de contribution à Restalo

Merci de votre intérêt pour contribuer à Restalo ! Ce document vous guidera à travers le processus de contribution.

## Table des matières

- [Code de conduite](#code-de-conduite)
- [Comment contribuer](#comment-contribuer)
- [Configuration de l'environnement](#configuration-de-lenvironnement)
- [Processus de développement](#processus-de-développement)
- [Standards de code](#standards-de-code)
- [Processus de soumission](#processus-de-soumission)
- [Types de contributions](#types-de-contributions)

## Code de conduite

Ce projet adhère au [Code de conduite Contributor Covenant](CODE_OF_CONDUCT.md). En participant, vous vous engagez à respecter ce code. Veuillez signaler tout comportement inacceptable aux mainteneurs du projet.

## Comment contribuer

### Avant de commencer

1. **Cherchez les issues existantes** : Vérifiez si quelqu'un travaille déjà sur ce que vous souhaitez faire
2. **Discutez des changements majeurs** : Pour les grandes fonctionnalités, ouvrez d'abord une issue pour discuter de votre approche
3. **Lisez la documentation** : Familiarisez-vous avec le [README](README.md) et l'architecture du projet

### Trouver quelque chose sur quoi travailler

- Consultez les [issues ouvertes](../../issues)
- Cherchez les issues avec le label `good first issue` pour les contributions débutantes
- Cherchez les issues avec le label `help wanted` pour les tâches prioritaires

## Configuration de l'environnement

### Prérequis

- **Java 21** (JDK)
- **Maven 3.x**
- **Docker & Docker Compose** (pour MongoDB)
- Un IDE Java (IntelliJ IDEA, Eclipse, VS Code recommandé)

### Installation

1. **Clonez le repository** :
   ```bash
   git clone https://github.com/IFT-3003-A25-Equipe-7/Restalo.git
   cd Restalo
   ```

2. **Configurez les variables d'environnement** :
   ```bash
   cp .env.example .env
   # Éditez .env selon vos besoins
   ```

3. **Démarrez MongoDB avec Docker** :
   ```bash
   docker-compose up -d
   ```

4. **Compilez le projet** :
   ```bash
   mvn clean compile
   ```

5. **Lancez les tests** :
   ```bash
   mvn test
   ```

6. **Exécutez l'application** :
   ```bash
   mvn exec:java
   ```

L'API devrait être accessible sur `http://localhost:8080`.

## Processus de développement

### 1. Créer une branche

Créez une branche depuis `main` avec un nom descriptif :

```bash
git checkout -b <numero-issue>-courte-description
```

Exemples :
- `42-ajout-validation-email`
- `73-correction-bug-reservation`

### 2. Développer votre fonctionnalité

- Écrivez du code clair et bien structuré
- Ajoutez des tests unitaires pour toute nouvelle fonctionnalité
- Faites des commits atomiques avec des messages descriptifs

### 3. Tester votre code

Avant de soumettre, assurez-vous que :

```bash
# Formatage automatique avec Google Java Format
mvn spotless:apply

# Vérification complète (tests + qualité)
mvn verify

# Vérification Checkstyle
mvn checkstyle:check
```

Toutes ces commandes doivent réussir sans erreurs.

### 4. Gardez votre branche à jour

```bash
git fetch origin
git rebase origin/main
```

## Standards de code

### Style de code

Le projet utilise le **Google Java Style Guide** appliqué automatiquement par Spotless.

- **Exécutez toujours** `mvn spotless:apply` avant de commiter
- L'indentation utilise **2 espaces** (pas de tabulations)
- Longueur maximale de ligne : **100 caractères**

### Qualité du code

Le projet applique les standards suivants :

- **Checkstyle** : Conventions Google Java
- **JaCoCo** : Couverture de tests (visez >80% pour le nouveau code)
- **Tests unitaires** : Utilisez JUnit 5, Mockito, et Google Truth

### Conventions de nommage

- **Classes** : `PascalCase` (ex: `ReservationService`)
- **Méthodes** : `camelCase` (ex: `createReservation()`)
- **Constantes** : `UPPER_SNAKE_CASE` (ex: `MAX_CAPACITY`)
- **Packages** : `lowercase` (ex: `ca.ulaval.glo2003.reservation`)

### Structure des tests

```java
@Test
public void methodName_scenario_expectedBehavior() {
    // Given (Arrange)
    // ... préparation

    // When (Act)
    // ... action

    // Then (Assert)
    // ... vérification
}
```

## Processus de soumission

### Commits

Utilisez des messages de commit clairs et descriptifs :

```
[TYPE] Courte description (max 50 caractères)

Description détaillée si nécessaire (max 72 caractères par ligne).
Expliquez le "pourquoi" plutôt que le "quoi".
```

Types de commits :
- `[ADD]` : Nouvelle fonctionnalité
- `[FIX]` : Correction de bug
- `[REFACTOR]` : Refactorisation sans changement de comportement
- `[TEST]` : Ajout ou modification de tests
- `[DOC]` : Documentation
- `[FORMAT]` : Formatage, points-virgules manquants, etc.

### Pull Requests

1. **Poussez votre branche** :
   ```bash
   git push origin <numero-issue>-courte-description
   ```

2. **Ouvrez une Pull Request** sur GitHub avec :
   - Un titre clair décrivant le changement
   - Une description détaillée incluant :
     - Le problème résolu
     - L'approche choisie
     - Les tests ajoutés
     - Des captures d'écran si pertinent

3. **Assurez-vous que les CI checks passent** :
   - Build réussit
   - Tests unitaires passent
   - Checkstyle valide
   - Aucun conflit avec `main`

4. **Répondez aux commentaires de review** :
   - Soyez ouvert aux suggestions
   - Discutez si vous n'êtes pas d'accord
   - Marquez les conversations comme résolues

5. **Une fois approuvée**, un mainteneur mergera votre PR

### Critères d'acceptation

Votre PR doit :
- Passer tous les tests CI/CD
- Maintenir ou améliorer la couverture de tests
- Suivre les standards de code
- Être revue et approuvée par au moins un mainteneur
- Avoir des commits atomiques et bien nommés
- Inclure de la documentation si nécessaire

## Types de contributions

### Signaler un bug

Ouvrez une issue avec :
- Une description claire du problème
- Les étapes pour reproduire
- Le comportement attendu vs observé
- Votre environnement (OS, Java version, etc.)

### Proposer une fonctionnalité

Ouvrez une issue avec :
- Le problème que cela résout
- Votre solution proposée
- Des alternatives considérées
- Des maquettes/exemples si applicable

### Améliorer la documentation

La documentation est aussi importante que le code ! Vous pouvez :
- Corriger des typos
- Clarifier des explications
- Ajouter des exemples
- Traduire la documentation

### Ajouter des tests

Les tests sont toujours bienvenus :
- Tests unitaires manquants
- Tests d'intégration
- Amélioration de la couverture

## Besoin d'aide ?

- Consultez le [README](README.md) pour la documentation technique
- Lisez les issues existantes
- Posez vos questions dans une nouvelle issue avec le label `question`

## Ressources

- [Documentation Java 21](https://docs.oracle.com/en/java/javase/21/)
- [Guide Maven](https://maven.apache.org/guides/)
- [Jersey Framework](https://eclipse-ee4j.github.io/jersey/)
- [MongoDB Java Driver](https://www.mongodb.com/docs/drivers/java/sync/current/)

---

**Merci d'avoir lu ce guide !** Vos contributions, grandes ou petites, sont appréciées. 🎉
