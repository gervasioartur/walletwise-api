[![production deploy workflow](https://github.com/gervasioartur/walletwise-api/actions/workflows/production-deploy.yml/badge.svg)](https://github.com/gervasioartur/walletwise-api/actions/workflows/production-deploy.yml) [![staging deploy workflow](https://github.com/gervasioartur/walletwise-api/actions/workflows/staging-deploy.yml/badge.svg)](https://github.com/gervasioartur/walletwise-api/actions/workflows/staging-deploy.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=walletwise-api&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=walletwise-api)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=walletwise-api&metric=bugs)](https://sonarcloud.io/summary/new_code?id=walletwise-api)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=walletwise-api&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=walletwise-api)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=walletwise-api&metric=coverage)](https://sonarcloud.io/summary/new_code?id=walletwise-api)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=walletwise-api&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=walletwise-api)

# **WALLETWISE-API**

WALLET-WISE is a Personal Budgeting app designed to provide users with a comprehensive and
easy-to-use solution for effectively managing their personal finances. It will be an indispensable tool
to help users understand their spending patterns,
set realistic budgets, and achieve their financial goals. By providing a clear and detailed view of finances,
the app aims to empower users to make informed financial decisions and promote a healthy financial life.

## DOCUMENTATION

> #### Use cases

#### Authentication

##### Sign up

- [Sign up use case BDD documentation ](docs/useCases/auth/signup/signup.md)
- [Sign up feature dependency diagram](docs/useCases/auth/signup/signup.drawio)

##### Sign in

- [Sign in use case BDD documentation ](docs/useCases/auth/signin/signin.md)
- [Sign in feature dependency diagram](docs/useCases/auth/signin/signin.drawio)

##### Password Recovery

- [Password recovery use case BDD documentation ](docs/useCases/auth/passwordRecovery/passwordRecovery.md)
- [Password recovery feature dependency diagram](docs/useCases/auth/passwordRecovery/passwordRecovery.drawio)

##### Confirm Password Recovery

- [Confirm password recovery use case BDD documentation ](docs/useCases/auth/confirmPasswordRecovery/confirmPasswordRecovery.md)
- [Confirm password recovery feature dependency diagram](docs/useCases/auth/confirmPasswordRecovery/confirmPasswordRecovery.drawio)

##### Get user Profile

- [Get user profile use case BDD documentation ](docs/useCases/auth/getUserProfile/getUserProfile.md)
- [Get user profile feature dependency diagram](docs/useCases/auth/getUserProfile/getUserProfile.drawio)

#### EXPENSES

- [Add fixed expenses use case BDD documentation](docs/useCases/expenses/addFixedExpense/addFixedExpense.md)
- [Add fixed expenses use case dependency diagram](docs/useCases/expenses/addFixedExpense/addFixedExpense.drawio)

## Technical Documentation

> ### Architecture Overview

The architecture of "WALLETWISE-API" is based on a clean architecture: application, domain,infra and main.
We use Docker containers to ensure the portability and scalability of the system.

> ### System Components

* Backend:  RESTful Api developed in Java with spring boot
* Database: Postgresql for data storage

> ### Installation

#### Prerequisites

* Java (version 17 or higher)
* Postgresql
* Docker

#### Installation Steps

* Clone the project repository and start service:
    ```bash
    git clone https://github.com/gervasioartur/walletwise-api.git
    ```
    ```bash
    cd walletwise-api
    ```
    ```bash
    docker-compose up -d
    ```

<br/>

> ### Testing

#### Prerequisites

* Java (version 17 or higher)
* Maven (version 3)
* Postgresql
* Docker

#### Unit Tests

* Use JUnit for write and tests for the api.

#### Integration Tests

* Integration tests to ensure that different components of the system work correctly together.

#### Running  Tests

* Run unit tests:
    ```bash
    mvn clean test
    ```

<br/>

> ### Principles

* Single Responsibility
* Open Closed
* Liskov Substitution
* Interface Segregation
* Dependency Inversion
* Separation of Concerns
* Don't Repeat Yourself
* You Aren't Gonna Need It
* Keep It Simple
* Composition Over Inheritance
* Small Commits

> ### Design Patterns

* Factory
* Adapter
* Composite
* Dependency Injection
* Abstract Server
* Composition Root
* Builder
* Template Method
* Singleton
* Proxy

> ### Code Smells (Anti-Patterns)

* Blank Lines
* Comments
* Data Clumps
* Divergent Change
* Duplicate Code
* Inappropriate Intimacy
* Feature Envy
* Large Class
* Long Method
* Long Parameter List
* Middle Man
* Primitive Obsession
* Refused Bequest
* Shotgun Surgery
* Speculative Generality

> ### Methodologies e Designs

* TDD
* Clean Architecture
* DDD
* Refactoring
* GitFlow
* Modular Design
* Dependency Diagrams
* Use Cases
* Spike (Agile)

> ### Programming Libraries and Tools

* Java 21
* Spring Boot
* Spring Data JPA
* Spring Security
* Spring Devtools
* JsonWebToken
* Swagger
* Lombok
* UUID
* JUnit
* H2 Database
* Postgres
* Jacoco
* Java Faker
* Maven

> ### Testing Features

* Unit Testing
* Integration Testing
* Code Coverage
* Test Doubles
* Fakes

