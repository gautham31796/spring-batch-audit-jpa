# spring-batch-jpa-audit

Spring Boot 3 + Spring Batch 5 + Spring Data JPA example that:
- Inserts rows into `sf_caseimpl_process_audit` using **JPA**
- Reads back rows via `JpaPagingItemReader` and prints them
- Exposes a REST endpoint to trigger the job (`POST /jobs/run`)
- Includes SQL to create the app table and optional Spring Batch metadata tables

## Quick start

```bash
# 1) Create DB and run DDL
psql -d sfintegration_db -f db/schema-app.sql
# (optional) Spring Batch metadata
psql -d sfintegration_db -f db/schema-batch-postgresql.sql

# 2) Run the app
mvn spring-boot:run

# 3) Trigger the job
curl -X POST http://localhost:8080/jobs/run
```

> If your table is in a non-default schema (e.g., `gbs_sfintegration`), ensure your DB user's `search_path` includes it **or** keep the `@Table(schema="gbs_sfintegration")` annotation on the entity.

---

## Package layout
```
src/main/java/com/example/audit
  ├─ AuditApplication.java
  ├─ batch/
  ├─ domain/
  ├─ repo/
  └─ web/
src/main/resources/application.yml
db/schema-app.sql
db/schema-batch-postgresql.sql
```

