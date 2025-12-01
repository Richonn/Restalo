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

**Réponse:**

Pour réduire les risques de vulnérabilités dans notre processus de développement, nous avons identifié et implémenté trois pratiques de sécurité essentielles :

### 1. Analyse automatique des dépendances (Software Supply Chain Security)

**Domaine :** Software Supply Chain Security

**Description :**
Cette pratique consiste à scanner automatiquement toutes les dépendances du projet (directes et transitives) pour détecter des vulnérabilités connues avant leur déploiement en production.

**Pourquoi c'est important :**
- Les applications modernes dépendent de centaines de librairies externes
- Une seule dépendance vulnérable peut compromettre toute l'application
- Les nouvelles CVE sont découvertes régulièrement dans des librairies populaires
- 80% des vulnérabilités proviennent de composants tiers (OWASP Top 10)

**Comment nous l'appliquons :**
- Utilisation d'OWASP Dependency-Check intégré au pipeline CI/CD
- Exécution automatique à chaque commit sur les branches principales
- Analyse de toutes les dépendances Maven (directes et transitives)
- Corrélation avec la base de données NVD (National Vulnerability Database)
- Génération de rapports détaillés avec liens vers les CVE et versions corrigées
- Alertes immédiates en cas de nouvelles vulnérabilités détectées

**Résultat :**
Cette pratique nous permet de :
- Identifier rapidement les dépendances vulnérables
- Prioriser les mises à jour en fonction du score CVSS
- Maintenir un inventaire à jour de nos dépendances
- Réduire la surface d'attaque de notre application

---

### 2. Static Application Security Testing - SAST (CI/CD/CS)

**Domaine :** Continuous Security (CI/CD/CS) et DevSecOps

**Description :**
Le SAST (Static Application Security Testing) est une technique d'analyse du code source sans l'exécuter, permettant de détecter des failles de sécurité dès les premières phases du développement.

**Pourquoi c'est important :**
- Détecte les vulnérabilités avant l'exécution du code
- Coût de correction 10x moins élevé en développement qu'en production
- Identifie les erreurs de programmation courantes (OWASP Top 10)
- Permet de former les développeurs sur les bonnes pratiques

**Comment nous l'appliquons :**
- Utilisation de PMD avec les rulesets de sécurité et bonnes pratiques
- Intégration dans le workflow CI/CD (`.github/workflows/security-scan.yml`)
- Analyse automatique à chaque commit
- Détection de :
  - Injections SQL potentielles
  - Cross-Site Scripting (XSS)
  - Hard-coded credentials
  - Gestion incorrecte des exceptions
  - Fuites de ressources
  - Violations de principes SOLID

**Résultat :**
Cette pratique nous permet de :
- "Shift left" la sécurité (détecter tôt dans le cycle)
- Réduire le nombre de bugs en production
- Améliorer la qualité globale du code
- Former l'équipe aux bonnes pratiques de sécurité

---

### 3. Gestion sécurisée des secrets (DevSecOps)

**Domaine :** DevSecOps et SSDLC (Secure Software Development Life Cycle)

**Description :**
Cette pratique vise à empêcher l'exposition de credentials, API keys, tokens et autres informations sensibles dans le code source ou l'historique Git.

**Pourquoi c'est important :**
- Les secrets commitées sont accessibles dans tout l'historique Git (même après suppression)
- Les repos publics sont scannés automatiquement par des bots malveillants
- Une seule clé API exposée peut compromettre tout le système
- Conformité avec les standards de sécurité (PCI-DSS, SOC2, ISO 27001)

**Comment nous l'appliquons :**
- Utilisation de GitHub Secrets pour stocker les clés sensibles :
  - `NVD_API_KEY` pour l'accès à la base de données NVD
  - Variables d'environnement pour les credentials MongoDB
- Fichiers `.env` exclus du versioning (`.gitignore`)
- Template `.env.example` pour documenter les variables nécessaires sans exposer les valeurs
- Injection des secrets uniquement au runtime via variables d'environnement
- Configuration Maven utilisant `${env.VARIABLE}` au lieu de valeurs en dur

**Exemple d'implémentation :**
```xml
<!-- pom.xml - Utilisation sécurisée de l'API key -->
<configuration>
    <nvdApiKey>${env.NVD_API_KEY}</nvdApiKey>
</configuration>
```

