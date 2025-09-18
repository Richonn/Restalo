# Exercices - TP1

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

## Avant chaque commit

- `mvn spotless:apply`
- `mvn verify`

Corrigez les erreurs indiquées par Checkstyle/SpotBugs avant de push.