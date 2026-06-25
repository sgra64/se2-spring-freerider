
<!-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -->
# E3: *Spring Data JPA and Enpoints (EP)*
<!-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -->

The assignment demonstrates
[*Spring Data JPA*](https://docs.spring.io/spring-data/jpa/reference/jpa.html),
*Spring's* extended implementation of the
[*Jakarta Persistance*](https://jakarta.ee/learn/docs/jakartaee-tutorial/current/persist/persistence-intro/persistence-intro.html)
(previously *Java Persistance Architecture (JPA)*) standard.


&nbsp;

---

Check-out branch [*freerider-jpa-ep*](https://github.com/sgra64/se2-spring-freerider/tree/freerider-jpa-ep)
from the remote repository: *se2-repo*, URL: *https://github.com/sgra64/se2-spring-freerider.git*
for the assignment.

Steps:

1. [*Jakarta Persistance* and *Spring Data JPA*](#1-jakarta-persistance-and-spring-data-jpa)

1. [*Spring Data JPA* access to the *FREERIDER-DB* Database](#2-spring-data-jpa-access-to-the-freerider-db-database)
 
1. [*Freerider Endpoints*](#3-freerider-endpoints)

1. [*Open-API* and *Swagger-UI*](#4-open-api-and-swagger-ui)

1. [Running *Freerider-EP* and *Database* as *Services* in separate *Containers*](#5-running-freerider-ep-and-database-as-services-in-separate-containers)


<!-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -->

&nbsp;

## 1. *Jakarta Persistance* and *Spring Data JPA*

[*Jakarta Persistence*](https://jakarta.ee/learn/docs/jakartaee-tutorial/current/persist/persistence-intro/persistence-intro.html) -- previously: *Java Persistence Architecture (JPA)* -- is part of the
[*Jakarta*](https://jakarta.ee/learn/docs/jakartaee-tutorial/current/intro/overview/overview.html)
standard (previously: *Java-2 Enterprice Edition (J2EE)*) that defines a standardized
approach to using relational databases in Java application.

*Jakarta Persistance* provides *EntityManager* to (see also [*Gemini explanation*](https://www.google.com/search?q=Difference+between+Spring+Data+JPA+and+Spring+Boot+JPA+Repository&sca_esv=f2fb4c7153e77b8e&sxsrf=APpeQnsMC9mcXdqpXw0HvkoalVz_KDuOYQ%3A1782374028283&source=hp&ei=jN48ap7WDpmExc8PkfbgwQY&iflsig=ABILxe8AAAAAajzsnIOcO5zRQOFJU3GRcUH6tqduMSia&ved=0ahUKEwiezL3W9KGVAxUZQvEDHRE7OGgQ4dUDCDo&uact=5&oq=Difference+between+Spring+Data+JPA+and+Spring+Boot+JPA+Repository&gs_lp=Egdnd3Mtd2l6IkFEaWZmZXJlbmNlIGJldHdlZW4gU3ByaW5nIERhdGEgSlBBIGFuZCBTcHJpbmcgQm9vdCBKUEEgUmVwb3NpdG9yeUiKzQFQ7CNYjssBcAh4AJABAJgB4ASgAa8jqgEINjcuMS41LTG4AQPIAQD4AQGYAhigArwLqAIFwgINECMYngYY8AUY6gIYJ8ICDRAjGPAFGJ4GGOoCGCfCAhEQABiABBixAxiDARieBhjwBcICFBAAGIAEGIoFGLEDGIMBGJ4GGPAFwgILEAAYgAQYngYY8AXCAg4QABiABBixAxieBhjwBZgD5wGSBwYyMy4wLjGgB_BjsgcGMjEuMC4xuAe2C8IHCjAuMjEuMi4wLjHIB0aACAE&sclient=gws-wiz&aep=107&cs=0&mstk=AUtExfBQpL_zW96QAnqOueJhFUPyoo02tGANtveQpvLdNG7ZJxpmM4_Xv_-BKw0cHfxQ6o2tBLr7QnGS75noFVmiyy-NcWjGr9v24zSDWRalF0EC_cr2Oj1f7tflUkBf2aegHtO-RmSf4Gfb1URUCIsMuO_zT6IOov5ykE7rvcxhuNB2D2S3gMHlSEaUzdEiTxUXsUc9QLs_1z4xXKfdYoCvxwGUwcSfDNgGLCFOJh46eFi_oTCc26Nd2JCGu8q1E1DcVl5l4XFKJpPdBo0Smy-4M0Y5wMITFDuXWr7nxHrF88QanajfzUBgZq74WDkmfaB5glJn_gTrUF1h0g&csuir=1&mtid=qN48aqeEAfSpxc8P59CCuQ8&udm=50&atvm=1)):

- manage database connections (open/close).

- provide *Object-Relatioal Mapping (ORM)* with:

    - database schema creation from *data model classes* with `@Entity` annotations
        to database tables.

    - preparation of *SQL*-statements from objects using
        [*Java Persistence query language (JPQL)*](https://thorben-janssen.com/jpql/).

    - the mapping to objects from *ResultSets* returned from database queries
        with resolving foreign-key relations by `@OneToOne`, `@OneToMany`,  `@ManyToOne`
        and `@ManyToMany` annotations on attributes.


&nbsp;

A typical *JPA-Query* shows a *JPQL* Query returning rows mapped to ojects of a
type passed as an argument, here type `Customer.class`:

```java
public class CustomerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Customer> findByLastName(String lastName) {
        // 
        // create JPQL-Query
        TypedQuery<Customer> query = entityManager.createQuery(
            "SELECT c FROM Customer c WHERE c.lastName = :lastName", Customer.class);
        // 
        query.setParameter("lastName", lastName);
        // 
        // execute query and return results
        return query.getResultList();
    }
}
```

Mind that a list of *Customer* objects is returned from `findByLastName`,
not [*ResultSet*](https://www.columbia.edu/cu/help/jdk/docs/guide/jdbc/getstart/resultset.doc.html)
rows as in *JDBC*.

*Object-Relatioal Mapping (ORM)* performs the mapping between rows in the
*ResultSet* to objects of the given type (here: `Customer.class`).
*Spring* uses the open *ORM* implementation [*Hibernate*](https://hibernate.org/orm).


&nbsp;

*Spring Data JPA* adopts these concepts and adds:

- a *no-SQL* interface to database tables by an interface:
    [*JpaRepository&lt;T, ID&gt;*](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html)
    for
    [*CRUD*](https://www.geeksforgeeks.org/advance-java/jpa-crud)
    (Create, Read, Update, Delete) operations.

- a *Java*-interface *JpaRepository&lt;T, ID&gt;* can be extended and generate
    queries from method-names following certain conventions.

```java
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Query method that returns objects from the {@code CUSTOMER} table
     * as {@link Customer} objects matching the {@code lastName} attribute.
     * <p>
     * Method will generate a query from the method name:
     * <pre>
     * "SELECT c FROM Customer c WHERE c.lastName = :lastName"
     * </pre>
     * @param lastName selection criteria
     * @return list of {@link Customer} objects matching the {@code lastName} attribute
     */
    List<Customer> findByLastName(String lastName);
}
```


<!-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -->

&nbsp;

## 2. *Spring Data JPA* access to the *FREERIDER-DB* Database

*Spring Data JPA* access to the *FREERIDER-DB* database requires:

- the defintion of `@Entity` classes in:
    [`de.bht_berlin.freerider_ep.model`](src/main/java/de/bht_berlin/freerider_ep/model),

- the definition of *repository interfaces* corresponding the tables in the
    database in:
    [`de.bht_berlin.freerider_ep.controller`](src/main/java/de/bht_berlin/freerider_ep/controller)


For table `CUSTOMER`, an `@Entity` class
[*Customer.java*](src/main/java/de/bht_berlin/freerider_ep/model/Customer.java)
must exists:

```java
package de.bht_berlin.freerider_ep.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;

/**
 * Class of a {@link Customer} entity matching the {@code 'CUSTOMER'}-table
 * of the DB-Schema:
 * <pre>
 * CREATE TABLE if not exists CUSTOMER (
 *   ID BIGINT AUTO_INCREMENT not null,
 *   NAME VARCHAR(60) not null,
 *   FIRSTNAME VARCHAR(60) default null,
 *   CONTACT VARCHAR(60) default null,
 *   STATUS enum('Active', 'InRegistration', 'Terminated') default null,
 *   STATUS_CHANGE TIMESTAMP default null,
 *   -- 
 *   PRIMARY KEY (ID)
 * );
 * </pre>
 */
@Entity
@Table(name="CUSTOMER")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "FIRSTNAME")
    private String firstName;

    @Column(name = "CONTACT")
    private String contact;

    enum Status { Active, InRegistration, Terminated }
    // 
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private Status status;

    @Column(name = "STATUS_CHANGE")
    private LocalDateTime statusChange;

    // public getter and setter methods in traditional style: getId(), setId()
}
```

The corresponding
[*CustomerRepository.java*](src/main/java/de/bht_berlin/freerider_ep/controller/CustomerRepository.java)
interface extends
[*JpaRepository&lt;T, ID&gt;*](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html)
and in-turn
[*CrudRepository&lt;T, ID&gt;*](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/CrudRepository.html)
with `&lt;T&gt;`: `Customer` and `&lt;ID&gt;`: `Long`.

The *Repository* can be extended by queries that are derived by the name of the
method or can explicitely specified in *JPQL*:

```java
package de.bht_berlin.freerider_ep.controller;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.bht_berlin.freerider_ep.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // In addition to CRUD-Operationen (findAll, save etc.) mit

    // creates query: SELECT * FROM CUSTOMER WHERE NAME = ? ;
    List<Customer> findByName(String name);
}

```


<!-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -->

&nbsp;

## 3. *Freerider Endpoints*

An *"Endpoint"* in general is a destination (endpoint) of a commnication.
An [*"API Endpoint"*](https://blog.postman.com/what-is-an-api-endpoint)
specifies a specific URL where an API receives requests and sends responses.
Each endpoint combines a resource path with an *HTTP method*: *GET*, *POST*,
*PUT*, *DELETE* that describes the action to perform:

```
GET https://api.example.com/v1/users/123?include=posts
│   │                      │  │        │  │
│   │                      │  │        │  └─ Query parameter
│   │                      │  │        └──── Path parameter (user ID)
│   │                      │  └─────────── Endpoint path
│   │                      └────────────── Version
│   └───────────────────────────────────── Base URL
└───────────────────────────────────────── HTTP method
```

A *RESTful* or [*REST*](https://en.wikipedia.org/wiki/REST) is an *API Endpoint*
that follows specific rules:

- addresses a *resource* (singular) or *resource set* (plural) as
    first part of the URL, e.g. `/customers` for the resource set.

- always uses a noun for a *resource* or *resource set*, not a verb (action).

- never includes or repeats verbs of associated HTTP operations.

A *REST-Endpoint* requires an HTTP-Server to serve the underlying HTTP protocol.
*Spring Boot* uses the embedded HTTP-server
[*tomcat*](https://tomcat.apache.org/tomcat-9.0-doc/config/http.html),
for which a dependency must be included:

```xml
<!-- Spring Boot Web für die REST-Endpunkte -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

With this dependency, the application runs *"as-a-service"*, which means it
does not end. Instead, the HTTP-server keeps waiting for incoming HTTP-requests.

Start the database server in the *mysqld-container*.

Launch the application:

```sh
mvn spring-boot:run
```
```
Attaching agents: []
2026-06-25T22:12:27.032+02:00  INFO 20604 --- [freerider] [ main] d.b.f.application.BootApplication : Starting BootApplication using Java 25.0.2 with PID 20604 (C:\Sven1\svgr2\workspaces\2-SE\e1-spring-freerider\target\classes started by svgr2 in C:\Sven1\svgr2\workspaces\2-SE\e1-spring-freerider)
2026-06-25T22:12:27.051+02:00  INFO 20604 --- [freerider] [ main] d.b.f.application.BootApplication : No active profile set, falling back to 1default profile: "default"
WARNING: A restricted method in java.lang.System has been called
WARNING: java.lang.System::load has been called by org.apache.tomcat.jni.Library in an unnamed module (file:/C:/Sven1/svgr2/.m2/repository/org/apache/tomcat/embed/tomcat-embed-core/10.1.36/tomcat-embed-core-10.1.36.jar)
2026-06-25T22:12:29.494+02:00  INFO 20604 --- [freerider] [ main] o.apache.catalina.core.StandardService : Starting service [Tomcat]
2026-06-25T22:12:29.494+02:00  INFO 20604 --- [freerider] [ main] o.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/10.1.36]
2026-06-25T22:12:29.583+02:00  INFO 20604 --- [freerider] [ main] o.a.c.c.C.[Tomcat].[localhost].[/]     : Initializing Spring embedded WebApplicationContext
...                                                                          ^^^^^^
                                                                                  +-- tomcat launches
Hibernate:                          <-- Hibernate has connected to the database and runs a query
    select
        count(*)
    from
        CUSTOMER c1_0
--> Datenbank enthält bereits 9 Kunden. Initialisierung übersprungen.
Hibernate:
    select
        c1_0.ID,
        c1_0.CONTACT,
        c1_0.FIRSTNAME,
        c1_0.NAME,
        c1_0.STATUS,
        c1_0.STATUS_CHANGE
    from
        CUSTOMER c1_0               <-- data found in the database is returned
{ "id": 100, "name": "Eric", "firstName": "Meyer", "contact": "eme22@gmail.com", "status": Active, "statusChange": "2024-06-04T12:35" }
{ "id": 101, "name": "Sommer", "firstName": "Tina", "contact": "+49 030 22458 29425", "status": Active, "statusChange": "2025-10-07T10:28" }
{ "id": 102, "name": "Schulze", "firstName": "Tim", "contact": "+49 171 2358124", "status": Active, "statusChange": "2024-12-28T18:00" }
...
```
*tomcat* is now listening on port *8080* to incoming HTTP-requests.

Open another terminal and run an `HTTP-GET` request:

```sh
curl -X 'GET' \
  'http://localhost:8080/api/customers/100' \
  -H 'accept: */*'
```

*Curl* creates an `HTTP` `GET`-**request** to path: `/api/customers/100`
requesting the controller to run an SQL Query for data of *Customer* with
*ID: 100*.

The `HTTP` **response** returns the requested *Customer* data rendered in
*JSON* format:

```
{ "id": 100, "name": "Eric", "firstName": "Meyer", "contact": "eme22@gmail.com", "status": "Active", "statusChange": "2024-06-04T12:35:00" }
```

The code processing the incoming `GET`-request (and all other requests for
the `/customers` path) resides in a *"Controller"*, here in
[*CustomerController.java*](src/main/java/de/bht_berlin/freerider_ep/controller/CustomerController.java):

```java
package de.bht_berlin.freerider_ep.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.bht_berlin.freerider_ep.model.Customer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/customers")
@Tag(name = "Customer Endpoint", description = "Endpoint to manage Customers in the database")
public class CustomerController {

    private final CustomerRepository repository;

    public CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get customer by id", description = "Return a single customer by ID from the database.")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        // 
        return repository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
```

The Controller method for path `@GetMapping("/{id}")` uses method `findById(id)`
from the inherietd
[*CrudRepository&lt;T, ID&gt;*](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/CrudRepository.html)
returning an *Optional&lt;Customer&gt;*` that is mapped into a
[*ResponseEntity*]()
that performs the conversion from a Java *Customer* object to the *JSON*
format and wrapping the *JSON* into the *HTTP-Reply-Body* returned to the
issuer of the *GET*-request (*curl*).

Issue request:

```sh
curl -X GET 'http://localhost:8080/api/customers' -H 'accept: */*'
```


<!-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -->

&nbsp;

## 4. *Open-API* and *Swagger-UI*

The [*OpenAPI Specification (OAS)*](https://www.openapis.org) (launched in 2010
and previously known as the *Swagger Specification*), is a specification for a
machine-readable *Interface Definition Language (IDL)* for describing, producing,
consuming and visualizing REST / API and Web services.

An *OpenAPI Description (OAD)* is a formal description of an API that tools can
use to generate code, documentation, test cases, and more.


Examples of prominent, well-designed Endpoint documentations / specifications are:

- [*Spotify API EP*](https://developer.spotify.com/documentation/web-api).

- [*PayPal Payments API*](https://developer.paypal.com/docs/api/payments/v1/).

- [*Docker Engine API, Container-EP*](https://docs.docker.com/reference/api/engine/version/v1.54/#tag/Container).


&nbsp;

[*Swagger-UI*](https://swagger.io/tools/swagger-ui)
is used to visualize and interact with an API's resources without requiring
implementation logic. *Swagger-UI* is generated from an *OpenAPI Description*.

*Swagger-UI* is introduced in a *Spring Boot* project by adding a dependency:

```xml
<!-- add OpenAPI 3 + Swagger-UI -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.8.5</version>
</dependency>
```

Run the application and point a browser to the *Swagger-URL*

- [`http://localhost:8080/swagger-ui/index.html`](http://localhost:8080/swagger-ui/index.html)

The browser shows the *Swagger-UI* through which

- REST-Operations can be inspected,

- REST-Operations can be issued to exercise enpoints,

- [*JSON-schema*](https://json-schema.org) can be inspeced for *JSON*-data passed
    to the endpoint in *HTTP-requests* or returned as answers in *HTTP-replies*.

*Swagger-UI* is generated from *"API-docs"* produced by the endpoints.

Click the

- [v3/api-docs](http://localhost:8080/v3/api-docs) - link in the upper left corner.
    Generated *"API-docs"* are shown in a compact (unreadable) *JSON*-format.

*"API-docs"* are also stored (in readable format) in the project under

- [`src/main/resources/api-docs.json`](src/main/resources/api-docs.json),

- [`src/main/resources/api-docs.yaml`](src/main/resources/api-docs.yaml).




&nbsp;

*Swagger-UI* for `/customers` endpoint:

<img src="https://github.com/sgra64/se2-spring-freerider/blob/markup/img/ep-customer.png?raw=true" width="800"/>


&nbsp;

Testing the `/customers` endpoint through *Swagger-UI*:


<img src="https://github.com/sgra64/se2-spring-freerider/blob/markup/img/open-api-3.png?raw=true" width="800"/>


Testing the `/customers` endpoint through *curl*:

```sh
# generating a HTTP GET-request with 'curl'
curl ...
```


&nbsp;

*Open-API*-specification for the `/customers` endpoint in `.yaml`-format (left).
Full *Open-API* documents are stored in the project under:

- `src/main/resources`[`/api-docs.json`](src/main/resources/api-docs.json)

- `src/main/resources`[`/api-docs.yaml`](src/main/resources/api-docs.yaml)

*Open-API* documents can be obtained from the *Swagger-UI* (via the `/v3/api-docs` link)
or can be *"written manually"* in a project that follows an
[*"API-first" Approach*](https://swagger.io/resources/articles/adopting-an-api-first-approach/).

<img src="https://github.com/sgra64/se2-spring-freerider/blob/markup/img/open-api-2.png?raw=true" width="800"/>





<!-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -->

&nbsp;

## 5. Running *Freerider-EP* and *Database* as *Services* in separate *Containers*

Both, the *Database* and the *Freerider-EP* application operate as services -
as continously running processes.

Rather than starting and stopping proccesses separately, a modern approach is
to package both services into containers and use
[*docker-compose*](https://docs.docker.com/compose) to manage containers.

Two steps are needed:

1. Integrate the management of the *Database* container into the
    *docker-compose* file.

1. Create a container for the *Freerider-EP* application by:

    1. create a packaged: `freerider-ep-1.0.0.jar` using maven.

    1. creating an image: `freerider-ep-image` that contains the
        packaged `.jar` using a [`Dockerfile`](Dockerfile).

    1. create a container: `freerider-ep` based on the image.

1. Manage the *creation*, *start* and *stop* of all containers using
    a common configuration file: [`docker-compose.yml`](docker-compose.yml):

```yaml
services:

  # service #1: 'mysqld', container-name: 'freerider-mysql-db'
  # database service running the MySQL database process 'mysqld'
  mysqld:
    image: mysql:8.4
    container_name: freerider-mysql-db
    restart: unless-stopped
    environment:
      MYSQL_DATABASE: "FREERIDER_DB"
      MYSQL_USER: "freerider"
      MYSQL_PASSWORD: "free.ride"
      MYSQL_ROOT_PASSWORD:
      MYSQL_ALLOW_EMPTY_PASSWORD: yes
    ports:
      - "3306:3306"
    volumes:
      - mysqld-volume:/var/lib/mysql

    healthcheck:
      test: ["CMD-SHELL", "mysqladmin ping -h localhost -uroot -prootpassword"]
      interval: 10s
      timeout: 5s
      retries: 10
      start_period: 20s

  # service #2: 'freerider-ep', container-name: 'freerider-ep'
  # container running the 'freerider-ep-1.0.0.jar', uses the 'mysqld' service
  freerider-ep:
    container_name: freerider-ep
    build:
      context: .
      dockerfile: Dockerfile
    image: freerider-ep-image:latest
    ports:
      - "8080:8080"
    restart: unless-stopped

    depends_on:
      mysqld:
        condition: service_healthy

    environment:
      # 'jdbc:mysql://<service-name>:3306/FREERIDER_DB'
      SPRING_DATASOURCE_URL: "jdbc:mysql://mysqld:3306/FREERIDER_DB"
      SPRING_DATASOURCE_USERNAME: "freerider"
      SPRING_DATASOURCE_PASSWORD: "free.ride"

# volume 'mysqld-volume' that holds the database data
volumes:
  mysqld-volume:
```

File [`Dockerfile`](Dockerfile) includes the specification of the creation
of the `freerider-ep-image`:

```Dockerfile
# use lightweight OpenJDK 21 runtime image
FROM eclipse-temurin:21-jre-alpine

WORKDIR /deploy

# copy the Spring Boot fat jar into the image
COPY target/freerider-ep-1.0.0.jar /deploy/freerider-ep-1.0.0.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/deploy/freerider-ep-1.0.0.jar"]
```

Perform the following commands:

- stop the *mysqld-container* running the database.

- `mvn clean package` -- compile and package `freerider-ep-1.0.0.jar`.

- `docker-compose up -d` -- create the image, the container and launch
    both services recognizing that the *database* must be started first.

- `docker-compose stop | start` -- stopp, start all containers.

- `docker logs -f freerider-mysql-db` -- show logs of `freerider-mysql-db` container.

- `docker logs -f freerider-ep` -- show logs of `freerider-ep` container.

<!-- 
```sh
# - mvn clean package             ; compile and package 'freerider-ep-1.0.0.jar'
# - mvn spring-boot:run           ; run Java-application separately
# - docker-compose up -d          ; build images start-up services in containers
# - docker-compose stop           ; stop all containers
# - docker logs -f freerider-mysql-db ; show logs of 'freerider-ep' container
# - docker logs -f freerider-ep       ; show logs of 'freerider-ep' container
``` -->

