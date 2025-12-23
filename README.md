# ToDoListToPeyman

Monorepo with:
- backend: Spring Boot (MySQL, JWT auth)
- frontend: Flutter app (login, topics, todos)
- docker-compose for dev and prod with a central `.env`

Quick start (dev):

```bash
# Set env in .env, then:
docker compose -f docker-compose.dev.yml up --build
# backend on :8080, MySQL on :3306
# open Flutter project: frontend/
```

See each folder README for details.
