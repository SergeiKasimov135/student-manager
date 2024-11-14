# student-manager

# README

## PostgreSQL Запуск в Docker

### 1) Для микросервиса manager:
```bash
docker run --name manager-db \
  -v /<your absolute path to project>/student-manager/manager/src/main/resources/db/migration:/docker-entrypoint-initdb.d \
  -p 5433:5432 \
  -e POSTGRES_DB=manager \
  -e POSTGRES_USER=manager \
  -e POSTGRES_PASSWORD=manager \
  postgres:16
```

### 2) Для микросервиса student-registry-service:
```bash
docker run --name registry-db \
  -v /<your absolute path to project>/student-manager/student-registry-service/src/main/resources/db/migration:/docker-entrypoint-initdb.d \
  -p 5433:5432 \
  -e POSTGRES_DB=registry \
  -e POSTGRES_USER=registry \
  -e POSTGRES_PASSWORD=registry \
  postgres:16
```

## KeyCloak Запуск в Docker:
```bash
docker run --name student-manager-keycloak \
  -p 8082:8080 \
  -e KEYCLOAK_ADMIN=admin \
  -e KEYCLOAK_ADMIN_PASSWORD=admin \
  -v ./config/keycloak/import:/opt/keycloak/data/import \
  quay.io/keycloak/keycloak:23.0.7 start-dev --import-realm
```

## MongoDB Запуск в Docker:
```bash
docker run --name feedback-db \
  -p 27017:27017 \
  mongo:7
```

