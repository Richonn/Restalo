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

## Tests

1. Quelle proportion de temps passez-vous à implémenter le code fonctionnel par rapport aux tests? Cette proportion évolue-t-elle avec le temps? Pourquoi?
***Réponse :*** ***Plus de temps à implémenter les tests (apprentissage de comment coder des tests correctement de façon clean ainsi que les frameworks, etc). Elle diminue avec le temps plus que cela deviens naturel (courbe d'aprentissage)***

2. L'implémentation des tests augmente naturellement la charge de travail. Comment cela a-t-il affecté votre processus? (ex. : taille des issues/PRs, temps d'implémentation, planification, etc.)
***Réponse :*** ***Oui évidemment. La taille des issues n'a pas nécessairement grandit (division), mais la charge de travail par membre oui, puisque sa reste que celui qui dévloppe la feature fait les tests relier en général. Le temps d'implémentation combiner et donc plus long. Pas vraiment besoin de plus de planification.***

3. Avez-vous davantage confiance en votre code maintenant que vous avez des tests? Justifiez votre réponse.
***Réponse :*** ***Oui, cela permet d'assurer que le code fonctionne adéquatement (ce qui est tester et correctement). Ainsi, il est plus facile de pouvoir ce concentrer sur d'autres éléments sans craindre les sections de code bien tester.***

4. Que pouvez-vous faire pour améliorer la qualité de vos tests? Donnez au moins **trois** solutions.
***Réponse :*** ***Plus de fixture, une meilleur division des concepts/rôle et une meilleur clarter/nomenclature (nom des variables, méthodes (parfois), etc)***

---

## Story 1 – Modifier un restaurant
En tant que restaurateur, je peux modifier les informations de mon restaurant existant.

### Critères de succès
- Accès restreint : Seul le propriétaire du restaurant peut le modifier. Cette condition doit
être validée par un header Owner: string dans la requête.
- Champs modifiables :
    - Nom (name) : Ne peut pas être vide.
    - Capacité (capacity) : Doit être supérieure ou égale à 1.
    - Horaires (hours) :
        - open : L’heure d’ouverture ne peut pas être après minuit.
        - close : L’heure de fermeture doit être avant minuit.
    - Configuration des réservations (reservations.duration) :
        - La durée des réservations en minutes. Par défaut, elle est de 60 minutes.
        - Si spécifiée, elle doit être un nombre entier positif.

### Spécifications

#### Requêtes

**Path:**
```PATCH /restaurants/<id>```

**Headers:**
Owner: string (ID du restaurateur)

**Body:**
Le body doit inclure uniquement les champs modifiés (par exemple, si seuls le nom et la capacité sont modifiés, seuls ces champs doivent être envoyés).

**Exemple de body:**

```json
{
    "name": "La Botega",
    "capacity": 20,
    "hours": {
        "open": "10:00:00",
        "close": "22:00:00"
    },
    "reservations": {
        "duration": 90
    }
}
```

#### Réponses
```200 OK : Mise à jour réussie.```

**Exemple de réponse :**

```json
{
    "id": "983db20h98sad",
    "name": "La Botega",
    "capacity": 20,
    "hours": {
        "open": "10:00:00",
        "close": "22:00:00"
    },
    "reservations": {
        "duration": 90
    }
}
```

```400 BAD REQUEST : Erreur de validation (paramètre manquant ou invalide).```

**Exemple de réponse d'erreur :**

```json
{
    "error": "INVALID_PARAMETER",
    "description": "La capacité doit être supérieure ou égale à 1."
}
```

## Story 2 - Ajouter une offre spéciale
En tant que restaurateur, je peux ajouter une offre spéciale (par exemple, un menu saisonnier, un repas combiné, etc.) à mon restaurant, sfin que les clients soient informés de la promotion disponible.

### Critères de succès
- Les restaurateurs peuvent ajouter des offres spéciales comme des menus saisonniers,
des repas combinés, etc., dans leur profil de restaurant.
- Ces offres apparaissent dans les résultats de recherche lorsque les utilisateurs
recherchent des restaurants.
- Chaque offre doit inclure :
    - Un titre.
    - Une description.
    - La période de validité (date de début et date de fin).
    - Des conditions spéciales éventuelles (par exemple, disponible uniquement les week-ends).

### Spécifications

#### Requêtes

**Path :**
```POST /restaurants/<id>/special-offers```

**Body :**

```json
{
    "offer": {
        "title": "Winter Menu",
        "description": "A special winter-themed menu available on weekends.",
        "validFrom": "2024-12-01",
        "validUntil": "2024-12-31"
    }
}
```

#### Réponses

```201 CREATED : Offre spéciale ajoutée avec succès.```

**Exemple de réponse :**

```json
{
    "offer": {
        "title": "Winter Menu",
        "description": "A special winter-themed menu available on weekends.",
        "validFrom": "2024-12-01",
        "validUntil": "2024-12-31"
    }
}
```

```400 BAD REQUEST : Erreur de validation (paramètre manquant ou invalide).```

**Exemple de réponse d'erreur :**

```json
{
    "error": "INVALID_PARAMETER",
    "description": "Le titre de l'offre ne peut pas être vide."
}
```

```404 NOT FOUND : Le restaurant n'existe pas.```
**Exemple de réponse d'erreur :**

```json
{
    "error": "NOT_FOUND",
    "description": "Le restaurant spécifié n'existe pas."
}
```

## Story 3 - Obtenir une commande

<!-- to complete -->

## Story 4 - Créer une commande

<!-- to complete -->

---

## Captures d'écran

### Screenshot du GitHub Project (colonnes + issues)

![alt text](screen-shots/rendu_3/)

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

---