```yaml
# .github/workflows/security-scan.yml - Injection du secret
- name: Run OWASP Dependency-Check
  env:
    NVD_API_KEY: ${{ secrets.NVD_API_KEY }}
  run: mvn dependency-check:check
```

**Résultat :**
Cette pratique nous permet de :
- Protéger les credentials de l'équipe et du projet
- Faciliter la rotation des secrets (changement sans commit)
- Respecter le principe de séparation des responsabilités
- Éviter les fuites de données sensibles
- Conformité avec les standards de sécurité

---

## Conclusion

L'intégration de ces trois pratiques dans notre processus de développement nous permet de :
- Détecter et corriger les vulnérabilités en amont
- Réduire significativement les risques de sécurité
- Améliorer la qualité globale du code
- Créer une culture de sécurité au sein de l'équipe

Ces pratiques s'inscrivent dans une démarche DevSecOps où la sécurité est intégrée dès le début du cycle de développement plutôt que d'être ajoutée en fin de processus.

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

**Réponse:**

### Problématique 1 : Temps de revue élevé causé par la taille des Pull Requests

**Description du problème :**
Au début du projet, nos Pull Requests étaient volumineuses car directement liées à des issues de grande taille. Cela entraînait des temps de revue prolongés, parfois jusqu'à 48 heures, créant des périodes d'attente où les développeurs se retrouvaient bloqués pour continuer à avancer sur des fonctionnalités dépendantes. Les reviewers devaient également consacrer beaucoup de temps à examiner de larges changements, ce qui ralentissait la vélocité globale de l'équipe.

**Impact :** Frustration des développeurs en attente, réduction de la productivité, accumulation de PRs en attente de revue, et risque accru de conflits de merge.

**Plan de résolution :**

1. **Décomposition systématique des issues** (ce que nous avons mis en place à mi-parcours) :
   - Diviser chaque grande fonctionnalité en sous-tâches plus petites dès la phase de planification
   - Viser des PRs de 200-300 lignes maximum (hors tests et fichiers générés)
   - Créer des issues intermédiaires qui peuvent être mergées indépendamment

2. **Établir des sessions de revue dédiées** :
   - Gérer les merge des PRs pendant des réunions de groupe
   - Prioriser les PRs bloquantes pendant ces sessions

**Résultat observé :** Après avoir implémenté le découpage des issues à mi-parcours, nous avons constaté une amélioration significative de notre fluidité de travail et une réduction des temps d'attente.

### Problématique 2 : Gestion des tests et méthodologie de validation

**Description du problème :**
Nous avons parfois sous-estimé le temps nécessaire pour tester correctement toutes les fonctionnalités des stories. Les tests étaient souvent implémentés sans une méthodologie très précise, ce qui pouvait rallonger le temps nécessaire pour vérifier toutes les fonctionnalités. Bien que cela n'ait pas entraîné d'erreurs majeures dans le code grâce à notre approche préventive, cette situation a créé du stress et un ralentissement dans la validation des stories, particulièrement lors des phases de revue.

**Impact :** Stress accru lors de la validation, ralentissement du processus de revue, temps de développement moins prévisible, difficulté à estimer le temps nécessaire pour compléter une story.

**Plan de résolution :**

1. **Définir une méthodologie de tests claire dès le début du développement** :
   - Créer une checklist de tests obligatoires pour chaque story, incluant les cas unitaires et d'intégration à vérifier
   - Définir une métrique de couverture minimale pour valider les PRs avant merge
   - Structurer le travail pour savoir exactement quelles fonctionnalités tester à chaque étape
   - Standardiser les approches de test au sein de l'équipe

2. **Intégration des tests dès le développement (Test-Driven Development adapté)** :
   - Écrire les tests unitaires et d'intégration le plus tôt possible lors du développement, au fur et à mesure que les fonctionnalités sont développées
   - Répartir la charge de travail pour détecter rapidement tout problème potentiel
   - Organiser des mini-revues de tests au sein de l'équipe pour partager les méthodes et s'assurer que chacun suit la même approche
   - Uniformiser la qualité et réduire le temps global consacré aux tests

