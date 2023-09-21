# Lagalt-Backend
Lagalt API is a RESTful API containing a datastore and an interface allowing users to store and manipulate users, projects. collaborators, users skill and commentss. The application constructed in Spring Web is comprised of database created through Hibernate. The data consists of user information such as the email, fullname, username and skills. A user can have multiple projects, where each project can have many collaborators and each project can also have comments from all user that comment on the project. A Project has a title as well as an description, github lin, category and status of the project which is the progress. Comment only has the date the comment was made and contains the text from the user. 

## Java Installation Guide
We used the programming language Java

### Step 1: Download Java
First download latest version of [Java](https://www.oracle.com/java/) to install.

### Step 2: Verify Installation
Open a new command prompt (or terminal) and type:

```bash
java --version
```
This should display Java's version, indicating a successful installation.

## Maven Installation Guide

### Step 1: Download Maven
First download the latest version of [Maven](https://maven.apache.org/download.cgi).

### Step 2: Installed Maven
Extract the downloaded file to a suitable location on your computer.

### Step 3: Add Maven To Path
To run Maven in your command line, you need to add Maven bin in your system `PATH` variable.

´Windows` : 
1. Right click on `This PC` and choose `Properties`.
2. Click on `Advanced system settings`.
3. Click on `Environment Variables`.
4. Under System Variables, locate `PATH` and click `Edit`.
5. Click `New` and add the path to Maven's bin.

Mac:
Add the following line to your .bashrc, .bash_profile, or .zshrc, depending on your shell:
```bash
export PATH=$PATH:/path/to/maven/bin
```
Replace /path/to/maven with the actual path where you extracted Maven.

### Step 4: Verify Installation
Open a new command prompt (or terminal) and type:

```bash
mvn --version
```

This should display Maven's version, indicating a successful installation.

## Spring boot
We utilized the Spring Boot framework to manage our database and handle our REST APIs.
You can create your Spring Boot project directly on their [website](https://start.spring.io/).

## Keycloak Client Setup Guide
We use Keycloak for authentication. You need to use Keycloak to access the endpoints since you won't be able to perform authorization to these endpoints without it.

### Step 1: Access Keycloak Admin Console
Visit the [Keycloak](https://www.cloud-iam.com/). Log in using your administrative credentials.

### Step 2: Select or Create Realm
If you're not using the default "Master" realm, navigate to the left sidebar and click on Add Realm. Provide a name for the new realm and save.

### Step 3: Create a New Client
  **1.** On the left sidebar, navigate to `Clients`.
  **2.** Click on `Create` at the top right corner.
  **3.** Fill in the mandatory fields:
      * **Client ID:** Enter a unique ID for your client.
      * **Client Protocol:** Choose `openid-connect`.
      * **Root URL:** Enter your application's root URL if needed.
   Click `Save`.

### Step 4: Configure the Client
After saving, you'll be redirected to the client's settings page:

**1.** Set `Access Type` to either `Access Type`, `Access Type`, or `bearer-only` depending on your application's needs.
**2.** Fill in other details like `Valid Redirect URIs`, which is crucial for security.
**3.** Under the `Credentials` tab, you can find the secret for confidential clients which might be needed for server-to-server authentication.

### Step 5: Assign Roles & Users (Optional)
If you want specific roles or users to access this client:
**1.** Navigate to the `Roles` tab in your client and add necessary roles.
**2.** Link users to roles by navigating to `Users`, selecting a user, and then navigating to the `Role Mappings` tab to assign roles to the use.

### Step 6: Verify Client Setup
1. Head to the main clients list.
2. You should see your newly created client in the list. Make sure it's enabled.

Remember to secure your Keycloak and application setup by following best practices and guidelines provided in Keycloak's [documentation](https://www.keycloak.org/documentation).
   
## Database

- A relational database management system (RDBMS) supporting SQL, suggestively [PostgreSQL](https://www.postgresql.org/)
- We used the tool [DBeaver](https://dbeaver.io/), which allowed us to view relationships, tables, and values within the tables.

### Configuration

To clone this application run the following command in your terminal:
```bash
git clone https://github.com/98emre/Lagalt-Backend
```
## Docker Setup Guide
Docker provides a way to run applications securely isolated in a container, packaged with all its dependencies and libraries. To run the Lagalt API using Docker

### Step 1: Install Docker 
First, download and install Docker from [Docker's official website](https://www.docker.com/).

### Step 2: Build Docker Image
Navigate to the root directory of the Lagalt API project and run:
```bash
docker build -t lagalt-backend .
```
### Step 3 (Optional): Push Docker Image to a Registry
If you want to distribute or deploy your image to a remote server, you can push it to a container registry.

**1. Log in to Docker Hub from the Command Line:**
```bash
docker login
```
Provide your Docker Hub credentials when prompted.

**2. Tag Your Image:**
Tag your image with a meaningful name and version. The general format is `username/repository:tag`. For example:
```bash
docker tag lagalt-backend yourusername/lagalt-backend:latest
```
Replace yourusername with your Docker Hub username.

**3. Push the Image:**
Push the image to Docker Hub:
```bash
docker push yourusername/lagalt-backend:latest
```

### Step 4: Run Docker Container
To run the application locally:

```bash
docker run -p 8080:8080 lagalt-backend
```
This command runs the "lagalt-backend" image and maps the container's port 8080 to the host's port 8080.

## Dependencies in your application.properties file

Make sure all you have all of the following dependencies in your pom.xml file and use the latest version:
- spring-boot-starter
- spring-boot-starter-data-jpa
- spring-boot-starter-actuator
- spring-boot-starter-web
- spring-boot-starter-test
- springdoc-openapi-starter-webmvc-ui
- spring-boot-starter-security
- spring-boot-starter-oauth2-resource-serve
- postgresql (make sure to change this dependency in case you use another relational database management system)
- lombok
- mapstruct
- mapstruct-processor

  
As well as verifying that your maven plugin version exists and is not later than your installed maven version.
  
In the application.properties file, to configure to your database, set your environment variables alternatively set them explicitly:
```
spring.datasource.url=${POSTGRES_URL}
spring.datasource.username=${POSTGRES_USERNAME}
spring.datasource.password=${POSTGRES_PASSWORD}
```

When mapping and seeding the database and running the application for the first time, make sure this property is set to create:
```
spring.jpa.hibernate.ddl-auto=create
```

When mapping and seeding is completed set the property to update:
```
spring.jpa.hibernate.ddl-auto=update
```

In the application.properties file, to configure to your keylcoak, set your environment variables alternatively set them explicitly:
```
keycloak.realm=${KEYCLOAK_REALM}
keycloak.client-id=${KEYCLOAK_CLIENT_ID}
keycloak.token-endpoint=${KEYCLOAK_TOKEN_ENDPOINT}
```

In the application.properties file, to configure to oAuth2 security with JWT, set your environment variables alternatively set them explicitly:
```
spring.security.oauth2.resourceserver.jwt.issuer-uri=
${SPRING_SECURITY_ISSUER_URI:https://lemur-8.cloud-iam.com/auth/realms/${keycloak.realm}}

spring.security.oauth2.resourceserver.jwt.jwk-set-uri=
${SPRING_SECURITY_JWK_SET_URI:https://lemur-8.cloud-iam.com/auth/realms/${keycloak.realm}/protocol/openid-connect/certs}
```

In the application.properties file, to configure custom server port make sure this property is set to create:
```
server.port:8081 (You can change it)
```

## Usage
The application will run on port 8080 by default. 
If another port would be desired, this can be set in the application.properties file.

## Testing with Postman
Postman is a popular tool for testing APIs. It allows you to send requests to your API and view responses in a user-friendly interface.

### Step 1: Install Postman
Download and install Postman from [Postman's official website](https://www.postman.com/).

### Step 2: Send Requests

With Postman open, you can send requests to the Lagalt API. For example, to retrieve all users on the platform, set the request type to "GET" and enter the endpoint URL: `http://localhost:8081/api/users/public`.

You can also add headers, body data, and other request parameters as needed for different endpoints.

### Step 3: Lagalt Endpoints
Further down you can find all our endpoints for the application that you can use

### API Endpoints
| HTTP Verbs | Endpoints | Action |
| --------- | --------- | --------- |
| **User** | | |
| POST | /api/user/add-user | To create a new user |
| GET | /api/users/public | To retrieve all users on the platform |
| GET | /api/users/:movieId | To retrieve details of a single user |
| GET | /api/users/public/token/username | To retrieve details of a single user while using token |
| GET | /api/users/username/:username |  To retrieve details of a single user while using username |
| PATCH | /api/users/:userId | To edit/update  the details of a single user |
| DELETE | /api/user/:userId | To delete a single user |
| **Project** | | |
| POST | /api/projects | To create a new project |
| GET | /api/projects/public | To retrieve all projects on the platform |
| GET | /api/projects/:projectId | To retrieve details of a single project |
| GET | /api/projects/:projectId/collaborators/approved | To retrieve all collaborator who has been accpted in the project |
| GET | /api/projects/public/collaborators/pending | Project owner, can see all request to the project |
| PATCH | /api/projects/:projectId | To edit/update  the details of a single project |
| DELETE | /api/projects/:projectId | To delete a single project |
| **Comment** | | |
| POST | /api/comments/project/:projectId | To create a new comment on a specific project |
| GET | /api/comments | To retrieve all comments on the platform |
| GET | /api/comments/:commentId | To retrieve details of a single comment |
| PATCH | /api/comments/:commentId | To edit/update  the details of a single comment |
| DELETE | /api/comments/:commentId | To delete a single comment |
| **Collaborator** | | |
| POST | /api/collaborators/:collaboratorId/collaborator | To create a request on a specific project |
| GET | /api/collaborators | To retrieve all collaborators on the platform both pending and approved |
| GET | /api/collaborators/:collaboratorId | To retrieve details of a single collaborator |
| PATCH | /api/collaborators/:collaboratorId | To edit/update the details of a single collaborators |
| DELETE | /api/collaborators/:collaboratorId | To delete a single collaborator |

## Authors
Emre Demirel @98emre

August Danell @AugustDanell

Vendela Österman @Vendelaosterman
