
<!-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -->
# E2: *Spring Boot - JDBC*
<!-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -->

The assignment demonstrates the
[*JDBC (Java Database Connectivity)*](https://de.wikipedia.org/wiki/Java_Database_Connectivity) -
interface between a relational database server and a *Java* program.


&nbsp;

---

Steps:

1. [Prepare Database *FREERIDER-DB*](#1-prepare-database-freerider-db)

1. [*Spring-JDBC* access to Database *FREERIDER-DB*](#2-spring-jdbc-access-to-database-freerider-db)
 
1. [Create *JDBC* - *Join*-Query](#3-create-jdbc---join-query)


<!-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -->

&nbsp;

## 1. Prepare Database *FREERIDER-DB*

A database *TEST_DB* was created in the previous
[*assignment* `DB`](https://github.com/sgra64/db-freerider/tree/database)
with tables:

- *CUSTOMER*,

- *VEHICLE* and

- *RESERVATION*.

Create a new branch *"freerider-db-jdbc"* off the base commit:

```sh
# create new branch 'freerider-db-jdbc' off the base commit
git switch -c freerider-db-jdbc base
```

Overlay the new branch with content from branch
[*se2-repo - freerider-db-jdbc*](https://github.com/sgra64/se2-spring-freerider/tree/freerider-db-jdbc):

```sh
# checkout content from branch 'freerider-db-jdbc-jdbc' from remote 'se2-repo'
git fetch se2-repo freerider-db-jdbc

git checkout se2-repo/freerider-db-jdbc -- src/main/resources

find src/main/resources
```
```
src/main/resources
src/main/resources/application.yaml
src/main/resources/init-account.sql     <-- new file
src/main/resources/init-data.sql        <-- new file
src/main/resources/init-schema.sql      <-- new file
```

Start the Docker container with the database:

```sh
# start Docker container with the database
docker start mysqld-container

# attach bash-process to container and log-into the database
docker exec -it mysqld-container bash

mysql -u root
```

<!-- 
```sh
docker run --name mysqld-container -d \
    -e MYSQL_ALLOW_EMPTY_PASSWORD=yes \
    -e MYSQL_ROOT_PASSWORD= \
    -v mysqld-volume:/var/lib/mysql \
    -p3306:3306 \
    mysql:8.4
```
-->


Create new database *"FREERIDER_DB"*:

```
mysql> show databases;
mysql> create database FREERIDER_DB;
```

Add the identity (*"user account"*): `freerider` with password: `free.ride`
to connect to the `FREERIDER_DB`:

```sql
-- grant user 'root' all privileges on all databases
CREATE USER 'root'@'%' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%';

-- grant user 'freerider' all privileges on database FREERIDER_DB
CREATE USER 'freerider'@'%' IDENTIFIED BY 'free.ride';
GRANT ALL PRIVILEGES ON FREERIDER_DB.* to 'freerider'@'%';
```

Show the new user account:

```sql
SELECT host, user, plugin FROM mysql.user;
```
```
+-----------+------------------+-----------------------+
| host      | user             | plugin                |
+-----------+------------------+-----------------------+
| %         | freerider        | caching_sha2_password | <-- new 'freerider' account
| %         | root             | caching_sha2_password |
| localhost | mysql.infoschema | caching_sha2_password |
| localhost | mysql.session    | caching_sha2_password |
| localhost | mysql.sys        | caching_sha2_password |
| localhost | root             | caching_sha2_password |
+-----------+------------------+-----------------------+
6 rows in set (0.00 sec)
```

Load schema and data into the *FREERIDER_DB* database:

```sh
# schema-load: load schema into the 'FREERIDER_DB' database
cat src/main/resources/init-schema.sql | docker exec -i "mysqld-container" mysql -D "FREERIDER_DB"

# data-load: load data into the 'FREERIDER_DB' database
cat src/main/resources/init-data.sql | docker exec -i "mysqld-container" mysql -D "FREERIDER_DB"

echo "USE FREERIDER_DB; SHOW TABLES;" | docker exec -i "mysqld-container" mysql
```
```
Tables_in_FREERIDER_DB:
CUSTOMER
RESERVATION
VEHICLE
```

Sketch the processes involved into these commands.


&nbsp;

Show data from the *FREERIDER_DB* database:

```
mysql> use FREERIDER_DB;
mysql> SELECT * FROM CUSTOMER;
```
```
mysql> SELECT * FROM CUSTOMER;
mysql> SELECT * FROM VEHICLE;
mysql> SELECT * FROM RESERVATION;
+-----+-----------+-----------+----------------------+----------------+---------------------+
| ID  | NAME      | FIRSTNAME | CONTACT              | STATUS         | STATUS_CHANGE       |
+-----+-----------+-----------+----------------------+----------------+---------------------+
| 100 | Eric      | Meyer     | eme22@gmail.com      | Active         | 2024-06-04 12:35:00 |
| 101 | Sommer    | Tina      | +49 030 22458 29425  | Active         | 2025-10-07 10:28:00 |
| 102 | Schulze   | Tim       | +49 171 2358124      | Active         | 2024-12-28 18:00:00 |
| 103 | Brinkmann | Tobias    | +49 030 662465724    | InRegistration | 2025-11-28 12:18:00 |
| 104 | Tony      | Allister  | +49 030 24253134     | Active         | 2023-02-10 18:00:00 |
| 105 | Sandra    | Ohlstadt  | ohlst@gmail.com      | Active         | 2023-08-17 18:00:00 |
| 106 | Erica     | Gronemann | gronemann@gmx.de     | InRegistration | 2022-02-26 07:02:00 |
| 107 | Khaleed   | Samadi    | -                    | Active         | 2020-09-24 18:00:00 |
| 108 | Igor      | Medwedev  | gopnik@bht-berlin.de | InRegistration | 2025-11-28 23:26:00 |
+-----+-----------+-----------+----------------------+----------------+---------------------+
9 rows in set (0.00 sec)

+------+----------+---------------+-------+----------+----------+----------+
| ID   | MAKE     | MODEL         | SEATS | CATEGORY | POWER    | STATUS   |
+------+----------+---------------+-------+----------+----------+----------+
| 1001 | VW       | Golf          |     4 | Sedan    | Gasoline | Active   |
| 1002 | VW       | Golf          |     4 | Sedan    | Hybrid   | Active   |
| 1200 | VW       | Multivan Life |     8 | Van      | Gasoline | Active   |
| 2000 | BMW      | 320d          |     4 | Sedan    | Diesel   | Active   |
| 3000 | Mercedes | EQS           |     4 | Sedan    | Electric | Active   |
| 6000 | Tesla    | Model 3       |     4 | Sedan    | Electric | Active   |
| 6001 | Tesla    | Model S       |     4 | Sedan    | Electric | Serviced |
+------+----------+---------------+-------+----------+----------+----------+
7 rows in set (0.00 sec)

+--------+-------------+------------+---------------------+---------------------+----------------+----------------+-----------+
| ID     | CUSTOMER_ID | VEHICLE_ID | TIME_BEGIN          | TIME_END            | PICKUP         | DROPOFF        | STATUS    |
+--------+-------------+------------+---------------------+---------------------+----------------+----------------+-----------+
| 145373 |         102 |       6001 | 2025-11-18 08:00:00 | 2025-11-20 08:00:00 | Berlin Wedding | Hamburg        | Booked    |
| 201235 |         103 |       1002 | 2025-11-17 10:00:00 | 2025-11-17 18:00:00 | Berlin Wedding | Berlin Wedding | Booked    |
| 351682 |         102 |       6000 | 2025-11-14 10:00:00 | 2025-11-17 16:30:00 | Berlin Wedding | Hamburg        | Cancelled |
| 382565 |         102 |       3000 | 2025-11-16 09:00:00 | 2025-11-17 09:00:00 | Berlin Wedding | Hamburg        | Inquired  |
| 682351 |         102 |       6000 | 2025-11-15 10:00:00 | 2025-11-16 20:00:00 | Potsdam        | Teltow         | Booked    |
+--------+-------------+------------+---------------------+---------------------+----------------+----------------+-----------+
5 rows in set (0.00 sec)
```

Try to connect to the database via the *VSCode* *"SQL-Tools"* extension based on the
configuration in `.vscode/settings.json`:

```json
"sqltools.connections": [ {
    "name": "Local MySQL Server (TEST_DB)",
    "database": "TEST_DB",
    "username": "root",
    "password": "",
    "server": "localhost",
    "port": 3306,
    "driver": "MySQL",
    "mysqlOptions": {
        "authProtocol": "default",
        "charset": "utf8mb4_general_ci"
    }
}, {
    "name": "Local MySQL Server (FREERIDER_DB)",
    "database": "FREERIDER_DB",
    "username": "freerider",
    "password": "free.ride",
    "server": "localhost",
    "port": 3306,
    "driver": "MySQL",
    "mysqlOptions": {
        "authProtocol": "default",
        "charset": "utf8mb4_general_ci"
    }
}, ],
```

Commit the sql-init files imported from branch *"se2-repo/freerider-db-jdbc"*:

```sh
git commit -m "add database init *.sql files under 'src/main/resource'"
```


<!-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -->

&nbsp;

## 2. *Spring-JDBC* access to Database *FREERIDER-DB*

<!-- 
Follow tutorial
[*"Spring JDBC"*](https://www.baeldung.com/spring-jdbc-jdbctemplate)
and create a *Spring Boot* application in the current project that
reads tables: *CUSTOMER*, *VEHICLE* and *RESERVATION* from the existing
*FREERIDER_DB* database running in the *mysqld* docker container.
 -->

[*Java Database Connectivity (JDBC)*](https://en.wikipedia.org/wiki/Java_Database_Connectivity)
is the first interface for database access in Java (1997). It is still used
today and forms the lowest database access layer in all frameworks.

<!-- 
<img src="https://lms.bht-berlin.de/pluginfile.php/2596129/mod_assign/intro/jdbc.jpg" width="240"/>
 -->

*Spring Boot's* *"data-access hierarchy"* is comprised of multiple layers:

```
  [ Spring Boot Application ]
              │
              ▼
 [ Spring Data (Repositories) ]     <-- Spring Data JPA: hide Queries, Schema - expose Classes, Objects
              │
              ▼
 [ Spring JDBC (JdbcTemplate) ]     <-- Spring Data JDBC: manages database connections (open/close)
              │
              ▼
       [ Plain JDBC ]               <-- native JDBC: interface
              │
              ▼
     [ Database-Driver ]          <-- MySQL-, PostgreSQL-, h2- or other database driver
              │
              ▼
     [ TCP-IP Connection ]          <-- TCP-IP connection to database server
```

We use the *Spring Data JDBC* package for demonstration.

Properties of the *"JDBC"* interface are:

1. *SQL* (queries, executions) is explicit and visible in programs.

1. Query results are returned as *tables* (*"ResultSet"* as rows/records) in
    text-form as delivered from the database.

1. A pre-processing mechanism allows insert parameters into SQL statements
    (*"SQL Prepare Statement"* with the replacement of `?` markers).

1. Object creation for returned records is optional(!) avoiding the creation
    of massive numbers of objects as with higher data access layers (*JPA*, *ORM*).

1. If objects are created for returned records, the Row-Mapper pattern is
    used to create objects from rows of a *ResultSet*.

*Spring Boot* needs configuration information for the database, which is
provided in the central application configuration file under path
`src/main/resources`:

- `application.properties` or

- `application.yaml`.

Add database connection information to file `application.yaml` (mind the
identation in *.yaml* files):

```yaml
# application configuration
spring:
  application:
    name: freerider

  # NEW: database connection
  datasource:
    # # connection to embedded h2-database
    # url: jdbc:h2:mem:FREERIDER_DB;DB_CLOSE_DELAY=-1
    # # url: jdbc:h2:file:./data/FREERIDER_DB
    # driver-class-name: org.h2.Driver
    # username: sa
    # password:
    # 
    # connection to external MySQL-database 'FREERIDER_DB'
    url: jdbc:mysql://localhost:3306/FREERIDER_DB
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: freerider
    password: free.ride
    # 
    # # connection to external MySQL-database 'TEST_DB'
    # url: jdbc:mysql://localhost:3306/TEST_DB
    # driver-class-name: com.mysql.cj.jdbc.Driver
    # username: root
    # password:

  h2:
    console.enabled: true
    console.path: /h2-console
    console.settings.web-allow-others: true

  sql:
    # initialize database: 'always', 'never', 'embedded'
    init.mode: never
    init.schema-locations: classpath:init-schema.sql
    init.data-locations: classpath:init-data.sql

  # show 'Spring Boot' banner: OFF, CONSOLE, LOG
  main.banner-mode: CONSOLE

# report logs: OFF, FATAL, ERROR, WARN, INFO, DEBUG, TRACE
logging:
  level:
    root: ERROR
```


&nbsp;

Checkout more content from remote branch *freerider-db-jdbc-jdbc* that demonstrates
the use of *Spring Boot's* JDBC-interface that is encapsulated in a *Bean* object
of type
[*JdbcTemplate*](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/JdbcTemplate.html).

```sh
# checkout content from remote branch 'freerider-db-jdbc-jdbc'
git checkout se2-repo/freerider-db-jdbc -- src/main/java

find src/main/java -name '*.java'
```
```
src/main/java/de/bht_berlin/freerider/FreeriderApplication.java
src/main/java/de/bht_berlin/freerider/JdbcRunner.java               <-- new
src/main/java/de/bht_berlin/freerider/TableFormatter.java           <-- new
src/main/java/de/bht_berlin/freerider/TableFormatterFactory.java    <-- new
```

Inspect and understand the new classes:

- [*JdbcRunner.java*](https://github.com/sgra64/se2-spring-freerider/tree/freerider-db-jdbc/src/main/java/de/bht_berlin/freerider)
     -- the class has the entry point of the application using
    a [*CommandLineRunner*](https://docs.spring.io/spring-boot/api/java/org/springframework/boot/CommandLineRunner.html)
    *Bean* factory method. The class also runs *JDBC* queries.

- [*TableFormatterFactory.java*](https://github.com/sgra64/se2-spring-freerider/tree/freerider-db-jdbc/src/main/java/de/bht_berlin/freerider/TableFormatterFactory.java)
    -- class with factory methods of *TableFormatter* *Bean* objetcs that are
    used to format output as a table.

- [*TableFormatter.java*](https://github.com/sgra64/se2-spring-freerider/tree/freerider-db-jdbc/src/main/java/de/bht_berlin/freerider/TableFormatter.java)
    -- class that formats tables.


&nbsp;

Run the application. The *JDBC-Query*:

```java
customerTableFormatter.header();

/** 
 * Traditional, iterative JDBC-Query producing table using {@code customerTableFormatter}.
 * <pre>
 * +-----+-----------+-----------+---------------------+----------------+------------------+
 * | ID  | NAME      | FIRSTNAME | CONTACT             | STATUS         | STATUS_CHANGE    |
 * +-----+-----------+-----------+---------------------+----------------+------------------+
 * | 101 | Sommer    | Tina      | +49 030 22458 29425 | Active         | 07.10.2025 10:28 |
 * | 102 | Schulze   | Tim       | +49 171 2358124     | Active         | 28.12.2024 06:00 |
 * | 103 | Brinkmann | Tobias    | +49 030 662465724   | InRegistration | 28.11.2025 12:18 |
 * +-----+-----------+-----------+---------------------+----------------+------------------+
 * </pre>
 */
jdbcTemplate.query(
    // query is sent to the database
    "SELECT * FROM CUSTOMER",
        // database returns ResultSet as iterator 'rs'
        (RowCallbackHandler) rs -> {
            while (rs.next()) {
                /*
                 * insert row into 'customerTableFormatter'-table from values extracted
                 * from {@link ResultSet} {@code 'rs'} passed by {@link RowCallbackHandler}
                 */
                customerTableFormatter.row(
                    String.format("%s", rs.getObject("ID")),
                    rs.getString("NAME"),
                    rs.getString("FIRSTNAME"),
                    rs.getString("CONTACT"),
                    rs.getString("STATUS"),
                    rs.getTimestamp("STATUS_CHANGE").toLocalDateTime().format(dateTimeFormatter)
                );
            }
        }
);
customerTableFormatter.footer().print(System.out);
```

The code fetches results from the *CUSTOMER* table. Results are passed back from the database as
[*java.sql.ResultSet*](https://docs.oracle.com/javase/8/docs/api/java/sql/ResultSet.html).
Fields are extracted for each row from the *ResultSet* and passed into the table formatter
to format output.


```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/

 :: Spring Boot ::                (v4.1.0)

...customers -->  9
....vehicles -->  7
reservations -->  5
+-----+----------------+------------------+----------------------+----------------+------------------+
| ID  | NAME           | FIRSTNAME        | CONTACT              | STATUS         | STATUS_CHANGE    |
+-----+----------------+------------------+----------------------+----------------+------------------+
| 101 | Sommer         | Tina             | +49 030 22458 29425  | Active         | 07.10.2025 10:28 |
| 102 | Schulze        | Tim              | +49 171 2358124      | Active         | 28.12.2024 06:00 |
| 103 | Brinkmann      | Tobias           | +49 030 662465724    | InRegistration | 28.11.2025 12:18 |
| 104 | Tony           | Allister         | +49 030 24253134     | Active         | 10.02.2023 06:00 |
| 105 | Sandra         | Ohlstadt         | ohlst@gmail.com      | Active         | 17.08.2023 06:00 |
| 106 | Erica          | Gronemann        | gronemann@gmx.de     | InRegistration | 26.02.2022 07:02 |
| 107 | Khaleed        | Samadi           | -                    | Active         | 24.09.2020 06:00 |
| 108 | Igor           | Medwedev         | gopnik@bht-berlin.de | InRegistration | 28.11.2025 11:26 |
+-----+----------------+------------------+----------------------+----------------+------------------+
```


&nbsp;

A more modern variation does not use an *iterator* with *while* - loop, but the
*"Row-Mapper"* pattern:

```java
/** 
 * JDBC-Query with row-mapper pattern without iterator
 */
customerTableFormatter.header();
List<String[]> resultSet = jdbcTemplate.query(
    // query is sent to the database
    "SELECT * FROM CUSTOMER",
        // row-mapper returns a mapped value for each row
        (rs, rowNum) -> new String[] {
            String.format("%s", rs.getObject("ID")),
            rs.getString("NAME"),
            rs.getString("FIRSTNAME"),
            rs.getString("CONTACT"),
            rs.getString("STATUS"),
            rs.getTimestamp("STATUS_CHANGE").toLocalDateTime().format(dateTimeFormatter)
        }
);
resultSet.forEach(mappedRow -> customerTableFormatter.row(mappedRow));
// 
customerTableFormatter.footer().print(System.out);
```


<!-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -->

&nbsp;

## 3. Create *JDBC* - *Join*-Query

Extend class *JdbcRunner* such that it produces the following output table
using *customerReservationsTableFormatter*. The output shows all reservations
with

- Customer names (mind the format),

- start and end date/time,

- the reserved car and

- the status of each reservation.

Think about the query to extract the content from the database. Write down
the SQL-Query and try with the database first before entering into the code.

Output:

```
+----------------------+------------------+----------------------+-----------+-----------+-----------+
| NAME                 | BEGIN            | END                  | MAKE      | MODEL     | STATUS    |
+----------------------+------------------+----------------------+-----------+-----------+-----------+
| Schulze, Tim         | 18.11.2025 08:00 | 20.11.2025 08:00     | Tesla     | Model S   | Booked    |
| Brinkmann, Tobias    | 17.11.2025 10:00 | 17.11.2025 06:00     | VW        | Golf      | Booked    |
| Schulze, Tim         | 14.11.2025 10:00 | 17.11.2025 04:30     | Tesla     | Model 3   | Cancelled |
| Schulze, Tim         | 16.11.2025 09:00 | 17.11.2025 09:00     | Mercedes  | EQS       | Inquired  |
| Schulze, Tim         | 15.11.2025 10:00 | 16.11.2025 08:00     | Tesla     | Model 3   | Booked    |
+----------------------+------------------+----------------------+-----------+-----------+-----------+
```

