
Spring + Spring MVC + Hibernate + JPA demo
--------------------------------------------------------------------------------

1. mvn archetype:generate -DarchetypeArtifactId=maven-archetype-webapp -DarchetypeVersion=1.0 -DarchetypeGroupId=org.apache.maven.archetypes

2. Jetty plugin added.

3. Libraries Used:

HSQLDB
Spring
Spring MVC
Spring ORM
Hibernate
JPA - Persistence
JTA - Transactions
JSTL
Servlet API

JUNIT
Spring Test

4. Approaches used:

JPA-annotated beans.
Spring Test-driven, annotated JUNIT tests.
Spring *-context.xml configuration files in resources.
Layered architecture: Dao and Service layer.
Annotated transactions are introduced in service layer.
