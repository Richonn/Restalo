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
