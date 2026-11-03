# Gainsoclock App

Gainsoclock App is a full-stack application with a Spring Boot API, a React frontend, and a MariaDB database.

> This is a starter README. The commands below assume separate `backend/` and `frontend/` folders, a Maven wrapper, and a Vite-based React app. Update the paths, scripts, endpoint names, and deployment details to match your repository.

## Stack

| Part | Technology | Purpose |
| --- | --- | --- |
| Frontend | React | User interface |
| Backend | Java 17, Spring Boot | API and business logic |
| Database | MariaDB | Persistent data |
| Build | Maven, npm | Compile, test, and package the applications |
| Deployment | Ubuntu, GitHub Actions | Run the application and automate releases |
| Public access | Cloudflare, optionally Nginx | Route requests to the server |

## Request flow

```text
Browser → Cloudflare → Nginx → React frontend
                            └── /api → Spring Boot → MariaDB
```

The API sends its response back through Nginx and Cloudflare to the browser. If Nginx is not yet configured, update this diagram to show your current route.

## Prerequisites

- JDK 17
- Node.js and npm
- MariaDB
- Git

## Run locally

### 1. Configure the backend

Set the database connection and any application secrets using your local environment or an untracked configuration file. Check the backend's `application.yml` for the exact property names. Do not commit passwords, database credentials, or JWT signing keys.

### 2. Start the API

```bash
cd backend
./mvnw spring-boot:run
```

If your Spring Boot project is at the repository root, run `./mvnw spring-boot:run` there instead. On Windows Command Prompt, use `mvnw.cmd`.

The example deployment uses port **8081**. Verify the port in your backend configuration, then check the health endpoint:

```bash
curl http://localhost:8081/actuator/health
```

### 3. Start React

In another terminal:

```bash
cd frontend
npm ci
npm run dev
```

Open the local URL printed by the frontend development server. Configure the frontend's API base URL to point at your local Spring Boot API, or configure a development proxy. If you use Vite, remember that only variables prefixed with `VITE_` are exposed to frontend code.

## Build and test

```bash
cd backend
./mvnw clean verify
```

```bash
cd frontend
npm ci
npm run build
```

The backend JAR is normally produced under `backend/target/`. A Vite frontend normally produces static files under `frontend/dist/`. Check your project configuration for the actual output paths.

## API

| Method | Path | Description | Access |
| --- | --- | --- | --- |
| `GET` | `/actuator/health` | Application health | Configure for deployment checks |
| TODO | TODO | Add your API endpoints | TODO |

Add request and response examples as the API grows. Avoid returning JPA entities that include passwords or other private fields; use response DTOs.

## Deployment

The intended release flow is:

1. A push to `main` starts GitHub Actions.
2. CI builds and tests the backend, then saves the JAR as an artifact.
3. A deployment job downloads the tested JAR on the Ubuntu runner.
4. A deployment script installs the JAR, restarts the Spring Boot service, and checks `/actuator/health`.
5. A frontend deployment can build React and publish its static files for Nginx to serve.

Keep MariaDB private to the server or trusted network. Point Cloudflare at the public-facing service you actually use, such as Nginx. Update this section when the frontend deployment is implemented.

## Project layout

```text
.
├── backend/                 # Spring Boot application (example path)
├── frontend/                # React application (example path)
├── scripts/                 # Deployment scripts, if present
└── .github/workflows/       # CI/CD workflows
```

## License

Add your license information here.