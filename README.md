# Library Management (Learning Project)

This branch (**Nivolos-gulpV1**) is a simple full-stack learning setup.

## Structure
- **backend/** → Spring Boot (Java 17, Maven) on port 8080  
- **frontend/** → Angular 17 (Node 18) on port 4200  

Both parts are designed for local development only — no production setup.

---

## Quickstart

### Requirements
- Java 17 (Temurin)
- Maven
- Node 18 (via nvm recommended)
- Angular CLI 17

### Start Backend
```bash
cd backend
./mvnw spring-boot:run
# → http://localhost:8080
```

### Start Frontend

```bash
cd frontend
npm ci
ng serve --proxy-config proxy.conf.json
# → http://localhost:4200
```

### Test the API

```bash
curl http://localhost:8080/api/publications
curl http://localhost:8080/api/loans
```

---

## Notes

* The Angular proxy forwards `/api/*` calls to the Spring Boot backend.
* This setup is for learning and local testing.
* Keep it simple — no CI, no Docker, no production deployment yet.

