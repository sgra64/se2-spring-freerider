
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

Steps:

1. [*Jakarta Persistance* and *Spring Data JPA*](#1-jakarta-persistance-and-spring-data-jpa)

1. [*Spring Data JPA* access to the *FREERIDER-DB* Database](#2-spring-data-jpa-access-to-the-freerider-db-database)
 
1. [*Freerider Endpoints*](#3-freerider-endpoints)

1. [*Open-API* and *Swagger-UI*](#4-open-api-and-swagger-ui)

1. [Running *Freerider* as *Services* in *Containers*](#5-running-freerider-as-services-in-containers)


<!-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -->

&nbsp;

## 1. *Jakarta Persistance* and *Spring Data JPA*

[*Jakarta Persistence*](https://jakarta.ee/learn/docs/jakartaee-tutorial/current/persist/persistence-intro/persistence-intro.html)
(previously: *Java Persistence Architecture (JPA)*)
is part of the
[*Jakarta*](https://jakarta.ee/learn/docs/jakartaee-tutorial/current/intro/overview/overview.html)
standard (previously: *Java-2 Enterprice Edition (J2EE)*) that defines a standardized
approach to using relational databases in Java application.

*Jakarta Persistance* provides *EntityManager* to (see also [*Gemini explanation*](https://www.google.com/search?q=Difference+between+Spring+Data+JPA+and+Spring+Boot+JPA+Repository&sca_esv=f2fb4c7153e77b8e&sxsrf=APpeQnsMC9mcXdqpXw0HvkoalVz_KDuOYQ%3A1782374028283&source=hp&ei=jN48ap7WDpmExc8PkfbgwQY&iflsig=ABILxe8AAAAAajzsnIOcO5zRQOFJU3GRcUH6tqduMSia&ved=0ahUKEwiezL3W9KGVAxUZQvEDHRE7OGgQ4dUDCDo&uact=5&oq=Difference+between+Spring+Data+JPA+and+Spring+Boot+JPA+Repository&gs_lp=Egdnd3Mtd2l6IkFEaWZmZXJlbmNlIGJldHdlZW4gU3ByaW5nIERhdGEgSlBBIGFuZCBTcHJpbmcgQm9vdCBKUEEgUmVwb3NpdG9yeUiKzQFQ7CNYjssBcAh4AJABAJgB4ASgAa8jqgEINjcuMS41LTG4AQPIAQD4AQGYAhigArwLqAIFwgINECMYngYY8AUY6gIYJ8ICDRAjGPAFGJ4GGOoCGCfCAhEQABiABBixAxiDARieBhjwBcICFBAAGIAEGIoFGLEDGIMBGJ4GGPAFwgILEAAYgAQYngYY8AXCAg4QABiABBixAxieBhjwBZgD5wGSBwYyMy4wLjGgB_BjsgcGMjEuMC4xuAe2C8IHCjAuMjEuMi4wLjHIB0aACAE&sclient=gws-wiz&aep=107&cs=0&mstk=AUtExfBQpL_zW96QAnqOueJhFUPyoo02tGANtveQpvLdNG7ZJxpmM4_Xv_-BKw0cHfxQ6o2tBLr7QnGS75noFVmiyy-NcWjGr9v24zSDWRalF0EC_cr2Oj1f7tflUkBf2aegHtO-RmSf4Gfb1URUCIsMuO_zT6IOov5ykE7rvcxhuNB2D2S3gMHlSEaUzdEiTxUXsUc9QLs_1z4xXKfdYoCvxwGUwcSfDNgGLCFOJh46eFi_oTCc26Nd2JCGu8q1E1DcVl5l4XFKJpPdBo0Smy-4M0Y5wMITFDuXWr7nxHrF88QanajfzUBgZq74WDkmfaB5glJn_gTrUF1h0g&csuir=1&mtid=qN48aqeEAfSpxc8P59CCuQ8&udm=50&atvm=1)):

- manage database connections (open/close).

- provide *Object-Relatioal Mapping (ORM)* with:

    - database schema creation from *data model classes* with `@Entity` annotations.

    - preparation of SQL-statements from objects using
        [*Java Persistence query language (JPQL)*](https://thorben-janssen.com/jpql/).

    - the mapping to objects from *ResultSets* returned from database queries
        with resolving foreign-key relations by `@OneToOne`, `@OneToMany`,  `@ManyToOne`
        and `@ManyToMany` annotations on attributes.


&nbsp;

A typical *JPA-Query* is:

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
        return query.getResultList();
    }
}
```

Mind that a list of *Customer* objects is returned from `findByLastName`,
not [*ResultSet*](https://www.columbia.edu/cu/help/jdk/docs/guide/jdbc/getstart/resultset.doc.html)
rows (*JDBC*).

*Object-Relatioal Mapping (ORM)* performs the mapping between rows in the
*ResultSet* to objects of the given type (here: `Customer.class`).
*Spring* uses the open *ORM* implementation [*Hibernate*](https://hibernate.org/orm).


&nbsp;

*Spring Data JPA* adopts these concepts and adds:

- a *no-SQL* interface to database tables by an interface:
    [*JpaRepository&lt;T, ID&gt;*](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html) for
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




<!-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -->

&nbsp;

## 3. *Freerider Endpoints*




<!-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -->

&nbsp;

## 4. *Open-API* and *Swagger-UI*

[*Open-API*](.)
is a ...

[*Swagger-UI*](.)
is a ...


*Swagger-UI* is introduced in a *Spring Boot* project by adding dependencies
for REST-API (embedded *tomcat/http* web-server starts) and for
*springdoc-openapi-starter-webmvc-ui*:

```xml
<!-- Spring Boot Web für die REST-Endpunkte -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- add OpenAPI 3 + Swagger-UI -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.8.5</version>
</dependency>
```




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

## 5. Running *Freerider* as *Services* in *Containers*



