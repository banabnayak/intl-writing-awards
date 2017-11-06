intl-writing-awards
===================
Scholastic Writing Awards setup in unix/linux based system
--------------------------------------------------------------

intl-writing-awards(Parent Module)
==============================================================
Sub modules
writing-awards
writing-awards-db

==============================================================
Technologies: JPA, EJB, JPA, JAX-RS, RESTEASY, VELOCITY

UI : AngularJS, CSS, HTML, JQUERY
DB : MySql
Target Product: WAR
Source: https://github.com/ScholasticInc/intl-writing-awards.git


System requirements
--------------------------------------------------------------

All we need to build this project is:

Java 7.0 (Java SDK 1.7)
Maven 3.0  
MySql 5.1.26.

The application this project produces is designed to be run on JBoss Enterprise Application JBoss AS 7.1 Final.


Configure JDK 1.7
--------------------------------------------------------------
1) Download jdk 1.7 package from oracle site
2) Extract in a folder(like jdk7 as JAVA_HOME)
3) Set bin(JAVA_HOME/bin) folder in system path
4) Run command java -version to check proper installation


Configure Maven
-------------------------------------------------------------
1) Download maven 3.0 package from maven site
2) Extract in a folder(like maven3 as MVN_HOME)
3) Set bin(MVN_HOME/bin) folder in system path
4) Run command mvn -version to check proper installation


Configure JBoss AS 7.1 Final
--------------------------------------------------------------
1) Download JBoss AS 7.1 Final package from jboss site
2) Extract in a folder(like jboss7 as JBOSS_HOME)
3) Drop awards-ds.xml and suitable mysql connector for mysql 5.1.16 in JBOSS_HOME/standalone/deployments folder (available in db-details Folder in GitHub)


Configure MySql 5.5.x
--------------------------------------------------------------
1) Download and install MySql 5.5.x package from MySQL site

Folder Structure under awards module
--------------------------------------------------------------
Top folder intl-writing-awards/awards
src/main/webapp  
Web application folder
src/main/java  Java Source Folder
src/main/resources  

All the resource releated to the project are residing in this folder
target  folder contains all the compiled class


Build and Deploy
--------------------------------------------------------------

1) Clone from https://github.com/ScholasticInc/intl-writing-awards.git to a folder(like swa is the SRC_DIR)
(git clone https://github.com/ScholasticInc/intl-writing-awards.git)
2) Navigate to SRC_DIR/intl-writing-awards and checkout dev branch(git checkout dev)
3) Run the command mvn clean package from SRC_DIR/intl-writing-awards
4) The war(awards.war) file will be available under SRC_DIR/intl-writing-awards/awards/target
5) Copy SRC_DIR/intl-writing-awards/writing-awards/target/writingawards.war to JBOSS_HOME/standalone/deployments/


Start JBoss Enterprise Application Platform 6 or JBoss AS 7 with the Web Profile
--------------------------------------------------------------
1. Open a command line and navigate to the root of the JBoss server directory.
2. The following shows the command line to start the server with the web profile:
      

For Linux:   JBOSS_HOME/bin/standalone.sh

For Windows: JBOSS_HOME\bin\standalone.bat



Access the application
--------------------------------------------------------------

The application will be running at the following URL: 

http://<server ip>:8080/awards/



Running Database Migrations
-------------------------------------------------------------------------------
Go to the folder 

intl-writing-awards/writing-awards-db

See what migrations have already been applied

mvn migration:status


Apply all of the migration scripts to the database

     mvn migration:up

Un-apply the last applied script

     mvn migration:down -Dmigration.down.steps=1


Create a new migration file. Make sure you put both an up and down SQL script in it.

     mvn migration:new -Dmigration.description="new table creation
