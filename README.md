# MyPortfolio — Backend

Backend of MyPortfolio, a platform for building detailed professional portfolios structured as stories. This service exposes the APIs consumed by the [React frontend](https://github.com/Rob097/MyPortfolio-FE-React).

---

## Architecture

The backend follows a **microservices design**, with each service running as a Docker container managed by a Kubernetes cluster.

| Service | Role |
|---|---|
| API Gateway | Entry point and load balancer for all microservices |
| Eureka Server | Service registry — tracks every microservice status |
| Zipkin | Distributed tracing across services |
| Auth Service | Authentication via Spring Security and JWT |
| Core Service | Main business logic, connected to MySQL |
| Cache | Redis for in-memory caching |

**Database:** MySQL

**Security:** Spring Security with JWT tokens

**Frontend:** React microfrontend architecture with Next.js — see the [frontend repository](https://github.com/Rob097/MyPortfolio-FE-React).

For full technical details, see the [repository wiki](https://github.com/Rob097/MyPortfolio-BE-v2/wiki).
