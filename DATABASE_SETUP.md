# Configuration de la Base de Données

Ce document explique comment configurer et utiliser la persistance des données dans l'application Restalo.

## Modes de Persistance

L'application supporte deux modes de persistance :

### 1. Mode In-Memory (par défaut)

Le mode in-memory utilise une base de données en mémoire qui est réinitialisée à chaque redémarrage de l'application.

**Démarrage en mode in-memory :**

```bash
mvn exec:java
```

Ou explicitement :

```bash
mvn exec:java -Dpersistence=inmemory
```

### 2. Mode MongoDB

Le mode MongoDB utilise une base de données MongoDB externe pour persister les données.

**Prérequis :**

- Variables d'environnement configurées :
  - `MONGO_CLUSTER_URL` : URL de connexion MongoDB
  - `MONGO_DATABASE` : Nom de la base de données

## Installation de MongoDB avec Docker

### Utiliser Docker Compose

1. **Créer le fichier .env:**
   ```bash
   cp .env.example .env
   ```

   Le fichier `.env` contient vos credentials MongoDB (ignoré par Git pour la sécurité).

2. **Démarrer MongoDB:**
   ```bash
   docker compose up -d
   ```

   Cette commande démarre :
   - MongoDB sur le port 27017
   - Mongo Express (interface web) sur le port 8081

3. **Arrêter MongoDB:**
   ```bash
   docker compose down
   ```

4. **Arrêter et supprimer les données:**
   ```bash
   docker compose down -v
   ```

## Accès à l'Interface Web Mongo Express

Lorsque MongoDB est démarré avec Docker Compose, vous pouvez accéder à l'interface web Mongo Express :

- URL : [http://localhost:8081](http://localhost:8081)
- Pas d'authentification requise (configuré pour le développement local)

## Configuration de Connexion

Les paramètres de connexion MongoDB sont définis dans le fichier `.env` :

- **Username** : Défini dans `MONGO_ROOT_USERNAME`
- **Password** : Défini dans `MONGO_ROOT_PASSWORD`
- **Port** : Défini dans `MONGO_PORT`
- **URL** : Défini dans `MONGO_CLUSTER_URL` (format: `mongodb://username:password@localhost:port`)

⚠️ **Important:** Ne jamais commiter le fichier `.env` dans Git! Il contient des informations sensibles.

## Exemple d'utilisation

```bash
# 1. Créer votre fichier .env
cp .env.example .env

# 2. Démarrer MongoDB
docker compose up -d

# 3. Démarrer l'application en mode MongoDB
mvn exec:java -Dpersistence=mongo
```

## Vérification du Mode

Au démarrage, l'application affiche le mode de persistance utilisé :

```
Starting application with persistence mode: inmemory
```

ou

```
Starting application with persistence mode: mongo
```
