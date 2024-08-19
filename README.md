# Projet MDD - Réseau Social de Programmation

## Description

Le projet MDD est un réseau social dédié aux passionnés de programmation informatique. Il permet aux utilisateurs de s'inscrire, de se connecter, de consulter et de gérer leurs abonnements à des thèmes spécifiques, et de publier ou commenter des articles sur divers sujets liés à la programmation.

Ce projet est divisé en deux parties :

- **Front-end** : développé avec Angular, il offre une interface utilisateur interactive pour accéder aux différentes fonctionnalités.
- **Back-end** : développé avec Spring Boot, il fournit une API RESTful pour gérer les utilisateurs, les abonnements, les articles, et les commentaires.

## Fonctionnalités

### Gestion des Utilisateurs

- **Inscription** : Les utilisateurs peuvent s'inscrire via un formulaire, en fournissant un e-mail, un mot de passe et un nom d'utilisateur.
- **Connexion** : Les utilisateurs peuvent se connecter avec leur e-mail et mot de passe. La connexion persiste entre les sessions.
- **Profil** : Les utilisateurs peuvent consulter et modifier leur profil (e-mail, nom d'utilisateur, mot de passe) et gérer leurs abonnements.
- **Déconnexion** : Les utilisateurs peuvent se déconnecter.

### Gestion des Abonnements

- **Consulter les Thèmes** : Les utilisateurs peuvent consulter une liste de tous les thèmes disponibles.
- **S'abonner/Désabonner** : Les utilisateurs peuvent s'abonner à des thèmes ou se désabonner via la page de profil.

### Gestion des Articles

- **Fil d'Actualité** : Les utilisateurs peuvent consulter leur fil d'actualité, qui affiche les articles des thèmes auxquels ils sont abonnés, triés par ordre chronologique.
- **Ajouter un Article** : Les utilisateurs peuvent publier un nouvel article en sélectionnant un thème, un titre et en rédigeant le contenu. L'auteur et la date de publication sont ajoutés automatiquement.
- **Consulter un Article** : Les utilisateurs peuvent consulter les détails d'un article, y compris son thème, son titre, son auteur, sa date de publication, et les commentaires associés.
- **Ajouter un Commentaire** : Les utilisateurs peuvent commenter un article en rédigeant du texte. L'auteur et la date du commentaire sont ajoutés automatiquement.

## Exigences Particulières

- **Responsive Design** : L'application est utilisable aussi bien sur mobile que sur ordinateur. Tous les écrans sont adaptatifs et s'ajustent à la taille de l'appareil utilisé.
- **Validation des Mots de Passe** : Un mot de passe est valide s'il contient au moins 8 caractères, dont un chiffre, une lettre minuscule, une lettre majuscule, et un caractère spécial.

## Technologies Utilisées

### Front-end

- **Angular** : Pour le développement de l'interface utilisateur.
- **HTML/CSS/JavaScript** : Pour la structuration, le style et les interactions de base.

### Back-end

- **Spring Boot** : Pour la création de l'API RESTful.
- **Java** : Pour le développement des services backend.
- **JPA/Hibernate** : Pour la gestion de la base de données.

## Installation

### Prérequis

- **Node.js** et **npm** : Pour exécuter l'application Angular.
- **Java 17+** : Pour exécuter l'application Spring Boot.
- **MySQL** : Pour la base de données (ou autre base de données compatible avec JPA).

### Étapes

1. **Clonez le dépôt** :

   ```
   git clone https://github.com/rosinalexis/Projet-P06-Orion
   ```

2. **Back-end (Spring Boot)** :

   - Accédez au répertoire `backend` :

   ```
   cd mdd-projet/backend
   ```

   - Configurez les propriétés de la base de données dans `application.properties`.
   - Exécutez l'application :

   ```
   ./mvnw spring-boot:run
   ```

3. **Front-end (Angular)** :
   - Accédez au répertoire `frontend` :
   ```
   cd ../frontend
   ```
   - Installez les dépendances :
   ```
   npm install
   ```
   - Exécutez l'application :
   ```
   ng serve
   ```

## Auteurs

- **Rosin Alexis** - _Développeur Front-end & Back-end_

## License

Ce projet est sous licence MIT - voir le fichier [LICENSE](LICENSE) pour plus de détails.