**Bénéfices attendus :** Réduction du stress lors des validations, temps de revue plus prévisibles, meilleure confiance dans la qualité du code, processus de validation plus fluide et standardisé.

---

**Question:**
Décrivez la démarche que vous avez entreprise pour intégrer de nouveaux outils technologiques. Quelles étaient les étapes du processus?

**Réponse:**

Notre processus d'intégration de nouveaux outils technologiques a varié selon la complexité de l'outil et l'expertise disponible dans l'équipe. Nous avons notamment intégré **Sentry** pour la remontée d'erreurs et **OWASP Dependency Check** comme outil SCA (Software Composition Analysis) pour l'analyse de sécurité et de qualité du code. OWASP permet de scanner le projet à la recherche des principales vulnérabilités connues (CVEs) et génère un rapport à chaque exécution. Nous l'avons intégré dans notre pipeline CI/CD afin qu'il s'exécute automatiquement à chaque push ou merge sur la branche principale.

### Étapes du processus d'intégration :

1. **Évaluation du besoin et recherche**
   - Identification du problème à résoudre (monitoring d'erreurs, sécurité)
   - Lecture de la documentation officielle de Sentry et OWASP
   - Recherche sur les technologies disponibles et leur adéquation avec notre stack

2. **Implémentation d'un Proof of Concept (PoC)**
   - Configuration initiale de Sentry et d'OWASP dans le projet
   - Implémentation d'une version minimale pour tester l'intégration avec le reste du système
   - Tests dans l'environnement de développement local

3. **Intégration dans l'environnement de test et CI/CD**
   - Intégration progressive dans la pipeline CI/CD
   - Observation du comportement des outils à chaque exécution
   - Adaptation du PoC aux standards du projet (clean architecture, tests, etc.)

4. **Analyse et correction**
   - Analyse des alertes et vulnérabilités remontées par OWASP
   - Examen des erreurs détectées par Sentry
   - Application progressive des corrections nécessaires

5. **Validation et déploiement**
   - Soumission d'une pull request avec description détaillée de la nouvelle technologie
   - Code review par plusieurs membres de l'équipe
   - Vérification des fonctionnalités à l'aide de tests unitaires et manuels
   - Merge et premiers usages en production

---

**Question:**
Comment avez-vous réagi aux différents bogues? Avez-vous exploré ces outils à l'aide de tests unitaires ou manuels? Qu'avez-vous appris grâce à cette démarche?

**Réponse:**

### Gestion des bogues et exploration des outils

Notre stratégie de gestion des bogues s'est appuyée principalement sur une **approche préventive par les tests** plutôt que réactive. Grâce à cette démarche, nous n'avons pas rencontré de bugs majeurs durant le développement, mais plutôt des bugs mineurs détectés et résolus avant même d'atteindre la branche principale.

Pour chaque problème détecté par Sentry ou OWASP, nous avons suivi une **approche méthodique** :

1. **Identifier et reproduire l'erreur** : Utilisation des rapports Sentry pour comprendre le contexte de l'erreur et les logs OWASP pour identifier la vulnérabilité
2. **Analyser la cause racine** : Examen du code concerné ou de la configuration des outils pour comprendre l'origine du problème
3. **Appliquer les corrections appropriées** : Mise en place de la solution adaptée (correction de code, mise à jour de dépendances, ajustement de configuration)
4. **Vérifier le bon fonctionnement** : Tests unitaires et manuels pour s'assurer que les ajustements n'affectaient pas le bon fonctionnement du projet et que les outils s'exécutaient correctement

Cette démarche nous a permis de mieux comprendre le comportement et les limites des outils tout en garantissant la stabilité du projet.

### Apprentissages clés

Grâce à l'intégration de Sentry et d'OWASP, nous avons appris plusieurs aspects essentiels :

**1. Intégration structurée d'outils externes**
- Comment incorporer de nouveaux outils technologiques dans un projet existant de manière structurée et sécurisée
- La valeur d'avoir des outils automatisés pour détecter les erreurs et vulnérabilités avant qu'elles n'atteignent la production
- L'importance de configurer et intégrer des outils externes dans une pipeline CI/CD de manière efficace

**2. Analyse et priorisation**
- Comment analyser les rapports générés par ces outils (stack traces Sentry, CVE reports OWASP)
- Prioriser les problèmes à corriger en fonction de leur sévérité et impact
- Vérifier l'impact des ajustements sur le reste du projet avant le déploiement

**3. Culture de tests et qualité**
- Renforcement de nos compétences en tests unitaires et manuels
- Comprendre l'importance de tester tôt et souvent pour éviter des problèmes évitables
- L'expertise interne accélère considérablement l'intégration, tandis que l'apprentissage d'outils complexes prend du temps
- Une architecture propre facilite l'ajout de nouveaux outils

**4. Contraintes et limites**
- Mieux comprendre les contraintes et limites des outils utilisés dans un projet réel
- Apprendre à configurer les seuils d'alerte pour éviter les faux positifs
- Savoir quand un outil est approprié et quand il ne l'est pas

---

**Question:**
Quels sont les bons coups de votre équipe? De quelles réalisations êtes-vous fiers? Nommez-en trois.

**Réponse:**

### Bons coups de l'équipe

**1. Une architecture propre et bien structurée dès le début**

Dès le départ du projet, nous avons fait le choix stratégique de privilégier la qualité du code et l'établissement de bonnes pratiques plutôt que la vitesse de développement. Nous avons réussi à séparer clairement les différentes couches du projet (API, application, domaine, infrastructure), ce qui a amélioré la lisibilité du code et facilité sa maintenance.

Cette décision s'est matérialisée par :
- La mise en place d'une architecture propre dès les premières fonctionnalités
- Des tests unitaires et d'intégration intégrés dès le début
- Une documentation claire et à jour tout au long du projet

Cet investissement initial a payé tout au long du projet. Les TP2, TP3 et TP4 ont été nettement moins chronophages car :
- Le code était déjà propre et documenté
- L'architecture permettait d'ajouter facilement de nouvelles fonctionnalités
- Les tests garantissaient la stabilité lors des modifications
- Chaque membre de l'équipe pouvait mieux comprendre le fonctionnement du projet et collaborer plus efficacement

Cette organisation a facilité l'implémentation des nouvelles fonctionnalités et modifications, tout en assurant que le reste du code restait stable et fonctionnel.

**2. Une pipeline CI/CD complète et une organisation GitHub rigoureuse**

Nous sommes fiers d'avoir mis en place une infrastructure de développement professionnelle et complète :

**Pipeline CI/CD automatisée :**
- Intégration de tests unitaires et d'intégration exécutés automatiquement
- Outils de sécurité comme OWASP Dependency Check pour détecter les vulnérabilités
- Monitoring en temps réel avec Sentry pour capturer et analyser les erreurs
- Vérifications automatiques à chaque push et merge

**Organisation GitHub collaborative :**
- GitHub Project clair et à jour tout au long du projet
- Issues bien découpées et structurées avec des milestones
- Pull Requests revues de manière proactive et collaborative
- Processus de code review rigoureux perçu comme un moment d'apprentissage

Ces processus automatisés ont renforcé la qualité du code et assuré que chaque mise à jour passait automatiquement par des vérifications essentielles. Ils ont également permis à l'équipe de travailler avec plus de confiance et de fluidité, sachant que les changements étaient validés et surveillés automatiquement. Cette organisation nous a permis de mieux coordonner notre travail et d'avancer efficacement tout au long du projet.

**3. Une collaboration d'équipe exceptionnelle basée sur la transparence et l'entraide**

Nous avons établi une cohésion forte dans l'équipe grâce à plusieurs pratiques clés :

**Réunions quotidiennes et communication transparente :**
- Réunions d'équipe quotidiennes qui nous ont permis d'apprendre à nous connaître et d'être plus à l'aise
- Communication transparente qui a permis d'avancer rapidement et sans risques de tensions
- Au fil du projet, cette collaboration régulière a renforcé la confiance entre les membres de l'équipe

**Entraide permanente et responsabilité collective :**
- Climat d'équipe basé sur l'entraide et la communication honnête
- Dans le cas où quelqu'un rencontrait une difficulté sur sa tâche, une autre personne avec moins de difficultés ou plus de temps pouvait rapidement s'en occuper
- Cela a permis d'éviter de perdre du temps inutilement et de maintenir la vélocité de l'équipe

**Culture de feedback constructif :**
- Les code reviews étaient perçues comme un moment d'apprentissage, d'amélioration collective et de communication honnête
- Nous sommes fiers d'avoir réussi à maintenir ce climat sans que cela devienne une source de friction
- Au contraire, chaque feedback était une opportunité de progresser ensemble

---

**Question:**
Quel conseil donneriez-vous aux prochains étudiants qui devront réaliser ce projet?

**Réponse:**

### Conseil principal : Privilégiez la qualité à la quantité dès le départ

L'instinct naturel en début de projet est de vouloir avancer vite, coder rapidement pour "avoir quelque chose qui marche". Cette approche semble productive au début, mais elle crée une dette technique qui se paie très cher plus tard : code difficile à maintenir, bugs difficiles à tracer, impossibilité d'ajouter de nouvelles fonctionnalités sans tout casser.

**Notre recommandation : Prenez directement le temps de bien faire les choses, même si cela semble plus lent au début.**

Vous n'êtes pas notés sur la quantité de code produit, mais sur la qualité du résultat final. Un projet avec 3 fonctionnalités parfaitement implémentées, une architecture propre et maintenable, des tests complets, une documentation claire, et un code qui respecte les standards vaut infiniment mieux qu'un projet avec 10 fonctionnalités mal faites, du code spaghetti, pas de tests, documentation inexistante, et bugs à chaque coin de rue.

Si nous devions refaire ce projet, nous ferions exactement la même chose : investir massivement dans la qualité dès le début. Cette approche nous a permis d'avoir une branche `main` stable en permanence (0 bug majeur), d'ajouter de nouvelles fonctionnalités facilement et avec plaisir, de passer les TP suivants sans stress, d'être fiers du résultat final, et d'apprendre des compétences réellement utiles pour notre carrière.

**Notre conseil final : Ne cherchez pas à aller vite, cherchez à bien faire. La vitesse viendra naturellement une fois que vous aurez de bonnes fondations.**

---

**Question:**
Quels apprentissages, astuces ou techniques acquis lors de ce projet pensez-vous pouvoir réutiliser plus tard? Décrivez-en au moins deux. Il peut s'agir d'apprentissages techniques, pratiques, liés au travail d'équipe ou encore au processus de développement.

**Réponse:**

### 1. Apprentissage technique : Architecture logicielle en couches et principes du Clean Code

**Compétence acquise :**
La capacité à concevoir et implémenter une architecture logicielle en couches (layered architecture) en respectant les principes du Domain-Driven Design (DDD) et du Clean Code.

**Concrètement, nous avons appris à :**
- Structurer un projet en couches avec responsabilités claires
- Appliquer les principes SOLID
- Utiliser des Design Patterns appropriés
- Respecter les conventions Clean Code

**Pourquoi c'est réutilisable :**
Ces compétences sont fondamentale pour toute carrière en développement logiciel. Les projets professionnels utilisent ces patterns (microservices, architectures hexagonales, DDD). Une architecture propre permet de faire évoluer un projet sur des années. La structure claire améliore la collaboration en équipe. Le code compréhensible réduit le temps de maintenance. Ces principes s'appliquent à tous les langages (Java, C#, Python, TypeScript, etc.).

### 2. Apprentissage pratique : Travail d'équipe efficace avec transparence et communication

**Compétence acquise :**
La capacité à travailler efficacement en équipe sur un projet de développement logiciel avec des pratiques de collaboration qui minimisent les frictions et maximisent la productivité.

**Concrètement, nous avons appris à :**
- Établir un cadre de collaboration clair dès le départ
- Pratiquer la transparence systématique
- Gérer les conflits et blocages de manière constructive
- Maintenir la qualité sans sacrifier la vélocité

**Pourquoi c'est réutilisable :**
Ces compétences sont essentielles dans tout environnement professionnel : travail en entreprise (développement logiciel moderne = travail d'équipe distribué), projets open source (collaboration avec des contributeurs du monde entier), startups (vélocité élevée avec qualité maintenue), grandes entreprises (coordination de multiples équipes sur un même codebase), et remote work (communication asynchrone efficace, de plus en plus courant).

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
