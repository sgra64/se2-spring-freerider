
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
# don't start apache/tomcat web server for faster start-up
# https://stackoverflow.com/questions/31897165/how-to-prevent-auto-start-of-tomcat-jetty-in-spring-boot-when-i-only-want-to-use
#
spring.main.web-application-type=none
-->

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

[*Spring Boot*](https://spring.io) is a professionally-grade framework for
developing *Java* applications.


&nbsp;

---

Steps:

1. [Create new Project *spring-freerider*](#1-create-new-project-spring-freerider)

1. [Check-into local *git*-Repository](#2-check-into-local-git-repository)

1. [*Source*, *Build* and *Run* the Application](#3-source-build-and-run-the-application)

1. [Demo: *Inversion-of-Control (IoC)*](#4-demo-inversion-of-control-ioc)

1. [Demo: *Beans* and *Dependency Injection (DI)*](#5-demo-beans-and-dependency-injection-di)

<!-- 
1. [*Customers* Endpoint](#8-customers-endpoint)

1. [*OpenAPI* and *Swagger-UI*](#9-openapi-and-swagger-ui)
 -->

<!-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -->

&nbsp;

## 1. Create new Project *spring-freerider*

Configure the project at
[*spring-initializr*](https://start.spring.io)
with:

- Group Id: `de.bht-berlin`,

- Artifact: `freerider`,

- use other choices: *Maven*, *Spring Boot: 4.1.0*, *Jar*, *YAML*,
    *Java 25* (mind the Java version on your laptop, choose Java 21 for older
    versions),

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
total 46
drwxr-xr-x 1     0 Jun 17 11:46 ./
drwxr-xr-x 1     0 Jun 17 11:15 ../
-rw-r--r-- 1    38 Jun 17 11:46 .gitattributes  <-- remove
-rw-r--r-- 1   394 Jun 17 11:46 .gitignore
drwxr-xr-x 1     0 Jun 17 11:46 .mvn/
-rw-r--r-- 1  1031 Jun 17 11:46 HELP.md         <-- remove
-rwxr-xr-x 1 11790 Jun 17 11:46 mvnw*           <-- remove
-rw-r--r-- 1  8292 Jun 17 11:46 mvnw.cmd        <-- remove
-rw-r--r-- 1  1342 Jun 17 11:46 pom.xml
drwxr-xr-x 1     0 Jun 17 11:46 src/
```

Remove content that is not needed.

```
total 17
drwxr-xr-x 1    0 Jun 17 11:48 ./
drwxr-xr-x 1    0 Jun 17 11:15 ../
-rw-r--r-- 1  394 Jun 17 11:46 .gitignore
drwxr-xr-x 1    0 Jun 17 11:46 .mvn/
-rw-r--r-- 1 1342 Jun 17 11:46 pom.xml
drwxr-xr-x 1    0 Jun 17 11:46 src/
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
src/main/resources/application.yaml     <-- application configuration
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

## 2. Check-into local *git*-Repository

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
git add .gitignore .mvn pom.xml src
git commit -m "spring initializr"
```

Step 3:

Import *git*-module
[*"vscode-spring-boot"*](https://github.com/sgra64/gitmodule-vscode-spring-boot):

```sh
# create new branch 'git-modules' for git-modules off the 'root'-commit
git switch -c git-modules root

# import git-module 'vscode-spring-boot'
git submodule add -f -- https://github.com/sgra64/gitmodule-vscode-spring-boot.git .vscode

# remove file '.gitmodules' from the tracking index to keep it when switching branches
git rm --cached .gitmodules

# commit the new git-module (already staged) to the 'git-modules' branch
git commit -m "git submodules: .env"

# switch back to the 'main'-branch (ignore warning: '.vscode': Directory not empty)
git switch main
```

Step 4:

Overlay the project with content from remote branch:
[*initializr-overlay*](https://github.com/sgra64/se2-spring-freerider/tree/initializr-overlay).
Set up a new remote: *"se2-repo"* and fetch the branch:

```sh
# setup new remote 'se2-repo' and fetch branch 'initializr-overlay'
git remote add se2-repo https://github.com/sgra64/se2-spring-freerider.git

git fetch se2-repo initializr-overlay
```
```
remote: Enumerating objects: 47, done.
remote: Counting objects: 100% (47/47), done.
remote: Compressing objects: 100% (27/27), done.
remote: Total 47 (delta 10), reused 44 (delta 10), pack-reused 0 (from 0)
Unpacking objects: 100% (47/47), 9.01 KiB | 22.00 KiB/s, done.
From https://github.com/sgra64/se2-spring-freerider
 * branch            initializr-overlay -> FETCH_HEAD
 * [new branch]      initializr-overlay -> se2-repo/initializr-overlay
```

Checkout the fetched branch into the project directory and show
modifications:

```sh
# checkout the fetched branch and show modifications
git checkout se2-repo/initializr-overlay -- .

git status
```
```
On branch main
Changes to be committed:
  (use "git restore --staged <file>..." to unstage)
        new file:   .env/env.sh
        modified:   .gitignore
        new file:   .mvn/jvm.config
        new file:   src/main/java/de/bht_berlin/freerider/Calculator.java
        new file:   src/main/java/de/bht_berlin/freerider/DemoCommandLineRunner.java
        new file:   src/main/java/de/bht_berlin/freerider/DemoEventListener.java
        modified:   src/main/resources/application.yaml
```

Commit the overlay:

```sh
# commit the overlay and tag as 'base'
git commit -m "se2-repo/initializr-overlay"
```

Show the git commit log:

```sh
git log --oneline               # show the commit log
```
```
087d424 (HEAD -> main) se2-repo/initializr-overlay
13aa82f spring initializr
7873a95 (tag: root) root commit (empty)
```

The project has now content that allows developments:

```sh
# show project content
ls -la
```
```
total 25
drwxr-xr-x 1 svgr2 Kein    0 Jun 17 12:32 .
drwxr-xr-x 1 svgr2 Kein    0 Jun 17 11:51 ..
drwxr-xr-x 1 svgr2 Kein    0 Jun 17 12:32 .env
drwxr-xr-x 1 svgr2 Kein    0 Jun 17 12:34 .git
-rw-r--r-- 1 svgr2 Kein 1117 Jun 17 12:32 .gitignore
-rw-r--r-- 1 svgr2 Kein  104 Jun 17 12:11 .gitmodules
drwxr-xr-x 1 svgr2 Kein    0 Jun 17 12:12 .mvn
drwxr-xr-x 1 svgr2 Kein    0 Jun 17 12:11 .vscode
-rw-r--r-- 1 svgr2 Kein 1597 Jun 17 12:12 pom.xml
drwxr-xr-x 1 svgr2 Kein    0 Jun 17 12:12 src
```


<!-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -->

&nbsp;

## 3. *Source*, *Build* and *Run* the Application

*Sourcing* the project means executing a script: `.env/env.sh` using the
*source* command that defines functions that can be executed as commands:

- `run` - if not present, creates the classpath file `target/.classpath`, the
    file containing the main-class: `target/.mainclass`, sets the *CLASSPATH*
    environment variable and runs the program in the terminal.

- `wipe` - removes created files `target/.classpath`, `target/.mainclass` and
    unsets *CLASSPATH*.


&nbsp;

***Source*** and run the project:

```sh
# source the project (creates the 'run' command)
source .env/env.sh
```


&nbsp;

***Build*** (compile) the application. Code is compiled to the target directory:

```sh
mvn compile                     # compile with maven

mvn test-compile                # compile tests under 'src/test/java'

find target                     # show compiled files under 'target'
```

*--> Fix:* If an error occurs during compilation:
`"unknown compiler flag: --sun-misc-unsafe-memory-access"`, your
Java version is likely less than *`25`* (probe with: `java --verion`). Java
versions higher or equal `25` require the flag - versions less than `25`
causes the error.
To fix, remove from file `.mvn/jvm.config` the line:

```sh
# remove line from file '.mvn/jvm.config':
--sun-misc-unsafe-memory-access=allow
```

Commit the change with message:
    `update .mvn/jvm.config, remove --sun-misc-unsafe-memory-access`.


&nbsp;

***Run*** the application.

The application should run in both, the terminal and the *VSCode* IDE, configured under
[*.vscode*](https://github.com/sgra64/gitmodule-vscode-spring-boot)
where file
[*settings.json*](https://github.com/sgra64/gitmodule-vscode-spring-boot/blob/main/settings.json)
has the *CodeRunner* configuration and *launchers* are also defined.


The *run* function (sourced before) can be called in the terminal directly:

```sh
run
```
```
mvn -q dependency:build-classpath -Dmdep.outputFile=target/.classpath
export CLASSPATH=$( < target/.classpath)
searching for main()-method in 'src/main/java'...
found main class: 'de.bht_berlin.freerider.FreeriderApplication', stored in file 'target/.mainclass'

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \                    <-- 'Spring Boot' banner
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/

 :: Spring Boot ::                (v4.1.0)

Bean (object) created for @Component class: DemoCommandLineRunner
Bean (object) created for @Component class: DemoEventListener
\\
DemoCommandLineRunner, result: 22                           <-- application output
DemoEventListener: ApplicationReadyEvent event received     <-- application output
```

Output shows application components are being invoked. Inspect the src tree for
components:

```sh
# show content under 'src'
find src
```
```
src
src/main
src/main/java
src/main/java/de
src/main/java/de/bht_berlin
src/main/java/de/bht_berlin/freerider
src/main/java/de/bht_berlin/freerider/Calculator.java               <-- 'Calculator' component
src/main/java/de/bht_berlin/freerider/DemoCommandLineRunner.java    <-- 'DemoCommandLineRunner' demo
src/main/java/de/bht_berlin/freerider/DemoEventListener.java        <-- 'DemoEventListener' demo
src/main/java/de/bht_berlin/freerider/FreeriderApplication.java     <-- class with 'main()' method
src/main/resources
src/main/resources/application.yaml                                 <-- application configuration file
src/test
src/test/java
src/test/java/de
src/test/java/de/bht_berlin
src/test/java/de/bht_berlin/freerider
src/test/java/de/bht_berlin/freerider/FreeriderApplicationTests.java    <-- sample unit test class
```

Other ways to run the program are:

```sh
# run the program with maven (-q, --quiet suppresses maven logs)
mvn -q spring-boot:run

# run with $CLASSPATH
java de.bht_berlin.freerider.FreeriderApplication

# run with classpath and mainclass files
java -cp @target/.classpath @target/.mainclass

# run executable jar created with: 'mvn package'
mvn package
java -jar target/freerider-0.0.1-SNAPSHOT.jar
```


&nbsp;

To run the application in the *VSCode* IDE, launchers (*"Run & Debug"* tab) can be used
that are defined in file
[*.vscode/launch.json*](https://github.com/sgra64/gitmodule-vscode-spring-boot/tree/main):

```json
{
    "version": "1.0.0",
    "configurations": [
        {
            "type": "java",
            "name": "FreeriderApplication",
            "request": "launch",
            "mainClass": "de.bht_berlin.freerider.FreeriderApplication",
            "args": [ ],
            "console": "integratedTerminal",
            "vmArgs": ""
        }
    ]
}
```

To run the application with the
[*VSCode CodeRunner*](https://marketplace.visualstudio.com/items?itemName=formulahendry.code-runner)
extension (if installed), use: *Ctrl + Alt + N* (invokes the *run* function) as the configuration in
[*.vscode/settings.json*](https://github.com/sgra64/gitmodule-vscode-spring-boot/tree/main)
shows:

```json
"code-runner.executorMap": {
    // '.env/env.sh' defines a 'run' function that creates files 'target/.classpath' and
    // 'target/.mainclass' and runs the code with: 'java -cp @target/.classpath @target/.mainclass'
    "java": "[ -f .env/env.sh ] && source .env/env.sh && run || echo no file \".env/env.sh\"",
    "vmArgs": "-Dfile.encoding=UTF-8"
},
```


&nbsp;

The *CLASSPATH* variable has been set and classpath and mainclass files:
`target/.classpath` and `target/.mainclass` have been created:

```sh
echo $CLASSPATH
echo $CLASSPATH | tr ';' '\n'   # Windows: use ';'
echo $CLASSPATH | tr ':' '\n'   # other use ':'
```
```
target/classes
C:\Sven1\svgr2\.m2\repository\org\springframework\boot\spring-boot-starter\4.1.0\spring-boot-starter-4.1.0.jar
C:\Sven1\svgr2\.m2\repository\org\springframework\boot\spring-boot-starter-logging\4.1.0\spring-boot-starter-logging-4.1.0.jar
C:\Sven1\svgr2\.m2\repository\ch\qos\logback\logback-classic\1.5.34\logback-classic-1.5.34.jar
...
C:\Sven1\svgr2\.m2\repository\org\jspecify\jspecify\1.0.0\jspecify-1.0.0.jar
C:\Sven1\svgr2\.m2\repository\org\springframework\spring-test\7.0.8\spring-test-7.0.8.jar
C:\Sven1\svgr2\.m2\repository\org\xmlunit\xmlunit-core\2.11.0\xmlunit-core-2.11.0.jar

(all dependencies: in total 50 .jar files)
```

Show the content of file `target/.mainclass`:

```sh
cat target/.mainclass
```
```
de.bht_berlin.freerider.FreeriderApplication
```


<!-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -->

&nbsp;

## 4. Demo: *Inversion-of-Control (IoC)*

Spring uses the concept of *Inversion-of-Control (IoC)*, see article by
Martin Fowler, June 2005:
[*Inversion Of Control*](https://martinfowler.com/bliki/InversionOfControl.html).

The concept reverses the regular control flow, which means one method or function
calls another creating a nested execution path defined in the program.
In *Java*, an execution starts in the *main()* function.

*"Inverse"* control flow means that methods are not called in the order stated
in the program, but as effect of external triggers (events) that are not
stated in the program.

*Inverse* control flows are inherent in user interfaces when the order in which
a user hits UI-elements (buttons) is unknown in the program.
Not the program defines control, but the external entity, e.g. a user by his
actions. The program must respond to these action.

Run the code and understand the code in:

- [*DemoCommandLineRunner.java*](https://github.com/sgra64/se2-spring-freerider/blob/initializr-overlay/src/main/java/de/bht_berlin/freerider/DemoCommandLineRunner.java) and

- [*DemoEventListener.java*](https://github.com/sgra64/se2-spring-freerider/blob/initializr-overlay/src/main/java/de/bht_berlin/freerider/DemoEventListener.java).

*DemoCommandLineRunner.java*:

```java
package de.bht_berlin.freerider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * The {@link CommandLineRunner} interface is a way in <i>Spring Boot</i>
 * to launch application code.
 * 
 * <i>Spring Boot</i> finds classes implementing the {@link CommandLineRunner}
 * interface during the initial class-scan. If the class carries a
 * {@code @Component} annotation, <i>Spring Boot</i> creates a <i>"Bean"</i>
 * object of this class and invokes the {@code run(String... args)}-method.
 * 
 * Multiple {@link CommandLineRunner} classes may exist and are being invoked
 * in random order.
 * 
 * The principle of not invoking {@code run(String... args)} directly from
 * own code, but <i>"being invoked"</i> by the framework is called
 * <i>"Inversion-of-Control (IoC)"</i>.
 */
@Component
public class DemoCommandLineRunner implements CommandLineRunner {

    /**
     * Definition of a variable of a <i>Bean</i> object that is automatically
     * initialized by the <i>Spring Boot</i> framework with {@code Autowired}.
     * 
     * The principle is called <i>"Dependency Injection (DI)"</i>.
     */
    @Autowired
    Calculator calculator;

    /**
     * Constructor. Dependencies can also be injected by the constructor of a
     * <i>Bean</i> class, which is called: <i>"Constructor Injection"</i>.
     * @param calculator
     */
    DemoCommandLineRunner(Calculator calculator) {
        this.calculator = calculator;
        // 
        System.out.println("Bean (object) created for @Component class: DemoCommandLineRunner");
    }

    /**
     * Method implementing the {@link CommandLineRunner} interface.
     */
    @Override
    public void run(String... args) throws Exception {

        /*
         * Variable {@code calculator} can be used after dependency injection.
         */
        int result = calculator.sum(10, 12);
        // 
        System.out.println(String.format("%sDemoCommandLineRunner, result: %d", "\\\\\n", result));
    }
}
```

*DemoEventListener.java*:

```java
package de.bht_berlin.freerider;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Class demonstrates an {@code @EventListener}.
 */
@Component
public class DemoEventListener {

    /**
     * Constructor.
     */
    private DemoEventListener() {
        System.out.println("Bean (object) created for @Component class: DemoEventListener");
    }

    /**
     * Method is not directly invoked anywhere in the code, it is
     * called upon an <i>event</i> raised after <i>Spring Boot</i>
     * has finished initialization.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        System.out.println("DemoEventListener: ApplicationReadyEvent event received");
    }
}
```

Add a constructor to class *FreeriderApplication* to demonstrate that annotation
`@SpringBootApplication` also creates a *Bean* object:

```java
package de.bht_berlin.freerider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FreeriderApplication {

    /**
     * Constructor.
     */
    private FreeriderApplication() {
        System.out.println("Bean (object) created for @SpringBootApplication class: FreeriderApplication");
    }

    public static void main(String[] args) {
        SpringApplication.run(FreeriderApplication.class, args);
    }
}
```

Run the code:

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/

 :: Spring Boot ::                (v4.1.0)

Bean (object) created for @SpringBootApplication class: FreeriderApplication
Bean (object) created for @Component class: DemoCommandLineRunner
Bean (object) created for @Component class: DemoEventListener
\\
DemoCommandLineRunner, result: 22
DemoEventListener: ApplicationReadyEvent event received
```

Commit the changes to branch *demo-web-controller* with the
- commit message: `"inverted control flow demo"`.


<!-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -->

&nbsp;

## 5. Demo: *Beans* and *Dependency Injection (DI)*

Java *Beans* - in general - are *Java objects* that have extended properties
over regular Java objects.
The definition of *Beans* has evolved over time and varies in different systems.

*Spring Beans* are Java objects that are:

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

Class
[*Calculator*](https://github.com/sgra64/se2-spring-freerider/blob/initializr-overlay/src/main/java/de/bht_berlin/freerider/Calculator.java)
is a simple `@Component` class for which *Spring Boot* creates a *Component Bean*.

Add a constructor to class *Calculator* to demonstrate that annotation
`@Component` also creates a *Bean* object:

```java
package de.bht_berlin.freerider;

import org.springframework.stereotype.Component;

/**
 * {@code @Component} class that provides calculation methods. Class is
 * instantiated as a <i>singleton</i> <i>"Spring Bean"</i> object by the\
 * <i>Spring Boot</i> framework.
 */
@Component
public class Calculator {

    /**
     * Constructor.
     */
    private Calculator() {
        System.out.println("Bean (object) created for @Component class: Calculator");
    }

    int sum(int x, int y) {
        return x + y;
    }
}
```

Run the code:

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/

 :: Spring Boot ::                (v4.1.0)

Bean (object) created for @SpringBootApplication class: FreeriderApplication
Bean (object) created for @Component class: Calculator
Bean (object) created for @Component class: DemoCommandLineRunner
Bean (object) created for @Component class: DemoEventListener
\\
DemoCommandLineRunner, result: 22
DemoEventListener: ApplicationReadyEvent event received
```

Remove the constructor dependency injection and `@Autowired` in class
*DemoCommandLineRunner* and re-run. A *NullpointerException* is thrown
due to the now uninitialized *calculator* variable:

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/

 :: Spring Boot ::                (v4.1.0)

Bean (object) created for @SpringBootApplication class: FreeriderApplication
Bean (object) created for @Component class: DemoEventListener
2026-06-17T19:12:10.939+02:00 ERROR 18904 --- [freerider] [           main] o.s.
boot.SpringApplication               : Application run failed

java.lang.NullPointerException: Cannot invoke "de.bht_berlin.freerider.Calculator.sum(int, int)" because "this.calculator" is null at de.bht_berlin.freerider.DemoCommandLineRunner.run(DemoCommandLineRunner.java:55) ~[classes/:na]
at org.springframework.boot.SpringApplication.lambda$callRunner$1(SpringApplication.java:792) ~[spring-boot-4.1.0.jar:4.1.0]
...
```

Clean up the demo code and prepare the *"base"* commit for the project:

```sh
# show the git-status of the project
git status

# remove all modifications, e.g. in 'FreeriderApplication.java'
git restore .

# remove files from branch
git rm src/main/java/de/bht_berlin/freerider/Calculator.java
git rm src/main/java/de/bht_berlin/freerider/DemoCommandLineRunner.java
git rm src/main/java/de/bht_berlin/freerider/DemoEventListener.java

# commit and tag as 'base'
git commit -m "project base commit"
git tag base

# show the commit log
git log --oneline
```
```
c509609 (HEAD -> main, tag: base) project base commit
087d424 se2-repo/initializr-overlay
13aa82f spring initializr
7873a95 (tag: root) root commit (empty)
```



<!-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -->
<!-- 
&nbsp;

## 8. *Customers* Endpoint

*API - Endpoints* are interfaces to resources that are provided by a server or
service. Resources can refer to objects managed by the service such as `/customers`
or to application concepts such as `/artists` or `/playlists`.

HTTP serves as the underlying protocol with operations: `POST` (create),
`GET` (read), `PUT` (update) and `DELETE` (delete).

*REST-EP* or *REST-API* follow the REST-style in *Roy Fielding's* PhD dissertation,
Chapter 5:
[*"Representational State Transfer (REST)"*](https://roy.gbiv.com/pubs/dissertation/fielding_dissertation.pdf),
University of California, Irvine, 2000.

Examples of well-designed, published REST-API are:

- [*Spotify-API*](https://developer.spotify.com/documentation/web-api),

- [*PayPal-API*](https://developer.paypal.com/api/rest/) or

- [*Docker-API*](https://docs.docker.com/reference/api/engine).


&nbsp;

An implementation of an *API Endpoint* consists of three parts that are typically
organized in different packages in order to separate concerns:

- `controller` -- with code that handles the endpoint with reading content from
    incoming [*RequestParam*](https://stackoverflow.com/questions/32367501/what-is-the-difference-between-pathparam-and-pathvariable),
    [*PathVariables*](https://stackoverflow.com/questions/32367501/what-is-the-difference-between-pathparam-and-pathvariable) or the
    [*RequestBody*](https://www.baeldung.com/spring-request-response-body)
    with *JSON-data* sent with the HTTP-request.

- `datamodel` -- definition (schema) of the underlying data types including
    derived types used to access objects in databases:
    [*Data Access Objects (DAO)*](https://www.baeldung.com/java-dao-pattern)
    or types defining structures sent over the network:
    [*Data Transfer Objects (DTO)*](https://bell-sw.com/blog/ultimate-guide-to-using-dtos-with-spring-boot/).

- `logic` -- contains the underlying business logic and object storage.


&nbsp;

Create a new branch `demo-customers-ep` off the *"base"* commit.

Fetch code drop:
[se2-repo/endpoints](https://github.com/sgra64/se2-spring-freerider/tree/endpoints)
and checkout files:

```sh
controller:
src/main/java/de/bht_berlin/freerider/controller/CustomerController.java
src/main/java/de/bht_berlin/freerider/controller/ShutdownController.java

datamodel:
src/main/java/de/bht_berlin/freerider/datamodel/Customer.java

logic:
src/main/java/de/bht_berlin/freerider/logic/CustomerStore.java
```

Inspect files:

- `controller` package:

    - [*CustomerController.java*](https://github.com/sgra64/se2-spring-freerider/blob/endpoints/src/main/java/de/bht_berlin/freerider/controller/CustomerController.java)

    - [*ShutdowController.java*](https://github.com/sgra64/se2-spring-freerider/blob/endpoints/src/main/java/de/bht_berlin/freerider/controller/ShutdowController.java)

- `datamodel` package:

    - [*Customer.java*](https://github.com/sgra64/se2-spring-freerider/blob/endpoints/src/main/java/de/bht_berlin/freerider/datamodel/Customer.java)

- `logic` package:

    - [*CustomerStore.java*](https://github.com/sgra64/se2-spring-freerider/blob/endpoints/src/main/java/de/bht_berlin/freerider/logic/CustomerStore.java)


&nbsp;

In order to create the *REST API Endpoint*, the HTTP-Server dependency must be included
in `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
-->


<!-- <dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>3.0.0</version>
</dependency> -->


<!-- 
Furthermore, `application.properties` must include the *HTTP-server* configuration:

```properties
spring.application.name=freerider

# show 'Spring' banner: OFF, CONSOLE, LOG
spring.main.banner-mode=CONSOLE

# configure HTTP-server listening port, shutdown phases
server.port=8080
spring.lifecycle.timeout-per-shutdown-phase=2s

# swagger configuration, disable for production
springdoc.swagger-ui.enabled=true

# report logs: OFF, FATAL, ERROR, WARN, INFO, DEBUG, TRACE
logging.level.root=INFO
```

Source the project such that *CLASSPATH* is updated with the new dependencies:

```sh
source .env/env.sh -f
```

Recompile and start the server:

```sh
mvn compile

# the application will start the HTTP-server with controllers
mvn spring-boot:run --quiet

# alternatively
java de.bht_berlin.freerider.FreeriderApplication
```
```

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/

 :: Spring Boot ::                (v4.0.1)

2026-01-23T00:14:01.299+01:00  INFO 10508 --- [freerider] [           main] d.b.freerider.FreeriderApplication       : Starting FreeriderApplication using Java 21 with PID 10508 (C:\Sven1
\svgr2\workspaces\2-SE\spring-freerider\target\classes started by svgr2 in C:\Sven1\svgr2\workspaces\2-SE\spring-freerider)
2026-01-23T00:14:01.323+01:00  INFO 10508 --- [freerider] [           main] d.b.freerider.FreeriderApplication       : No active profile set, falling back to 1 default profile: "default"
2026-01-23T00:14:04.436+01:00  INFO 10508 --- [freerider] [           main] o.s.boot.tomcat.TomcatWebServer          : Tomcat initialized with port 8080 (http)
2026-01-23T00:14:04.476+01:00  INFO 10508 --- [freerider] [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2026-01-23T00:14:04.477+01:00  INFO 10508 --- [freerider] [           main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/11.0.15]
2026-01-23T00:14:04.629+01:00  INFO 10508 --- [freerider] [           main] b.w.c.s.WebApplicationContextInitializer : Root WebApplicationContext: initialization completed in 3086 ms
2026-01-23T00:14:06.172+01:00  INFO 10508 --- [freerider] [           main] o.s.boot.tomcat.TomcatWebServer          : Tomcat started on port 8080 (http) with context path '/'
2026-01-23T00:14:06.184+01:00  INFO 10508 --- [freerider] [           main] d.b.freerider.FreeriderApplication       : Started FreeriderApplication in 5.971 seconds (process running for 7
.571)
```

The built-in
[Tomcat](https://tomcat.apache.org)
web-server that came with the `spring-boot-starter-web` dependency is running and listening
on TCP-Port: *8080*.

Pointing the web-browser to URL:
[*http://localhost:8080/customers*](http://localhost:8080/customers)
will trigger a GET-Request being sent to the `/customers` controller.
The controller will fetch all customer objects from the *DataStore*
and return as JSON-array:

```
[
    {"id":100, "name":"Meyer", "firstNames":"Eric", "contacts":"eme24@gmail.com"},
    {"id":101, "name":"Blumenfeld", "firstNames":"Anne", "contacts":"+49 030 239531265"},
    {"id":102, "name":"Weimer", "firstNames":"Tim", "contacts":"tim@gmail.com"}
]
```

Next, request objects in the terminal. The
[*curl*](geeksforgeeks.org/linux-unix/curl-command-in-linux-with-examples) -
command

```sh
curl -X GET http://localhost:8080/customers
```
```
[{"id":100, "name":"Meyer", "firstNames":"Eric", "contacts":"eme24@gmail.com"}, {"id":101, "name":"Blumenfeld", "firstNames":"Anne", "contacts":"+49 030 239531265"}, {"id":102, "name":"Weimer", "firstNames":"Tim", "contacts":"tim@gmail.com"}]
```

Try the json pretty printer (if installed) to format the output:

```sh
# try the json pretty printer (if installed)
curl -X GET http://localhost:8080/customers -s | json_pp 2>/dev/null
```
```
[
   {
      "contacts" : "eme24@gmail.com",
      "firstNames" : "Eric",
      "id" : 100,
      "name" : "Meyer"
   },
   {
      "contacts" : "+49 030 239531265",
      "firstNames" : "Anne",
      "id" : 101,
      "name" : "Blumenfeld"
   },
   {
      "contacts" : "tim@gmail.com",
      "firstNames" : "Tim",
      "id" : 102,
      "name" : "Weimer"
   }
]
```


Find a specific *Customer* with the *id: 100*.


```sh
curl -X GET http://localhost:8080/customers/100

# pretty print json
curl -X GET http://localhost:8080/customers/100 -s | json_pp 2>/dev/null
```

Mind that a single *Customer* is returned, not an array (no brackets `[]`):

```
{ "id":100, "name":"Meyer", "firstNames":"Eric", "contacts":"eme24@gmail.com" }
```

Pretty-printed output:

```
{
   "contacts" : "eme24@gmail.com",
   "firstNames" : "Eric",
   "id" : 100,
   "name" : "Meyer"
}
```

What happens when the *Customer* *id* does not exist?


```sh
curl -X GET http://localhost:8080/customers/500 | json_pp 2>/dev/null
```

In this case,
[*Status Code*](https://developer.mozilla.org/de/docs/Web/HTTP/Reference/Status)
404 (not found) is returned.

```
{
   "error" : "Not Found",
   "path" : "/customers/500",
   "status" : 404,
   "timestamp" : "2026-01-23T09:25:05.341Z"
}
```


&nbsp;

Creating a new object via the endpoint means that data needs to be sent to the server
as JSON-data:

```sh
# include JSON-data into the POST-request
curl -X POST -H "Content-Type: application/json" --data \
    \
    '{ "id":104, "name":"Medvedev", "firstNames":"Dmitry", "contacts":"gopnik@gmail.com" }' \
    \
    http://localhost:8080/customers

# read JSON-data from file: 'json/dmitry.json'
curl -X POST -H "Content-Type: application/json" --data @src/main/resources/json/dmitry.json \
        http://localhost:8080/customers

# check the new customer has been added
curl -X GET http://localhost:8080/customers -s | json_pp 2>/dev/null
```
```
[
    { "id":100, "name":"Meyer", "firstNames":"Eric", "contacts":"eme24@gmail.com" },
    { "id":101, "name":"Blumenfeld", "firstNames":"Anne", "contacts":"+49 030 239531265" },
    { "id":102, "name":"Weimer", "firstNames":"Tim", "contacts":"tim@gmail.com" },
    { "id":104, "name":"Medvedev", "firstNames":"Dmitry", "contacts":"gopnik@gmail.com" }   <-- new object
]
```

Repeat the POST-request:

```sh
# read JSON-data from file: 'json/dmitry.json'
curl -X POST -H "Content-Type: application/json" --data @json/dmitry.json \
        http://localhost:8080/customers
```

An error `409` (conflict) is returned since an object with *id: 104* already exists:

```
{ "timestamp":"2026-01-23T00:53:04.063Z", "status":409, "error":"Conflict", "path":"/customers" }
```


&nbsp;

Complete the endpoint with `PUT` and `DELETE` operations, also add a method:

- `void deleteById(long id);`

to class 
[*CustomerStore.java*](https://github.com/sgra64/se2-spring-freerider/blob/endpoints/src/main/java/de/bht_berlin/freerider/logic/CustomerStore.java).

Demonstrate the result:

```sh
# show all customers
curl -X GET http://localhost:8080/customers

# remove customer with id: 101 and show again
curl -X DELETE http://localhost:8080/customers/101
curl -X GET    http://localhost:8080/customers


# update contact for customer id: 102
curl -X PUT -H "Content-Type: application/json" --data \
    \
    '{ "id":102, "name":"Weimer", "firstNames":"Tim", "contacts":"+49 030 9399488" }' \
    \
    http://localhost:8080/customers

# show all customers
curl -X GET http://localhost:8080/customers -s | json_pp 2>/dev/null
```

Customer with *id: 101* is gone and the contact for Customer *id: 102* has been
updated:

```
[
    { "id":100, "name":"Meyer", "firstNames":"Eric", "contacts":"eme24@gmail.com"},
    { "id":102, "name":"Weimer", "firstNames":"Tim", "contacts":"+49 030 9399488"}
]                                                                ^^^^^^^^^^^^^^^ -- updated
```


&nbsp;

When done, commit changes to branch *demo-freerider-jdbc* with
- commit message: `"/customers endpoint"`.
-->


<!-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -->
<!-- 
&nbsp;

## 9. *OpenAPI* and *Swagger-UI*

[*OpenAPI*](https://www.openapis.org)
is a developing standard for the specification of *HTTP* application programming interfaces:

<img src="https://www.openapis.org/wp-content/uploads/sites/31/2023/05/What-is-OpenAPI-Simple-API-Lifecycle-Vertical.png" width="240"/>


&nbsp;

[*Swagger*](https://swagger.io/specification) is a toolset supporting the development and
maintanance of *HTTP* application programming interfaces.
[*OpenAPI Description (OAD)*](https://swagger.io/specification) describes the surface of an API
and its semantics. It is composed of an entry document and referenced documents.

*Swagger-UI* (right) is a prominent tool to visualize endpoint definitions from a formal
endpoint specification called *OpenAPI document* shown in *YAML*-format to the left:

<img src="https://raw.githubusercontent.com/sgra64/se2-spring-freerider/refs/heads/markup/img/open-api-2.png" width="1000"/>

Refer to folder:

- `src/main/resources/api-docs`:

    - [*api-docs.json*](https://github.com/sgra64/se2-spring-freerider/blob/endpoints/src/main/resources/api-docs/api-docs.json),

    - [*api-docs.yaml*](https://github.com/sgra64/se2-spring-freerider/blob/endpoints/src/main/resources/api-docs/api-docs.yaml).


&nbsp;

In order to enable *OpenAPI* and *Swagger*, the following dependency must be included
in `pom.xml`:

```xml
<groupId>org.springdoc</groupId>
<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
<version>3.0.0</version>
```

Re-source the project to update *CLASSPATH* with the new dependency:

```sh
source .env/env.sh -f
```

Recompile and start the server:

```sh
mvn compile

# the application will start the HTTP-server with controllers
mvn spring-boot:run --quiet

# alternatively
java de.bht_berlin.freerider.FreeriderApplication
```

Point a browser to URLs in order to see the *OpenAPI* specification for all endpoints
supported by this HTTP-server and the *Swagger-UI*:

- *OpenAPI* specification:
    [*http://localhost:8080/v3/api-docs*](http://localhost:8080/v3/api-docs)

- *Swagger-UI*:
    [*http://localhost:8080/swagger-ui/index.html*](http://localhost:8080/swagger-ui/index.html)

- Open the hosted *Swagger-Editor* to develop *OpenAPI* specifications:
    [*https://editor.swagger.io*](https://editor.swagger.io)


&nbsp;

*Swagger-UI* is now active for the endpoint. It is an active UI, which means HTTP-requests
can be issued from the Web-browser and results inspected:

<img src="https://raw.githubusercontent.com/sgra64/se2-spring-freerider/refs/heads/markup/img/open-api-1.png" width="1000"/>


&nbsp;

Show all customers by opening the `GET /customers` view (triangle to the right).
Click `Try it out` and then execute:


<img src="https://raw.githubusercontent.com/sgra64/se2-spring-freerider/refs/heads/markup/img/open-api-3.png" width="1000"/>

Update the contact for *Anne Blumenfeld* to `+49 030 84850040` and delete *Tim Weimer*
using the *Swagger UI*.


&nbsp;

When done, commit changes to branch *demo-freerider-jdbc* with the
- commit message: `"OpenAPI and Swagger-UI for /customers endpoint"`.
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
