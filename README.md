# Restalo

[![CI - Modular](https://github.com/IFT-3003-A25-Equipe-7/Restalo/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/IFT-3003-A25-Equipe-7/Restalo/actions/workflows/ci.yml)

[![CD - Docker Deploy](https://github.com/IFT-3003-A25-Equipe-7/Restalo/actions/workflows/cd.yml/badge.svg?branch=main)](https://github.com/IFT-3003-A25-Equipe-7/Restalo/actions/workflows/cd.yml)

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

**Restalo** est une API REST moderne pour la gestion de réservations de restaurants. Développée en Java avec Jersey (JAX-RS), elle offre une solution complète pour gérer les restaurants, les clients, et leurs réservations avec une base de données MongoDB.

## Fonctionnalités principales

- Gestion complète des restaurants (création, modification, consultation)
- Système de réservations avec validation
- Gestion des clients
- API RESTful suivant les bonnes pratiques
- Persistance avec MongoDB via Morphia ODM
- Pipeline CI/CD automatisé avec GitHub Actions
- Conteneurisation Docker

## Documentation

- [Guide de contribution](CONTRIBUTING.md) - Comment contribuer au projet
- [Code de conduite](CODE_OF_CONDUCT.md) - Nos standards communautaires
- [Licence](LICENSE) - Licence MIT

## Démarrage rapide

### Prérequis

- Java 21
- Maven 3.x
- Docker & Docker Compose (pour MongoDB)

### Installation

1. Clonez le repository :
   ```bash
   git clone https://github.com/IFT-3003-A25-Equipe-7/Restalo.git
   cd Restalo
   ```

2. Configurez les variables d'environnement :
   ```bash
   cp .env.example .env
   # Éditez .env selon vos besoins
   ```

3. Démarrez MongoDB avec Docker :
   ```bash
   docker-compose up -d
   ```

   Pour plus de détails sur la configuration de la base de données, consultez [DATABASE_SETUP.md](DATABASE_SETUP.md).

4. Compilez et lancez l'application :
   ```bash
   mvn clean compile
   mvn exec:java
   ```

L'API sera accessible sur `http://localhost:8080`.

## Commandes Maven

### Clean

```
mvn clean
```

### Compilation

```
mvn compile
```

### Exécution

```
mvn exec:java
```

### Tests

Lancer tous les tests :

```
mvn test
```

Lancer les tests avec rapport de couverture :

```
mvn clean test jacoco:report
```

Le rapport de couverture sera disponible dans `target/site/jacoco/index.html`.

---

## Qualité du code

Le projet utilise plusieurs outils de qualité et de formatage pour garantir un code propre et maintenable.

### Spotless (formatage automatique)

- Appliquer le formatage :

```
mvn spotless:apply
```

- Vérifier sans corriger (échoue si non conforme) :

```
mvn spotless:check
```

### Checkstyle (style et conventions)

- Vérifier le respect des conventions Google :

```
mvn checkstyle:check
```

### SpotBugs (bugs potentiels)

- Analyser le code pour détecter des bugs fréquents :

```
mvn spotbugs:check
```

### Vérification complète

Pour lancer tous les outils de qualité en même temps :

```
mvn verify
```

Cela exécutera Checkstyle et SpotBugs.

---

## Pipeline CI/CD

Le projet utilise GitHub Actions pour l'intégration continue (CI) et le déploiement continu (CD).

### Intégration Continue (CI)

Le pipeline CI s'exécute automatiquement sur :
- Tous les **pull requests** (toutes les branches)
- Tous les **push** vers la branche `main`

#### Étapes du pipeline CI

1. **Build** (`.github/workflows/build.yml`)
   - Vérification Checkstyle
   - Compilation du projet (`mvn clean package -DskipTests`)
   - Publication du JAR comme artifact (`restalo-jar`)

2. **Tests unitaires** (`.github/workflows/unit-tests.yml`)
   - Exécution des tests avec Maven (`mvn clean test`)
   - Publication des rapports de tests comme artifact (`test-reports`)

3. **Linting** (`.github/workflows/linting.yml`)
   - Vérification Checkstyle (`mvn checkstyle:check`)

4. **Résumé** (`.github/workflows/summary.yml`)
   - Génération d'un résumé visuel des résultats du pipeline
   - Affichage du statut de chaque étape (Build, Tests, Linting)

#### Accéder aux artifacts

Les artifacts sont disponibles dans l'onglet **Actions** de GitHub :
1. Sélectionner le workflow concerné
2. Descendre à la section **Artifacts**
3. Télécharger :
   - `restalo-jar` : fichier JAR compilé
   - `test-reports` : rapports de tests unitaires

### Déploiement Continu (CD)

Le pipeline CD s'exécute automatiquement sur :
- Push vers la branche `main`
- Création de tags
- Déclenchement manuel (`workflow_dispatch`)

#### Étapes du pipeline CD

1. **Build et publication Docker** (`.github/workflows/cd.yml`)
   - Construction de l'image Docker
   - Publication vers GitHub Container Registry (ghcr.io)
   - Tags générés :
     - `latest` : dernière version de la branche main
     - `<commit-sha>` : version spécifique basée sur le commit
     - `<tag-name>` : version basée sur les tags Git

#### Utiliser l'image Docker

```bash
# Récupérer la dernière version
docker pull ghcr.io/ift-3003-a25-equipe-7/restalo:latest

# Récupérer une version spécifique
docker pull ghcr.io/ift-3003-a25-equipe-7/restalo:<commit-sha>
docker pull ghcr.io/ift-3003-a25-equipe-7/restalo:<tag-name>

# Exécuter le conteneur
docker run -p 8080:8080 ghcr.io/ift-3003-a25-equipe-7/restalo:latest
```

### Visualiser les résultats

- **GitHub Actions** : Onglet `Actions` du repository
- **Résumé du pipeline** : Disponible dans chaque exécution du workflow CI
- **Artifacts** : Section `Artifacts` dans les détails de chaque workflow

---

## Avant chaque commit

- `mvn spotless:apply`
- `mvn verify`

Corrigez les erreurs indiquées par Checkstyle/SpotBugs avant de push.

---

## Contribuer

Nous accueillons chaleureusement les contributions de tous ! Que vous souhaitiez corriger un bug, améliorer la documentation ou proposer une nouvelle fonctionnalité, votre aide est précieuse.

Avant de contribuer, veuillez :
1. Lire le [Guide de contribution](CONTRIBUTING.md)
2. Consulter le [Code de conduite](CODE_OF_CONDUCT.md)
3. Vérifier les issues existantes

## Licence

Ce projet est sous licence MIT. Consultez le fichier [LICENSE](LICENSE) pour plus de détails.

