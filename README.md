
<!-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -->
# E1: *Spring Boot*
<!-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -->

<!-- 
Spring supports a variety of "starters", which are configurations to launch Spring
- spring-boot-starter           ; Spring starter that only launches Spring
- spring-boot-starter-web       ; Spring also launches the tomcat HTTP-server
- spring-boot-starter-test      ; Spring also launches the JUnit5 test framework
- ...
- list of starters: https://github.com/spring-projects/spring-boot/tree/main/spring-boot-project/spring-boot-starters
-->

<!-- 
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <! -- choose main class to launch -- >
                <! -- configuration>
                    <mainClass>de.bht_berlin.freerider.FreeriderApplication</mainClass>
                </configuration -- >
            </plugin>
        </plugins>
    </build>
-->

<!-- 
# don't start apache/tomcat web server for faster start-up
# https://stackoverflow.com/questions/31897165/how-to-prevent-auto-start-of-tomcat-jetty-in-spring-boot-when-i-only-want-to-use
#
spring.main.web-application-type=none
-->

<!-- 
@SpringBootApplication
public class FreeriderApplication {
    @Bean
    CommandLineRunner runner() {
        return args -> {
            log( "CommandLineRunner runner()" );
            //
            Customer c1 = new Customer( "Baerlinsky", "Max", "max3245@gmx.de" );
            Customer c2 = new Customer( "Meyer", "Anne", "ma2958@gmx.de" );
            c1.setStatus( Customer.Status.InRegistration );
            c1.setId( "C020301" );
            //
            customerRepository.save( c1 );
            customerRepository.save( c2 );
            //
            long count = customerRepository.count();	// triggers loading data
            System.out.println( "CustomerRepository.count() -> " + count );
        };
    }
} -->

<!-- 
mvn validate          # validate project structure and pom.xml
mvn clean             # clear target directory
mvn compile           # compile sources from: src/main to: target/classes
mvn test-compile      # compile tests from: src/test to: target/test-classes
mvn test              # run tests, create report: target/site/surefire-report.html
mvn site -DgenerateReports=false surefire-report:report
mvn package           # package compiled classes to executable .jar in ./target
mvn package -DskipTests=true
mvn dependency:tree
mvn dependency:tree -DoutputFile=dependencies.txt
mvn dependency:build-classpath
mvn dependency:build-classpath -Dmdep.outputFile=classpath.txt
 -->

<!-- 
<img src="https://raw.githubusercontent.com/sgra64/ordering-system/refs/heads/markup/img/customer-order-article.png" width="800"/>
 -->


<!-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -->

