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
- Évitez les messages vagues comme *"update"* ou *"changes"*.
- Utilisez des verbes à l’infinitif ou une description brève de l’action.

---

En suivant ces conventions, nous garantissons un historique Git plus **lisible**, **professionnel** et **facilement exploitable** par tous les membres de l’équipe.
