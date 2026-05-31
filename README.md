# do:todo 
A coding challenge for dotega.  
A simple Todo app with AI todo item generation.

## Requirements
- OpenAI API Key
- Docker
- Node.js 24+
- Java 21

## Starting the application

### Backend

#### Add OpenAI API Key

Create a `application-secret.yaml` with your OpenAI API key.

```shell
cp backend/src/main/resources/application-secret.example.yaml backend/src/main/resources/application-secret.yaml
```

#### Start the Postgres Docker container

```shell
docker compose -f backend/docker-compose.yaml up -d
```

#### Run the liquibase migration

Since JOOQ will attempt to generate classes from the database schema, we need to run the Liquibase migrations first.

```shell
cd backend
./gradlew liquibaseUpdate
```

#### Start the backend

Start the backend with the `secret` and `local` profile active.  
`secret` will configure Spring AI with your OpenAI key.  
`local` will configure your local database connection.

```shell
./gradlew bootRun --args='--spring.profiles.active=secret,local'
```


### Frontend

```shell
cd frontend
pnpm install
pnpm start
```

## Usage
- Open the frontend at http://localhost:4200  
- Click "Add Items"
- Describe your tasks

### Example
```text
Today I need to take out the trash, tomorrow drive to the office, pick up Max from the train station and then go to the gym in the evening.
```
