# Recipe Backend (Culinary Companion)

Spring Boot 3 (Java 17) REST API for the Culinary Companion app.

## Features (implemented)
- Recipes: CRUD + search + categories/tags
- Favorites: per-user recipe favorites
- Shopping list: create list from recipe ingredients, manage items
- Comments: add/list comments per recipe
- Ratings: rate a recipe (1-5) and compute average rating

## Tech
- Spring Boot Web
- Spring Data MongoDB
- springdoc OpenAPI (Swagger UI)

## Configuration
This service reads configuration from environment variables (recommended) with sensible defaults for local development.

Required env vars (provided in this environment):
- `MONGODB_URL` - MongoDB connection string (e.g. `mongodb://user:pass@host:port/?authSource=admin`)
- `MONGODB_DB` - Database name

Optional:
- `PORT` - Server port (defaults to 3001)
- `ALLOWED_ORIGINS` - Comma-separated list for CORS
- `ALLOWED_METHODS` - Comma-separated list for CORS
- `ALLOWED_HEADERS` - Comma-separated list for CORS

## Run
Gradle:
```bash
./gradlew bootRun
```

OpenAPI:
- API docs JSON: `/api-docs`
- Swagger UI: `/swagger-ui.html`

Health:
- `/actuator/health`
"""
