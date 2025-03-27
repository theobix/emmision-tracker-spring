# Emission Tracker - Backend

<!--About the project-->
## About the project

A web application that records CO2 emissions based on users' journeys. Presents it as various statistics, so that the user can see how much it pollutes - and how it can be improved. 
See [Emission Tracker Frontend](https://github.com/FireGreeks/emission-tracker-quasar) for more information about the app. This README covers the backend spesification.

### Built with

* [![Spring][Spring]][Spring-url]
* [![GraphQL][GraphQL]][GraphQL-url]
* [![GraphQL-SPQR][GraphQL-SPQR]][GraphQL-SPQR-url]

<!--Features-->
## Features

* A code-first-approach to set up the GraphQL API using [GraphQL SPQR starter][GraphQL-SPQR-url] 
* Stores user data in a PostgreSQL database, that is set-up, configured, and queried with [Spring Data JPA](https://spring.io/projects/spring-data-jpa) and [Hibernate](https://docs.spring.io/spring-framework/reference/data-access/orm/hibernate.html)
* User authentification with [Spring Security](https://spring.io/projects/spring-security) and JWT tokens.
* Industry standard password encryption with [BCrypt password encoder](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/crypto/bcrypt/BCrypt.html)





[Spring]: https://img.shields.io/badge/spring-boot?style=for-the-badge&logo=spring&colorB=white
[Spring-url]: https://spring.io/
[GraphQL]: https://img.shields.io/badge/GraphQl-E10098?style=for-the-badge&logo=graphql&logoColor=white
[GraphQL-url]: https://graphql.org/
[GraphQL-SPQR]: https://img.shields.io/badge/GraphQl-SPQR-E10098?style=for-the-badge&logo=graphql&logoColor=white
[GraphQL-SPQR-url]: https://github.com/leangen/graphql-spqr-spring-boot-starter
