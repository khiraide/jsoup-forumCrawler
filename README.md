jsoup-forumCrawler
=====================

Program used to crawl discussion forums using the Jsoup API. To install this package:

1. Install Maven
----------------

Start by following the [directions on installing Maven](http://maven.apache.org/download.cgi).

Be sure to run mvn --version to verify that it is correctly installed.  This package has been tested using Maven 3.0.4.

2. Download this jsoup-forumCrawler package
----------------------------------------------

For those who do not know about git, the easiest way is to click the "ZIP" button at the top of this page, which will download the latest version of this repository as a .zip file. 

For those who know about git, you will want to clone it. 

4. Install jsoup jar files into your local Maven repository
--------------------------------------------------------------

Jsoup binaries are not provided as part of public Maven repositories, so the next step is to install its jar files needed for compilation and testing into your local repository.   You accomplish this by changing directory to the directory containing the jsoup jars and executing the following commands:

```
ToDo
```

A typical output from one of these commands might be:

```
[~/robocode/libs]-> mvn install:install-file -Dfile=jsoup.jar -DartifactId=jsoup -DgroupId=net.sourceforge.jsoup -Dversion=1.7.4.4 -Dpackaging=jar -DgeneratePom=true
[INFO] Scanning for projects...
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building Maven Stub Project (No POM) 1
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-install-plugin:2.3.1:install-file (default-cli) @ standalone-pom ---
[INFO] Installing /Users/keone/jsoup/libs/robocode.jar to /Users/keone/.m2/repository/net/sourceforge/jsoup/jsoup/jsoup.jar
[INFO] Installing /var/folders/__/qq1ydtj56n3fxtccjh9k6dk80000gn/T/mvninstall1027288217865601684.pom to /Users/keone/.m2/repository/net/sourceforge/jsoup.pom
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 0.454s
[INFO] Finished at: Mon Jan 28 14:28:31 HST 2013
[INFO] Final Memory: 7M/309M
[INFO] ------------------------------------------------------------------------
[~/robocode/libs]-> 
```
5.  Build and test the system
-----------------------------

Now that everything is installed, build and test the system. You use the standard Maven 'test' target.  

Here is an example of the command line used to build and test the system, along with the output.

```shell
[INFO] Scanning for projects...
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building crawler 1.0-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- jacoco-maven-plugin:0.6.2.201302030002:prepare-agent (prepare-agent) @ crawler ---
[INFO] argLine set to -javaagent:/home/tatsujin/.m2/repository/org/jacoco/org.jacoco.agent/0.6.2.201302030002/org.jacoco.agent-0.6.2.201302030002-runtime.jar=destfile=/home/tatsujin/Documents/school_N_work/spring_2013/ICS_669/finalProject/crawler/target/jacoco.exec
[INFO] 
[INFO] --- maven-checkstyle-plugin:2.9.1:check (default) @ crawler ---
[INFO] Starting audit...
Audit done.

[INFO] 
[INFO] --- maven-resources-plugin:2.3:resources (default-resources) @ crawler ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory /home/tatsujin/Documents/school_N_work/spring_2013/ICS_669/finalProject/crawler/src/main/resources
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ crawler ---
[INFO] Compiling 1 source file to /home/tatsujin/Documents/school_N_work/spring_2013/ICS_669/finalProject/crawler/target/classes
[INFO] 
[INFO] --- maven-resources-plugin:2.3:testResources (default-testResources) @ crawler ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory /home/tatsujin/Documents/school_N_work/spring_2013/ICS_669/finalProject/crawler/src/test/resources
[INFO] 
[INFO] --- maven-compiler-plugin:2.3.2:testCompile (default-testCompile) @ crawler ---
[INFO] Nothing to compile - all classes are up to date
[INFO] 
[INFO] --- maven-surefire-plugin:2.10:test (default-test) @ crawler ---
[INFO] Surefire report directory: /home/tatsujin/Documents/school_N_work/spring_2013/ICS_669/finalProject/crawler/target/surefire-reports

-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running hawaii.edu.crawler.TestCrawler
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.428 sec

Results :

Tests run: 1, Failures: 0, Errors: 0, Skipped: 0

[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 11.659s
[INFO] Finished at: Wed Apr 10 11:52:12 HST 2013
[INFO] Final Memory: 13M/33M
[INFO] ------------------------------------------------------------------------
```
6.  Install jsoup-forumCrawler into Eclipse
----------------------------------------------

Now that the system is running from the command line, you'll want to also run it from Eclipse.  To do so, bring up Eclipse, and select File | Import | Maven | Existing Maven Projects, and then complete the dialog boxes to import your project.  Eclipse will read the POM file in order to determine the libraries to include on the build path.  




