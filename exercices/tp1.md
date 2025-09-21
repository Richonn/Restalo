# Exercices - TP1

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

### Avant chaque commit

- `mvn spotless:apply`
- `mvn verify`

Corrigez les erreurs indiquées par Checkstyle/SpotBugs avant de push.

---

## Normes de Commit Git

Afin de garantir la lisibilité et la cohérence de l’historique Git, nous utilisons une convention stricte pour les messages de commit.
Chaque message doit être **clair et concis**, de manière à comprendre les modifications apportées sans avoir besoin de consulter directement le code.

### Structure du message de commit

Un message de commit doit commencer par un **tag** entre crochets (`[ ]`), indiquant le type de modification effectuée, suivi d’une brève description.

#### Tags disponibles

- **[ADD]**: Ajout d’un ou plusieurs fichiers
  Exemple :

  ```bash
  git commit -m "[ADD] readme.md"
  ```

- **[FIX]**: Correction d’un bug
  Exemple :

  ```bash
  git commit -m "[FIX] login error when password is empty"
  ```

- **[FEAT]**: Nouvelle fonctionnalité terminée et opérationnelle sur votre branche
  Exemple :

  ```bash
  git commit -m "[FEAT] User Registration feature"
  ```

- **[RM]**: Suppression de fichiers ou de code devenu inutile
  Exemple :
  ```bash
  git commit -m "[RM] deprecated config file"
  ```

### Bonnes pratiques

- Le message doit être rédigé en **anglais ou français**, mais rester **uniforme** dans tout le projet.
- Évitez les messages vagues comme _"update"_ ou _"changes"_.
- Utilisez des verbes à l’infinitif ou une description brève de l’action.

### Source

Inspiration du model definit par [Conventional Commits](https://www.conventionalcommits.org/fr/v1.0.0/)

---

## Stratégie de Branches Git

Afin de garder un historique clair et une organisation structurée du projet, nous suivons une convention stricte pour la création et la gestion des branches.

### Règles générales

- Une **branche par fonctionnalité**.
- Les branches doivent toujours être créées à partir de `main`.
- La création d’une branche se fait directement via **GitHub** à partir du ticket correspondant dans le projet.

### Étapes pour créer une branche

1. Accédez au ticket relié à la fonctionnalité sur GitHub.
2. Cliquez sur **"Create a branch"** dans le menu du ticket.
3. Modifiez le nom de la branche pour qu’il respecte notre norme.

### Nommage des branches

Le nom d’une branche doit suivre la structure :

```
X-SCOPE-FEATURE
```

- **X** : Numéro du ticket ou identifiant de la tâche
- **SCOPE** : Domaine concerné (`backend`, `frontend`, `devops`, `processus`, `tests`).
- **FEATURE** : Brève description de la fonctionnalité, en _kebab-case_ (mots séparés par des tirets `-`)

#### Exemple

Pour le ticket n°2 concernant l’obtention d’un restaurant côté backend :

```
2-backend-get-restaurant-by-id
```

### Bonnes pratiques

- Utiliser des noms de branche **courts mais explicites**.
- Toujours associer une branche à un **ticket GitHub**.
- Ne pas travailler directement sur `main`.

### Source

Inspiré par le  [model de Vincent Driessen](https://nvie.com/posts/a-successful-git-branching-model/)

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

Nous certifions que le code soumis est le fruit de notre travail personnel, avec l'appui de l'outil mentionné.
