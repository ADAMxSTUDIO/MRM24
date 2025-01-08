# MRM24

Application pour Maison de Retraite Médicalisée sous JEE

## Introduction
L’objectif du projet est de développer une application de bureau (desktop) pour la gestion d’une maison de retraite médicalisée. Cette application doit permettre la gestion des patients, du personnel, des rendez-vous médicaux et des chambres. L’application sera conçue avec les technologies suivantes :

- **Java Standard Edition (JSE)**
- **Hibernate (JPA)**
- **JavaFX** pour l’interface utilisateur
- **Architecture GUI-DAO-Métier**
- **Base de données Oracle (replaced by MySQL)**

Le projet est limité à 4 classes métier et une interface qui contiendra toutes les méthodes à implémenter.

---

## Conception de la Base de Données

### Modèle Conceptuel de Données (MCD)

#### Entités principales :
1. **Patient**
   - ID (Primary Key)
   - Nom
   - Prénom
   - Date de naissance
   - Adresse
   - Numéro de téléphone
   - Antécédents médicaux

2. **Personnel**
   - ID (Primary Key)
   - Nom
   - Prénom
   - Fonction (ex : Infirmier, Médecin)
   - Spécialité
   - Numéro de téléphone
   - Email

3. **RendezVous**
   - ID (Primary Key)
   - Date et heure
   - PatientID (Foreign Key)
   - PersonnelID (Foreign Key)
   - Raison

4. **Chambre**
   - ID (Primary Key)
   - Numéro de chambre
   - Type (individuelle/double)
   - Disponibilité (oui/non)

---

## Architecture Logicielle

L’application suivra une architecture GUI-DAO-Métier :

- **Couche GUI (Graphical User Interface)** : Utilise JavaFX pour créer des interfaces intuitives permettant aux utilisateurs de naviguer facilement dans l’application.
- **Couche Métier** : Contient les classes Java représentant la logique fonctionnelle (Patient, Personnel, RendezVous, Chambre).
- **Couche DAO (Data Access Object)** : Assure l’interaction avec la base de données via Hibernate.

### Classes Métier
1. **Patient**
2. **Personnel**
3. **RendezVous**
4. **Chambre**

### Interface Commune
**GestionMaisonRetraite** :
```java
public interface GestionMaisonRetraite {
    void add(T o);
    T get(Long id);
    List<T> getAll();
    void modify(T o, Long id);
    void delete(Long id);
}
```

Chaque classe métier implémentera cette interface pour garantir une gestion uniforme des entités.

---

## Fonctionnalités de l’Application

### Gestion des Patients
- Ajouter un patient
- Modifier les informations d’un patient
- Supprimer un patient
- Rechercher un patient par ID
- Lister tous les patients

### Gestion du Personnel
- Ajouter un membre du personnel
- Modifier les informations du personnel
- Supprimer un membre du personnel
- Rechercher un membre du personnel par ID
- Lister tout le personnel

### Gestion des Rendez-Vous
- Planifier un rendez-vous
- Modifier les détails d’un rendez-vous
- Annuler un rendez-vous
- Rechercher un rendez-vous par ID
- Lister tous les rendez-vous

### Gestion des Chambres
- Ajouter une chambre
- Modifier les informations d’une chambre
- Supprimer une chambre
- Rechercher une chambre par ID
- Lister toutes les chambres

---

## Roadmap de Production

### Phase 1 : Analyse et Conception
1. Analyse des besoins.
2. Conception du Modèle Conceptuel de Données (MCD).
3. Conception des classes métier et de l’interface.
4. Validation de la conception.

### Phase 2 : Développement
1. Implémentation de la base de données Oracle.
2. Création des entités JPA avec Hibernate.
3. Développement des classes DAO pour chaque entité.
4. Développement des classes métier.
5. Développement de l’interface utilisateur avec JavaFX.

### Phase 3 : Tests
1. Tests unitaires des méthodes métier.
2. Tests des interactions avec la base de données.
3. Tests fonctionnels de l’interface utilisateur.
4. Correction des anomalies.

### Phase 4 : Livraison
1. Documentation technique et utilisateur.
2. Livraison de l’application.

---

## Détails Techniques

### Outils et Technologies
- **IDE** : IntelliJ IDEA / Eclipse
- **Base de données** : Oracle Database
- **Frameworks** : Hibernate (JPA)
- **Bibliothèque graphique** : JavaFX
- **Gestion des dépendances** : Maven

### Environnement de Développement
- JDK 17+
- Oracle Database 19c
- Hibernate 6+
- JavaFX 20+

---

## Conclusion

Ce projet permettra de gérer efficacement les activités d’une maison de retraite médicalisée. Avec une architecture modulaire et des technologies modernes, l’application offrira une base robuste et une interface intuitive pour ses utilisateurs.
