# MDD — Monde de Dév

[Lire ce fichier en français](#mdd--monde-de-dév-version-française)

## Description

MDD (Monde de Dév) is a full-stack social network dedicated to developers. Built as an MVP (Minimum Viable Product), it allows developers to subscribe to programming topics (JavaScript, Java, Python, etc.), read a personalized feed of articles from their subscriptions, write articles, and post comments.

The project is divided into two parts:
- **Backend**: a REST API built with Spring Boot 4
- **Frontend**: a Single Page Application built with Angular 19

## Technologies Used

### Backend

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.3-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6.x-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-HS256-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![OpenAPI](https://img.shields.io/badge/OpenAPI-3.0-6BA539?style=for-the-badge&logo=openapi-initiative&logoColor=white)

### Frontend

![Angular](https://img.shields.io/badge/Angular-19.1-DD0031?style=for-the-badge&logo=angular&logoColor=white)
![TypeScript](https://img.shields.io/badge/TypeScript-5.7-3178C6?style=for-the-badge&logo=typescript&logoColor=white)
![RxJS](https://img.shields.io/badge/RxJS-7.8-B7178C?style=for-the-badge&logo=reactivex&logoColor=white)
![SCSS](https://img.shields.io/badge/SCSS-CC6699?style=for-the-badge&logo=sass&logoColor=white)

## Features

- **User Authentication**: Registration and login via email or username, JWT-based session persistence
- **Personalized Feed**: Chronological list of articles from subscribed topics, sortable ascending/descending
- **Topic Subscriptions**: Browse all topics, subscribe/unsubscribe
- **Articles**: Create articles (topic, title, content), view article details
- **Comments**: Add comments to articles
- **User Profile**: View and edit email, username, and password; manage subscriptions
- **REST API**: Well-structured endpoints secured with JWT
- **API Documentation**: Interactive Swagger UI

## Prerequisites

Before running this project, make sure you have installed:

- **Java 21** or higher
- **Maven 3.9** or higher
- **MySQL 8.0** or higher
- **Node.js 18+** and **npm** (for the frontend)
- **Angular CLI 19** — `npm install -g @angular/cli`
- **Git**

## Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/IbrahimAlsabr/projet-6_MDD.git
cd projet-6_MDD
```

---

### 2. Backend Setup

#### Database

1. Create a MySQL database named `mdd`
2. Configure your credentials in `backend/src/main/resources/application-local.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/mdd
spring.datasource.username=your_username
spring.datasource.password=your_password

app.jwt.secret=your-256-bit-secret
app.jwt.issuer=mdd-issuer
app.jwt.exp-min=1500
```

> The schema is generated automatically on startup (`ddl-auto=create-drop`).  
> Sample data (users, topics, posts, comments) is loaded from `data.sql`.

#### Run the Backend

```bash
cd backend

# On Windows
./mvnw.cmd spring-boot:run

# On Unix/Linux/macOS
./mvnw spring-boot:run
```

The API will start at `http://localhost:8080`

---

### 3. Frontend Setup

```bash
cd frontend
npm install
ng serve
```

The application will start at `http://localhost:4200`

---

### 4. Test Accounts (from seed data)

| Email | Username | Password |
|---|---|---|
| user@user.com | user | Test1234! |
| alice@example.com | alice | Alice1234! |
| bob@example.com | bob | Bob1234! |

## API Documentation

Once the backend is running, access the interactive API documentation at:

- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

## Architecture

### Backend — Layered Architecture

```
Controller  →  Service  →  Repository  →  Model (JPA Entity)
                  ↕
                 DTO
```

- **Controller**: Handles HTTP requests and responses
- **Service**: Contains all business logic
- **Repository**: Data access via Spring Data JPA
- **Model**: JPA entities mapped to MySQL tables
- **DTO**: Request/response objects with validation (`@NotBlank`, `@Size`, `@Email`)
- **Security**: JWT configuration, Spring Security filter chain

### Frontend — Angular Modular Architecture

```
app.routes.ts  →  HomePageComponent (layout)
                      ├── ArticlesPageComponent    (/home/articles)
                      ├── ArticleDetailsComponent  (/home/articles/:id)
                      ├── CreateArticleComponent   (/home/articles/create)
                      ├── TopicsPageComponent      (/home/themes)
                      └── ProfilePageComponent     (/home/profile)
```

- **Services** (`core/services/`): `AuthService`, `PostService`, `CommentService`, `TopicService`, `UserService`
- **Models** (`core/models/`): Typed interfaces matching backend DTOs
- **Interceptor** (`core/interceptors/`): Injects JWT token into every HTTP request
- **Guard** (`core/guards/`): Redirects unauthenticated users to `/login`

## API Endpoints

### Authentication — `/api/v1/auth`

| Method | Endpoint | Description | Auth required |
|---|---|---|---|
| POST | `/api/v1/auth/signup` | Register a new user | No |
| POST | `/api/v1/auth/login` | Login (email or username) | No |
| POST | `/api/v1/auth/refresh` | Refresh access token | No |

### User Profile — `/api/v1/me`

| Method | Endpoint | Description | Auth required |
|---|---|---|---|
| GET | `/api/v1/me` | Get current user profile + subscriptions | Yes |
| PUT | `/api/v1/me` | Update email, username, or password | Yes |

### Feed — `/api/v1/feed`

| Method | Endpoint | Description | Auth required |
|---|---|---|---|
| GET | `/api/v1/feed?sort=desc` | Get articles from subscribed topics | Yes |

### Posts — `/api/v1/posts`

| Method | Endpoint | Description | Auth required |
|---|---|---|---|
| POST | `/api/v1/posts` | Create a new article | Yes |
| GET | `/api/v1/posts/{id}` | Get article details with comments | Yes |

### Comments — `/api/v1/posts/{postId}/comments`

| Method | Endpoint | Description | Auth required |
|---|---|---|---|
| GET | `/api/v1/posts/{postId}/comments` | List comments for a post | Yes |
| POST | `/api/v1/posts/{postId}/comments` | Add a comment | Yes |

### Topics — `/api/v1/topics`

| Method | Endpoint | Description | Auth required |
|---|---|---|---|
| GET | `/api/v1/topics` | List all topics with subscription status | Yes |
| POST | `/api/v1/topics/{topicId}/subscribe` | Subscribe to a topic | Yes |

### Subscriptions — `/api/v1/subscriptions`

| Method | Endpoint | Description | Auth required |
|---|---|---|---|
| DELETE | `/api/v1/subscriptions/{topicId}` | Unsubscribe from a topic | Yes |

## Building for Production

### Backend

```bash
cd backend
./mvnw clean package
# JAR file created in target/
java -jar target/backend-0.0.1-SNAPSHOT.jar
```

### Frontend

```bash
cd frontend
ng build --configuration production
# Output in dist/frontend/
```

## Author

### Ibrahim Alsabr

[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/IbrahimAlsabr)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/ibrahim-alsabr-188939231/)

---
---
---

<br>

# MDD — Monde de Dév (Version française)

[Read this file in English](#mdd--monde-de-dév)

## Description

MDD (Monde de Dév) est un réseau social full-stack dédié aux développeurs. Construit sous forme de MVP (Minimum Viable Product), il permet aux développeurs de s'abonner à des thèmes liés à la programmation (JavaScript, Java, Python, etc.), de consulter un fil d'actualité personnalisé, d'écrire des articles et de poster des commentaires.

Le projet est divisé en deux parties :
- **Backend** : une API REST construite avec Spring Boot 4
- **Frontend** : une Single Page Application construite avec Angular 19

## Technologies Utilisées

### Backend

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.3-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6.x-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-HS256-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![OpenAPI](https://img.shields.io/badge/OpenAPI-3.0-6BA539?style=for-the-badge&logo=openapi-initiative&logoColor=white)

### Frontend

![Angular](https://img.shields.io/badge/Angular-19.1-DD0031?style=for-the-badge&logo=angular&logoColor=white)
![TypeScript](https://img.shields.io/badge/TypeScript-5.7-3178C6?style=for-the-badge&logo=typescript&logoColor=white)
![RxJS](https://img.shields.io/badge/RxJS-7.8-B7178C?style=for-the-badge&logo=reactivex&logoColor=white)
![SCSS](https://img.shields.io/badge/SCSS-CC6699?style=for-the-badge&logo=sass&logoColor=white)

## Fonctionnalités

- **Authentification** : Inscription et connexion par email ou nom d'utilisateur, session persistante via JWT
- **Fil d'actualité personnalisé** : Articles des thèmes auxquels l'utilisateur est abonné, triables chronologiquement
- **Abonnements aux thèmes** : Consulter tous les thèmes, s'abonner / se désabonner
- **Articles** : Créer un article (thème, titre, contenu), consulter le détail d'un article
- **Commentaires** : Ajouter un commentaire à un article
- **Profil utilisateur** : Consulter et modifier email, nom d'utilisateur, mot de passe ; gérer ses abonnements
- **API REST** : Endpoints bien structurés et sécurisés par JWT
- **Documentation API** : Interface Swagger UI interactive

## Prérequis

Avant d'exécuter ce projet, assurez-vous d'avoir installé :

- **Java 21** ou supérieur
- **Maven 3.9** ou supérieur
- **MySQL 8.0** ou supérieur
- **Node.js 18+** et **npm** (pour le frontend)
- **Angular CLI 19** — `npm install -g @angular/cli`
- **Git**

## Installation et Configuration

### 1. Cloner le dépôt

```bash
git clone https://github.com/IbrahimAlsabr/projet-6_MDD.git
cd projet-6_MDD
```

---

### 2. Configuration du Backend

#### Base de données

1. Créez une base de données MySQL nommée `mdd`
2. Configurez vos identifiants dans `backend/src/main/resources/application-local.properties` :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/mdd
spring.datasource.username=votre_utilisateur
spring.datasource.password=votre_mot_de_passe

app.jwt.secret=votre-secret-256-bits
app.jwt.issuer=mdd-issuer
app.jwt.exp-min=1500
```

> Le schéma est généré automatiquement au démarrage (`ddl-auto=create-drop`).  
> Les données d'exemple (utilisateurs, thèmes, articles, commentaires) sont chargées depuis `data.sql`.

#### Lancer le Backend

```bash
cd backend

# Sur Windows
./mvnw.cmd spring-boot:run

# Sur Unix/Linux/macOS
./mvnw spring-boot:run
```

L'API démarrera sur `http://localhost:8080`

---

### 3. Configuration du Frontend

```bash
cd frontend
npm install
ng serve
```

L'application démarrera sur `http://localhost:4200`

---

### 4. Comptes de test (données d'exemple)

| Email | Nom d'utilisateur | Mot de passe |
|---|---|---|
| user@user.com | user | Test1234! |
| alice@example.com | alice | Alice1234! |
| bob@example.com | bob | Bob1234! |

## Documentation API

Une fois le backend démarré, accédez à la documentation interactive à :

- **Swagger UI** : `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON** : `http://localhost:8080/v3/api-docs`

## Architecture

### Backend — Architecture en couches

```
Controller  →  Service  →  Repository  →  Model (Entité JPA)
                  ↕
                 DTO
```

- **Controller** : Gère les requêtes et réponses HTTP
- **Service** : Contient toute la logique métier
- **Repository** : Accès aux données via Spring Data JPA
- **Model** : Entités JPA mappées sur les tables MySQL
- **DTO** : Objets de transfert avec validation (`@NotBlank`, `@Size`, `@Email`)
- **Security** : Configuration JWT, chaîne de filtres Spring Security

### Frontend — Architecture modulaire Angular

```
app.routes.ts  →  HomePageComponent (layout)
                      ├── ArticlesPageComponent    (/home/articles)
                      ├── ArticleDetailsComponent  (/home/articles/:id)
                      ├── CreateArticleComponent   (/home/articles/create)
                      ├── TopicsPageComponent      (/home/themes)
                      └── ProfilePageComponent     (/home/profile)
```

- **Services** (`core/services/`) : `AuthService`, `PostService`, `CommentService`, `TopicService`, `UserService`
- **Modèles** (`core/models/`) : Interfaces TypeScript correspondant aux DTOs backend
- **Intercepteur** (`core/interceptors/`) : Injecte le token JWT dans chaque requête HTTP
- **Guard** (`core/guards/`) : Redirige les utilisateurs non connectés vers `/login`

## Endpoints API

### Authentification — `/api/v1/auth`

| Méthode | Endpoint | Description | Auth requise |
|---|---|---|---|
| POST | `/api/v1/auth/signup` | Inscription | Non |
| POST | `/api/v1/auth/login` | Connexion (email ou username) | Non |
| POST | `/api/v1/auth/refresh` | Rafraîchir le token | Non |

### Profil utilisateur — `/api/v1/me`

| Méthode | Endpoint | Description | Auth requise |
|---|---|---|---|
| GET | `/api/v1/me` | Obtenir le profil + abonnements | Oui |
| PUT | `/api/v1/me` | Modifier email, username ou mot de passe | Oui |

### Fil d'actualité — `/api/v1/feed`

| Méthode | Endpoint | Description | Auth requise |
|---|---|---|---|
| GET | `/api/v1/feed?sort=desc` | Articles des thèmes abonnés | Oui |

### Articles — `/api/v1/posts`

| Méthode | Endpoint | Description | Auth requise |
|---|---|---|---|
| POST | `/api/v1/posts` | Créer un article | Oui |
| GET | `/api/v1/posts/{id}` | Détails d'un article avec commentaires | Oui |

### Commentaires — `/api/v1/posts/{postId}/comments`

| Méthode | Endpoint | Description | Auth requise |
|---|---|---|---|
| GET | `/api/v1/posts/{postId}/comments` | Lister les commentaires | Oui |
| POST | `/api/v1/posts/{postId}/comments` | Ajouter un commentaire | Oui |

### Thèmes — `/api/v1/topics`

| Méthode | Endpoint | Description | Auth requise |
|---|---|---|---|
| GET | `/api/v1/topics` | Lister tous les thèmes (avec statut d'abonnement) | Oui |
| POST | `/api/v1/topics/{topicId}/subscribe` | S'abonner à un thème | Oui |

### Abonnements — `/api/v1/subscriptions`

| Méthode | Endpoint | Description | Auth requise |
|---|---|---|---|
| DELETE | `/api/v1/subscriptions/{topicId}` | Se désabonner d'un thème | Oui |

## Construction pour la Production

### Backend

```bash
cd backend
./mvnw clean package
# Fichier JAR créé dans target/
java -jar target/backend-0.0.1-SNAPSHOT.jar
```

### Frontend

```bash
cd frontend
ng build --configuration production
# Résultat dans dist/frontend/
```

## Auteur

### Ibrahim Alsabr

[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/IbrahimAlsabr)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/ibrahim-alsabr-188939231/)