[*Spring Boot*](https://spring.io) is a comprehensive framework for *Java*
to develop commercial-grade applications.


&nbsp;

---

Steps:

1. [Create new Project *spring-freerider*](#1-create-new-project-spring-freerider)

1. [Build-up to *base* Commit](#2-build-up-to-base-commit)

1. [*Source*, *Build* and *Run*](#3-source-build-and-run)

1. [Demo: *Inversion-of-Control (IoC)*](#4-demo-inversion-of-control-ioc)

1. [Demo: *Beans* and *Dependency Injection (DI)*](#5-demo-beans-and-dependency-injection-di)

1. [Demo: *Hello World* Web Controller](#6-demo-hello-world-web-controller)

1. [*Spring-JDBC* access to *FREERIDER-DB*](#7-spring-jdbc-access-to-freerider-db)


<!-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -->

&nbsp;

## 1. Create new Project *spring-freerider*

Configure the project at
[*spring-initializr*](https://start.spring.io)
with:

- Group Id: `de.bht-berlin`,

- Artifact: `freerider`,

- use other default choices: *Maven*, *Spring Boot: 4.0.1*, *Jar*, *Properties*, *Java 21*,

- select no other dependencies (in the right panel).

The selection should be:

<img src="https://raw.githubusercontent.com/sgra64/se2-spring-freerider/refs/heads/markup/img/spring-initializr.png" width="600"/>

Then click: *Generate* - which will download a file: `freerider.zip` to your laptop.

Unpack the zip-file into a new project directory: `spring-freerider`

Inspect content:

```sh
ls -la      # list initial content of the Spring Boot project
```
```
total 42
drwxr-xr-x 1     0 Jan 15 21:04 ./
drwxr-xr-x 1     0 Jan 15 21:04 ../
-rw-r--r-- 1    38 Jan 15 21:04 .gitattributes  <-- remove
-rw-r--r-- 1   394 Jan 15 21:04 .gitignore
drwxr-xr-x 1     0 Jan 15 21:04 .mvn/           <-- remove
-rw-r--r-- 1  1031 Jan 15 21:04 HELP.md         <-- remove
-rwxr-xr-x 1 11790 Jan 15 21:04 mvnw*           <-- remove
-rw-r--r-- 1  8292 Jan 15 21:04 mvnw.cmd        <-- remove
-rw-r--r-- 1  1398 Jan 15 21:04 pom.xml
drwxr-xr-x 1     0 Jan 15 21:04 src/
```

You may remove content that is not needed:

```
total 42
drwxr-xr-x 1     0 Jan 15 21:04 ./
drwxr-xr-x 1     0 Jan 15 21:04 ../
-rw-r--r-- 1   394 Jan 15 21:04 .gitignore
-rw-r--r-- 1  1398 Jan 15 21:04 pom.xml
drwxr-xr-x 1     0 Jan 15 21:04 src/
```

Inspect `src`:

```sh
find src    # inspect 'src'
```
```
src
src/main
src/main/java
src/main/java/de
src/main/java/de/bht_berlin
src/main/java/de/bht_berlin/freerider
src/main/java/de/bht_berlin/freerider/FreeriderApplication.java
src/main/resources
src/main/resources/application.properties   <-- application configuration
src/test
src/test/java
src/test/java/de
src/test/java/de/bht_berlin
src/test/java/de/bht_berlin/freerider
src/test/java/de/bht_berlin/freerider/FreeriderApplicationTests.java
             ^--^----------^            <-- GroupId
                           ^---------^  <-- ArtifactId
```

The package structure is defined by *GroupId* (de.bht-berlin) and
*ArtifactId* (freerider) configured with *spring-initializr*.

Both are also defined in *pom.xml*:

```xml
<groupId>de.bht-berlin</groupId>
<artifactId>freerider</artifactId>
<version>0.0.1-SNAPSHOT</version>
```


<!-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -->

&nbsp;

## 2. Build-up to *base* Commit

Put the project under *git* control and build up to a *base* from where
different developments can be branched (*base* commit).

Step 1:

Create an new git repository with an empty root commit and tag
the new commit as *'root'*.

```sh
git init --initial-branch=main
git commit --allow-empty -m "root commit (empty)"
git tag root
```

Step 2:

Commit the *spring-initializr* state of the project (with unneeded content
removed).

```sh
git add .gitignore pom.xml src
git commit -m "spring initializr"
```

Step 3:

Overlay the project with content from branch: `initializr-overlay` from the
remote repository:

- `https://github.com/sgra64/se2-spring-freerider.git`

- Set up a new remote: *"se2-repo"* for the URL.

Fetch the branch from the remote:

```sh
git fetch se2-repo initializr-overlay
```
```
remote: Enumerating objects: 21, done.
remote: Counting objects: 100% (21/21), done.
remote: Compressing objects: 100% (16/16), done.
remote: Total 21 (delta 2), reused 18 (delta 2), pack-reused 0 (from 0)
Unpacking objects: 100% (21/21), 6.38 KiB | 45.00 KiB/s, done.
From https://github.com/sgra64/se2-spring-freerider
 * branch            initializr-overlay -> FETCH_HEAD
 * [new branch]      initializr-overlay -> se2-repo/initializr-overlay
```

Checkout the fetched branch into the current branch and show
modifications:

```sh
git checkout se2-repo/initializr-overlay -- .

git status
```
```
On branch main
Changes to be committed:
  (use "git restore --staged <file>..." to unstage)
    new file:   .env/env.sh                 <-- new directory '.env' with sourcing script
    modified:   .gitignore
    new file:   .vscode/launch-terminal.sh  <-- new directory with '.vscode' settings
    new file:   .vscode/launch.json
    new file:   .vscode/settings.json
    new file:   pom.patch                   <-- patch to '.pom.xml'
    modified:   pom.xml
    modified:   src/main/resources/application.properties
```

You may apply a patch `pom.patch` to clean up `pom.xml`:

```sh
git reset pom.xml && git restore pom.xml

cat pom.xml                     # show content of pom.xml prior to patch

git apply pom.patch             # apply patch

cat pom.xml                     # show content after the patch

rm pom.patch                    # remove patch file
git add pom.xml pom.patch       # stage pom.xml after the patch
```

Commit the overlay and tag as *'base'*:

```sh
git commit -m "se2-repo initializr-overlay"
git tag base
```

Show the git commit log:

```sh
git log --oneline               # show the commit log
```
```
a124e11 (HEAD -> main, tag: base) spring initializr
e5882df spring initializr
88ff620 root commit (empty)
```

The project has now reached the stage from where further developments
can be branched.


<!-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -->

&nbsp;

## 3. *Source*, *Build* and *Run*

*Sourcing* the project means executing a script: `.env/env.sh` using the
*source* command that defines functions that can be executed as commands:

- `mkcp` - the command *"makes a classpath file"* `.class.path`, which
    contains the *CLASSPATH* based on *pom.xml* dependencies.

    The classpath file must be rebuild every time dependencies are changed
    in *pom.xml*.

- `wipe` - removes the classpath file `.class.path`.

- `run` - run the program in the terminal.


&nbsp;

***Source*** the project:

```sh
source .env/env.sh              # sourcing the project
```

The *CLASSPATH* has been set and the classpath file: `.class.path` has
been created:

```sh
echo $CLASSPATH
echo $CLASSPATH | tr ':' '\n'   # Windows: use ';' instead of ':'

cat .class.path
```

*CLASSPATH* and the classpath file are preconditions for building and
running the application.


&nbsp;

***Build*** (compile) the application.

Code is compiled to the target directory.

```sh
mvn compile                     # compile with maven

mvn test-compile                # compile tests under 'src/test/java'

find target                     # show compiled files under 'target'
```


&nbsp;

***Run*** the application.

The application should run in *VSCode*, configured under
[*.vscode*](https://github.com/sgra64/se2-spring-freerider/tree/initializr-overlay/.vscode)
where file
[*settings.json*](https://github.com/sgra64/se2-spring-freerider/blob/initializr-overlay/.vscode/settings.json)
has the
[*Code Runner*](https://marketplace.visualstudio.com/items?itemName=formulahendry.code-runner)
configuration:

```json
// Java Code Runner settings
"code-runner.defaultLanguage": "java",
"code-runner.clearPreviousOutput": true,
"code-runner.saveAllFilesBeforeRun": true,
"code-runner.executorMap": {
    // create file '.class.path' with:
    // - mvn dependency:build-classpath -q -Dmdep.outputFile=.class.path
    "java": "java -cp @.class.path de.bht_berlin.freerider.FreeriderApplication"
},
```


&nbsp;

Similarly, [*launch.json*](https://github.com/sgra64/se2-spring-freerider/blob/initializr-overlay/.vscode/launch.json) has launch configurations:

```json
//- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
// VSCode launch configuration for "Run and Debug" pane (Shift+Ctrl+D)
//  - https://code.visualstudio.com/docs/java/java-debugging
//- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
{
    "version": "1.0.0",
    "configurations": [
        {
            "type": "java",
            "name": "FreeriderApplication",
            "request": "launch",
            "mainClass": "de.bht_berlin.freerider.FreeriderApplication",
            "args": "",
            "console": "integratedTerminal",
            "vmArgs": ""
        }
    ]
}
```

Run the application in the IDE with the launcher (*"Run & Debug"* tab) and with
*Code Runner*, if you have it installed (with: *Ctrl + Alt + N*)

Run the application in the terminal with *maven*, with the *java* command and
with the *run* function (set during *sourcing*):

```sh
# run application with maven
mvn spring-boot:run --quiet\

# run application with the run function set during sourcing
run

# run with $CLASSPATH
java de.bht_berlin.freerider.FreeriderApplication

# run with classpath file (useful for VSCode runner)
java -cp @.class.path de.bht_berlin.freerider.FreeriderApplication

# run executable jar (create with: mvn package)
mvn package
java -jar target/freerider-0.0.1-SNAPSHOT.jar
```

The *Spring-Banner* indicates that the Spring Boot application has
been started:

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/

 :: Spring Boot ::                (v4.0.1)
```


<!-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -->

&nbsp;

## 4. Demo: *Inversion-of-Control (IoC)*

Spring uses the concept of *Inversion-of-Control (IoC)*, see article by
Martin Fowler, June 2005:
[*Inversion Of Control*](https://martinfowler.com/bliki/InversionOfControl.html).

The concept reverses the regular control flow, which starts in Java with the
*main()* function and then propagates by calling methods as stated in the program.

*"Inverted"* control flow means that methods are not called in the order as
stated in the program, but as effect of external triggers (events) that are not
stated in the program.

*Inverted* control flows are inherent in user interfaces when the order in which
a user hits UI-elements (buttons) cannot be stated in the program.
Not the program defines control, but the user by his actions. The program must
respond to these action.

Create a new branch off the *"base"* commit for the `demo-ioc`:

```sh
git switch -c demo-ioc base         # create branch 'demo-ioc' off the 'base' commit
```

Modify code in
`src/main/java/de/bht_berlin/freerider/FreeriderApplication.java`:


```java
package de.bht_berlin.freerider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class FreeriderApplication {

    /**
     * Constructor executes when Spring creates the Application Bean,
     * which is a singleton object of class {@link FreeriderApplication}
     * created by Spring due to the {@link @SpringBootApplication}.
     */
    public FreeriderApplication() {
        System.out.println("-<2>--> Constructor called");
    }

    public static void main(String[] args) {
        System.out.println("-<1>--> before Spring has been started");
        SpringApplication.run(FreeriderApplication.class, args);
        System.out.println("-<4>--> after Spring has exited");
    }

    /**
     * Called from Spring after Spring has been initialized.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        System.out.println("-<3>--> doSomethingAfterStartup()");
    }
}
```

Output shows the inverted control flow:

```
-<1>--> before Spring has been started
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/

 :: Spring Boot ::                (v4.0.1)

-<2>--> Constructor called
-<3>--> doSomethingAfterStartup()
-<4>--> after Spring has exited
```

Commit the changes to branch *demo-web-controller* with the
- commit message: `"inverted control flow demo"`.


<!-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -->

&nbsp;

## 5. Demo: *Beans* and *Dependency Injection (DI)*

Java *Beans* - in general - are Java objects that have extended properties over
regular Java objects.
The definition of *Beans* has evolved over time and varies in different systems.

Spring *Beans* are Java objects that are:

1. created by Spring - not by the program either as:

    - *Component Beans* are *Singleton* objects created when Spring finds
        annotations on classes:

        - with the top-level: `@Component` annotation and derived:
            `@Service`, `@RestController`, `@Controller` annotations.

    - *Entity Beans* are Java objects created when records are queried from a
        table in a database when the corresponding class carries the `@Entity`
        annotation.

    - *Factory Beans* are Java objects returned from factory methods carrying
        the `@Bean` annotation.

1. Since *beans* are created by Spring, they *must not* be created by the
    program using `new`. An exception will be thrown in this case.

1. *Component Beans* reside inside the Spring *"IoC Container"*, which is the
    name of the Spring runtime environment.

1. Access to *Component Beans* can be obtained by
    [*Dependency Injection (DI)*](https://martinfowler.com/articles/injection.html#FormsOfDependencyInjection).


&nbsp;

Create a new branch `demo-dependency-injection` off the *"base"* commit:

```sh
git switch -c demo-dependency-injection base    # create new branch off the 'base' commit
```

Create two component classes:

```java
package de.bht_berlin.freerider;

import org.springframework.stereotype.Component;

@Component
public class Calculator {

    /**
     * Calculate the {@link VAT} included in a gross value.
     * @param grossValue value with included {@link VAT}
     * @param rate {@link VAT} rate in percent, e.g. {@code 0.19} for {@code 19%}
     * @return {@link VAT} included in gross value
     */
    public long includedVAT(long grossValue, double rate) {
        if(grossValue <= 0 || rate <= 0.0)
            return 0L;
        //
        double vat = grossValue * rate / (1.0 + rate);
        long rounded = Math.round(vat);
        return rounded;
    }
}
```

and

```java
package de.bht_berlin.freerider;

import org.springframework.stereotype.Component;

@Component
public class Formatter {

    /**
     * Format long value to price according to a format (0 is default):
     * <pre>
     * Example: long value: 499
     * Style: 0: "4.99"
     *        1: "4.99 EUR"     3: "4.99 €"
     *        2: "4.99EUR"      4: "4.99€"
     * </pre>
     * @param price long value as price
     * @param style price formatting style
     * @return price formatted according to selcted style
     */
    public String fmtPrice(long price, int... style) {
        final int ft = style.length > 0? style[0] : 0;  // 0 is default format
        switch(ft) {
        case 0: return fmtDecimal(price, 2);
        case 1: return fmtDecimal(price, 2, " EUR");
        case 2: return fmtDecimal(price, 2, "EUR");
        case 3: return fmtDecimal(price, 2, " €");
        case 4: return fmtDecimal(price, 2, "€");
        default: return fmtPrice(price, 0);
        }
    }

    /**
     * Format long value to a decimal String with specified digit formatting:
     * <pre>
     *      {      "%,d", 1L },     // no decimal digits:  16,000Y
     *      { "%,d.%01d", 10L },
     *      { "%,d.%02d", 100L },   // double-digit price: 169.99E
     *      { "%,d.%03d", 1000L },  // triple-digit unit:  16.999-
     * </pre>
     * @param value value to format to String in decimal format
     * @param decimalDigits number of digits
     * @param unit appended unit as String
     * @return decimal value formatted according to specified digit formatting
     */
    public String fmtDecimal(long value, int decimalDigits, String... unit) {
        final String unitStr = unit.length > 0? unit[0] : null;
        final Object[][] dec = {
            {      "%,d", 1L },     // no decimal digits:  16,000Y
            { "%,d.%01d", 10L },
            { "%,d.%02d", 100L },   // double-digit price: 169.99E
            { "%,d.%03d", 1000L },  // triple-digit unit:  16.999-
        };
        String result;
        String fmt = (String)dec[decimalDigits][0];
        if(unitStr != null && unitStr.length() > 0) {
            fmt += "%s";        // add "%s" to format for unit string
        }
        int decdigs = Math.max(0, Math.min(dec.length - 1, decimalDigits));
        //
        if(decdigs==0) {
            Object[] args = {value, unitStr};
            result = String.format(fmt, args);
        } else {
            long digs = (long)dec[decdigs][1];
            long frac = Math.abs( value % digs );
            Object[] args = {value/digs, frac, unitStr};
            result = String.format(fmt, args);
        }
        return result;
    }
}
```

By means of the `@Component` annotation, Spring will create singleton-objects
as beans.

References to component beans are *"injected"* into a class that uses them.

There two forms:

- *Constructor injection* - by passing bean references as arguments to the
    constructor that then can initialize (final) variables or by

- *Variable injection* - by initilizing variable definitions of *@Component*
    classes using the `@Autowired` annotation.


&nbsp;

Modify code in
`src/main/java/de/bht_berlin/freerider/FreeriderApplication.java`:

```java
package de.bht_berlin.freerider;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.beans.factory.annotation.Autowired;


@SpringBootApplication
public class FreeriderApplication implements CommandLineRunner {

    // @Autowired
    private Calculator calculator;

    // @Autowired
    private Formatter formatter;

    /**
     * Constructor with "constructor-injection" of {@code @Component}
     * bean references.
     * @param calculator injected reference to {@link Calculator} component
     * @param formatter injected reference to {@link Formatter} component
     */
    FreeriderApplication(Calculator calculator, Formatter formatter) {
        this.calculator = calculator;
        this.formatter = formatter;
    }

    public static void main(String[] args) {
        SpringApplication.run(FreeriderApplication.class, args);
    }

    @Override
    public void run(String... args) {
        long price = 10000;         // 100 Euro in cent
        double vatRate = 19.0;      // VAT rate in percent
        // 
        long vat = calculator.includedVAT(price, vatRate / 100.0);
        long net = price - vat;
        // 
        var priceFmt = formatter.fmtPrice(price, 1);
        var vatFmt = formatter.fmtPrice(vat, 1);
        var netFmt = formatter.fmtPrice(net, 1);
        // 
        String result = String.format(
            "In %s (brutto) sind %s MwSt (%.1f%%) enthalten.\n" +
            "Der netto-Preis ist %s.", priceFmt, vatFmt, vatRate, netFmt);
        // 
        System.out.println(result);
    }
}
```

Run the demo:

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/

 :: Spring Boot ::                (v4.0.1)

In 100.00 EUR (brutto) sind 15.97 EUR MwSt (19.0%) enthalten.
Der netto-Preis ist 84.03 EUR.
```

Commit the changes to branch *demo-web-controller* with the
- commit message: `"dependency injection demo"`.


<!-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -->

&nbsp;

## 6. Demo: *Hello World* Web Controller

The demo recreates Spring's [*Quickstart*](https://spring.io/quickstart)
example.

Perform the demo in the this project (don't create a new project).
Create a new branch `demo-web-controller` off the *"base"* commit.

Leave class *FreeriderApplication* unchanged and create separate class
for the Web-Controller:

```java
package de.bht_berlin.freerider;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    @GetMapping("/hello")
    public String hello(
        @RequestParam(value="name", defaultValue="World") String name
    ) {
        return String.format("Hello %s!", name);
    }
}
```

Likely, the imports will light-up in red in your IDE.

- What might be the reason?

- Ask your *AI* or try to find out from the *Quickstart* guide.

<!-- 
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
 -->

Find out and fix the problem.

Change the root-logger configuration in
`application.properties` from level *"ERROR"* to *"INFO"* to see
some log-line printed:

```properties
spring.application.name=freerider

# show 'Spring' banner: OFF, CONSOLE, LOG
spring.main.banner-mode=CONSOLE

# report logs: FATAL, ERROR, WARN, DEBUG, INFO, TRACE, OFF
logging.level.root=ERROR --> INFO
```

Run the program. If the program exits quickly after start, the
classpath needs to be rebuilt due to the change in pom-dependencies:

```sh
mkcp            # rebuild CLASSPATH due to the change in pom-dependencies
```

Re-run the application:

```sh
run
```

The startup-log should now show some detail:

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/

 :: Spring Boot ::                (v4.0.1)

2026-01-16T01:30 INFO [main] d.b.freerider.FreeriderApplication       : Starting FreeriderApplication using Java 21 with PID 6972
2026-01-16T01:30 INFO [main] d.b.freerider.FreeriderApplication       : No active profile set, falling back to 1 default profile: "default"
2026-01-16T01:30 INFO [main] o.s.boot.tomcat.TomcatWebServer          : Tomcat initialized with port 8080 (http)
2026-01-16T01:30 INFO [main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2026-01-16T01:30 INFO [main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/11.0.15]
2026-01-16T01:30 INFO [main] b.w.c.s.WebApplicationContextInitializer : Root WebApplicationContext: initialization completed in 1707 ms
2026-01-16T01:30 INFO [main] o.s.boot.tomcat.TomcatWebServer          : Tomcat started on port 8080 (http) with context path '/'
2026-01-16T01:30 INFO [main] d.b.freerider.FreeriderApplication       : Started FreeriderApplication in 3.301 seconds (process running for 3.925)
```

Notice the program is not exiting and still running - rather waiting.
The embedded *HTTP*-Server
[*tomcat*](https://tomcat.apache.org/)
is waiting for HTTP-requests to answer.

Open a web-browser and point to

- [*http://localhost:8080/hello*](http://localhost:8080/hello)

The HTTP-GET request issued by the web-browser hits the *tomcat*
HTTP-Server that is embedded in the Spring-application. The GET-Request
to route: `"/hello"` is passed to the hello() method and answered.

<img src="https://spring.io/img/extra/quickstart-3.png" width="800"/>

What should happen if you add `?name=Amy` to the end of the URL?

To end the program running in the terminal, enter `^C`. The HTTP-Server
reports in logs that it is *"Commencing graceful shutdown"*:

```
2026-01-16T01:44 INFO [ionShutdownHook] o.s.boot.tomcat.GracefulShutdown   : Commencing graceful shutdown. Waiting for active requests to complete
2026-01-16T01:44 INFO [tomcat-shutdown] o.s.boot.tomcat.GracefulShutdown   : Graceful shutdown complete
```

Commit the changes to branch *demo-web-controller* with the
- commit message: `"Hello World web controller"`.


<!-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -->

&nbsp;

## 7. *Spring-JDBC* access to *FREERIDER-DB*

Create a new branch `demo-freerider-jdbc` off the *"base"* commit for this
development.

Follow tutorial
[*"Spring JDBC"*](https://www.baeldung.com/spring-jdbc-jdbctemplate)
and create a *Spring Boot* application in the current project that
reads tables: *CUSTOMER*, *VEHICLE* and *RESERVATION* from the existing
*FREERIDER_DB* database running in the *mysqld* docker container.

Don't create new records in the database (as suggested in the tutorial),
it is sufficient to read the existing records and output as tables:

```
+-----+-----------+-----------+----------------------+----------------+---------------------+
| ID  | NAME      | FIRSTNAME | CONTACT              | STATUS         | STATUS_CHANGE       |
+-----+-----------+-----------+----------------------+----------------+---------------------+
| 100 | Eric      | Meyer     | eme22@gmail.com      | Active         | 2024-06-04 12:35:00 |
| 101 | Sommer    | Tina      | +49 030 22458 29425  | Active         | 2025-10-07 10:28:00 |
| 102 | Schulze   | Tim       | +49 171 2358124      | Active         | 2024-12-28 18:00:00 |
| 103 | Brinkmann | Tobias    | +49 030 662465724    | InRegistration | 2025-11-28 12:18:00 |
| 104 | Tony      | Allister  | +49 030 24253134     | Active         | 2023-02-10 18:00:00 |
|     |           |           |                      |                |                     |
| ... | ...       | ...       |     ...              | ...            |      ...            |
|     |           |           |                      |                |                     |
| 105 | Sandra    | Ohlstadt  | ohlst@gmail.com      | Active         | 2023-08-17 18:00:00 |
| 108 | Igor      | Medwedev  | gopnik@bht-berlin.de | InRegistration | 2025-11-28 23:26:00 |
+-----+-----------+-----------+----------------------+----------------+---------------------+
37 rows in set (0.00 sec)   <-- 37 rows

+------+----------+-----------+-------+----------+----------+----------+
| ID   | MAKE     | MODEL     | SEATS | CATEGORY | POWER    | STATUS   |
+------+----------+-----------+-------+----------+----------+----------+
| 1001 | VW       | Golf      |     4 | Sedan    | Gasoline | Active   |
| 1002 | VW       | Golf      |     4 | Sedan    | Hybrid   | Active   |
| 2000 | BMW      | 320d      |     4 | Sedan    | Diesel   | Active   |
|      |          |           |       |          |          |          |
| ...  | ...      | ...       |   ... | ...      | ...      | ...      |
|      |          |           |       |          |          |          |
| 6000 | Tesla    | Model 3   |     4 | Sedan    | Electric | Active   |
| 6001 | Tesla    | Model S   |     4 | Sedan    | Electric | Serviced |
+------+----------+-----------+-------+----------+----------+----------+
273 rows in set (0.00 sec)  <-- 237 rows

+--------+-------------+------------+------------------+------------------+----------------+----------------+-----------+
| ID     | CUSTOMER_ID | VEHICLE_ID | TIME_BEGIN       | TIME_END         | PICKUP         | DROPOFF        | STATUS    |
+--------+-------------+------------+------------------+------------------+----------------+----------------+-----------+
| 145373 |         102 |       6001 | 2025-11-18 08:00 | 2025-11-20 08:00 | Berlin Wedding | Hamburg        | Booked    |
| 201235 |         103 |       1002 | 2025-11-17 10:00 | 2025-11-17 18:00 | Berlin Wedding | Berlin Wedding | Booked    |
| 351682 |         102 |       6000 | 2025-11-14 10:00 | 2025-11-17 16:30 | Berlin Wedding | Hamburg        | Cancelled |
| 382565 |         102 |       3000 | 2025-11-16 09:00 | 2025-11-17 09:00 | Berlin Wedding | Hamburg        | Inquired  |
| 682351 |         102 |       6000 | 2025-11-15 10:00 | 2025-11-16 20:00 | Potsdam        | Teltow         | Booked    |
+--------+-------------+------------+------------------+------------------+----------------+----------------+-----------+
5 rows in set (0.00 sec)    <-- 5 rows
```

&nbsp;

When done, commit changes to branch *demo-freerider-jdbc* with the
- commit message: `"Spring-JDBC access to FREERIDER-DB"`.
