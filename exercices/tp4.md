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
Décrivez deux problématiques liées à votre processus et proposez deux plans distincts pour les résoudre. Soyez
constructifs dans vos critiques et évitez d'attribuer la faute à une personne ou un groupe en particulier.

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

![alt text](screen-shots/)

### Screenshots du Milestone (titre, description, date, issues)

![alt text](screen-shots/)

### Screenshots de 3 issues

#### Issue 1

![alt text](screen-shots/)

#### Issue 2

![alt text](screen-shots/)

#### Issue 3 (Parent-issue)

![alt text](screen-shots/)

### Screenshot de 3 Pull Requests (avec reviews)

#### Pull request 1
![alt text](screen-shots/)

#### Pull request 2
![alt text](screen-shots/)

#### Pull request 3
![alt text](screen-shots/)

### Screenshots de 'arbre de commits et branches (≥10 commits)

![alt text](screen-shots/)

---

## Diagramme d'architecture

![alt text](screen-shots/.png)

---
